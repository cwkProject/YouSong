package com.yousong.yousong.work

import com.yousong.yousong.global.AppConfig
import com.yousong.yousong.value.ValueUrl
import org.json.JSONObject

/**
 * 获取token
 *
 * @author 超悟空
 * @version 1.0 2018/7/17
 * @since 1.0
 */
class UserCreateTokenWork : BaseSimpleWorkModel<String, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
    }

    override fun onSuccessExtract(jsonResult: JSONObject) {
        AppConfig.token = jsonResult.getString(RESULT)
        AppConfig.save()
    }

    override fun onTaskUri() = ValueUrl.URL_USER_CREATE_TOKEN
}