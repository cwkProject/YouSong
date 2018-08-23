package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import android.databinding.Observable
import android.view.View
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.common.FileUploadWork
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserCompanyAuthWork
import org.jetbrains.anko.indeterminateProgressDialog
import java.io.File

/**
 * 企业认证数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
class CompanyAuthViewModel : AuthViewModel() {

    /**
     * 营业执照副本地址
     */
    var businessLicenceImgUrl: String? = null

    /**
     * 认证状态
     */
    var state: Int = ValueConst.REVIEW_UNSUBMITTED

    /**
     * 是否可以执行提交
     */
    @Bindable
    var submittable = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.submittable)
        }

    /**
     * 营业执照副本本地路径
     */
    @Bindable
    var businessLicencePath: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.businessLicencePath)
        }

    /**
     * 营业执照副本上传进度
     */
    @Bindable
    var businessLicenceProgress = ValueConst.PROGRESS_IDLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.businessLicenceProgress)
        }

    init {
        if (LoginStatus.userInfo.authOk) {
            onInitAuth()
        } else {
            LoginStatus.userInfo.loadUserData {
                onInitAuth()
            }
        }

        // 属性改变监听器
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId != BR.submittable) {
                    checkSubmitProperty()
                }
            }
        })
    }

    /**
     * 初始化认证信息
     */
    private fun onInitAuth() {
        LoginStatus.userInfo.companyAuth?.let {
            realName = it.fullName
            idCard = it.idCard
            businessLicenceImgUrl = it.businessLicenceImgUrl
            state = it.reviewState
            editable = !it.valid
            visibility = !it.valid
        }
    }

    /**
     * 检测属性是否可以提交
     */
    private fun checkSubmitProperty() {
        submittable = when {
            realName.isBlank() -> false
            idCard.length < 18 -> false
            mobile.length < 11 -> false
            verifyCode.length < 6 -> false
            businessLicenceImgUrl.isNullOrBlank() -> false
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

        UserCompanyAuthWork().start(realName, idCard, mobile, verifyCode, businessLicenceImgUrl) {
            dialog.cancel()
            submitResult.value = SubmitResult(it.isSuccess, it.message, if (it.isSuccess)
                SubmitResult.LEVEL_ALERT_FINISH else SubmitResult.LEVEL_LONG_TOAST)
        }
    }

    /**
     * 营业执照副本选择完成
     *
     * @param path 图片路径
     */
    fun businessLicenceSelected(path: String) {
        businessLicencePath = path

        FileUploadWork()
                .setOnNetworkProgressListener { current, total, _ -> businessLicenceProgress = (current / total * 100).toInt() }
                .start(File(path)) {
                    if (it.isSuccess) {
                        businessLicenceImgUrl = it.result
                        businessLicenceProgress = ValueConst.PROGRESS_SUCCESS
                    } else {
                        businessLicenceProgress = ValueConst.PROGRESS_FAILED
                    }
                }
    }
}