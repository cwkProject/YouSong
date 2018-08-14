package com.yousong.yousong.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yousong.yousong.R
import com.yousong.yousong.databinding.ItemMyAdsBinding
import com.yousong.yousong.model.local.Ads
import org.cwk.android.library.architecture.recycler.SimpleRecyclerViewAdapter

/**
 * 我的未发布广告列表
 *
 * @author 超悟空
 * @version 1.0 2018/8/14
 * @since 1.0
 */
class MyAdsAdapter : SimpleRecyclerViewAdapter<Ads, MyAdsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdsViewHolder =
            MyAdsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_ads, parent, false))

    override fun onBindViewHolder(holder: MyAdsViewHolder, position: Int) {
        holder.binding.data = dataList[position]
        holder.binding.executePendingBindings()
    }
}

class MyAdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * 绑定器
     */
    val binding = ItemMyAdsBinding.bind(itemView)
}