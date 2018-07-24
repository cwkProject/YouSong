package com.yousong.yousong.architecture.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.value.ValueConst

/**
 * 答案
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class Answer : BaseObservable() {

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
    var isAnswer: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isAnswer)
        }
}