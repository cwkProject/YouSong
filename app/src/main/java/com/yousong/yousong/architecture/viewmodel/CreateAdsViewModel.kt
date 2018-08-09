package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.model.local.Option
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.ads.AdsCreateWork
import com.yousong.yousong.work.common.FileUploadWork
import com.yousong.yousong.work.common.start
import java.io.File

/**
 * 发布广告数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
class CreateAdsViewModel : ObservableViewModel() {

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
     */
    fun submit() {
        AdsCreateWork().start(adsDetail) {

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
        FileUploadWork()
                .setOnNetworkProgressListener { current, total, _ -> posterProgress = (current / total * 100).toInt() }
                .start(File(path)) {
                    if (it.isSuccess) {
                        adsDetail.ads.poster = it.result
                        posterProgress = ValueConst.PROGRESS_SUCCESS
                    } else {
                        posterProgress = ValueConst.PROGRESS_FAILED
                    }
                }
    }
}