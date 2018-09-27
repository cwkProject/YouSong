package com.yousong.yousong.architecture.viewmodel.ads

import android.content.Context
import android.databinding.Bindable
import android.graphics.Bitmap
import android.text.Editable
import android.view.View
import android.widget.RadioGroup
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.architecture.viewmodel.common.ObservableViewModel
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.model.local.Option
import com.yousong.yousong.third.GlideApp
import com.yousong.yousong.util.FileUtil
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.ads.AdsCreateWork
import com.yousong.yousong.work.ads.AdsReviewWork
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import com.yousong.yousong.work.common.FileUploadWork
import com.yousong.yousong.work.common.start
import org.cwk.android.library.global.Global
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import java.io.File
import java.math.BigDecimal
import kotlin.concurrent.thread
import kotlin.math.abs

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
     * 提交结果
     */
    val submitResult = SubmitResultLiveData()

    /**
     * 广告详情
     */
    var adsDetail = AdsDetail().apply {
        question.option.add(Option(1).apply { answer = true })
        question.option.add(Option(2))
    }
        set(value) {
            field = value

            if (value.ads.cover != null) {
                coverProgress = ValueConst.PROGRESS_SUCCESS
            }

            if (value.ads.poster != null) {
                posterProgress = ValueConst.PROGRESS_SUCCESS
            }
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
    private var coverTempPath: String? = null

    /**
     * 提交审核
     */
    fun onSubmit(view: View) {
        if (!checkAds(view.context)) {
            return
        }

        val dialog = view.context.indeterminateProgressDialog(R.string.prompt_submitting) {
            setCancelable(false)
        }

        val work: BaseSimpleWorkModel<AdsDetail, Unit> = if (adsDetail.ads.reviewState == ValueConst.REVIEW_REFUSE) AdsReviewWork() else AdsCreateWork()

        work.start(adsDetail) {
            dialog.cancel()

            submitResult.value = SubmitResult(it.isSuccess, it.message, if (it.isSuccess)
                SubmitResult.LEVEL_ALERT_FINISH else SubmitResult.LEVEL_LONG_TOAST)
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

        // 广告内容校验
        adsDetail.ads.apply {
            resId = when {
                name.isBlank() -> R.string.hint_name_not_null
                cover.isNullOrBlank() -> R.string.hint_cover_not_null
                poster.isNullOrBlank() -> R.string.hint_poster_not_null
                else -> null
            }
        }

        // 问题选项校验
        if (resId == null) {
            adsDetail.question.apply {
                resId = when {
                    content.isBlank() -> R.string.hint_question_not_null
                    option.any { it.content.isBlank() } -> R.string.hint_option_not_null
                    else -> null
                }
            }
        }

        // 定向数据校验
        if (resId == null && !adsDetail.ads.type) {
            when (adsDetail.directional.locationType) {
                ValueConst.LOCATION_TYPE_LOCAL_REGION -> {
                    adsDetail.directional.apply {
                        resId = when {
                            abs(latitude) < 10E-6 && abs(longitude) < 10E-6 -> R.string.hint_local_range_not_location
                            range?.takeIf { it > 0 } == null -> R.string.hint_local_range_not_null
                            else -> null
                        }
                    }
                }
                ValueConst.LOCATION_TYPE_TARGET_CITY -> {
                    adsDetail.directional.apply {
                        resId = when {
                            addresses.isEmpty() -> R.string.hint_target_city_not_null
                            addresses.any { it.addressCode == null } -> R.string.hint_target_city_has_empty
                            else -> null
                        }
                    }
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
     * 创建临时图片路径
     *
     * @return 生成的路径
     */
    fun createImagePath(): String {
        return FileUtil.newTempJpegPath().apply {
            coverTempPath = this
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

    /**
     * 添加问题选项点击事件
     */
    fun onAddOptionClick(view: View) {
        adsDetail.question.option.apply {
            if (size < 4) {
                add(Option(size + 1))
            }
        }
    }

    /**
     * 移除问题选项点击事件
     */
    fun onRemoveOptionClick(view: View) {
        adsDetail.question.option.apply {
            if (size > 2) {
                removeAt(size - 1).takeIf { it.answer }?.let {
                    this[0].answer = true
                }
            }
        }
    }

    /**
     * 每位用户所得金额输入变更
     */
    fun onMoneyChanged(edt: Editable) {
        val posDot = edt.indexOf('.')

        // 最小值1，保留两位小数
        when {
            edt.isEmpty() || posDot == 0 || edt.startsWith('0') -> edt.replace(0, edt.length, "1")
            posDot == edt.length - 1 -> return
            posDot > 0 && edt.length - posDot - 1 > 2 -> edt.delete(posDot + 3, edt.length)
        }

        adsDetail.ads.userUnitPrice = BigDecimal(edt.toString())
    }

    /**
     * 目标人数变化
     */
    fun onTargetCountChanged(edt: Editable) {
        // 最小值1
        if (edt.isEmpty() || edt.startsWith('0')) {
            edt.replace(0, edt.length, "1")
        }

        adsDetail.ads.targetCount = edt.toString().toInt()
    }

    /**
     * 重试次数选中事件
     */
    fun onRetryCheckedChanged(group: RadioGroup, checkedId: Int) {
        adsDetail.question.retries = when (checkedId) {
            R.id.retry_zero -> 0
            R.id.retry_once -> 1
            R.id.retry_twice -> 2
            else -> 0
        }
    }
}