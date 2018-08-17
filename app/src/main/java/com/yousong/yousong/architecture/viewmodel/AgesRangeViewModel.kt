package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import com.yousong.yousong.BR

/**
 * 年龄选择器的数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/5
 * @since 1.0
 */
class AgesRangeViewModel : ObservableViewModel() {

    /**
     * 是否限制年龄
     */
    @Bindable
    var limitedAges = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.limitedAges)
        }

    /**
     * 最小年龄可选范围
     */
    @Bindable
    var minAges: List<Int> = emptyList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.minAges)
        }

    /**
     * 最大年龄可选范围
     */
    @Bindable
    var maxAges: List<Int> = emptyList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxAges)
        }

    /**
     * 当前最大年龄选中序号
     */
    @Bindable
    var maxSelection: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.maxSelection)
        }
}