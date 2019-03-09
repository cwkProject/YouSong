package com.yousong.yousong.architecture.databinding

import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.TextView
import com.yousong.yousong.R
import com.yousong.yousong.value.ValueConst

/**
 * 通用绑定适配器
 *
 * @author 超悟空
 * @version 1.0 2018/8/1
 * @since 1.0
 */
object BindingAdapters {

    @BindingAdapter("goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("invisibleUnless")
    @JvmStatic
    fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * 显示上传进度的文本
     *
     * @param process 百分比进度，忽略小数
     *
     * @see [ValueConst.PROGRESS_IDLE]
     * @see [ValueConst.PROGRESS_FAILED]
     * @see [ValueConst.PROGRESS_SUCCESS]
     */
    @BindingAdapter("uploadProgressText")
    @JvmStatic
    fun uploadProgressText(view: TextView, process: Int) {
        when (process) {
            ValueConst.PROGRESS_IDLE -> return
            ValueConst.PROGRESS_FAILED -> view.setText(R.string.failed_upload)
            ValueConst.PROGRESS_SUCCESS -> view.setText(R.string.success_upload)
            else -> view.text = view.context.getString(R.string.format_uploading).format(process)
        }
    }
}