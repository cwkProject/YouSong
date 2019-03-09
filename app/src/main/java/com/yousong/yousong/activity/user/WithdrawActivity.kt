package com.yousong.yousong.activity.user

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.activity.viewModels
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.user.WithdrawViewModel
import com.yousong.yousong.databinding.ActivityWithdrawBinding
import com.yousong.yousong.global.LoginStatus
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 提现页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class WithdrawActivity : BaseActivity() {

    /**
     * 数据模型
     */
    private val viewModel by viewModels<WithdrawViewModel>()

    override val rootViewId = R.layout.activity_withdraw

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_withdraw)
        ActivityWithdrawBinding.bind(rootView).apply {
            viewModel = this@WithdrawActivity.viewModel
            userInfo = LoginStatus.userInfo
        }

        viewModel.submitResult.observe(this, Observer { it?.show(this) })
    }
}