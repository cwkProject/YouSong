package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject
import java.math.BigDecimal

/**
 * 获取用户总收益
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 */
class UserGetTotalIncomeWork : BaseSimpleWorkModel<Unit, BigDecimal>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject): BigDecimal =
            jsonResult.getLong(RESULT).toBigDecimal().divide(BigDecimal(100))

    override fun onTaskUri() = ValueUrl.URL_USER_GET_TOTAL_INCOME
}