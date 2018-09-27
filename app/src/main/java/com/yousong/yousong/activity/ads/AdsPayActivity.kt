package com.yousong.yousong.activity.ads

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.ads.AdsPayViewModel
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.databinding.ActivityAdsPayBinding
import com.yousong.yousong.value.ValueAction
import com.yousong.yousong.value.ValueTag
import org.cwk.android.library.architecture.broadcast.LifecycleBroadcastReceiver
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 广告付款页面
 *
 * @author 超悟空
 * @version 1.0 2018/9/27
 * @since 1.0 2018/9/27
 **/
class AdsPayActivity : BaseActivity() {

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AdsPayViewModel::class.java)
    }

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityAdsPayBinding.bind(rootView)
    }

    override val rootViewId = R.layout.activity_ads_pay

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_pay_order)
        Receiver().register(this, lifecycle, true)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        viewModel.ads = intent.getParcelableExtra(ValueTag.TAG_ADS)
        viewModel.submitResult.observe(this, Observer { it?.show(this) })
    }

    /**
     * 广播监听器
     */
    private inner class Receiver : LifecycleBroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ValueAction.ACTION_PAY_SUCCESS) {
                setResult(RESULT_OK)
                finish()
            }
        }

        override fun onRegisterIntentFilter(filter: IntentFilter) {
            filter += ValueAction.ACTION_PAY_SUCCESS
        }
    }
}