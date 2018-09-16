package com.yousong.yousong.work.third

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 获取微信支付接口
 *
 * @author 超悟空
 * @version 1.0 2018/9/16
 * @since 1.0
 */
class WXGetPayResultWork : BaseSimpleWorkModel<String, Int>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["outTradeNo"] = params[0]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = jsonResult.getInt(RESULT)

    override fun onTaskUri() = ValueUrl.URL_WECHAT_GET_PAY_RESULT
}