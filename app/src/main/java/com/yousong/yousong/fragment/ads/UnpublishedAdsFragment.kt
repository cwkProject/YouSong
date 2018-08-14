package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.adapter.MyAdsAdapter
import com.yousong.yousong.architecture.viewmodel.MyAdsViewModel
import com.yousong.yousong.common.plusAssign

/**
 * 未发布的广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class UnpublishedAdsFragment : BaseAdsListFragment() {

    override val adapter = MyAdsAdapter()

    /**
     * 广告数据模型
     */
    private val adsViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MyAdsViewModel::class.java)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        adsViewModel.unpublishedAds
                .observe(this, Observer {
                    it?.let {
                        adapter.clear()
                        adapter += it
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