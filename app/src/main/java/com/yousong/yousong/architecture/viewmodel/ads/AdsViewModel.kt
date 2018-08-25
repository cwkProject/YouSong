package com.yousong.yousong.architecture.viewmodel.ads

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.model.server.BannerAds
import com.yousong.yousong.work.ads.AdsGetBannerWork
import com.yousong.yousong.work.ads.AdsPullListWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables

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
    val adsListData = MutableLiveData<Pair<BannerAds?, List<Ads>?>>()

    init {
        loadAds()
    }

    /**
     * 加载广告
     */
    fun loadAds() {
        Observables.zip(AdsGetBannerWork().observable(), AdsPullListWork().observable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (banner, ads) ->
                    val bannerData = if (banner.isSuccess) {
                        banner.result
                    } else {
                        adsListData.value?.first
                    }

                    val adsData = if (ads.isSuccess) {
                        ads.result
                    } else {
                        adsListData.value?.second
                    }

                    adsListData.value = Pair(bannerData, adsData)
                }
    }
}