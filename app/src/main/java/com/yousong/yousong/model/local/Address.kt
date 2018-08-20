package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR

/**
 * 地址数据
 *
 * @author 超悟空
 * @version 1.0 2018/8/20
 * @since 1.0
 *
 * @param addressCode 行政区码
 */
class Address(addressCode: Int? = null) : BaseObservable() {

    /**
     * 行政区码
     */
    @Bindable
    var addressCode: Int? = addressCode
        set(value) {
            field = value
            notifyPropertyChanged(BR.addressCode)
        }

    /**
     * 完整地址
     */
    @Bindable
    var address: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }
}