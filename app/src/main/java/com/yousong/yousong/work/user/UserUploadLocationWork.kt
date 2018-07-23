package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject

/**
 * 上传位置
 *
 * @author 超悟空
 * @version 1.0 2018/7/17
 * @since 1.0
 */
class UserUploadLocationWork : BaseSimpleWorkModel<String, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["province"] = params[0]
        dataMap["city"] = params[1]
        dataMap["latitude"] = params[2]
        dataMap["longitude"] = params[3]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) {
    }

    @Post
    override fun onTaskUri() = ValueUrl.URL_USER_LOCATION
}