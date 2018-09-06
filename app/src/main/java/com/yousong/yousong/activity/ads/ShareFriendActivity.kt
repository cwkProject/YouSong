package com.yousong.yousong.activity.ads

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import kotlinx.android.synthetic.main.activity_share_friend.*
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

        initAction()
    }

    /**
     * 初始化功能
     */
    private fun initAction() {
        send_wechat.setOnClickListener {}

        send_moments.setOnClickListener { }

        send_copy_url.setOnClickListener { }
    }
}