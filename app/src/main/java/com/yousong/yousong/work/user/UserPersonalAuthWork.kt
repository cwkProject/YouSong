package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject

/**
 * 个人认证
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0 2018/8/10
 **/
class UserPersonalAuthWork : BaseSimpleWorkModel<String, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["fullName"] = params[0]
        dataMap["idCard"] = params[1]
        dataMap["mobileNum"] = params[2]
        dataMap["verifyCode"] = params[3]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = Unit

    @Post
    override fun onTaskUri() = ValueUrl.URL_USER_PERSONAL_AUTH
}