package com.yousong.yousong.work.user

import com.yousong.yousong.value.ValueUrl
import com.yousong.yousong.work.common.BaseSimpleWorkModel
import org.json.JSONObject

/**
 * 用户邀请的好友数量
 *
 * @author 超悟空
 * @version 1.0 2018/7/17
 * @since 1.0
 */
class UserRefereeCountWork : BaseSimpleWorkModel<Unit, Int>() {
    override fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Unit?) = Unit

    override fun onSuccessExtract(jsonResult: JSONObject) = jsonResult.getInt(RESULT)

    override fun onTaskUri() = ValueUrl.URL_USER_REFEREE_COUNT
}