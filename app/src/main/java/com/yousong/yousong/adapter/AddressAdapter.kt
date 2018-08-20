package com.yousong.yousong.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yousong.yousong.R
import com.yousong.yousong.databinding.ItemAddressSelectorBinding
import com.yousong.yousong.model.local.Address
import com.yousong.yousong.operator.OnDirectionalOperator
import org.cwk.android.library.architecture.recycler.SimpleRecyclerViewAdapter

/**
 * 目标地址适配器
 *
 * @author 超悟空
 * @version 1.0 2018/8/20
 * @since 1.0
 *
 * @property operator ui操作集合
 */
class AddressAdapter(private val operator: OnDirectionalOperator) : SimpleRecyclerViewAdapter<Address, AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
            AddressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address_selector, parent, false)).apply {
                binding.holder = operator
            }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.binding.data = dataList[position]
        holder.binding.executePendingBindings()
    }
}

/**
 * 目标地址布局管理器
 *
 * @author 超悟空
 * @version 1.0 2018/8/20
 * @since 1.0
 */
class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * 绑定器
     */
    val binding = ItemAddressSelectorBinding.bind(itemView)
}