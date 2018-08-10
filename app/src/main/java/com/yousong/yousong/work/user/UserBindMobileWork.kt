package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject

/**
 * 绑定手机号
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0 2018/8/10
 **/
class UserBindMobileWork : BaseSimpleWorkModel<String, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["mobileNum"] = params[0]
        dataMap["verifyCode"] = params[1]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) {
    }

    @Post
    override fun onTaskUri() = ValueUrl.URL_USER_BIND_MOBILE
}