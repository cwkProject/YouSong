package com.yousong.yousong.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.yousong.yousong.R
import com.yousong.yousong.adapter.MyAdViewPagerAdapter
import kotlinx.android.synthetic.main.activity_my_ad.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import org.jetbrains.anko.startActivity

/**
 * 我的广告页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class MyAdActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_my_ad

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_my_ad)
        initNavigation()
    }

    /**
     * 初始化页导航
     */
    private fun initNavigation() {
        viewPager.adapter = MyAdViewPagerAdapter(supportFragmentManager)

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_ad, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_my_ad_publish -> startActivity<PublishAdActivity>()
        }

        return super.onOptionsItemSelected(item)
    }
}