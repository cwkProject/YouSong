package com.yousong.yousong.activity.user

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.WithdrawViewModel
import com.yousong.yousong.databinding.ActivityWithdrawBinding
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 提现页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class WithdrawActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_withdraw

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.name_withdraw)
        ActivityWithdrawBinding.bind(rootView).viewModel = ViewModelProviders.of(this).get(WithdrawViewModel::class.java)
    }
}