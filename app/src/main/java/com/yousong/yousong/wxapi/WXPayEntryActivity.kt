package com.yousong.yousong.wxapi

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.databinding.ActivityWxPayEntryBinding
import com.yousong.yousong.value.ValueAction
import com.yousong.yousong.value.ValueKey
import com.yousong.yousong.work.third.WXGetPayResultWork
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import java.util.*
import kotlin.concurrent.timer

/**
 * 处理微信支付结果
 *
 * @author 超悟空
 * @version 1.0 2018/9/15
 * @since 1.0
 */
class WXPayEntryActivity : BaseActivity(), IWXAPIEventHandler {

    /**
     * 超时时间
     */
    private val timeout by lazy {
        resources.getInteger(R.integer.pay_timeout)
    }

    /**
     * 超时时间单位比例
     */
    private val timeoutScale by lazy {
        resources.getInteger(R.integer.pay_timeout_unit)
    }

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityWxPayEntryBinding.bind(rootView)
    }

    /**
     * 计时器
     */
    private var pollTimer: Timer? = null

    override val rootViewId = R.layout.activity_wx_pay_entry

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.app_name, true)

        binding.failed = false
        binding.remainingTime = timeout
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        if (!WXAPIFactory.createWXAPI(this, ValueKey.WX_APP_ID).handleIntent(intent, this)) {
            finish()
        }
    }

    override fun onReq(p0: BaseReq?) {
    }

    override fun onResp(resp: BaseResp) {
        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode != BaseResp.ErrCode.ERR_USER_CANCEL) {
            val prepayId = (resp as PayResp).prepayId

            val work = WXGetPayResultWork().apply {
                setLifecycleOwner(this@WXPayEntryActivity, false)

                liveData.observe(this@WXPayEntryActivity, Observer {
                    if (it != null && it.isSuccess) {
                        when (it.result) {
                            1 -> {
                                stopTimer()
                                sendBroadcast(Intent(ValueAction.ACTION_PAY_SUCCESS))
                                finish()
                            }
                            2 -> {
                                stopTimer()
                                binding.failed = true
                            }
                        }
                    }
                })

                beginExecute(prepayId)
            }

            pollTimer = timer(period = 10L) {
                binding.remainingTime--

                if (binding.remainingTime <= 0) {
                    stopTimer()
                    binding.failed = true
                } else if (binding.remainingTime % timeoutScale == 0) {
                    work.beginExecute(prepayId)
                }
            }
        } else {
            finish()
        }
    }

    /**
     * 停止计时
     */
    private fun stopTimer() {
        pollTimer?.cancel()
        pollTimer = null
    }

    override fun onDestroy() {
        stopTimer()
        super.onDestroy()
    }
}