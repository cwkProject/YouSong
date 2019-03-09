package com.yousong.yousong.fragment.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.ShareFriendActivity
import com.yousong.yousong.architecture.viewmodel.ads.AdsViewModel
import com.yousong.yousong.architecture.viewmodel.user.UserViewModel
import com.yousong.yousong.common.asOther
import com.yousong.yousong.databinding.FragmentFriendBinding
import com.yousong.yousong.fragment.common.BaseFragment
import com.yousong.yousong.model.server.BannerAdsItem
import com.yousong.yousong.third.GlideApp
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
    private val userViewModel by activityViewModels<UserViewModel>()

    /**
     * 广告数据模型
     */
    private val adsViewModel by activityViewModels<AdsViewModel>()

    /**
     * 绑定器
     */
    private val binding by lazy {
        FragmentFriendBinding.bind(rootView)
    }

    override val rootViewId = R.layout.fragment_friend

    override fun onInitView(savedInstanceState: Bundle?) {
        initBanner()
        initAction()
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        binding.viewModel = userViewModel

        adsViewModel.bannerAdsData.observe(this, Observer {
            if (it != null) {
                binding.bannerGuideLayout.setAutoPlayAble(it.items.size > 1)
                binding.bannerGuideLayout.setData(it.items, null)
            }
        })
    }

    /**
     * 初始化轮播广告
     */
    private fun initBanner() {
        // 21:9的轮播图
        binding.bannerGuideLayout.setAdapter { _, imageView, model, _ ->
            GlideApp.with(imageView.context)
                    .load(model.asOther<BannerAdsItem>()?.imgUrl)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView as ImageView)
        }
    }

    /**
     * 初始化动作事件
     */
    private fun initAction() {

        binding.inviteCountLayout.setOnClickListener {
            startActivity<ShareFriendActivity>()
        }

        binding.totalIncomingLayout.setOnClickListener {
            userViewModel.refreshTotalIncome()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bannerGuideLayout.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        binding.bannerGuideLayout.stopAutoPlay()
    }
}