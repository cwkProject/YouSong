package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 发送手机验证码
 *
 * @author 超悟空
 * @version 1.0 2018/7/17
 * @since 1.0
 */
class UserSendMobileVerifyCodeWork : BaseSimpleWorkModel<String, Unit>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["mobileNum"] = params[0]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = Unit

    override fun onTaskUri() = ValueUrl.URL_USER_GET_MOBILE_VERIFY_CODE
}