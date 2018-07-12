package com.yousong.yousong.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

/**
 * Fragment基类
 *
 * @author 超悟空
 * @version 1.0 2017/2/22
 * @since 1.0 2017/2/22
 */
abstract class BaseFragment : Fragment() {

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
        view!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(rootViewId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                onActivityLoadEnd()
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
     * 初始化布局，在创建[.rootView]之后执行
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
    protected open fun onActivityLoadEnd() {
    }
}
