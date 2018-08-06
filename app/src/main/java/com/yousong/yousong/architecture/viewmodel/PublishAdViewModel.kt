package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.model.local.AdDetail
import com.yousong.yousong.model.local.Option

/**
 * 发布广告数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
class PublishAdViewModel : ObservableViewModel() {

    /**
     * 广告详情
     */
    val adDetail = AdDetail().apply {
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
     * 存放缩略图临时图片路径
     */
    var coverTempPath: String? = null

    /**
     * 封面图片选择完成
     */
    fun coverSelected() {
        coverPath = coverTempPath
    }

    /**
     * 海报图片选择完成
     *
     * @param path 图片路径
     */
    fun posterSelected(path: String) {
        posterPath = path
    }
}