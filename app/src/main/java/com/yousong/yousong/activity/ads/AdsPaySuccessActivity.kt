package com.yousong.yousong.activity.ads

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 广告支付成功页面
 *
 * @author 超悟空
 * @version 1.0 2018/9/27
 * @since 1.0 2018/9/27
 **/
class AdsPaySuccessActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_ads_pay_success

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_pay_finish)

        initAction()
    }

    private fun initAction(){

    }
}