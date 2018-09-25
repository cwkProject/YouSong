package com.yousong.yousong.work.third

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.cwk.android.library.annotation.Post
import org.json.JSONObject
import java.math.BigDecimal

/**
 * 微信提现
 *
 * @author 超悟空
 * @version 1.0 2018/9/25
 * @since 1.0
 */
class WxWithdrawWork : BaseSimpleWorkModel<BigDecimal, Unit>() {

    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: BigDecimal?) {
        dataMap["amount"] = params[0]?.multiply(BigDecimal(100))?.toPlainString()
    }

    override fun onSuccessExtract(jsonResult: JSONObject) = Unit

    @Post
    override fun onTaskUri() = ValueUrl.URL_WECHAT_WITHDRAW
}