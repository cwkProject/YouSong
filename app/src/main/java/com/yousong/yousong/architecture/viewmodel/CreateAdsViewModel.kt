package com.yousong.yousong.architecture.viewmodel

import android.content.Context
import android.databinding.Bindable
import android.graphics.Bitmap
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.model.local.Option
import com.yousong.yousong.third.GlideApp
import com.yousong.yousong.util.FileUtil
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.ads.AdsCreateWork
import com.yousong.yousong.work.common.FileUploadWork
import com.yousong.yousong.work.common.start
import org.cwk.android.library.global.Global
import org.jetbrains.anko.toast
import java.io.File
import kotlin.concurrent.thread

/**
 * 发布广告数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
class CreateAdsViewModel : ObservableViewModel() {

    /**
     * 正文宽度
     */
    private val POSTER_WIDTH = 640

    /**
     * 广告详情
     */
    val adsDetail = AdsDetail().apply {
        question.option.add(Option(1).apply { answer = true })
        question.option.add(Option(2))
    }

    /**
     * 海报的图片路径
     */
    @Bindable
    var posterPath: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.posterPath)
        }

    /**
     * 缩略图的图片路径
     */
    @Bindable
    var coverPath: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.coverPath)
        }

    /**
     * 缩略图上传进度
     */
    @Bindable
    var coverProgress = ValueConst.PROGRESS_IDLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.coverProgress)
        }

    /**
     * 海报上传进度
     */
    @Bindable
    var posterProgress = ValueConst.PROGRESS_IDLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.posterProgress)
        }

    /**
     * 存放缩略图临时图片路径
     */
    var coverTempPath: String? = null

    /**
     * 提交审核
     *
     * @param context 用于弹出提示
     * @param call 请求执行回调，仅在参数校验通过后才会被执行
     */
    fun submit(context: Context, call: (Boolean) -> Unit) {
        if (!checkAds(context)) {
            return
        }

        AdsCreateWork().start(adsDetail) {
            call(it.isSuccess)
        }
    }

    /**
     * 提交前校验广告参数
     *
     * @param context 用于弹出提示
     *
     * @return true表示通过
     */
    private fun checkAds(context: Context): Boolean {
        var resId: Int? = null

        adsDetail.ads.apply {
            resId = when {
                name.isBlank() -> R.string.hint_name_not_null
                cover.isBlank() -> R.string.hint_cover_not_null
                poster.isBlank() -> R.string.hint_poster_not_null
                cityCode.isBlank() -> R.string.hint_not_location
                else -> null
            }
        }

        if (resId == null) {
            adsDetail.question.apply {
                resId = when {
                    content.isBlank() -> R.string.hint_question_not_null
                    option.any { it.content.isBlank() } -> R.string.hint_option_not_null
                    else -> null
                }
            }
        }

        return if (resId != null) {
            context.toast(resId!!)
            false
        } else {
            true
        }
    }

    /**
     * 封面图片选择完成
     */
    fun coverSelected() {
        coverPath = coverTempPath

        FileUploadWork()
                .setOnNetworkProgressListener { current, total, _ -> coverProgress = (current / total * 100).toInt() }
                .start(File(coverPath)) {
                    if (it.isSuccess) {
                        adsDetail.ads.cover = it.result
                        coverProgress = ValueConst.PROGRESS_SUCCESS
                    } else {
                        coverProgress = ValueConst.PROGRESS_FAILED
                    }
                }
    }

    /**
     * 海报图片选择完成
     *
     * @param path 图片路径
     */
    fun posterSelected(path: String) {
        posterPath = path

        thread {
            posterProgress = 0

            try {
                val bitmap = GlideApp.with(Global.getApplication())
                        .asBitmap()
                        .load(File(path))
                        .skipMemoryCache(true)
                        .submit(POSTER_WIDTH, POSTER_WIDTH)
                        .get()

                if (bitmap != null) {

                    val file = File(FileUtil.newTempJpegPath())

                    file.outputStream().use {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    }

                    posterPath = file.path
                    FileUploadWork()
                            .setOnNetworkProgressListener { current, total, _ -> posterProgress = (current / total * 100).toInt() }
                            .start(file) {
                                if (it.isSuccess) {
                                    adsDetail.ads.poster = it.result
                                    posterProgress = ValueConst.PROGRESS_SUCCESS
                                } else {
                                    posterProgress = ValueConst.PROGRESS_FAILED
                                }
                            }
                }

            } catch (e: Exception) {
                posterProgress = ValueConst.PROGRESS_FAILED
            }
        }
    }
}