package com.yousong.yousong.model.local

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import com.yousong.yousong.BR

/**
 * 问题选项
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 *
 * @param order 选项序号
 */
class Option(order: Int = 0) : BaseObservable(), Parcelable {

    /**
     * 选项id
     */
    var id = 1L

    /**
     * 选项序号
     */
    @Bindable
    var order = order
        set(value) {
            field = value
            notifyPropertyChanged(BR.order)
        }

    /**
     * 选项内容
     */
    @Bindable
    var content = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    /**
     * 是否正确答案
     */
    @Bindable
    var answer = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.answer)
        }

    private constructor(parcel: Parcel) : this(parcel.readInt()) {
        id = parcel.readLong()
        content = parcel.readString()!!
        answer = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(order)
        parcel.writeLong(id)
        parcel.writeString(content)
        parcel.writeByte(if (answer) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Option> {
        override fun createFromParcel(parcel: Parcel): Option {
            return Option(parcel)
        }

        override fun newArray(size: Int): Array<Option?> {
            return arrayOfNulls(size)
        }
    }
}