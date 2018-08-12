package com.yousong.yousong.fragment.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.MyAdsActivity
import com.yousong.yousong.activity.user.PersonalAuthenticationActivity
import com.yousong.yousong.activity.user.WithdrawActivity
import com.yousong.yousong.architecture.viewmodel.UserViewModel
import com.yousong.yousong.databinding.FragmentMeBinding
import com.yousong.yousong.fragment.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * 我的页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class MeFragment : BaseFragment() {

    override val rootViewId = R.layout.fragment_me

    override fun onInitView(savedInstanceState: Bundle?) {
        initAction()
        FragmentMeBinding.bind(rootView).userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    /**
     * 初始化功能导航
     */
    private fun initAction() {
        withdraw.setOnClickListener {
            startActivity<WithdrawActivity>()
        }

        publish_ad.setOnClickListener {
            startActivity<MyAdsActivity>()
        }

        authenticate.setOnClickListener {
            startActivity<PersonalAuthenticationActivity>()
        }
    }
}