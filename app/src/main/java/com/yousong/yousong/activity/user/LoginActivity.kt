package com.yousong.yousong.activity.user

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.activity.common.MainActivity
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.third.WechatFunction
import com.yousong.yousong.value.ValueAction
import com.yousong.yousong.value.ValueTag
import kotlinx.android.synthetic.main.activity_login.*
import org.cwk.android.library.architecture.broadcast.LifecycleBroadcastReceiver
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 登录界面
 *
 * @author 超悟空
 * @version 1.0 2018/7/23
 * @since 1.0
 */
class LoginActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_login

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_login, true, false)
        initWxLogin()
        ChangeReceiver().register(this, lifecycle, true)
    }

    /**
     * 初始化微信登录
     */
    private fun initWxLogin() {
        login.setOnClickListener {
            it.isEnabled = false

            WechatFunction.login {
                hasListener = { wx_textView.setText(R.string.prompt_going_wechat) }
                failedListener = { wx_textView.setText(R.string.name_wechat_login) }
                snackbarView = it
            }

            it.isEnabled = true
        }
    }

    /**
     * 监听用户数据改变
     */
    private inner class ChangeReceiver : LifecycleBroadcastReceiver() {

        override fun onRegisterIntentFilter(filter: IntentFilter) {
            filter += ValueAction.ACTION_WX_LOGIN_SUCCESS
            filter += ValueAction.ACTION_WX_LOGIN_FAILED
        }

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ValueAction.ACTION_WX_LOGIN_SUCCESS -> {
                    startActivity<MainActivity>()
                    finish()
                }
                ValueAction.ACTION_WX_LOGIN_FAILED -> {
                    wx_textView.setText(R.string.name_wechat_login)
                    intent.getStringExtra(ValueTag.TAG_ERROR_MESSAGE)?.let {
                        toast(it)
                    }
                }
            }
        }
    }
}