package com.yousong.yousong.adapter

import com.yousong.yousong.R
import com.yousong.yousong.fragment.ads.PublishedAdsFragment
import com.yousong.yousong.fragment.ads.UnpublishedAdsFragment
import org.cwk.android.library.global.Global

/**
 * 我的广告界面分页适配器
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class MyAdsViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment =
            when (position) {
                0 -> PublishedAdsFragment()
                1 -> UnpublishedAdsFragment()
                else -> PublishedAdsFragment()
            }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? =
            when (position) {
                0 -> Global.getApplication().getText(R.string.title_published)
                1 -> Global.getApplication().getText(R.string.title_unpublished)
                else -> null
            }
}