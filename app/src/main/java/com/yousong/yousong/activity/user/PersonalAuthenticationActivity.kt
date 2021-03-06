package com.yousong.yousong.activity.user

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.activity.viewModels
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.user.PersonalAuthViewModel
import com.yousong.yousong.databinding.ActivityPersonalAuthenticationBinding
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
    private val viewModel by viewModels<PersonalAuthViewModel>()

    override val rootViewId = R.layout.activity_personal_authentication

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_personal_authentication)

        binding.data = viewModel

        viewModel.submitResult.observe(this, Observer { it?.show(this) })
    }
}