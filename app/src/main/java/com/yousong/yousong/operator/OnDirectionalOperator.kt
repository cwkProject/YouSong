package com.yousong.yousong.operator

import android.view.View
import com.yousong.yousong.model.local.Address


/**
 * 定向筛选的操作集合
 *
 * @author 超悟空
 * @version 1.0 2018/8/20
 * @since 1.0
 */
interface OnDirectionalOperator {

    /**
     * 重定位点击事件
     */
    fun onRelocationClick(view: View)

    /**
     * 增加一个地址点击事件
     */
    fun onAddAddressClick(view: View)

    /**
     * 选择新地址点击事件
     *
     * @param address 选中的地址对象
     */
    fun onSelectAddressClick(address: Address)

    /**
     * 移除一个地址对象
     *
     * @param address 需要移除的地址对象
     */
    fun onRemoveAddressClick(address: Address)
}