package com.yousong.yousong.architecture.viewmodel.user

import android.databinding.Bindable
import android.text.Editable
import android.util.Log
import android.view.View
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.architecture.viewmodel.common.ObservableViewModel
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.third.WxWithdrawWork
import org.jetbrains.anko.indeterminateProgressDialog
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
     * 提现结果
     */
    val submitResult = SubmitResultLiveData()

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
            posDot < edt.length - 1 -> withdrawalAmount = BigDecimal(edt.toString())
        }

        checkAmount()
    }

    /**
     * 执行提现
     */
    fun onSubmit(view: View) {
        checkAmount()

        if (canWithdrawal) {

            val dialog = view.context.indeterminateProgressDialog(R.string.prompt_submitting) {
                setCancelable(false)
            }

            // 提现
            WxWithdrawWork().start(withdrawalAmount) {
                dialog.cancel()
                submitResult.value = SubmitResult(it.isSuccess, it.message, if (it.isSuccess)
                    SubmitResult.LEVEL_ALERT_FINISH else SubmitResult.LEVEL_ALERT_WITH_OK)
            }
        }
    }

    /**
     * 检查金额是否可以提现
     */
    private fun checkAmount() {
        Log.v("WithdrawViewModel", "amount:$withdrawalAmount")
        withdrawalAmount?.let {
            canWithdrawal = it > BigDecimal.ZERO && it <= LoginStatus.userInfo.balance

            return
        }

        canWithdrawal = false
    }
}