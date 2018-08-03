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
    var sex: Int = 3
        set(value) {
            field = value
            sexSelector.forEach {
                it.notifyChange()
            }
        }

    /**
     * 性别选择器，用于databinding绑定
     */
    val sexSelector: List<SexSelector> =
            listOf(SexSelector(1),
                    SexSelector(2),
                    SexSelector(3))

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
            notifyPropertyChanged(BR.minAge)

            minAge?.let {
                if (value != null && value < it) {
                    minAge = value
                }
            }
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

    /**
     * 性别选择器
     *
     * @property sexId 绑定的性别编号
     */
    inner class SexSelector(val sexId: Int) : BaseObservable() {

        /**
         * 该性别是否被选中
         */
        var checked: Boolean
            @Bindable get() = sexId == sex
            set(value) {
                if (value) {
                    sex = sexId
                }
            }
    }
}