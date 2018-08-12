package com.yousong.yousong.global

import com.yousong.yousong.model.server.Auth
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserGetAuthDetailWork

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
     * 个人认证信息
     */
    var personalAuth: Auth? = null

    /**
     * 企业认证信息
     */
    var companyAuth: Auth? = null

    /**
     * 登录成功后加载用户数据，重复调用会刷新用户数据
     *
     * @param call 加载完成后回调
     */
    fun loadUserData(call: (() -> Unit)? = null) {
        if (login) {
            UserGetAuthDetailWork().start {
                call?.invoke()
            }
        } else {
            call?.invoke()
        }
    }

    /**
     * 重置数据
     */
    fun reset() {
        // 初始化用户参数
        login = false
        personalAuth = null
        companyAuth = null

        UserAssets.reset()
    }
}