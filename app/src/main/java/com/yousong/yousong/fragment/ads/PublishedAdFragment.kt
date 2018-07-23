package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.architecture.viewmodel.AdsViewModel
import com.yousong.yousong.common.plusAssign

/**
 * 已发布的广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class PublishedAdFragment : BaseAdListFragment(){

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
    }

    override fun onInitListAction() {

    }

    override fun loadAds(first: Boolean) {
        adsViewModel.loadAds(!first)
    }
}