package com.yousong.yousong.architecture.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.model.local.MyAds
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.ads.AdsGetMyAdsListWork
import com.yousong.yousong.work.common.start

/**
 * 我发布的广告数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 */
class MyAdsViewModel : ViewModel() {

    /**
     * 已发布的广告
     */
    val publishedAds = MutableLiveData<List<Ads>>()

    /**
     * 未发布的广告
     */
    val unpublishedAds = MutableLiveData<List<MyAds>>()

    /**
     * 是否首次加载
     */
    private var isFirst = true

    /**
     * 加载广告
     *
     * @param first 是否首次刷新
     */
    fun loadAds(first: Boolean = false) {
        if (first && !isFirst) {
            return
        }

        isFirst = false

        AdsGetMyAdsListWork().start {
            val result = if (it.isSuccess) {
                it.result.partition { it.reviewState == ValueConst.PASS }
            } else {
                Pair(null, null)
            }

            publishedAds.value = result.first?.map { it.ads }
            unpublishedAds.value = result.second
        }
    }
}