package com.yousong.yousong.work.ads

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 获取广告池总金额
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 */
class AdsGetPoolTotalAmountWork : BaseSimpleWorkModel<Unit, BigDecimal>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject): BigDecimal =
            jsonResult.getString(RESULT).let { BigDecimal(it).div(BigDecimal(100)).setScale(0, RoundingMode.DOWN) }

    override fun onTaskUri() = ValueUrl.URL_ADS_GET_POOL_TOTAL_AMOUNT
}