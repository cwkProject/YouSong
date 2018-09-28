package com.yousong.yousong.architecture.viewmodel.ads

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import com.lljjcoder.citywheel.CityParseHelper
import com.yousong.yousong.BR
import com.yousong.yousong.architecture.livedata.SubmitResult
import com.yousong.yousong.architecture.livedata.SubmitResultLiveData
import com.yousong.yousong.architecture.viewmodel.common.ObservableViewModel
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.work.ads.AdsGetDetailWork
import com.yousong.yousong.work.ads.AdsPublishWork
import com.yousong.yousong.work.common.start
import org.cwk.android.library.global.Global

/**
 * 我的广告详情页面
 *
 * @author 超悟空
 * @version 1.0 2018/9/26
 * @since 1.0
 */
class MyAdsDetailViewModel : ObservableViewModel() {

    /**
     * 提交结果
     */
    val submitResult = SubmitResultLiveData()

    /**
     * 广告详情
     */
    val adsDetail = MutableLiveData<AdsDetail>()

    /**
     * 城市数据
     */
    private val cityParseHelper by lazy {
        CityParseHelper().apply {
            initData(Global.getApplication())
        }
    }

    /**
     * 城市地址列表
     */
    @Bindable
    var address: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }

    /**
     * 位置描述
     */
    @Bindable
    var location: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.location)
        }

    /**
     * 加载广告
     *
     * @param id 广告id
     */
    fun loadAds(id: String) {
        AdsGetDetailWork().start(id) {
            if (it.isSuccess) {
                adsDetail.value = it.result
            }
        }
    }

    /**
     * 发布广告
     */
    fun publish() {
        adsDetail.value?.ads?.id?.let { id ->
            AdsPublishWork().start(id) {
                if (it.isSuccess) {
                    loadAds(id)
                }

                submitResult.value = SubmitResult(it.isSuccess, it.message, SubmitResult.LEVEL_ALERT_WITH_OK)
            }
        }
    }

    /**
     * 获取位置数据
     */
    fun getLocation() {

    }

    /**
     * 匹配城市数据
     */
    fun matchCity() {

    }
}