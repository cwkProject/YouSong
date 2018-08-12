package com.yousong.yousong.operator

import android.view.View

/**
 * 广告详情页面操作集合
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
interface OnAdsDetailOperator {

    /**
     * 提交答案
     */
    fun onSubmit(view: View)

    /**
     * 分享到微信
     */
    fun onWechat(view: View)

    /**
     * 分享到朋友圈
     */
    fun onMoments(view: View)

    /**
     * 复制链接
     */
    fun onCopy(view: View)
}