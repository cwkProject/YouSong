package com.yousong.yousong.architecture.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserGetTotalIncomeWork
import com.yousong.yousong.work.user.UserRefereeCountWork
import java.math.BigDecimal

/**
 * 用户相关数据的数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/7/14
 * @since 1.0
 */
class UserViewModel : ViewModel() {

    /**
     * 总收益
     */
    val totalIncome = MutableLiveData<BigDecimal>()

    /**
     * 邀请的人数
     */
    val inviteCount = MutableLiveData<Int>()

    init {
        totalIncome.value = BigDecimal("0")
        inviteCount.value = 0
        refreshTotalIncome()
        refreshInviteCount()
        LoginStatus.userInfo.loadUserData()
    }

    /**
     * 刷新总收益
     */
    fun refreshTotalIncome() {
        UserGetTotalIncomeWork().start {
            if (it.isSuccess) {
                totalIncome.value = it.result
            }
        }

        // 同步请求余额刷新
        LoginStatus.userInfo.refreshBalance()
    }

    /**
     * 刷新邀请人数
     */
    fun refreshInviteCount() {
        UserRefereeCountWork().start {
            if (it.isSuccess) {
                inviteCount.value = it.result
            }
        }
    }
}