package com.yousong.yousong.work.common

import com.yousong.yousong.R
import com.yousong.yousong.global.AppConfig
import org.cwk.android.library.global.Global
import org.cwk.android.library.network.factory.CommunicationBuilder
import org.cwk.android.library.work.SimpleWorkModel

/**
 * [SimpleWorkModel]的定制基类，加入基础设置
 *
 * @author 超悟空
 * @version 1.0 2017/3/6
 * @since 1.0 2017/3/6
 */
abstract class BaseSimpleWorkModel<Parameters, Result> : SimpleWorkModel<Parameters, Result>() {
    @SafeVarargs
    override fun onFill(dataMap: MutableMap<String, String?>, vararg parameters: Parameters?) {
        onFillParams(dataMap, *parameters)
    }

    /**
     * 填充服务请求所需的参数
     *
     * @param dataMap    将要填充的参数数据集<参数名></参数名>,参数值>
     * @param params 任务传入的参数
     */
    protected abstract fun onFillParams(dataMap: MutableMap<String, String?>, vararg params: Parameters?)

    override fun onCreateCommunication(builder: CommunicationBuilder) {
        super.onCreateCommunication(builder)
        AppConfig.token?.let {
            builder.addHeader("Authorization", "bearer $it")
        }
    }

    override fun onParseFailed(): String? = ERROR_NETWORK_REQUEST

    override fun onNetworkRequestFailed(): String? = ERROR_NETWORK_REQUEST

    override fun onNetworkError(): String? = ERROR_NETWORK

    companion object {
        /**
         * 网络请求失败信息
         */
        private val ERROR_NETWORK_REQUEST by lazy {
            Global.getApplication().getString(R.string.error_network_request)
        }

        /**
         * 网络连接失败信息
         */
        private val ERROR_NETWORK by lazy {
            Global.getApplication().getString(R.string.error_network)
        }
    }
}
