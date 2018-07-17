package com.yousong.yousong.global

/**
 * 登录状态数据类
 *
 * @author 超悟空
 * @version 1.0 2017/2/15
 * @since 1.0
 */
object LoginStatus {

    /**
     * 标记是否已登录
     */
    var login = false

    /**
     * 用户昵称
     */
    var nickname: String? = null

    /**
     * 手机号
     */
    var mobile: String? = null

    /**
     * 微信唯一id
     */
    var unionId: String? = null

    /**
     * 用户头像地址
     */
    var avatar: String? = null

    /**
     * 是否是匿名用户
     */
    var anonymous = false

    /**
     * 重置数据
     */
    fun reset() {
        onCreate()
    }

    /**
     * 初始化参数
     */
    private fun onCreate() {
        // 初始化用户参数
        login = false
        mobile = null
        nickname = null
        avatar = null
        anonymous = false
    }
}