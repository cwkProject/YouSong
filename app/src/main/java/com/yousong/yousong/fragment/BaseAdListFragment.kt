package com.yousong.yousong.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import com.yousong.yousong.R
import com.yousong.yousong.adapter.AdAdapter

/**
 * 广告列表界面基类
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
abstract class BaseAdListFragment : BaseFragment() {

    /**
     * 列表适配器
     */
    protected val adapter = AdAdapter()

    override val rootViewId = R.layout.fragment_ad_list

    @CallSuper
    override fun onInitView(savedInstanceState: Bundle?) {
        initList()
        onInitListAction()
    }

    @CallSuper
    override fun onInitData(savedInstanceState: Bundle?) {
        loadAds()
    }

    /**
     * 初始化列表
     */
    private fun initList() {
        val recyclerView = rootView as RecyclerView

        recyclerView.adapter = adapter
    }

    /**
     * 加载广告列表数据
     */
    protected abstract fun loadAds()

    /**
     * 初始化列表操作事件
     */
    protected abstract fun onInitListAction()
}