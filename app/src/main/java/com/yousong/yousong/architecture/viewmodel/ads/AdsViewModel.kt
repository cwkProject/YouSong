package com.yousong.yousong.architecture.viewmodel.ads

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.model.server.BannerAds
import com.yousong.yousong.work.ads.AdsGetBannerWork
import com.yousong.yousong.work.ads.AdsPullListWork
import com.yousong.yousong.work.common.start

/**
 * 主界面广告数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/7/19
 * @since 1.0
 */
class AdsViewModel : ViewModel() {

    /**
     * 首页广告列表
     */
    val adsListData = MutableLiveData<List<Ads>>()

    /**
     * 轮播图
     */
    val bannerAdsData = MutableLiveData<BannerAds>()

    init {
        loadAds()
        loadBanner()
    }

    /**
     * 加载广告
     */
    fun loadAds() {
        AdsPullListWork().start {
            if (it.isSuccess) {
                adsListData.value = it.result
            }
        }
    }

    /**
     * 加载轮播图
     */
    private fun loadBanner() {
        AdsGetBannerWork().start(retry = 3) {
            if (it.isSuccess) {
                bannerAdsData.value = it.result
            }
        }
    }
}