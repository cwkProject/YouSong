package com.yousong.yousong.activity.common

import android.os.Bundle
import android.widget.TextView
import com.yousong.yousong.R
import com.yousong.yousong.adapter.MainViewPagerAdapter
import com.yousong.yousong.architecture.lifecycle.LocationUpdateLifeCycle
import com.yousong.yousong.third.BDLocationClient
import kotlinx.android.synthetic.main.activity_main.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

class MainActivity : BaseActivity() {

    /**
     * 标题控件
     */
    private val toolbarTitle by lazy {
        findViewById<TextView>(R.id.toolbar_title)
    }

    override val rootViewId = R.layout.activity_main

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_ads, true, false)
        initNavigation()
        LocationUpdateLifeCycle(this)
    }

    /**
     * 初始化导航栏
     */
    private fun initNavigation() {
        viewPager.apply {
            offscreenPageLimit = 3
            adapter = MainViewPagerAdapter(supportFragmentManager)
            addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    navigation.selectedItemId = when (position) {
                        0 -> R.id.navigation_ads
                        1 -> R.id.navigation_friend
                        2 -> R.id.navigation_me
                        else -> return
                    }
                }
            })
        }

        navigation.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when (it.itemId) {
                R.id.navigation_ads -> 0
                R.id.navigation_friend -> 1
                R.id.navigation_me -> 2
                else -> return@setOnNavigationItemSelectedListener true
            }

            toolbarTitle.text = it.title

            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        BDLocationClient.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
