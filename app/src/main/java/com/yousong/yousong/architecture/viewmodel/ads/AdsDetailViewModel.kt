package com.yousong.yousong.architecture.viewmodel.ads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.value.ValueConst
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
class AdsDetailViewModel : ViewModel() {

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
        if (adsDetail.value != null && adsDetail.value?.ads?.id == id) {
            // 过滤重复请求
            return
        }

        AdsGetDetailWork().start(id) {
            if (it.isSuccess) {
                adsDetail.value = it.result?.apply {
                    if (question.canAnswer) {
                        question.option.forEach {
                            it.answer = false
                        }
                    }
                }
            }
        }
    }

    /**
     * 提交答案
     */
    fun onSubmit(view: View) {
        adsDetail.value?.let { detail ->
            detail.question.option.find { it.answer }?.let {

                val dialog = view.context.indeterminateProgressDialog(R.string.prompt_submitting) {
                    setCancelable(false)
                }

                AdsAnswerWork().start(detail.ads.id, it.id) {
                    dialog.cancel()

                    if (it.isSuccess) {
                        // 变更答题状态
                        detail.question.answered = true
                        detail.question.canAnswer = it.result.canAnswer == ValueConst.SERVER_TRUE
                    }

                    submitResult.value = SubmitResult(it.isSuccess, it.message, if (it.isSuccess)
                        SubmitResult.LEVEL_ALERT_WITH_OK else SubmitResult.LEVEL_TOAST)
                }
            }
        }
    }

    /**
     * 分享到微信
     */
    fun onWechat(view: View) {
    }

    /**
     * 分享到朋友圈
     */
    fun onMoments(view: View) {
    }

    /**
     * 复制链接
     */
    fun onCopy(view: View) {

    }
}