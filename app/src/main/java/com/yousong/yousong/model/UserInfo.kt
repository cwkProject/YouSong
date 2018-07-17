package com.yousong.yousong.model

import com.yousong.yousong.global.AppConfig
import com.yousong.yousong.global.LoginStatus

/**
 * 用户信息数据
 *
 * @author 超悟空
 * @version 1.0 2018/7/17
 * @since 1.0
 *
 * @property token 用户token
 * @property unionId 微信统一用户id
 * @property openId 微信开放平台id
 * @property nickName 用户昵称
 * @property headPic 用户头像
 * @property mobileNum 用户手机号
 */
data class UserInfo(
        val token: String,
        val unionId: String,
        val openId: String,
        val nickName: String,
        val headPic: String?,
        val mobileNum: String?) {

    /**
     * 登录成功设置全局属性
     */
    fun loginSuccess() {
        LoginStatus.apply {
            nickname = this@UserInfo.nickName
            avatar = this@UserInfo.headPic
            mobile = this@UserInfo.mobileNum
            unionId = this@UserInfo.unionId
            anonymous = false
            login = true
        }

        AppConfig.token = token
        AppConfig.save()
    }
}