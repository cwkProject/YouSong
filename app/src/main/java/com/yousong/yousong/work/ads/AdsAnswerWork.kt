package com.yousong.yousong.work.ads

import com.yousong.yousong.R
import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.server.AnswerResult
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.cwk.android.library.global.Global
import org.json.JSONObject
import java.math.BigDecimal

/**
 * 答题
 *
 * @author 超悟空
 * @version 1.0 2018/7/18
 * @since 1.0
 */
class AdsAnswerWork : BaseSimpleWorkModel<Any, AnswerResult>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Any?) {
        dataMap["adsId"] = params[0] as String
        dataMap["adsAnswerOptionId"] = params[1].toString()
    }

    override fun onSuccessExtract(jsonResult: JSONObject): AnswerResult =
            jsonResult.getString(RESULT).jsonToObject()

    @Post
    override fun onTaskUri() = ValueUrl.URL_ADS_ANSWER

    override fun onRequestSuccessMessage(handleResult: JSONObject): String {
        val context = Global.getApplication()

        mData.result.apply {
            when {
                isAnswer == ValueConst.SERVER_TRUE -> return context.getString(R.string
                        .format_answer_success).format(BigDecimal(myBudget / 100.0).stripTrailingZeros())
                canAnswer == ValueConst.SERVER_TRUE -> return context.getString(R.string.error_answer_again)
                canAnswer != ValueConst.SERVER_TRUE -> return context.getString(R.string.error_answer)
            }
        }

        return context.getString(R.string.error_answer)
    }
}