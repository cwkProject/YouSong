package com.yousong.yousong.fragment.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.ShareFriendActivity
import com.yousong.yousong.architecture.viewmodel.UserViewModel
import com.yousong.yousong.databinding.FragmentFriendBinding
import com.yousong.yousong.fragment.common.BaseFragment
import org.jetbrains.anko.support.v4.startActivity

/**
 * 好友页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class FriendFragment : BaseFragment() {

    /**
     * 用户数据
     */
    private val userViewModel by lazy {
        ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    /**
     * 绑定器
     */
    private val binding by lazy {
        FragmentFriendBinding.bind(rootView)
    }

    override val rootViewId = R.layout.fragment_friend

    override fun onInitView(savedInstanceState: Bundle?) {
        initAction()
        binding.data = userViewModel
    }

    /**
     * 初始化动作事件
     */
    private fun initAction() {
        binding.inviteCountLayout.setOnClickListener {
            userViewModel.refreshInviteCount()
        }

        binding.totalIncomingLayout.setOnClickListener {
            userViewModel.refreshTotalIncome()
        }

        binding.shareFriendLayout.setOnClickListener {
            startActivity<ShareFriendActivity>()
        }
    }
}