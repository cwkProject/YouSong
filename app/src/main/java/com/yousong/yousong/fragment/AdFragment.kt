package com.yousong.yousong.fragment

import android.os.Bundle
import android.util.Log
import com.yousong.yousong.R
import com.yousong.yousong.activity.AdDetailActivity
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.model.BannerAds
import org.jetbrains.anko.support.v4.startActivity

/**
 * 广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class AdFragment : BaseAdListFragment() {

    override fun onInitData(savedInstanceState: Bundle?) {
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
    override fun loadAds() {
        adapter.adsList += (1..100).toList()
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