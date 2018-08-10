package com.yousong.yousong.work.ads

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.server.WxPay
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject

/**
 * 广告充值
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 */
class AdsRechargeWork : BaseSimpleWorkModel<String, WxPay>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["adsId"] = params[0]
        dataMap["device"] = ValueConst.DEVICE_TYPE
    }

    override fun onSuccessExtract(jsonResult: JSONObject): WxPay =
            jsonResult.getString(RESULT).jsonToObject()

    @Post
    override fun onTaskUri() = ValueUrl.URL_ADS_RECHARGE
}