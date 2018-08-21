package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.yousong.yousong.BR
import com.yousong.yousong.value.ValueConst

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
    @Bindable
    var sex = 3
        set(value) {
            field = value
            notifyPropertyChanged(BR.sex)
        }

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
    @Bindable
    var latitude = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.latitude)
        }

    /**
     * 经度
     */
    @Bindable
    var longitude = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.longitude)
        }

    /**
     * 目标地区筛选类型
     */
    @Bindable
    var locationType = ValueConst.LOCATION_TYPE_NOT_LIMITED
        set(value) {
            field = value
            notifyPropertyChanged(BR.locationType)
        }

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
     * 地址集合
     */
    val addresses = ObservableArrayList<Address>()
}