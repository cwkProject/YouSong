package com.yousong.yousong.work.ads

import com.yousong.yousong.common.jsonToObject
import com.yousong.yousong.model.server.BannerAds
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 获取轮播图
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 */
class AdsGetBannerWork : BaseSimpleWorkModel<Unit, BannerAds>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject): BannerAds =
            jsonResult.getString(RESULT).jsonToObject()

    override fun onTaskUri() = ValueUrl.URL_ADS_GET_BANNER
}