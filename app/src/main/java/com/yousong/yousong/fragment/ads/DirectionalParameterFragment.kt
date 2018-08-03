package com.yousong.yousong.fragment.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.CompoundButton
import com.yousong.yousong.R
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

    override val rootViewId = R.layout.fragment_directional_parameter

    override fun onInitView(savedInstanceState: Bundle?) {
        binding.data = viewModel.adDetail
        binding.holder = this
    }

    override fun onAgeNotLimitedChecked(buttonView: CompoundButton, isChecked: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}