package com.yousong.yousong.architecture.databinding

import android.databinding.BindingConversion
import java.math.BigDecimal

/**
 * BigDecimal相关的适配器与转换器
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
object BigDecimalBindingAdapters {

    /**
     * BigDecimal to Int
     */
    @BindingConversion
    @JvmStatic
    fun convertBigDecimal(number: BigDecimal): Int = number.toInt()
}