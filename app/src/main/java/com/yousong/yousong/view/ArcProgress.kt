package com.yousong.yousong.view

import android.content.Context
import android.util.AttributeSet
import com.github.lzyzsd.circleprogress.ArcProgress

/**
 * 环形进度条，增加显示数据和进度数据的比例关系
 *
 * @author 超悟空
 * @version 1.0 2018/9/16
 * @since 1.0
 */
class ArcProgress(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ArcProgress(context, attrs, defStyleAttr) {

    /**
     * 进度实际值和显示值的比例，progress / progressScale = centerText
     */
    var progressScale = 1

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun getProgress(): Int {
        return super.getProgress() / progressScale
    }
}