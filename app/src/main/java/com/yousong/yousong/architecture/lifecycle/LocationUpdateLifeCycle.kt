package com.yousong.yousong.architecture.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.yousong.yousong.third.BDLocationClient
import com.yousong.yousong.work.user.UserUploadLocationWork

/**
 * 位置上报组件
 *
 * @author 超悟空
 * @version 1.0 2018/8/17
 * @since 1.0 2018/8/17
 *
 * @param activity 绑定生命周期的activity
 **/
class LocationUpdateLifeCycle(activity: AppCompatActivity) : LifecycleObserver {

    private val TAG = "LocationUpdateLifeCycle"

    /**
     * 定位监听器
     */
    private val locationListener = LocationListener()

    init {
        activity.lifecycle.addObserver(this)

        // 启动组件，目前仅上报一次
        BDLocationClient.registerLocationListener(locationListener)
        BDLocationClient.start(activity)
    }

    /**
     * 销毁组件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        BDLocationClient.unregisterLocationListener(locationListener)
    }

    /**
     * 定位监听器
     */
    private inner class LocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            Log.v(TAG, "onReceiveLocation adCode:${location.adCode}, describe:${location.locationDescribe} ,address:${location.addrStr} ")

            if (location.adCode != null) {
                UserUploadLocationWork().beginExecute(location.adCode, location.latitude.toString(), location.longitude.toString())
            }
        }
    }
}