package com.yousong.yousong.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import cn.bingoogolapple.bgabanner.BGALocalImageSize
import com.yousong.yousong.R
import com.yousong.yousong.architecture.databinding.Ad
import com.yousong.yousong.databinding.ItemAdBinding
import com.yousong.yousong.model.BannerAds
import org.cwk.android.library.architecture.recycler.MultipleRecyclerViewAdapter
import org.cwk.android.library.architecture.recycler.RecyclerViewHolderManager


/**
 * 广告列表适配器
 *
 * @author 超悟空
 * @version 1.0 2018/6/28
 * @since 1.0
 */
class AdAdapter : MultipleRecyclerViewAdapter() {

    /**
     * 点击事件，ads为数据源，position为点中的广告索引
     */
    var bannerClickListener: (ads: BannerAds, position: Int) -> Unit = { _, _ -> }

    /**
     * 当前自动播放状态
     */
    var playState = true
        set(value) {
            field = value
            playControl(value)
        }

    /**
     * 播放开关监听器
     */
    private var playControl: (onOff: Boolean) -> Unit = {}

    /**
     * 顶部轮播广告
     */
    val topList = object : RecyclerViewHolderManager<BannerAds, BannerViewHolder>(this) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
                BannerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner_ads, parent, false))

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int, viewType: Int) {
            val localImageSize = BGALocalImageSize(720, 1280, 320f, 640f)
            holder.banner.setData(localImageSize, ImageView.ScaleType.FIT_XY, *(dataList[position].ads.toIntArray()))
            holder.banner.setDelegate { _, _, _, p ->
                bannerClickListener(dataList[position], p)
            }

            playControl = {
                if (it) {
                    holder.banner.startAutoPlay()
                } else {
                    holder.banner.stopAutoPlay()
                }
            }
        }
    }

    /**
     * 广告列表
     */
    val adsList = object : RecyclerViewHolderManager<Ad, AdViewHolder>(this) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder =
                AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false))

        override fun onBindViewHolder(holder: AdViewHolder, position: Int, viewType: Int) {
            holder.binding.data = dataList[position]
            holder.binding.executePendingBindings()
        }
    }

    override fun onBindManagers(managerList: MutableList<RecyclerViewHolderManager<*, out RecyclerView.ViewHolder>>) {
        managerList += topList
        managerList += adsList
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is BannerViewHolder) {
            playControl = {}
        }
    }

    /**
     * 轮播控件管理器
     */
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * 轮播控件
         */
        val banner = itemView as BGABanner
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * 绑定器
         */
        val binding = ItemAdBinding.bind(itemView)
    }
}