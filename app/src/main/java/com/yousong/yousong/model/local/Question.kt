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
    var content: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    /**
     * 重答次数
     */
    var retries: Int = 0

    /**
     * 重试次数选择器，用于databinding绑定
     */
    val retrySelector: List<RetrySelector> =
            listOf(RetrySelector(0),
                    RetrySelector(1),
                    RetrySelector(2))

    /**
     * 选项
     */
    val option: MutableList<Option> = ObservableArrayList()

    /**
     * 重试次数选择器
     *
     * @property times 绑定的重试次数
     */
    inner class RetrySelector(val times: Int) : BaseObservable() {

        /**
         * 该次数是否被选中
         */
        var checked: Boolean
            @Bindable get() = times == retries
            set(value) {
                if (value) {
                    retries = times
                }

                notifyPropertyChanged(BR.checked)
            }
    }
}