package com.yousong.yousong.architecture.viewmodel.ads

import androidx.databinding.Bindable
import android.view.View
import com.yousong.yousong.BR
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.architecture.viewmodel.common.ObservableViewModel
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.third.WechatFunction
import com.yousong.yousong.work.ads.AdsRechargeWork
import com.yousong.yousong.work.common.start

/**
 * 广告支付数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/9/27
 * @since 1.0 2018/9/27
 **/
class AdsPayViewModel : ObservableViewModel() {

    /**
     * 提交结果
     */
    val submitResult = SubmitResultLiveData()

    @Bindable
    var ads: Ads? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.ads)
        }

    /**
     * 支付
     */
    fun onSubmit(view: View) {
        if (ads != null) {
            AdsRechargeWork().start(ads?.id) {
                if (it.isSuccess) {
                    WechatFunction.pay(it.result)
                } else {
                    submitResult.value = SubmitResult(it.isSuccess, it.message, SubmitResult.LEVEL_ALERT)
                }
            }
        }
    }
}