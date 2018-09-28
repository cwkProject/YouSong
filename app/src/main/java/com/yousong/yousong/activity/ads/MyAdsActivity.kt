package com.yousong.yousong.activity.ads

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.activity.user.CompanyCertificationActivity
import com.yousong.yousong.adapter.MyAdsViewPagerAdapter
import com.yousong.yousong.global.LoginStatus
import kotlinx.android.synthetic.main.activity_my_ads.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity

/**
 * 我的广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class MyAdsActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_my_ads

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_my_ads)
        initNavigation()
        initFab()
    }

    /**
     * 初始化页导航
     */
    private fun initNavigation() {
        viewPager.adapter = MyAdsViewPagerAdapter(supportFragmentManager)

        tabLayout.setupWithViewPager(viewPager)
    }

    /**
     * 初始化悬浮按钮
     */
    private fun initFab() {
        fab.setOnClickListener {
            when {
                LoginStatus.userInfo.companyAuth?.valid == true -> startActivity<CreateAdsActivity>()
                LoginStatus.userInfo.authOk -> it.longSnackbar(R.string.hint_company_authentication_reward, R.string.name_go_to_authentication) {
                    startActivity<CompanyCertificationActivity>()
                }
                !LoginStatus.userInfo.authOk -> {
                    it.snackbar(R.string.prompt_poor_network_reload_user_data)
                    LoginStatus.userInfo.loadUserData()
                }
            }
        }
    }
}