package com.yousong.yousong.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yousong.yousong.fragment.home.AdsFragment
import com.yousong.yousong.fragment.home.FriendFragment
import com.yousong.yousong.fragment.home.MeFragment

/**
 * 主界面分页适配器
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? =
            when (position) {
                0 -> AdsFragment()
                1 -> FriendFragment()
                2 -> MeFragment()
                else -> null
            }

    override fun getCount(): Int = 3
}