package com.yousong.yousong.fragment.home

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.MyAdsActivity
import com.yousong.yousong.activity.user.EnterpriseCertificationActivity
import com.yousong.yousong.activity.user.WithdrawActivity
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
        initUserInfo()
        initAction()
    }

    /**
     * 初始化个人信息
     */
    private fun initUserInfo() {

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
            startActivity<EnterpriseCertificationActivity>()
        }
    }
}