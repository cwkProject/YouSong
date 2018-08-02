package com.yousong.yousong.architecture.databinding

import android.databinding.BindingAdapter
import android.view.View

/**
 * 通用绑定适配器
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
object BindingAdapters {

    @BindingAdapter("goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("invisibleUnless")
    @JvmStatic
    fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}