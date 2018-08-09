package com.yousong.yousong.activity.ads

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.adapter.MyAdsViewPagerAdapter
import kotlinx.android.synthetic.main.activity_my_ads.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
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
    }

    /**
     * 初始化页导航
     */
    private fun initNavigation() {
        viewPager.adapter = MyAdsViewPagerAdapter(supportFragmentManager)

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_ads, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_my_ads_publish -> startActivity<CreateAdsActivity>()
        }

        return super.onOptionsItemSelected(item)
    }
}