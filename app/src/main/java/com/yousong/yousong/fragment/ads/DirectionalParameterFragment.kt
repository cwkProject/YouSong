package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import com.yousong.yousong.R
import com.yousong.yousong.architecture.viewmodel.AgesRangeViewModel
import com.yousong.yousong.architecture.viewmodel.PublishAdViewModel
import com.yousong.yousong.databinding.FragmentDirectionalParameterBinding
import com.yousong.yousong.fragment.common.BaseFragment
import com.yousong.yousong.operator.OnDirectionOperator

/**
 * 定向型广告用户筛选参数
 *
 * @author 超悟空
 * @version 1.0 2018/7/12
 * @since 1.0
 */
class DirectionalParameterFragment : BaseFragment(), OnDirectionOperator {

    /**
     * 绑定器
     */
    private val binding by lazy {
        FragmentDirectionalParameterBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!).get(PublishAdViewModel::class.java)
    }

    /**
     * 可选年龄范围数据模型
     */
    private val agesViewModel by lazy {
        ViewModelProviders.of(activity!!).get(AgesRangeViewModel::class.java)
    }

    override val rootViewId = R.layout.fragment_directional_parameter

    override fun onInitView(savedInstanceState: Bundle?) {
        binding.data = viewModel.adDetail
        binding.ages = agesViewModel
        binding.holder = this
    }

    override fun onAgeNotLimitedChecked(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            viewModel.adDetail.directional.minAge = null
            viewModel.adDetail.directional.maxAge = null
            agesViewModel.maxSelection = -1
            agesViewModel.minAges = emptyList()
            agesViewModel.maxAges = emptyList()
        } else {
            agesViewModel.minAges = (0..99).toList()
            agesViewModel.maxAges = (0..99).toList()
            agesViewModel.maxSelection = 65
        }
        binding.invalidateAll()
    }

    override fun onMinAgeItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        viewModel.adDetail.directional.minAge = agesViewModel.minAges[position]

        viewModel.adDetail.directional.minAge?.let { min ->
            agesViewModel.maxAges = (min..99).toList()

            agesViewModel.maxAges.indexOf(viewModel.adDetail.directional.maxAge).let {
                agesViewModel.maxSelection = it
            }
        }
    }

    override fun onMaxAgeItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        viewModel.adDetail.directional.maxAge = agesViewModel.maxAges[position]

        viewModel.adDetail.directional.maxAge?.let { max ->
            agesViewModel.minAges = (0..max).toList()
        }
    }
}