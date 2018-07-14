package com.yousong.yousong.architecture.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR
import java.math.BigDecimal

/**
 * 用户资产
 *
 * @author 超悟空
 * @version 1.0 2018/7/14
 * @since 1.0
 */
class UserAssets : BaseObservable() {

    /**
     * 总收益
     */
    @Bindable
    var totalIncome = BigDecimal("0.00")
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalIncome)
        }

    /**
     * 余额
     */
    @Bindable
    var balance = BigDecimal("0.00")
        set(value) {
            field = value
            notifyPropertyChanged(BR.balance)
        }

    /**
     * 邀请的人数
     */
    @Bindable
    var inviteCount = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.inviteCount)
        }
}