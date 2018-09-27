package com.yousong.yousong.activity.ads

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.value.ValueTag
import com.yousong.yousong.work.ads.AdsPublishWork
import com.yousong.yousong.work.common.start
import kotlinx.android.synthetic.main.activity_ads_pay_success.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast

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

    /**
     * 初始化功能按钮
     */
    private fun initAction() {
        button_cancel.setOnClickListener {
            finish()
        }

        button_ok.setOnClickListener {
            AdsPublishWork().start(intent.getStringExtra(ValueTag.TAG_ADS_ID)) {
                if (it.isSuccess) {
                    alert(it.message!!) {
                        okButton {
                            setResult(RESULT_OK)
                            finish()
                        }

                        show()
                    }
                } else {
                    it.message?.let {
                        toast(it)
                    }
                }
            }
        }
    }
}