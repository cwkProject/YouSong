package com.yousong.yousong.architecture.databinding

import android.databinding.InverseMethod
import java.math.BigDecimal
import java.math.RoundingMode

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
    fun intToString(value: Int): String = value.toString()

    /**
     * String to Int
     */
    @JvmStatic
    fun stringToInt(value: String): Int = if (value.isEmpty()) 0 else value.toInt()

    /**
     * 金额转换到字符串
     */
    @InverseMethod("moneyToBigDecimal")
    @JvmStatic
    fun moneyToString(value: BigDecimal): String = value.toString()

    /**
     * 金额字符串转换到BigDecimal
     */
    @JvmStatic
    fun moneyToBigDecimal(value: String): BigDecimal = if (value.isEmpty()) BigDecimal("0") else BigDecimal(value).setScale(2, RoundingMode.DOWN)
}