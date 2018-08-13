package com.yousong.yousong.function

import android.content.Context
import com.yousong.yousong.architecture.livedata.SubmitResult
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

/**
 * 用于展示[SubmitResult]
 *
 * @author 超悟空
 * @version 1.0 2018/8/13
 * @since 1.0 2018/8/13
 **/
object SubmitResultUtil {

    /**
     * 显示结果消息，显示不成功时可能需要UI控制器进一步处理显示
     *
     * @param context 上下文
     * @param submitResult 需要展示的数据
     *
     * @return 显示结果，true表示成功显示，false表示未能显示，可能情况有message为空，level为[SubmitResult.LEVEL_UNKNOWN]，[SubmitResult.LEVEL_IGNORE]
     */
    fun show(context: Context, submitResult: SubmitResult): Boolean {
        val message = submitResult.message

        if (message == null || message.isBlank() || submitResult.level <= 0) {
            return false
        }

        when (submitResult.level) {
            SubmitResult.LEVEL_TOAST -> context.toast(message)
            SubmitResult.LEVEL_LONG_TOAST->context.longToast(message)
        }

        return true
    }
}