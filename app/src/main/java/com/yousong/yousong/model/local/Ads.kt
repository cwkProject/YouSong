package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import com.yousong.yousong.BR
import com.yousong.yousong.value.ValueConst
import java.math.BigDecimal

/**
 * 广告
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 *
 * @property id 广告id
 */
class Ads(var id: String = "") : BaseObservable(), Parcelable {

    /**
     * 广告名称
     */
    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    /**
     * 封面
     */
    @Bindable
    var cover: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.cover)
        }

    /**
     * 大图海报
     */
    @Bindable
    var poster: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.poster)
        }

    /**
     * 广告类型，true推广型，false定向型
     */
    @Bindable
    var type = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    /**
     * 目标广告人数
     */
    @Bindable
    var targetCount = 1000
        set(value) {
            field = value
            notifyPropertyChanged(BR.targetCount)
        }

    /**
     * 每位用户所得金额，单位元
     */
    @Bindable
    var userUnitPrice = BigDecimal("1")
        set(value) {
            field = value
            notifyPropertyChanged(BR.userUnitPrice)
        }

    /**
     * 是否需要发票
     */
    @Bindable
    var needInvoice = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.needInvoice)
        }

    /**
     * 城市名称
     */
    @Bindable
    var city: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.city)
        }

    /**
     * 总金额，单位元
     */
    @Bindable
    var totalAmount = BigDecimal("0")
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalAmount)
        }

    /**
     * 余额，单位元
     */
    @Bindable
    var balance = BigDecimal("0")
        set(value) {
            field = value
            notifyPropertyChanged(BR.balance)
        }

    /**
     * 广告发布状态
     */
    @Bindable
    var publishState = ValueConst.PUBLISH_UNPUBLISH
        set(value) {
            field = value
            notifyPropertyChanged(BR.publishState)
        }

    /**
     * 广告审核状态
     */
    @Bindable
    var reviewState = ValueConst.REVIEW_UNSUBMITTED
        set(value) {
            field = value
            notifyPropertyChanged(BR.reviewState)
        }

    /**
     * 支付状态
     */
    @Bindable
    var payState = ValueConst.PAY_UNPAID
        set(value) {
            field = value
            notifyPropertyChanged(BR.payState)
        }

    /**
     * 审核失败原因
     */
    var responseExplain: String? = null

    private constructor(parcel: Parcel) : this(parcel.readString()!!) {
        name = parcel.readString()!!
        cover = parcel.readString()!!
        poster = parcel.readString()!!
        type = parcel.readByte() != 0.toByte()
        targetCount = parcel.readInt()
        userUnitPrice = parcel.readSerializable() as BigDecimal
        needInvoice = parcel.readByte() != 0.toByte()
        city = parcel.readString()
        totalAmount = parcel.readSerializable() as BigDecimal
        balance = parcel.readSerializable() as BigDecimal
        publishState = parcel.readInt()
        reviewState = parcel.readInt()
        payState = parcel.readInt()
        responseExplain = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(cover)
        parcel.writeString(poster)
        parcel.writeByte(if (type) 1 else 0)
        parcel.writeInt(targetCount)
        parcel.writeSerializable(userUnitPrice)
        parcel.writeByte(if (needInvoice) 1 else 0)
        parcel.writeString(city)
        parcel.writeSerializable(totalAmount)
        parcel.writeSerializable(balance)
        parcel.writeInt(publishState)
        parcel.writeInt(reviewState)
        parcel.writeInt(payState)
        parcel.writeString(responseExplain)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ads> {
        override fun createFromParcel(parcel: Parcel): Ads {
            return Ads(parcel)
        }

        override fun newArray(size: Int): Array<Ads?> {
            return arrayOfNulls(size)
        }
    }
}