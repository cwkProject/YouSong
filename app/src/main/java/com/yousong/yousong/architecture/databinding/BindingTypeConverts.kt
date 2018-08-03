package com.yousong.yousong.architecture.databinding

import android.databinding.*
import android.view.View
import android.widget.EditText

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
    fun stringToInt(value: String): Int? = if (value.isEmpty()) 0 else value.toInt()

    /**
     * Double to String
     */
    @BindingConversion
    @JvmStatic
    fun doubleToString(value: Double): String = value.toString()

    /**
     * 焦点丢失通知的变更的绑定方法
     */
    @BindingAdapter("focusOfSets")
    @JvmStatic
    fun setFocusChangeOfSets(view: EditText, value: String?) = view.setText(value)

    /**
     * 设置焦点改变监听器
     */
    @BindingAdapter(value = ["focusOfSetsAttrChanged"])
    @JvmStatic
    fun setFocusChangeListener(view: EditText, listener: InverseBindingListener?) {
        view.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                listener?.onChange()
            }
        }
    }

    /**
     * 焦点丢失通知的变更的逆绑定方法
     */
    @InverseBindingAdapter(attribute = "focusOfSets")
    @JvmStatic
    fun getFocusChangeOfSets(editText: EditText): String = editText.text.toString()
}