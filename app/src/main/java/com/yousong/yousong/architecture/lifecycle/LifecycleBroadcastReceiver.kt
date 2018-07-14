package com.yousong.yousong.architecture.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import org.cwk.android.library.architecture.broadcast.BaseBroadcastReceiver

/**
 * 带有生命周期感知的广播接收器
 *
 * @author 超悟空
 * @version 1.0 2018/5/21
 * @since 1.0 2018/5/21
 **/
abstract class LifecycleBroadcastReceiver : BaseBroadcastReceiver(), LifecycleObserver {

    /**
     * 是否同时在全局范围注册接收，即接收系统事件和其他进程事件，默认为false
     */
    private var globalRegister = false

    /**
     * Android 上下文
     */
    private var context: Context? = null

    abstract override fun onReceive(context: Context, intent: Intent)

    abstract override fun onRegisterIntentFilter(filter: IntentFilter)

    /**
     * 设置为同时在全局范围注册接收，即接收系统事件和其他进程事件
     *
     * @return 本接收者实例
     */
    fun globalRegister(): LifecycleBroadcastReceiver {
        this.globalRegister = true
        return this
    }

    /**
     * 注册接收者
     *
     * @param context Android 上下文
     * @param lifecycle 生命周期
     * @param globalRegister 设置是否同时在全局范围注册接收，即接收系统事件和其他进程事件，默认为false
     */
    fun register(context: Context, lifecycle: Lifecycle, globalRegister: Boolean = false) {
        this.globalRegister = globalRegister
        this.context = context

        registerLocalReceiver(context)
        if (globalRegister) {
            context.registerReceiver(this, registerIntentFilter)
        }

        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        unregisterLocalReceiver(context)
        if (globalRegister) {
            context?.unregisterReceiver(this)
        }
    }
}