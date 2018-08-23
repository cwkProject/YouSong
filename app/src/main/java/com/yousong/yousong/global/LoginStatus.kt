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
     * 用户信息
     */
    val userInfo = UserInfo()

    /**
     * 重置数据
     */
    fun reset() {
        // 初始化用户参数
        login = false

        userInfo.reset()
    }
}