package com.yousong.yousong.operator

import android.view.View

/**
 * 我的页面操作集合
 *
 * @author 超悟空
 * @version 1.0 2018/8/21
 * @since 1.0
 */
interface OnMeFunctionOperator {

    /**
     * 提现按钮点击事件
     */
    fun onWithdrawClick(view: View)

    /**
     * 发布广告按钮点击事件
     */
    fun onPublishAdClick(view: View)

    /**
     * 实名按钮点击事件
     */
    fun onRealNameClick(view: View)

    /**
     * 企业按钮点击事件
     */
    fun onCompanyClick(view: View)
}