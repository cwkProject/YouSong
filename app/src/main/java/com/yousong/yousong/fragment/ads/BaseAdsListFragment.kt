package com.yousong.yousong.fragment.ads

import android.os.Bundle
import android.support.annotation.CallSuper
import com.yousong.yousong.R
import com.yousong.yousong.adapter.AdsAdapter
import com.yousong.yousong.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_ads_list.*

/**
 * 广告列表界面基类
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
abstract class BaseAdsListFragment : BaseFragment() {

    /**
     * 列表适配器
     */
    open protected val adapter = AdsAdapter()

    override val rootViewId = R.layout.fragment_ads_list

    @CallSuper
    override fun onInitView(savedInstanceState: Bundle?) {
        initRefreshLayout()
        initList()
    }

    @CallSuper
    override fun onInitData(savedInstanceState: Bundle?) {
        loadAds(true)
    }

    /**
     * 初始化刷新器
     */
    private fun initRefreshLayout() {

        val typedArray = context!!.theme.obtainStyledAttributes(intArrayOf(R.attr.colorPrimary))

        refreshLayout.setColorSchemeResources(typedArray.getResourceId(0, 0))

        typedArray.recycle()

        refreshLayout.setOnRefreshListener {
            loadAds(false)
        }
    }

    /**
     * 初始化列表
     */
    private fun initList() {
        recyclerView.adapter = adapter

        onInitListAction()
    }

    /**
     * 停止刷新控件
     */
    protected fun stopRefresh() {
        refreshLayout?.isRefreshing = false
    }

    /**
     * 加载广告列表数据，页面首次加载和下拉刷新时会调用
     *
     * @param first 是否首次加载
     */
    protected abstract fun loadAds(first: Boolean)

    /**
     * 初始化列表操作事件
     */
    protected abstract fun onInitListAction()
}