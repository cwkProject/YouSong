package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import android.databinding.Observable
import android.view.View
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserPersonalAuthWork
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * 个人认证数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
class PersonalAuthViewModel : AuthViewModel() {

    /**
     * 认证状态
     */
    var state: Int = ValueConst.UNSUBMIT

    /**
     * 是否可以执行提交
     */
    @Bindable
    var submitable = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.submitable)
        }

    init {
        LoginStatus.personalAuth?.let {
            realName = it.fullName
            idCard = it.idCard
            state = it.reviewState
            editable = it.reviewState != ValueConst.PASS
            visibility = it.reviewState != ValueConst.PASS
        }

        // 属性改变监听器
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId != BR.submitable) {
                    checkSubmitProperty()
                }
            }
        })
    }

    /**
     * 检测属性是否可以提交
     */
    private fun checkSubmitProperty() {
        submitable = when {
            realName.isBlank() -> false
            idCard.length < 18 -> false
            mobile.length < 11 -> false
            verifyCode.length < 6 -> false
            else -> true
        }
    }

    /**
     * 提交审核
     */
    fun onSubmit(view: View) {
        val dialog = view.context.indeterminateProgressDialog(R.string.prompt_submitting) {
            setCancelable(false)
        }

        UserPersonalAuthWork().start(realName, idCard, mobile, verifyCode) {
            dialog.cancel()
            submitResult.value = SubmitResult(it.isSuccess, it.message, if (it.isSuccess)
                SubmitResult.LEVEL_ALERT_FINISH else SubmitResult.LEVEL_LONG_TOAST)
        }
    }
}