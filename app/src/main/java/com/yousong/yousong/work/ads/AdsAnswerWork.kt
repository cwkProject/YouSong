package com.yousong.yousong.work.ads

import com.yousong.yousong.R
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.cwk.android.library.global.Global
import org.json.JSONObject

/**
 * 答题
 *
 * @author 超悟空
 * @version 1.0 2018/7/18
 * @since 1.0
 */
class AdsAnswerWork : BaseSimpleWorkModel<Any, Int>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Any?) {
        dataMap["adsId"] = params[0] as String
        dataMap["adsAnswerOptionId"] = params[1].toString()
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = jsonResult.getInt(RESULT)

    @Post
    override fun onTaskUri() = ValueUrl.URL_ADS_ANSWER

    override fun onRequestSuccessMessage(handleResult: JSONObject): String {
        val resId = when (mData.result) {
            1 -> R.string.success_answer
            2 -> R.string.error_answer_again
            3 -> R.string.error_answer
            else -> R.string.error_answer
        }

        return Global.getApplication().getString(resId)
    }
}