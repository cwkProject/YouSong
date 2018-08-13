package com.yousong.yousong.architecture.livedata

import android.arch.lifecycle.MutableLiveData

/**
 * 网络服务提交结果生命周期数据
 *
 * @author 超悟空
 * @version 1.0 2018/8/13
 * @since 1.0 2018/8/13
 **/
class SubmitResultLiveData : MutableLiveData<SubmitResult>()

/**
 * 网络服务提交结果数据类
 *
 * @author 超悟空
 * @version 1.0 2018/8/13
 * @since 1.0 2018/8/13
 *
 * @property result 执行结果
 * @property message 需要显示的消息
 * @property level 消息显示级别
 **/
data class SubmitResult(val result: Boolean, val message: String? = null, val level: Int = LEVEL_UNKNOWN) {

    companion object {
        /**
         * 未知级别，由UI控制器决定
         */
        const val LEVEL_UNKNOWN = -1

        /**
         * 可忽略
         */
        const val LEVEL_IGNORE = 0

        /**
         * 用toast显示
         */
        const val LEVEL_TOAST = 1

        /**
         * 用long toast显示
         */
        const val LEVEL_LONG_TOAST = 2

        /**
         * 用弹窗显示，无按钮
         */
        const val LEVEL_ALERT = 3

        /**
         * 用弹窗显示，包含确定按钮
         */
        const val LEVEL_ALERT_WITH_OK = 4

        /**
         * 用弹窗显示，包含确定按钮，确认或取消后关闭界面
         */
        const val LEVEL_ALERT_FINISH = 5
    }
}