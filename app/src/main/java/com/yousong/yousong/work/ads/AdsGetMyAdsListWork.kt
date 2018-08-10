package com.yousong.yousong.work.ads

import com.yousong.yousong.common.jsonToCollection
import com.yousong.yousong.model.local.MyAds
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 获取我发布过的广告列表
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 */
class AdsGetMyAdsListWork : BaseSimpleWorkModel<Unit, List<MyAds>>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject): List<MyAds> =
            jsonResult.getString(RESULT).jsonToCollection()

    override fun onTaskUri() = ValueUrl.URL_ADS_GET_MY_ADS_LIST
}