package com.yousong.yousong.activity.common

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver

/**
 * Activity基类
 *
 * @author 超悟空
 * @version 1.0 2017/2/22
 * @since 1.0 2017/2/22
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 日志或其他工具使用的标签
     */
    protected val TAG: String by lazy {
        this.javaClass.simpleName + "@" + this.hashCode().toString(16)
    }

    /**
     * 根布局
     */
    protected val rootView: View by lazy {
        findViewById<View>(android.R.id.content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCustomBeforeContent(savedInstanceState)
        setContentView(rootViewId)
        onInitView(savedInstanceState)
        onInitData(savedInstanceState)
        onInitLoadListener()
    }

    /**
     * 页面初始化加载监听器
     */
    private fun onInitLoadListener() {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                onInitLoadEnd()
            }
        })
    }

    /**
     * 设置根布局资源
     *
     * @return 根布局id
     */
    @get:LayoutRes
    protected abstract val rootViewId: Int

    /**
     * 自定义设置，在[.setContentView]之前
     *
     * @param savedInstanceState 保存的数据状态[.onCreate]
     */
    protected open fun onCustomBeforeContent(savedInstanceState: Bundle?) {

    }

    /**
     * 初始化布局，在[.setContentView]之后执行
     *
     * @param savedInstanceState 保存的数据状态[.onCreate]
     */
    protected abstract fun onInitView(savedInstanceState: Bundle?)

    /**
     * 初始化数据，在[.onInitView]之后执行
     *
     * @param savedInstanceState 保存的数据状态[.onCreate]
     */
    protected open fun onInitData(savedInstanceState: Bundle?) {

    }

    /**
     * 加载界面完成，基于[ViewTreeObserver.OnGlobalLayoutListener]
     */
    protected open fun onInitLoadEnd() {
    }
}
