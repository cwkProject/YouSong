package com.yousong.yousong.fragment

import com.yousong.yousong.activity.AdDetailActivity
import com.yousong.yousong.common.plusAssign
import org.jetbrains.anko.support.v4.startActivity

/**
 * 分享好友页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class ShareFriendFragment :BaseAdListFragment(){

    override fun loadAds() {
        adapter.adsList += (1..3).toList()
    }

    override fun onInitListAction() {
        adapter.adsList.setOnItemClickListener { holder, dataSource, position ->
            startActivity<AdDetailActivity>()
        }
    }
}