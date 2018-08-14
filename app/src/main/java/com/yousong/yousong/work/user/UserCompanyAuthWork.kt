package com.yousong.yousong.work.user

import com.yousong.yousong.R
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.cwk.android.library.global.Global
import org.json.JSONObject

/**
 * 企业认证
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0 2018/8/10
 **/
class UserCompanyAuthWork : BaseSimpleWorkModel<String, Unit>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["fullName"] = params[0]
        dataMap["idCard"] = params[1]
        dataMap["mobileNum"] = params[2]
        dataMap["verifyCode"] = params[3]
        dataMap["businessLicenceImgUrl"] = params[4]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = Unit

    @Post
    override fun onTaskUri() = ValueUrl.URL_USER_COMPANY_AUTH

    override fun onRequestSuccessMessage(handleResult: JSONObject): String =
            Global.getApplication().getString(R.string.success_submit)
}