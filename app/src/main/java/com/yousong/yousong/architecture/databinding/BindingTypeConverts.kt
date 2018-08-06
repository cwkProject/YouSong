package com.yousong.yousong.architecture.databinding

import android.databinding.BindingConversion
import android.databinding.InverseMethod

/**
 * 双向数据绑定类型转换器
 *
 * @author 超悟空
 * @version 1.0 2018/8/2
 * @since 1.0
 */
object BindingTypeConverts {

    /**
     * Int to String
     */
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(value: Int?): String? = value?.toString()

    /**
     * String to Int
     */
    @JvmStatic
    fun stringToInt(value: String): Int? = if (value.isEmpty()) null else value.toInt()

    /**
     * Double to String
     */
    @BindingConversion
    @JvmStatic
    fun doubleToString(value: Double): String = value.toString()
}