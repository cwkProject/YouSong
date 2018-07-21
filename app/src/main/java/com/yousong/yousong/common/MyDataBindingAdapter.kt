package com.yousong.yousong.common

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.yousong.yousong.third.GlideApp

/**
 * 自定义属性的DataBinding适配器
 *
 * @author 超悟空
 * @version 1.0 2017/2/24
 * @since 1.0 2017/2/24
 */
object MyDataBindingAdapter {

    /**
     * 填充网络图片
     *
     * @param view  图片控件
     * @param defaultSrc 默认占位和失败图片
     * @param url   图片地址
     */
    @BindingAdapter("defaultSrc", "imageUrl")
    @JvmStatic
    fun loadImageFromUrl(view: ImageView, defaultSrc: Drawable, url: String?) {
        GlideApp.with(view).load(url).placeholder(defaultSrc).error(defaultSrc).into(view)
    }
}
