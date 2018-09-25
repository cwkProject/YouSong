package com.yousong.yousong.model.local

import android.os.Parcel
import android.os.Parcelable

/**
 * 广告详情
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 *
 * @property ads 广告基础数据
 * @property question 广告问题
 * @property directional 定向信息
 */
data class AdsDetail(
        val ads: Ads = Ads(),
        val question: Question = Question(),
        val directional: Directional = Directional()) : Parcelable {

    private constructor(parcel: Parcel) : this(
            parcel.readParcelable(Ads::class.java.classLoader)!!,
            parcel.readParcelable(Question::class.java.classLoader)!!,
            parcel.readParcelable(Directional::class.java.classLoader)!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(ads, flags)
        parcel.writeParcelable(question, flags)
        parcel.writeParcelable(directional, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdsDetail> {
        override fun createFromParcel(parcel: Parcel): AdsDetail {
            return AdsDetail(parcel)
        }

        override fun newArray(size: Int): Array<AdsDetail?> {
            return arrayOfNulls(size)
        }
    }
}