package com.yousong.yousong.work.ads

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 获取广告详情
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 */
class AdsGetDetailWork : BaseSimpleWorkModel<String, AdsDetail>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["adsId"] = params[0]
    }

    override fun onSuccessExtract(jsonResult: JSONObject): AdsDetail =
            jsonResult.getString(RESULT).jsonToObject()

    override fun onTaskUri() = ValueUrl.URL_ADS_GET_ADS_DETAIL
}