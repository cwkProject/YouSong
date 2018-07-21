package com.yousong.yousong.work

import com.yousong.yousong.value.ValueUrl
import org.cwk.android.library.annotation.Post
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

    override fun onSuccessExtract(jsonResult: JSONObject): Int = jsonResult.getInt(RESULT)

    @Post
    override fun onTaskUri() = ValueUrl.URL_ADS_ANSWER
}