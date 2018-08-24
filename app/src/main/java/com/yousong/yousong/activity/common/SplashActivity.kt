package com.yousong.yousong.activity.common

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.user.LoginActivity
import com.yousong.yousong.common.plusAssign
import com.yousong.yousong.global.AppConfig
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.global.MyApplication
import com.yousong.yousong.value.ValueAction
import org.cwk.android.library.architecture.broadcast.LifecycleBroadcastReceiver
import org.jetbrains.anko.startActivity

/**
 * 启动页
 *
 * @author 超悟空
 * @version 1.0 2018/7/23
 * @since 1.0
 */
class SplashActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_splash

    override fun onInitView(savedInstanceState: Bundle?) {
        // 注册广播接收者
        LoadingReceiver().register(this, lifecycle)
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        if ((application as MyApplication).isFinish) {
            if (!LoginStatus.login && AppConfig.token != null) {
                (application as MyApplication).initData()
            } else {
                loadFinish()
            }
        }
    }

    /**
     * 数据加载完毕
     */
    private fun loadFinish() {
        if (LoginStatus.login) {
            startActivity<MainActivity>()
        } else {
            startActivity<LoginActivity>()
            // startActivity<MainActivity>()
        }

        finish()
    }

    /**
     * 数据加载结果的广播接收者，
     * 用于提前结束启动页
     *
     * @author 超悟空
     * @version 1.0 2017/2/21
     * @since 1.0
     */
    private inner class LoadingReceiver : LifecycleBroadcastReceiver() {

        /**
         * 动作队列信号量，
         * 初始时为注册的动作数量，
         * 当减少到0时表示数据加载完毕
         */
        @Volatile
        private var actionSemaphore = 1

        override fun onRegisterIntentFilter(filter: IntentFilter) {
            filter += ValueAction.ACTION_LOAD_DATA_FINISH
        }

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ValueAction.ACTION_LOAD_DATA_FINISH ->
                    // 完成一个动作信号量减1
                    actionSemaphore--
            }

            if (actionSemaphore == 0) {
                // 数据加载完成
                loadFinish()
            }
        }
    }
}