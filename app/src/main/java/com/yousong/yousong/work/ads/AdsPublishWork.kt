package com.yousong.yousong.work.ads

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject

/**
 * 审核后发布广告
 *
 * @author 超悟空
 * @version 1.0 2018/7/18
 * @since 1.0
 */
class AdsPublishWork : BaseSimpleWorkModel<String, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: String?) {
        dataMap["adsId"] = params[0]
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = Unit

    @Post
    override fun onTaskUri() = ValueUrl.URL_ADS_PUBLISH
}