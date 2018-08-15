package com.yousong.yousong.work.ads

import com.google.gson.reflect.TypeToken
import com.yousong.yousong.common.GsonUtil
import com.yousong.yousong.model.local.Ads
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 获取广告列表
 *
 * @author 超悟空
 * @version 1.0 2018/7/18
 * @since 1.0
 */
class AdsPullListWork : BaseSimpleWorkModel<Unit, List<Ads>>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject): List<Ads> =
            GsonUtil.gson.fromJson(jsonResult.getString(RESULT), object : TypeToken<List<Ads>>() {}.type)

    override fun onTaskUri() = ValueUrl.URL_ADS_GET_ADS_LIST
}