package com.yousong.yousong.global

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserGetAssetBalanceWork
import java.math.BigDecimal

/**
 * 用户资产
 *
 * @author 超悟空
 * @version 1.0 2018/7/14
 * @since 1.0
 */
object UserAssets : BaseObservable() {

    /**
     * 余额
     */
    @Bindable
    var balance = BigDecimal("0.00")
        private set(value) {
            field = value
            notifyPropertyChanged(BR.balance)
        }

    /**
     * 刷新余额
     */
    fun refreshBalance() {
        UserGetAssetBalanceWork().start {
            if (it.isSuccess) {
                balance = it.result
            }
        }
    }

    /**
     * 重置数据
     */
    fun reset() {
        balance = BigDecimal("0.00")
    }
}