package com.yousong.yousong.adapter

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
class MainViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment =
            when (position) {
                0 -> AdsFragment()
                1 -> FriendFragment()
                2 -> MeFragment()
                else -> AdsFragment()
            }

    override fun getCount(): Int = 3
}