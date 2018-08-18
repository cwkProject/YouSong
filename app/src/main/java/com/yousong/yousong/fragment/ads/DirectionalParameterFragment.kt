package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.architecture.viewmodel.CreateAdsViewModel
import com.yousong.yousong.architecture.viewmodel.DirectionalViewModel
import com.yousong.yousong.databinding.FragmentDirectionalParameterBinding
import com.yousong.yousong.fragment.common.BaseFragment

/**
 * 定向型广告用户筛选参数
 *
 * @author 超悟空
 * @version 1.0 2018/7/12
 * @since 1.0
 */
class DirectionalParameterFragment : BaseFragment() {

    /**
     * 绑定器
     */
    private val binding by lazy {
        FragmentDirectionalParameterBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val adsDetailViewModel by lazy {
        ViewModelProviders.of(activity!!).get(CreateAdsViewModel::class.java)
    }

    /**
     * 可选年龄范围数据模型
     */
    private val directionalViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DirectionalViewModel::class.java)
    }

    override val rootViewId = R.layout.fragment_directional_parameter

    override fun onInitView(savedInstanceState: Bundle?) {
        directionalViewModel.directional = adsDetailViewModel.adsDetail.directional
        binding.adsDetail = adsDetailViewModel.adsDetail
        binding.viewModel = directionalViewModel
    }
}