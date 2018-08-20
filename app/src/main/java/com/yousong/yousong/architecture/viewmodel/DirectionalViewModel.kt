package com.yousong.yousong.architecture.viewmodel

import android.databinding.Bindable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.model.local.Directional

/**
 * 定向数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/8/5
 * @since 1.0
 */
class DirectionalViewModel : ObservableViewModel() {

    /**
     * 定向数据引用，由UI控制器传入
     */
    var directional: Directional? = null

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

    /**
     * 当前位置描述
     */
    @Bindable
    var currentLocation: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentLocation)
        }

    /**
     * 年龄不限选择状态变更
     */
    fun onAgeNotLimitedChecked(group: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            directional?.minAge = null
            directional?.maxAge = null
            maxSelection = -1
            minAges = emptyList()
            maxAges = emptyList()
        } else {
            minAges = (0..99).toList()
            maxAges = (0..99).toList()
            maxSelection = 65
        }
    }

    /**
     * 最小年龄选择事件
     */
    fun onMinAgeItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        directional?.minAge = minAges[position]

        directional?.let { directional ->
            directional.minAge?.let { min ->
                maxAges = (min..99).toList()

                maxAges.indexOf(directional.maxAge).let {
                    maxSelection = it
                }
            }
        }
    }

    /**
     * 最大年龄选择事件
     */
    fun onMaxAgeItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        directional?.maxAge = maxAges[position]

        directional?.maxAge?.let { max ->
            minAges = (0..max).toList()
        }
    }

    /**
     * 性别选中事件
     */
    fun onSexCheckedChanged(group: RadioGroup, checkedId: Int) {
        Log.v("Directional", "onSexCheckedChanged:$checkedId")
        directional?.sex = when (checkedId) {
            R.id.not_limited -> 3
            R.id.male -> 1
            R.id.female -> 2
            else -> 3
        }
    }

    /**
     * 目标地区选中事件
     */
    fun onDestinationCheckedChanged(group: RadioGroup, checkedId: Int) {
        directional?.locationType = when (checkedId) {
            R.id.not_limited -> 0
            R.id.local_perimeter -> 1
            R.id.designated_city -> 2
            else -> 0
        }
    }
}