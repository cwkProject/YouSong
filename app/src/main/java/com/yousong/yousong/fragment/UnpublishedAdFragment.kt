package com.yousong.yousong.fragment

import com.yousong.yousong.common.plusAssign

/**
 * 未发布的广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class UnpublishedAdFragment :BaseAdListFragment(){

    override fun loadAds() {
        adapter.adsList += (1..10).toList()
    }

    override fun onInitListAction() {
    }
}