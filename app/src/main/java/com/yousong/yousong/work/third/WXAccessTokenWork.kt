package com.yousong.yousong.work.third

import com.yousong.yousong.R
import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.global.AppConfig
import com.yousong.yousong.model.server.WxResponse
import com.yousong.yousong.value.ValueKey
import com.yousong.yousong.value.ValueUrl
import org.cwk.android.library.data.JsonDataModel
import org.cwk.android.library.global.Global
import org.cwk.android.library.work.StandardWorkModel
import org.json.JSONObject

/**
 * 获取微信access_token
 *
 * @author 超悟空
 * @version 1.0 2017/3/1
 * @since 1.0 2017/3/1
 */
class WXAccessTokenWork : StandardWorkModel<String, JsonDataModel<String, WxResponse>>() {

    override fun onCreateDataModel(): JsonDataModel<String, WxResponse> {
        return object : JsonDataModel<String, WxResponse>(TAG) {
            override fun onFillRequestParameters(dataMap: MutableMap<String, String>, vararg parameters: String) {
                dataMap["appid"] = ValueKey.WX_APP_ID
                dataMap["secret"] = ValueKey.WX_APP_SECRET
                dataMap["grant_type"] = "authorization_code"
                dataMap["code"] = parameters[0]
            }

            override fun onRequestSuccess(handleResult: JSONObject): WxResponse? =
                    handleResult.toString().jsonToObject()

            override fun onRequestResult(handleResult: JSONObject): Boolean =
                    !handleResult.has("errcode")

            override fun onRequestFailedMessage(handleResult: JSONObject): String? =
                    handleResult.optString("errmsg")
        }
    }

    override fun onSuccess() {
        val wxResponse = mData.result

        AppConfig.wxAccessToken = wxResponse.access_token
        AppConfig.wxOpenId = wxResponse.openid
        AppConfig.wxRefreshToken = wxResponse.refresh_token
        AppConfig.wxUnionId = wxResponse.unionid
        AppConfig.save()
    }

    override fun onTaskUri() = ValueUrl.URL_WX_ACCESS_TOKEN

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
