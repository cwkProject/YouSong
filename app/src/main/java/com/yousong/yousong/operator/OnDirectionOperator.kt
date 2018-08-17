package com.yousong.yousong.operator

import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.RadioGroup

/**
 * 定向参数的相关操作
 *
 * @author 超悟空
 * @version 1.0 2018/8/3
 * @since 1.0
 */
interface OnDirectionOperator {

    /**
     * 年龄不限选择状态变更
     */
    fun onAgeNotLimitedChecked(buttonView: CompoundButton, isChecked: Boolean)

    /**
     * 最小年龄选择事件
     */
    fun onMinAgeItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)

    /**
     * 最大年龄选择事件
     */
    fun onMaxAgeItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)

    /**
     * 性别选中事件
     */
    fun onSexCheckedChanged(group: RadioGroup, checkedId: Int)

    /**
     * 目标地区选中事件
     */
    fun onDestinationCheckedChanged(group: RadioGroup, checkedId: Int)
}