package com.yousong.yousong.work

import com.yousong.yousong.common.jsonToCollection
import com.yousong.yousong.model.Ad
import com.yousong.yousong.value.ValueUrl
import org.json.JSONObject

/**
 * 获取广告列表
 *
 * @author 超悟空
 * @version 1.0 2018/7/18
 * @since 1.0
 */
class AdsPullListWork : BaseSimpleWorkModel<Unit, List<Ad>>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) {
    }

    override fun onSuccessExtract(jsonResult: JSONObject): List<Ad> =
            jsonResult.getString(RESULT).jsonToCollection()

    override fun onTaskUri() = ValueUrl.URL_ADS_GET_ADS_LIST
}