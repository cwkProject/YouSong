package com.yousong.yousong.activity

import android.os.Bundle
import com.yousong.yousong.R
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
    }
}