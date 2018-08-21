package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.view.View
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citypickerview.CityPickerView
import com.yousong.yousong.R
import com.yousong.yousong.adapter.AddressAdapter
import com.yousong.yousong.architecture.viewmodel.CreateAdsViewModel
import com.yousong.yousong.architecture.viewmodel.DirectionalViewModel
import com.yousong.yousong.common.removeAll
import com.yousong.yousong.databinding.FragmentDirectionalParameterBinding
import com.yousong.yousong.fragment.common.BaseFragment
import com.yousong.yousong.model.local.Address
import com.yousong.yousong.operator.OnDirectionalOperator
import com.yousong.yousong.third.BDLocationClient
import kotlinx.android.synthetic.main.layout_directional_location.*


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

    /**
     * 城市选择器
     */
    private val cityPicker = CityPickerView()

    /**
     * 城市选择器配置
     */
    private val cityPickerConfigBuilder = CityConfig.Builder()

    override val rootViewId = R.layout.fragment_directional_parameter

    override fun onInitView(savedInstanceState: Bundle?) {
        initAddressList()
        initCityPicker()
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        directionalViewModel.directional = adsDetailViewModel.adsDetail.directional
        binding.adsDetail = adsDetailViewModel.adsDetail
        binding.viewModel = directionalViewModel
        binding.holder = this

        BDLocationClient.start(activity!!, this)
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

    /**
     * 初始化城市选择器
     */
    private fun initCityPicker() {
        cityPicker.init(context)
        cityPickerConfigBuilder
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
    }

    override fun onRelocationClick(view: View) {
        BDLocationClient.start(activity!!, this)
    }

    override fun onAddAddressClick(view: View) {
        adapter.add(Address())
    }

    override fun onSelectAddressClick(address: Address) {
        if (address.address != null) {
            cityPickerConfigBuilder.province(address.province)
            cityPickerConfigBuilder.city(address.city)
            cityPickerConfigBuilder.district(address.district)
        }

        cityPicker.setConfig(cityPickerConfigBuilder.build())

        cityPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                address.province = province?.name
                address.city = city?.name
                address.district = district?.name
                address.address = province?.name + city?.name + district?.name
                address.addressCode = district?.id ?: city?.id ?: province?.id
            }
        })

        cityPicker.showCityPicker()
    }

    override fun onRemoveAddressClick(address: Address) {
        adapter.removeAll { it == address }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        BDLocationClient.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}