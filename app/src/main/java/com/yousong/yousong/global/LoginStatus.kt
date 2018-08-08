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
    }
}