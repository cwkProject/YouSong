package com.yousong.yousong.activity.ads

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.ads.AdsDetailViewModel
import com.yousong.yousong.databinding.ActivityAdsDetailBinding
import com.yousong.yousong.value.ValueTag
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 广告详情页
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class AdsDetailActivity : BaseActivity() {

    /**
     * 数据模型
     */
    private val viewModel by viewModels<AdsDetailViewModel>()

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityAdsDetailBinding.bind(rootView)
    }

    override val rootViewId = R.layout.activity_ads_detail

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.empty)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.adsDetail.observe(this, Observer { title = it?.ads?.name })
        viewModel.submitResult.observe(this, Observer { it?.show(this) })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        viewModel.loadAds(intent.getStringExtra(ValueTag.TAG_ADS_ID))
    }
}