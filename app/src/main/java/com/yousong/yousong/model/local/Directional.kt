package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.yousong.yousong.BR
import java.math.BigDecimal

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
    var sex: Int = 3
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
        }

    /**
     * 最大年龄
     */
    @Bindable
    var maxAge: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.minAge)
        }

    /**
     * 纬度
     */
    var latitude = BigDecimal("0.000000")

    /**
     * 经度
     */
    var longitude = BigDecimal("0.000000")

    /**
     * 城市代码
     */
    val cityIds: MutableList<Int> = ObservableArrayList()

    /**
     * 周边范围单位米
     */
    @Bindable
    var range: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.range)
        }
}