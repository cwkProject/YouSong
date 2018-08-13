package com.yousong.yousong.architecture.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.operator.OnAdsDetailOperator
import com.yousong.yousong.work.ads.AdsAnswerWork
import com.yousong.yousong.work.ads.AdsGetDetailWork
import com.yousong.yousong.work.common.start
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * 广告详情页面数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
class AdsDetailViewModel : ViewModel(), OnAdsDetailOperator {

    /**
     * 提交结果
     */
    val submitResult = SubmitResultLiveData()

    /**
     * 广告详情
     */
    val adsDetail = MutableLiveData<AdsDetail>()

    /**
     * 加载广告
     *
     * @param id 广告id
     */
    fun loadAds(id: String) {
        AdsGetDetailWork().start(id) {
            if (it.isSuccess) {
                adsDetail.value = it.result?.apply {
                    question.option.forEach {
                        it.answer = false
                    }
                }
            }
        }
    }

    override fun onSubmit(view: View) {
        adsDetail.value?.let { detail ->
            detail.question.option.find { it.answer }?.let {

                val dialog = view.context.indeterminateProgressDialog(R.string.prompt_submitting) {
                    setCancelable(false)
                }

                AdsAnswerWork().start(detail.ads.id, it.order) {
                    dialog.cancel()

                    submitResult.value = SubmitResult(it.isSuccess, it.message, if (it.isSuccess)
                        SubmitResult.LEVEL_ALERT_WITH_OK else SubmitResult.LEVEL_TOAST)
                }
            }
        }
    }

    override fun onWechat(view: View) {
    }

    override fun onMoments(view: View) {
    }

    override fun onCopy(view: View) {

    }
}