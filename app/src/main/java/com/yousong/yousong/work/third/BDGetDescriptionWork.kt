package com.yousong.yousong.work.third

import com.yousong.yousong.value.ValueKey
import com.yousong.yousong.value.ValueUrl
import org.cwk.android.library.data.JsonDataModel
import org.cwk.android.library.work.StandardWorkModel
import org.json.JSONObject

/**
 * 获取位置描述
 *
 * @author 超悟空
 * @version 1.0 2018/9/28
 * @since 1.0 2018/9/28
 **/
class BDGetDescriptionWork : StandardWorkModel<Double, JsonDataModel<Double, String>>() {
    override fun onTaskUri() = ValueUrl.URL_BD_GEOCODER

    override fun onCreateDataModel(): JsonDataModel<Double, String> =
            object : JsonDataModel<Double, String>(TAG) {
                override fun onRequestResult(handleResult: JSONObject): Boolean =
                        handleResult.getInt("status") == 0

                override fun onRequestSuccess(handleResult: JSONObject): String =
                        handleResult.getJSONObject("result").getString("sematic_description")

                override fun onFillRequestParameters(dataMap: MutableMap<String, String>, vararg parameters: Double?) {
                    dataMap["location"] = "${parameters[0]},${parameters[1]}"
                    dataMap["output"] = "json"
                    dataMap["ak"] = ValueKey.BD_WEB_AK
                }

                override fun onRequestFailedMessage(handleResult: JSONObject): String? = null
            }
}