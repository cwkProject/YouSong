package com.yousong.yousong.model.server

/**
 * 微信响应数据
 *
 * @author 超悟空
 * @version 1.0 2017/3/1
 * @since 1.0 2017/3/1
 *
 * @property access_token 接口调用凭证
 * @property expires_in access_token接口调用凭证超时时间，单位（秒）
 * @property refresh_token 用户刷新access_token
 * @property openid 授权用户唯一标识
 * @property scope 用户授权的作用域，使用逗号（,）分隔
 * @property unionid 开放平台授权用户统一标识
 * @property nickname 昵称
 * @property sex 性别
 * @property province 省
 * @property city 市
 * @property country 国家
 * @property headimgurl 头像
 * @property privilege 用户特权
 */
data class WxResponse(
        val access_token: String? = null,
        val expires_in: Long = 0,
        val refresh_token: String? = null,
        val openid: String? = null,
        val scope: String? = null,
        val unionid: String? = null,
        val nickname: String? = null,
        val sex: Int = 1,
        val province: String? = null,
        val city: String? = null,
        val country: String? = null,
        val headimgurl: String? = null,
        val privilege: List<String>? = null)
