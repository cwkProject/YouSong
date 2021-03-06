package com.yousong.yousong.fragment.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.yousong.yousong.activity.ads.AdsDetailActivity
import com.yousong.yousong.adapter.AdsAdapter
import com.yousong.yousong.architecture.viewmodel.ads.AdsViewModel
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.fragment.ads.BaseAdsListFragment
import com.yousong.yousong.model.local.Ads
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

    override val adapter = AdsAdapter()

    /**
     * 广告数据模型
     */
    private val adsViewModel by activityViewModels<AdsViewModel>()

    override fun onInitData(savedInstanceState: Bundle?) {
        adsViewModel.adsListData.observe(this, Observer { onAdsRefresh(it) })
    }

    /**
     * 广告刷新
     *
     * @param adsList 广告数据集合
     */
    private fun onAdsRefresh(adsList: List<Ads>?) {
        adsList?.let {
            adapter.beginTransaction()
            adapter.adsList.clear()
            adapter.adsList += it
            adapter.commit()
        }
        stopRefresh()
    }

    override fun onInitListAction() {
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
}