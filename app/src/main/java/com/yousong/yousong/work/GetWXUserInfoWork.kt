package com.yousong.yousong.work

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.WxResponse
import com.yousong.yousong.value.ValueUrl
import org.cwk.android.library.data.IntegratedJsonDataModel
import org.cwk.android.library.work.IntegratedWorkModel
import org.json.JSONObject

/**
 * 获取微信用户信息
 *
 * @author 超悟空
 * @version 1.0 2017/3/2
 * @since 1.0 2017/3/2
 */
class GetWXUserInfoWork : IntegratedWorkModel<String, WxResponse, IntegratedJsonDataModel<String, WxResponse>>() {
    override fun onCreateDataModel(): IntegratedJsonDataModel<String, WxResponse> {
        return object : IntegratedJsonDataModel<String, WxResponse>(TAG) {
            override fun onFillRequestParameters(dataMap: MutableMap<String, String?>, vararg parameters: String) {
                dataMap["access_token"] = parameters[0]
                dataMap["openid"] = parameters[1]
            }

            @Throws(Exception::class)
            override fun onSuccessResult(handleResult: JSONObject): WxResponse? =
                    handleResult.toString().jsonToObject()

            @Throws(Exception::class)
            override fun onRequestResult(handleResult: JSONObject): Boolean =
                    !handleResult.has("errcode")

            @Throws(Exception::class)
            override fun onRequestMessage(result: Boolean, handleResult: JSONObject): String? =
                    if (result) null else handleResult.optString("errmsg")
        }
    }

    override fun onTaskUri(): String = ValueUrl.WX_USER_INFO_URL
}
