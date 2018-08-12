package com.yousong.yousong.architecture.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.databinding.Bindable
import android.view.View
import android.view.animation.LinearInterpolator
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserSendMobileVerifyCodeWork
import org.jetbrains.anko.toast

/**
 * 手机号验证数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
open class MobileVerifyViewModel : ObservableViewModel() {

    /**
     * 手机号
     */
    @Bindable
    var mobile = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)
        }

    /**
     * 验证码
     */
    @Bindable
    var verifyCode = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.verifyCode)
        }

    /**
     * 重发倒计时，-1表示未开始倒计时
     */
    @Bindable
    var resendCountdown = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.resendCountdown)
        }

    /**
     * 布局是否可见
     */
    @Bindable
    var visibility = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.visibility)
        }

    /**
     * 发送验证码
     */
    fun onSendVerifyCode(view: View) {
        startAnimator()
        UserSendMobileVerifyCodeWork().start(mobile) {
            if (it.isSuccess) {
                view.context.toast(R.string.prompt_verify_code_send)
            } else {
                view.context.toast(R.string.failed_verify_code_send)
            }
        }
    }

    /**
     * 启动倒计时动画
     */
    private fun startAnimator() {
        resendCountdown = 60
        ValueAnimator.ofInt(60, 0).setDuration(60000).apply {
            interpolator = LinearInterpolator()

            addUpdateListener {
                resendCountdown = it.animatedValue as Int
            }

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    resendCountdown = -1
                }
            })

            start()
        }
    }
}