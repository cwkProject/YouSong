package com.yousong.yousong.fragment.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.yousong.yousong.activity.ads.AdsDetailActivity
import com.yousong.yousong.architecture.viewmodel.AdsViewModel
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.fragment.ads.BaseAdsListFragment
import com.yousong.yousong.value.ValueTag
import org.jetbrains.anko.support.v4.startActivity

/**
 * 广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class AdsFragment : BaseAdsListFragment() {

    /**
     * 广告数据模型
     */
    private val adsViewModel by lazy {
        ViewModelProviders.of(activity!!).get(AdsViewModel::class.java)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        adsViewModel.adsListData
                .observe(this, Observer {
                    it?.let {
                        adapter.beginTransaction()
                        adapter.topList.clear()
                        adapter.adsList.clear()
                        adapter.topList += it.first
                        adapter.adsList += it.second
                        adapter.commit()
                    }
                    stopRefresh()
                })
    }

    override fun onInitListAction() {
        adapter.bannerClickListener = { ads, position ->
            Log.v(TAG, "position:$position")
        }

        adapter.adsList.setOnItemClickListener { _, dataSource, _ ->
            startActivity<AdsDetailActivity>(ValueTag.TAG_ADS_ID to dataSource.id)
        }
    }

    /**
     * 加载普通广告
     */
    override fun loadAds() {
        adsViewModel.loadAds()
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