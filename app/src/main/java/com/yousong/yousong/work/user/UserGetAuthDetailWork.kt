package com.yousong.yousong.work.user

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.server.Auth
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
class UserGetAuthDetailWork : BaseSimpleWorkModel<Unit, Pair<Auth?, Auth?>>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject): Pair<Auth?, Auth?> =
            jsonResult.getJSONObject(RESULT).let { jsonObject ->
                val personalAuth = if (jsonObject.has("personalAuth")) {
                    jsonObject.getString("personalAuth").jsonToObject<Auth>()
                } else {
                    null
                }

                val companyAuth = if (jsonObject.has("companyAuth")) {
                    jsonObject.getString("companyAuth").jsonToObject<Auth>()
                } else {
                    null
                }

                Pair(personalAuth, companyAuth)
            }

    override fun onTaskUri() = ValueUrl.URL_USER_GET_AUTH_DETAIL
}