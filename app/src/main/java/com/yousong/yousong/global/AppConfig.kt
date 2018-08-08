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
     * 用户会话id
     */
    @Encrypt
    var token: String? = null

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
     * 用户头像地址
     */
    var avatar: String? = null

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

    init {
        refresh()
    }

    override fun onDefault() {
        super.onDefault()
        token = null
        wxLoginState = null
        wxAccessToken = null
        wxRefreshToken = null
        wxOpenId = null
        wxUnionId = null
    }
}
