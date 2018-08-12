package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import android.text.Editable
import com.yousong.yousong.BR

/**
 * 身份认证数据结构
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
open class AuthViewModel : MobileVerifyViewModel() {

    /**
     * 真实姓名
     */
    @Bindable
    var realName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.realName)
        }

    /**
     * 身份证
     */
    @Bindable
    var idCard = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.idCard)
        }

    /**
     * 是否可编辑内容
     */
    @Bindable
    var editable = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.editable)
        }

    /**
     * 身份证内容变化
     */
    fun onIdCardChanged(edt: Editable) {

        val posX = edt.indexOfAny(charArrayOf('x', 'X'))

        // 仅最后一位允许为X，且把小写转为大写
        when {
            posX in 0..16 -> edt.delete(posX, edt.length)
            posX == 17 && edt[17] == 'x' -> edt.replace(posX, posX, "X")
        }

        idCard = edt.toString()
    }
}