package com.yousong.yousong.architecture.databinding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.yousong.yousong.R
import com.yousong.yousong.third.GlideApp
import java.io.File

/**
 * 图片相关的DataBinding适配器
 *
 * @author 超悟空
 * @version 1.0 2017/2/24
 * @since 1.0 2017/2/24
 */
object ImageBindingAdapters {

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

    /**
     * 填充头像
     *
     * @param view  图片控件
     * @param url   图片地址
     */
    @BindingAdapter("avatar")
    @JvmStatic
    fun loadAvatar(view: ImageView, url: String?) {
        GlideApp.with(view).load(url).circleCrop()
                .placeholder(R.drawable.ic_account_circle_grey_600_72dp)
                .error(R.drawable.ic_account_circle_grey_600_72dp).into(view)
    }

    /**
     * 填充本地图片
     *
     * @param view  图片控件
     * @param path 本地图片路径
     */
    @BindingAdapter("imageFile")
    @JvmStatic
    fun loadImageFromFile(view: ImageView, path: String?) {
        if (path != null) {
            GlideApp.with(view).load(File(path)).into(view)
        }
    }
}
