package com.yousong.yousong.fragment.ads

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.yousong.yousong.adapter.AdsAdapter
import com.yousong.yousong.architecture.viewmodel.ads.MyAdsViewModel
import com.yousong.yousong.common.plusAssign

/**
 * 已发布的广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class PublishedAdsFragment : BaseAdsListFragment() {

    override val adapter = AdsAdapter()

    /**
     * 广告数据模型
     */
    private val adsViewModel by activityViewModels<MyAdsViewModel>()

    override fun onInitData(savedInstanceState: Bundle?) {
        adsViewModel.publishedAds.observe(this, Observer {
            it?.let {
                adapter.adsList.clear()
                adapter.adsList += it
            }
            stopRefresh()
        })
    }

    override fun onInitListAction() {

    }

    override fun loadAds() {
        adsViewModel.loadAds()
    }
}