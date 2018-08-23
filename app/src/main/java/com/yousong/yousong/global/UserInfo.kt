package com.yousong.yousong.global

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.model.server.Auth
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserGetAssetBalanceWork
import com.yousong.yousong.work.user.UserGetAuthDetailWork
import java.math.BigDecimal

/**
 * 用户资产
 *
 * @author 超悟空
 * @version 1.0 2018/7/14
 * @since 1.0
 */
class UserInfo : BaseObservable() {

    /**
     * 余额
     */
    @Bindable
    var balance = BigDecimal("0")
        private set(value) {
            field = value
            notifyPropertyChanged(BR.balance)
        }

    /**
     * 个人认证信息
     */
    @Bindable
    var personalAuth: Auth? = null
        private set(value) {
            field = value
            notifyPropertyChanged(BR.personalAuth)
        }

    /**
     * 企业认证信息
     */
    @Bindable
    var companyAuth: Auth? = null
        private set(value) {
            field = value
            notifyPropertyChanged(BR.companyAuth)
        }

    /**
     * 是否成功加载了认证信息
     */
    var authOk = false

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
     * 登录成功后加载用户数据，重复调用会刷新用户数据
     *
     * @param call 加载完成后回调
     */
    fun loadUserData(call: (() -> Unit)? = null) {
        if (LoginStatus.login) {
            UserGetAuthDetailWork().start {
                authOk = it.isSuccess
                if (it.isSuccess) {
                    personalAuth = it.result.first
                    companyAuth = it.result.second
                }

                call?.invoke()
            }
        } else {
            call?.invoke()
        }
    }

    /**
     * 重置数据
     */
    fun reset() {
        balance = BigDecimal("0")
        personalAuth = null
        companyAuth = null
        authOk = false
    }
}