package com.yousong.yousong.model.local

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
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
class Address(addressCode: String? = null) : BaseObservable(), Parcelable {

    /**
     * 行政区码
     */
    @Bindable
    var addressCode: String? = addressCode
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

    /**
     * 省
     */
    var province: String? = null

    /**
     * 市
     */
    var city: String? = null

    /**
     * 区
     */
    var district: String? = null

    private constructor(parcel: Parcel) : this(parcel.readString()) {
        address = parcel.readString()
        province = parcel.readString()
        city = parcel.readString()
        district = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addressCode)
        parcel.writeString(address)
        parcel.writeString(province)
        parcel.writeString(city)
        parcel.writeString(district)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}