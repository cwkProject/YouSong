package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR

/**
 * 问题选项
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class Option : BaseObservable() {

    /**
     * 选项序号
     */
    @Bindable
    var order: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.order)
        }

    /**
     * 选项内容
     */
    @Bindable
    var content: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    /**
     * 是否正确答案
     */
    @Bindable
    var answer: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.answer)
        }
}