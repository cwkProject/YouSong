package com.yousong.yousong.operator

import android.view.View

/**
 * 发布广告的操作集合
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
interface OnPublishAdOperator {

    /**
     * 缩略图点击事件
     */
    fun onCoverClick(view: View)

    /**
     * 海报图点击事件
     */
    fun onPosterClick(view: View)

    /**
     * 增加选项
     */
    fun onAddOptionClick(view: View)

    /**
     * 移除选项
     */
    fun onRemoveOptionClick(view: View)
}