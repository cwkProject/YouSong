package com.yousong.yousong.model.local

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.value.ValueConst
import java.math.BigDecimal

/**
 * 我发布过的广告
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 *
 * @property ads 广告内容
 * @property rechargeAmount 充值金额
 */
class MyAds(val ads: Ads, val rechargeAmount: BigDecimal) : BaseObservable() {

    /**
     * 审核状态
     */
    @Bindable
    var reviewState = ValueConst.UNSUBMIT
        set(value) {
            field = value
            notifyPropertyChanged(BR.reviewState)
        }
}