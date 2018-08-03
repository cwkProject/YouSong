package com.yousong.yousong.operator

import android.widget.CompoundButton

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
}