package com.yousong.yousong.work.user

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.global.LoginStatus
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 获取认证信息
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 */
class UserGetAuthDetailWork : BaseSimpleWorkModel<Unit, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject) {
        jsonResult.getJSONObject(RESULT).let { jsonObject ->
            if (jsonObject.has("personalAuth")) {
                LoginStatus.personalAuth = jsonObject.getString("personalAuth").jsonToObject()
            }

            if (jsonObject.has("companyAuth")) {
                LoginStatus.companyAuth = jsonObject.getString("companyAuth").jsonToObject()
            }
        }
    }

    override fun onSuccess() {
        LoginStatus.authOk = true
    }

    override fun onFailed() {
        LoginStatus.authOk = false
    }

    override fun onTaskUri() = ValueUrl.URL_USER_GET_AUTH_DETAIL
}