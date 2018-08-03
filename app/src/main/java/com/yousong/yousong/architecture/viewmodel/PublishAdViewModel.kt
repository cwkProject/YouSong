package com.yousong.yousong.architecture.viewmodel

import android.arch.lifecycle.ViewModel
import com.yousong.yousong.model.local.AdDetail

/**
 * 发布广告数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
class PublishAdViewModel : ViewModel() {

    /**
     * 广告详情
     */
    val adDetail = AdDetail()
}