package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import android.text.Editable
import android.view.View
import com.yousong.yousong.BR
import com.yousong.yousong.global.LoginStatus
import org.jetbrains.anko.toast
import java.math.BigDecimal

/**
 * 提现数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/21
 * @since 1.0
 */
class WithdrawViewModel : ObservableViewModel() {

    /**
     * 待提现金额
     */
    @Bindable
    var withdrawalAmount: BigDecimal? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.withdrawalAmount)
        }

    /**
     * 当前是否可提现
     */
    @Bindable
    var canWithdrawal = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.canWithdrawal)
        }

    init {
        // 每到提现页面刷新一次余额
        LoginStatus.userInfo.refreshBalance()
    }

    /**
     * 待提现金额输入变更
     */
    fun onMoneyChanged(edt: Editable) {
        val posDot = edt.indexOf('.')

        // 保留两位小数
        when {
            edt.isEmpty() -> withdrawalAmount = null
            posDot > 0 && edt.length - posDot - 1 > 2 -> {
                edt.delete(posDot + 3, edt.length)
                withdrawalAmount = BigDecimal(edt.toString())
            }
        }

        checkAmount()
    }

    /**
     * 执行提现
     */
    fun onSubmit(view: View) {
        checkAmount()

        if (canWithdrawal) {
            // 提现
            view.context.toast("可以执行提现")
        }
    }

    /**
     * 检查金额是否可以提现
     */
    private fun checkAmount() {
        withdrawalAmount?.let {
            canWithdrawal = it > BigDecimal.ZERO && it <= LoginStatus.userInfo.balance

            return
        }

        canWithdrawal = false
    }
}