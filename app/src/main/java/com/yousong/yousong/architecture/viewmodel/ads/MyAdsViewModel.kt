package com.yousong.yousong.architecture.viewmodel.ads

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.ads.AdsPublishWork
import com.yousong.yousong.work.ads.AdsPullMyAdsListWork
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
     * 提交结果
     */
    val submitResult = SubmitResultLiveData()

    /**
     * 已发布的广告
     */
    val publishedAds = MutableLiveData<List<Ads>>()

    /**
     * 未发布的广告
     */
    val unpublishedAds = MutableLiveData<List<Ads>>()

    init {
        loadAds()
    }

    /**
     * 加载广告
     */
    fun loadAds() {
        AdsPullMyAdsListWork().start {
            val result = if (it.isSuccess) {
                it.result.partition { it.publishState != ValueConst.PUBLISH_UNPUBLISH }
            } else {
                Pair(null, null)
            }

            publishedAds.value = result.first
            unpublishedAds.value = result.second
        }
    }

    /**
     * 发布广告
     *
     * @param ads 需要发布的广告
     */
    fun publish(ads: Ads) {
        AdsPublishWork().start(ads.id) {
            if (it.isSuccess) {
                loadAds()
            }

            submitResult.value = SubmitResult(it.isSuccess, it.message, SubmitResult.LEVEL_ALERT_WITH_OK)
        }
    }
}