package com.yousong.yousong.fragment.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.AdDetailActivity
import com.yousong.yousong.architecture.viewmodel.AdsViewModel
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.fragment.ads.BaseAdListFragment
import com.yousong.yousong.model.server.BannerAds
import org.jetbrains.anko.support.v4.startActivity

/**
 * 广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class AdFragment : BaseAdListFragment() {

    /**
     * 广告数据模型
     */
    private val adsViewModel by lazy {
        ViewModelProviders.of(activity!!).get(AdsViewModel::class.java)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        adsViewModel.adListData
                .observe({ lifecycle }) {
                    it?.let {
                        adapter.adsList.clear()
                        adapter.adsList += it
                    }
                    stopRefresh()
                }

        super.onInitData(savedInstanceState)
        loadTopAds()
    }

    override fun onInitListAction() {
        adapter.bannerClickListener = { ads, position ->
            Log.v(TAG, "position:$position")
        }

        adapter.adsList.setOnItemClickListener { holder, dataSource, position ->
            startActivity<AdDetailActivity>()
        }
    }

    /**
     * 加载顶部轮播广告数据
     */
    private fun loadTopAds() {
        adapter.topList += BannerAds(listOf(R.mipmap.ad01, R.mipmap.ad02, R.mipmap.ad03))
    }

    /**
     * 加载普通广告
     */
    override fun loadAds(first: Boolean) {
        adsViewModel.loadAds(!first)
    }

    override fun onResume() {
        super.onResume()
        adapter.playState = true
    }

    override fun onPause() {
        super.onPause()
        adapter.playState = false
    }
}