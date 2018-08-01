package com.yousong.yousong.architecture.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yousong.yousong.model.local.UserAssets

/**
 * 用户相关数据的数据模型
 *
 * @author 超悟空
 * @version 1.0 2018/7/14
 * @since 1.0
 */
class UserViewModel : ViewModel() {

    /**
     * 用户资产数据
     */
    val userAssetsData = MutableLiveData<UserAssets>()
}