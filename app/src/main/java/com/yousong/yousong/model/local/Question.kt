package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.os.Parcel
import android.os.Parcelable
import com.yousong.yousong.BR
import java.util.*

/**
 * 问题
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class Question() : BaseObservable(), Parcelable {

    /**
     * 问题内容
     */
    @Bindable
    var content = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    /**
     * 重答次数
     */
    var retries = 0

    /**
     * 选项
     */
    val option = ObservableArrayList<Option>()

    /**
     * 当前是否可答题
     */
    @Bindable
    var canAnswer = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.canAnswer)
        }

    /**
     * 是否已回答过问题
     */
    @Bindable
    var answered = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.answered)
        }

    private constructor(parcel: Parcel) : this() {
        content = parcel.readString()!!
        retries = parcel.readInt()
        val options = ArrayList<Option>()
        parcel.readList(options, Option::class.java.classLoader)
        option.addAll(options)
        canAnswer = parcel.readByte() != 0.toByte()
        answered = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
        parcel.writeInt(retries)
        parcel.writeList(option)
        parcel.writeByte(if (canAnswer) 1 else 0)
        parcel.writeByte(if (answered) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}