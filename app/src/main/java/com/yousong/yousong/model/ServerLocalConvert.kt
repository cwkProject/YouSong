package com.yousong.yousong.model

/**
 * 服务器数据类与本地数据模型互转接口，推荐由服务器数据类实现
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 *
 * @param Server 服务器数据类类型
 * @param Local 与本服务器数据类对应的本地数据模型类型
 */
interface ServerLocalConvert<Server, Local> {

    /**
     * 转换为本地数据模型
     *
     * @return 本地数据类
     */
    fun toLocal(): Local

    /**
     * 转换为服务器数据类
     *
     * @return 服务器数据类
     */
    fun Local.toServer(): Server
}