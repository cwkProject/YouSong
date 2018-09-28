package com.yousong.yousong.architecture.viewmodel.ads

import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.yousong.yousong.BR
import com.yousong.yousong.R
import com.yousong.yousong.architecture.viewmodel.common.ObservableViewModel
import com.yousong.yousong.model.local.Address
import com.yousong.yousong.model.local.Directional
import com.yousong.yousong.third.BDLocationClient
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.work.ads.AdsFilterUserWork
import com.yousong.yousong.work.common.syncStart
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.math.abs

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
        set(value) {
            if (field != value) {
                field = value
                onAddPropertyCallback()
                onInitAge()
            }
        }

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
     * 当前最小年龄选中序号
     */
    @Bindable
    var minSelection: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.minSelection)
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
     * 当前筛选目标人数
     */
    @Bindable
    var currentTargetCount = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentTargetCount)
        }

    /**
     * 定向条件改变监听器
     */
    private val directionalChangedCallback = DirectionalChangedCallback()

    /**
     * 定位监听器
     */
    private val locationListener = object : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            if (location.locationDescribe != null) {
                currentLocation = location.locationDescribe
                directional?.latitude = location.latitude
                directional?.longitude = location.longitude
            }
        }
    }

    init {
        BDLocationClient.registerLocationListener(locationListener)
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
        directional?.sex = when (checkedId) {
            R.id.not_limited -> 0
            R.id.male -> 1
            R.id.female -> 2
            else -> 0
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

    /**
     * 初始化定向年龄数据
     */
    private fun onInitAge() {
        directional?.let {
            val minAge = it.minAge
            val maxAge = it.maxAge
            if (minAge != null && maxAge != null && (minAge > 0 || maxAge > 0)) {
                // 已存在年龄数据
                limitedAges = true
                maxSelection = if (maxAge == 0) 99 else maxAge
                minSelection = minAge
            }
        }
    }

    /**
     * 添加定向数据变更监听器
     */
    private fun onAddPropertyCallback() {
        directional?.addOnPropertyChangedCallback(directionalChangedCallback)
        directional?.addresses?.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<Address>>() {
            override fun onChanged(sender: ObservableArrayList<Address>?) {
            }

            override fun onItemRangeRemoved(sender: ObservableArrayList<Address>?, positionStart: Int, itemCount: Int) {
            }

            override fun onItemRangeMoved(sender: ObservableArrayList<Address>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            }

            override fun onItemRangeInserted(sender: ObservableArrayList<Address>, positionStart: Int, itemCount: Int) {
                for (i in positionStart until positionStart + itemCount) {
                    sender[i].addOnPropertyChangedCallback(directionalChangedCallback)
                }
            }

            override fun onItemRangeChanged(sender: ObservableArrayList<Address>?, positionStart: Int, itemCount: Int) {
            }
        })
    }

    /**
     * 检测定向数据合法性
     *
     * @return true表示条件合法
     */
    private fun onCheckDirectional(): Boolean {
        when (directional?.locationType) {
            ValueConst.LOCATION_TYPE_LOCAL_REGION -> {
                directional?.apply {
                    return when {
                        abs(latitude) < 10E-6 && abs(longitude) < 10E-6 -> false
                        range?.takeIf { it > 0 } == null -> false
                        else -> true
                    }
                }
            }
            ValueConst.LOCATION_TYPE_TARGET_CITY -> {
                directional?.apply {
                    return when {
                        addresses.isEmpty() -> false
                        addresses.any { it.addressCode == null } -> false
                        else -> true
                    }
                }
            }
        }

        return true
    }

    override fun onCleared() {
        BDLocationClient.unregisterLocationListener(locationListener)
        directionalChangedCallback.stop()
    }

    /**
     * 定向条件改变监听器
     */
    private inner class DirectionalChangedCallback : Observable.OnPropertyChangedCallback() {

        /**
         * 数据发射器
         */
        private val publish = PublishSubject.create<Int>()

        /**
         * 筛选任务
         */
        private val work = AdsFilterUserWork()

        init {
            // 200毫秒的限流器
            publish.debounce(200L, TimeUnit.MILLISECONDS)
                    .filter { onCheckDirectional() }
                    .doOnNext { work.cancel() }
                    .observeOn(Schedulers.newThread())
                    .subscribe {
                        work.syncStart(directional) {
                            if (it.isSuccess) {
                                currentTargetCount = it.result
                            }
                        }
                    }
        }

        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            publish.onNext(0)
        }

        /**
         * 停止
         */
        fun stop() = publish.onComplete()
    }
}