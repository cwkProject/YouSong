package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.adapter.AddressAdapter
import com.yousong.yousong.architecture.viewmodel.CreateAdsViewModel
import com.yousong.yousong.architecture.viewmodel.DirectionalViewModel
import com.yousong.yousong.common.removeAll
import com.yousong.yousong.databinding.FragmentDirectionalParameterBinding
import com.yousong.yousong.fragment.common.BaseFragment
import com.yousong.yousong.model.local.Address
import com.yousong.yousong.operator.OnDirectionalOperator
import kotlinx.android.synthetic.main.layout_directional_region.*

/**
 * 定向型广告用户筛选参数
 *
 * @author 超悟空
 * @version 1.0 2018/7/12
 * @since 1.0
 */
class DirectionalParameterFragment : BaseFragment(), OnDirectionalOperator {

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
     * 定向数据模型
     */
    private val directionalViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DirectionalViewModel::class.java)
    }

    /**
     * 地址列表适配器
     */
    private val adapter = AddressAdapter(this)

    override val rootViewId = R.layout.fragment_directional_parameter

    override fun onInitView(savedInstanceState: Bundle?) {
        initAddressList()
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        directionalViewModel.directional = adsDetailViewModel.adsDetail.directional
        binding.adsDetail = adsDetailViewModel.adsDetail
        binding.viewModel = directionalViewModel
        binding.holder = this
    }

    /**
     * 初始化地址列表
     */
    private fun initAddressList() {
        adapter.bind(adsDetailViewModel.adsDetail.directional.addresses)

        address_recyclerView.apply {
            layoutManager.isAutoMeasureEnabled = true
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
            adapter = this@DirectionalParameterFragment.adapter
        }
    }

    override fun onRelocationClick(view: View) {

    }

    override fun onAddAddressClick(view: View) {
        adapter.add(Address())
    }

    override fun onSelectAddressClick(address: Address) {

    }

    override fun onRemoveAddressClick(address: Address) {
        adapter.removeAll { it == address }
    }
}