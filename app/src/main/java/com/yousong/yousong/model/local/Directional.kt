package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.yousong.yousong.BR

/**
 * 定向信息
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 */
class Directional : BaseObservable() {

    /**
     * 性别，1男，2女，3不限
     */
    var sex = 3

    /**
     * 最小年龄
     */
    @Bindable
    var minAge: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.minAge)

            maxAge?.let {
                if (value != null && value > it) {
                    maxAge = value
                }
            }
        }

    /**
     * 最大年龄
     */
    @Bindable
    var maxAge: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxAge)

            minAge?.let {
                if (value != null && value < it) {
                    minAge = value
                }
            }
        }

    /**
     * 纬度
     */
    var latitude = 0.0

    /**
     * 经度
     */
    var longitude = 0.0

    /**
     * 目标地区筛选类型
     */
    var destinationType = 0

    /**
     * 周边范围单位米
     */
    @Bindable
    var range: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.range)
        }

    /**
     * 城市代码
     */
    val cityIds = ObservableArrayList<Int>()
}