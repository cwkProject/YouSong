package com.yousong.yousong.activity

import android.os.Bundle
import com.yousong.yousong.R
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 分享好友页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class ShareFriendActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_share_friend

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_share_friend)
    }
}