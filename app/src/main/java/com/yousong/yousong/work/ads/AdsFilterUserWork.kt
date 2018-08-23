package com.yousong.yousong.work.ads

import com.yousong.yousong.common.toJson
import com.yousong.yousong.model.local.Directional
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 定向数据筛选用户
 *
 * @author 超悟空
 * @version 1.0 2018/8/23
 * @since 1.0
 */
class AdsFilterUserWork : BaseSimpleWorkModel<Directional, Int>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Directional?) {
        dataMap["adsDirectional"] = params[0].toJson()
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = jsonResult.getInt(RESULT)

    override fun onTaskUri() = ValueUrl.URL_ADS_FILTER_USER
}