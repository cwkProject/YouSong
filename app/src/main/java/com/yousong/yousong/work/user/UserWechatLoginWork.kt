package com.yousong.yousong.work.user

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.server.ServerUserInfo
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject

/**
 * 微信登录
 *
 * @author 超悟空
 * @version 1.0 2018/7/17
 * @since 1.0
 */
class UserWechatLoginWork : BaseSimpleWorkModel<String, Unit>() {

    override fun onSuccessExtract(jsonResult: JSONObject) =
            jsonResult.getString(RESULT).jsonToObject<ServerUserInfo>().loginSuccess()

    @Post
    override fun onTaskUri() = ValueUrl.URL_USER_WECHAT_LOGIN

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["unionId"] = params[0]
        dataMap["openId"] = params[1]
        dataMap["nickName"] = params[2]
        dataMap["headPic"] = params[3]
        dataMap["device"] = ValueConst.DEVICE_TYPE
    }
}