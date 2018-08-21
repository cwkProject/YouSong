package com.yousong.yousong.fragment.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.MyAdsActivity
import com.yousong.yousong.activity.user.CompanyCertificationActivity
import com.yousong.yousong.activity.user.PersonalAuthenticationActivity
import com.yousong.yousong.activity.user.WithdrawActivity
import com.yousong.yousong.architecture.viewmodel.UserViewModel
import com.yousong.yousong.databinding.FragmentMeBinding
import com.yousong.yousong.fragment.common.BaseFragment
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.operator.OnMeFunctionOperator
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * 我的页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class MeFragment : BaseFragment(), OnMeFunctionOperator {

    override val rootViewId = R.layout.fragment_me

    override fun onInitView(savedInstanceState: Bundle?) {
        FragmentMeBinding.bind(rootView).apply {
            userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
            holder = this@MeFragment
        }
    }

    override fun onWithdrawClick(view: View) {
        when {
            LoginStatus.personalAuth?.valid == true -> startActivity<WithdrawActivity>()
            LoginStatus.authOk -> startActivity<PersonalAuthenticationActivity>()
            !LoginStatus.authOk -> {
                toast(R.string.prompt_poor_network_reload_user_data)
                LoginStatus.loadUserData()
            }
        }
    }

    override fun onPublishAdClick(view: View) {
        when {
            LoginStatus.companyAuth?.valid == true -> startActivity<MyAdsActivity>()
            LoginStatus.authOk -> startActivity<CompanyCertificationActivity>()
            !LoginStatus.authOk -> {
                toast(R.string.prompt_poor_network_reload_user_data)
                LoginStatus.loadUserData()
            }
        }
    }

    override fun onRealNameClick(view: View) {
        startActivity<PersonalAuthenticationActivity>()
    }

    override fun onCompanyClick(view: View) {
        startActivity<CompanyCertificationActivity>()
    }
}