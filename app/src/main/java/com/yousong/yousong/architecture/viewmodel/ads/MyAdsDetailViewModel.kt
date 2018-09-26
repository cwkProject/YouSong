package com.yousong.yousong.architecture.viewmodel.ads

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import com.yousong.yousong.BR
import com.yousong.yousong.architecture.viewmodel.common.ObservableViewModel
import com.yousong.yousong.model.local.AdsDetail
import com.yousong.yousong.work.ads.AdsGetDetailWork
import com.yousong.yousong.work.common.start

/**
 * 我的广告详情页面
 *
 * @author 超悟空
 * @version 1.0 2018/9/26
 * @since 1.0
 */
class MyAdsDetailViewModel : ObservableViewModel() {

    /**
     * 广告详情
     */
    val adsDetail = MutableLiveData<AdsDetail>()

    /**
     * 城市地址列表
     */
    @Bindable
    var address: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }

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
        if (adsDetail.value != null && adsDetail.value?.ads?.id == id) {
            // 过滤重复请求
            return
        }

        AdsGetDetailWork().start(id) {
            if (it.isSuccess) {
                adsDetail.value = it.result
            }
        }
    }
}