package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.yousong.yousong.BR

/**
 * 问题
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class Question : BaseObservable() {

    /**
     * 问题内容
     */
    @Bindable
    var content = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    /**
     * 重答次数
     */
    var retries = 0

    /**
     * 选项
     */
    val option = ObservableArrayList<Option>()

    /**
     * 当前是否可答题
     */
    @Bindable
    var canAnswer = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.canAnswer)
        }

    /**
     * 是否已回答过问题
     */
    @Bindable
    var answered = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.answered)
        }
}