package com.yousong.yousong.work.ads

import com.yousong.yousong.R
import com.yousong.yousong.common.toJson
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.cwk.android.library.global.Global
import org.json.JSONObject

/**
 * 创建广告提交审核
 *
 * @author 超悟空
 * @version 1.0 2018/8/8
 * @since 1.0
 */
class AdsCreateWork : BaseSimpleWorkModel<AdsDetail, Unit>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: AdsDetail?) {
        dataMap["adsDetail"] = params[0].toJson()
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = Unit

    @Post
    override fun onTaskUri() = ValueUrl.URL_ADS_CREATE

    override fun onRequestSuccessMessage(handleResult: JSONObject): String =
            Global.getApplication().getString(R.string.success_create_ads)
}