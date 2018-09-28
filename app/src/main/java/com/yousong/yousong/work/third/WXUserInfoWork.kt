package com.yousong.yousong.work.third

import com.yousong.yousong.R
import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.server.WxResponse
import com.yousong.yousong.value.ValueUrl
import org.cwk.android.library.data.JsonDataModel
import org.cwk.android.library.global.Global
import org.cwk.android.library.work.StandardWorkModel
import org.json.JSONObject

/**
 * 获取微信用户信息
 *
 * @author 超悟空
 * @version 1.0 2017/3/2
 * @since 1.0 2017/3/2
 */
class WXUserInfoWork : StandardWorkModel<String, JsonDataModel<String, WxResponse>>() {
    override fun onCreateDataModel(): JsonDataModel<String, WxResponse> =
            object : JsonDataModel<String, WxResponse>(TAG) {
                override fun onFillRequestParameters(dataMap: MutableMap<String, String?>, vararg parameters: String) {
                    dataMap["access_token"] = parameters[0]
                    dataMap["openid"] = parameters[1]
                }

                override fun onRequestSuccess(handleResult: JSONObject): WxResponse? =
                        handleResult.toString().jsonToObject()

                override fun onRequestResult(handleResult: JSONObject): Boolean =
                        !handleResult.has("errcode")

                override fun onRequestFailedMessage(handleResult: JSONObject): String? =
                        handleResult.optString("errmsg")
            }

    override fun onTaskUri() = ValueUrl.URL_WX_USER_INFO

    override fun onParseFailed(): String {
        return Global.getApplication().getString(R.string.error_network_request)
    }

    override fun onNetworkRequestFailed(): String {
        return Global.getApplication().getString(R.string.error_network_request)
    }

    override fun onNetworkError(): String {
        return Global.getApplication().getString(R.string.error_network)
    }
}
