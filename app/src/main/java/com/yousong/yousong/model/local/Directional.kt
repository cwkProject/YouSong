package com.yousong.yousong.model.local

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import android.os.Parcel
import android.os.Parcelable
import com.yousong.yousong.BR
import com.yousong.yousong.value.ValueConst
import java.util.*

/**
 * 定向信息
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 */
class Directional() : BaseObservable(), Parcelable {

    /**
     * 性别，1男，2女，0不限
     */
    @Bindable
    var sex = 0
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

    private constructor(parcel: Parcel) : this() {
        sex = parcel.readInt()
        minAge = parcel.readValue(Int::class.java.classLoader) as? Int
        maxAge = parcel.readValue(Int::class.java.classLoader) as? Int
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
        locationType = parcel.readInt()
        range = parcel.readValue(Int::class.java.classLoader) as? Int
        val address = ArrayList<Address>()
        parcel.readList(address, Address::class.java.classLoader)
        addresses.addAll(address)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(sex)
        parcel.writeValue(minAge)
        parcel.writeValue(maxAge)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(locationType)
        parcel.writeValue(range)
        parcel.writeList(addresses)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Directional> {
        override fun createFromParcel(parcel: Parcel): Directional {
            return Directional(parcel)
        }

        override fun newArray(size: Int): Array<Directional?> {
            return arrayOfNulls(size)
        }
    }


}