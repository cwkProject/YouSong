package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject
import java.math.BigDecimal

/**
 * 获取用户账户余额
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0 2018/8/10
 **/
class UserGetAssetBalanceWork : BaseSimpleWorkModel<Unit, BigDecimal>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject) =
            jsonResult.getLong(RESULT).toBigDecimal().div(BigDecimal(100))

    override fun onTaskUri() = ValueUrl.URL_USER_GET_ASSET_BALANCE
}