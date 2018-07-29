package com.yousong.yousong.architecture.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.yousong.yousong.BR

/**
 * 选项
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
    var content: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    /**
     * 重答次数
     */
    @Bindable
    var reAnswerCount: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.reAnswerCount)
        }

    /**
     * 答案
     */
    val answers: MutableList<Answer> = ObservableArrayList()
}