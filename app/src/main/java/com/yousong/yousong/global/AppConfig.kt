package com.yousong.yousong.global

import com.yousong.yousong.BuildConfig
import org.cwk.android.library.annotation.Encrypt
import org.cwk.android.library.architecture.preferences.PersistenceConfigModel
import org.cwk.android.library.global.ApplicationStaticValue
import org.cwk.android.library.global.Global

/**
 * 应用配置文件
 *
 * @author 超悟空
 * @version 1.0 2017/3/1
 * @since 1.0 2017/3/1
 */
object AppConfig : PersistenceConfigModel(Global.getApplication(), ApplicationStaticValue
        .AppConfig.APPLICATION_CONFIG_FILE_NAME + if (BuildConfig.DEBUG) "_debug" else "") {

    /**
     * 用户标识
     */
    @Encrypt
    var accountId: String? = null

    /**
     * 用户会话id
     */
    @Encrypt
    var sessionId: String? = null

    /**
     * 用户头像地址
     */
    var userHeadImageUrl: String? = null

    /**
     * 用户昵称
     */
    var nickname: String? = null

    /**
     * 手机号
     */
    @Encrypt
    var mobile: String? = null

    /**
     * 邮箱
     */
    @Encrypt
    var email: String? = null

    /**
     * 用户性别，1男性，2女性
     */
    var sex = 1

    /**
     * 是否匿名用户
     */
    var anonymous = false

    /**
     * 微信登录状态保存
     */
    var wxLoginState: String? = null

    /**
     * 微信接口调用凭证
     */
    @Encrypt
    var wxAccessToken: String? = null

    /**
     * 微信用户刷新access_token
     */
    @Encrypt
    var wxRefreshToken: String? = null

    /**
     * 微信授权用户唯一标识
     */
    @Encrypt
    var wxOpenId: String? = null

    /**
     * 微信开放平台授权用户统一标识
     */
    @Encrypt
    var wxUnionId: String? = null

    /**
     * 是否支持微信功能
     */
    var weChatEnable = true

    /**
     * 是否支持邮箱功能
     */
    var emailEnable = true

    /**
     * 是否支持手机号功能
     */
    var mobileEnable = true

    init {
        refresh()
    }

    override fun onDefault() {
        super.onDefault()
        accountId = null
        sessionId = null
        wxLoginState = null
        wxAccessToken = null
        wxRefreshToken = null
        wxOpenId = null
        wxUnionId = null
        userHeadImageUrl = null
        sex = 1
        anonymous = false
        weChatEnable = true
        emailEnable = true
        mobileEnable = true
    }

    /**
     * 保存登录状态
     */
    fun saveLogin() {
        accountId = LoginStatus.accountId
        sessionId = LoginStatus.sessionId
        mobile = LoginStatus.mobile
        email = LoginStatus.email
        userHeadImageUrl = LoginStatus.userHeadImageUrl
        sex = LoginStatus.sex
        wxUnionId = LoginStatus.unionId
        anonymous = false
        save()
    }
}
