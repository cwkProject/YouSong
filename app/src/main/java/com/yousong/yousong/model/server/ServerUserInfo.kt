package com.yousong.yousong.model.server

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
data class ServerUserInfo(
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
        AppConfig.apply {
            nickname = this@ServerUserInfo.nickName
            avatar = this@ServerUserInfo.headPic
            mobile = this@ServerUserInfo.mobileNum
            wxUnionId = this@ServerUserInfo.unionId
            token = this@ServerUserInfo.token
            AppConfig.save()
        }

        LoginStatus.login = true
    }
}