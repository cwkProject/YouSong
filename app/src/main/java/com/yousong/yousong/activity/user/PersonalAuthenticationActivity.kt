package com.yousong.yousong.activity.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.PersonalAuthViewModel
import com.yousong.yousong.databinding.ActivityPersonalAuthenticationBinding
import com.yousong.yousong.function.SubmitResultUtil
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 个人认证页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class PersonalAuthenticationActivity : BaseActivity() {

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityPersonalAuthenticationBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PersonalAuthViewModel::class.java)
    }

    override val rootViewId = R.layout.activity_personal_authentication

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_personal_authentication)

        binding.data = viewModel

        viewModel.submitResult.observe(this, Observer {
            if (it != null) {
                SubmitResultUtil.show(this, it)
            }
        })
    }
}