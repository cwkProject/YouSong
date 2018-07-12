package com.yousong.yousong.fragment

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.ShareFriendActivity
import kotlinx.android.synthetic.main.fragment_friend.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * 好友页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class FriendFragment : BaseFragment() {
    override val rootViewId = R.layout.fragment_friend

    override fun onInitView(savedInstanceState: Bundle?) {
        share_friend_layout.setOnClickListener {
            startActivity<ShareFriendActivity>()
        }
    }
}