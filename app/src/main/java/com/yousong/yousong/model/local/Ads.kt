package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
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
class Ads(var id: String = "") : BaseObservable() {

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
    var cover = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cover)
        }

    /**
     * 大图海报
     */
    @Bindable
    var poster = ""
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
    var totalAmount = BigDecimal("0.00")
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalAmount)
        }

    /**
     * 余额，单位元
     */
    @Bindable
    var balance = BigDecimal("0.00")
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
    var reviewState = ValueConst.REVIEW_UNDER_REVIEW
        set(value) {
            field = value
            notifyPropertyChanged(BR.reviewState)
        }
}