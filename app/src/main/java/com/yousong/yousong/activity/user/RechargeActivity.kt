package com.yousong.yousong.activity.user

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 充值页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/12
 * @since 1.0
 */
class RechargeActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_recharge

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.name_recharge)
    }
}