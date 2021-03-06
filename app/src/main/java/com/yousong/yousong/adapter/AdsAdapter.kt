package com.yousong.yousong.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.yousong.yousong.R
import com.yousong.yousong.common.asOther
import com.yousong.yousong.databinding.ItemAdsBinding
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.model.server.BannerAds
import com.yousong.yousong.model.server.BannerAdsItem
import com.yousong.yousong.third.GlideApp
import org.cwk.android.library.architecture.recycler.MultipleRecyclerViewAdapter
import org.cwk.android.library.architecture.recycler.RecyclerViewHolderManager

/**
 * 广告列表适配器
 *
 * @author 超悟空
 * @version 1.0 2018/6/28
 * @since 1.0
 */
class AdsAdapter : MultipleRecyclerViewAdapter() {

    /**
     * 点击事件，ads为广告项数据，position为点中的广告索引
     */
    var bannerClickListener: (ads: BannerAdsItem, position: Int) -> Unit = { _, _ -> }

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
            holder.banner.setAutoPlayAble(dataList[position].items.size > 1)
            holder.banner.setData(dataList[position].items, null)
            holder.banner.setDelegate { _, _, m, p ->
                bannerClickListener(m as BannerAdsItem, p)
            }

            playControl = holder.playControl
        }
    }

    /**
     * 广告列表
     */
    val adsList = object : RecyclerViewHolderManager<Ads, AdViewHolder>(this) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder =
                AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false))

        override fun onBindViewHolder(holder: AdViewHolder, position: Int, viewType: Int) {
            holder.binding.data = dataList[position]
            holder.binding.executePendingBindings()
        }
    }

    override fun onBindManagers(managerList: MutableList<RecyclerViewHolderManager<*, out androidx.recyclerview.widget.RecyclerView.ViewHolder>>) {
        managerList += topList
        managerList += adsList
    }

    override fun onViewRecycled(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
        if (holder is BannerViewHolder && playControl == holder.playControl) {
            playControl = {}
        }
    }
}

/**
 * 轮播控件管理器
 *
 * @author 超悟空
 * @version 1.0 2018/6/28
 * @since 1.0
 */
class BannerViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    /**
     * 轮播控件
     */
    val banner = itemView as BGABanner

    /**
     * 播放开关监听器
     */
    var playControl: (onOff: Boolean) -> Unit = {
        if (it) {
            banner.startAutoPlay()
        } else {
            banner.stopAutoPlay()
        }
    }

    init {
        // 21:9的轮播图
        val itemHeight = itemView.context.resources.displayMetrics.widthPixels * 9 / 21

        itemView.layoutParams.height = itemHeight

        banner.setAdapter { _, imageView, model, _ ->
            GlideApp.with(imageView.context)
                    .load(model.asOther<BannerAdsItem>()?.imgUrl)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView as ImageView)
        }
    }
}

/**
 * 广告列表项管理器
 *
 * @author 超悟空
 * @version 1.0 2018/6/28
 * @since 1.0
 */
class AdViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    /**
     * 绑定器
     */
    val binding = ItemAdsBinding.bind(itemView)
}