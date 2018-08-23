package com.yousong.yousong.architecture.livedata

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast

/**
 * 网络服务提交结果生命周期数据
 *
 * @author 超悟空
 * @version 1.0 2018/8/13
 * @since 1.0 2018/8/13
 **/
class SubmitResultLiveData : MutableLiveData<SubmitResult>() {
    override fun setValue(value: SubmitResult?) {
        super.setValue(value)
        if (value != null) {
            setValue(null)
        }
    }
}

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

    /**
     * 显示结果消息，显示不成功时可能需要UI控制器进一步处理显示
     *
     * @param activity UI界面
     *
     * @return 显示结果，true表示成功显示，false表示未能显示，可能情况有message为空，level为[SubmitResult.LEVEL_UNKNOWN]，[SubmitResult.LEVEL_IGNORE]
     */
    fun show(activity: Activity): Boolean {
        if (message == null || message.isBlank() || level <= 0) {
            return false
        }

        when (level) {
            SubmitResult.LEVEL_TOAST -> activity.toast(message)
            SubmitResult.LEVEL_LONG_TOAST -> activity.longToast(message)
            SubmitResult.LEVEL_ALERT -> activity.alert(message).show()
            SubmitResult.LEVEL_ALERT_WITH_OK -> activity.alert(message) {
                okButton {}
            }.show()
            SubmitResult.LEVEL_ALERT_FINISH -> activity.alert(message) {
                okButton { activity.finish() }
                onCancelled { activity.finish() }
            }.show()
            else -> return false
        }
        return true
    }
}