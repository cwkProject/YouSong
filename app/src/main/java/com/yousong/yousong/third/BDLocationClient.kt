package com.yousong.yousong.third

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.yousong.yousong.R
import com.yousong.yousong.util.CheckAndroidMPermission
import org.cwk.android.library.global.Global

/**
 * 百度定位客户端
 *
 * @author 超悟空
 * @version 1.0 2018/8/17
 * @since 1.0 2018/8/17
 **/
object BDLocationClient {

    private const val TAG = "BDLocationClient"

    /**
     * 权限请求码
     */
    private const val PERMISSIONS_REQUEST_CODE = 999

    /**
     * 百度定位客户端
     */
    @SuppressLint("StaticFieldLeak")
    private val locationClient = LocationClient(Global.getApplication())

    init {
        locationClient.locOption = LocationClientOption().apply {
            isOpenGps = true
            setWifiCacheTimeOut(5 * 60 * 1000)
            setIsNeedAddress(true)
            setIsNeedLocationDescribe(true)
        }

        locationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(p0: BDLocation?) {
                // 目前仅单次定位
                locationClient.stop()
            }

            override fun onLocDiagnosticMessage(p0: Int, p1: Int, p2: String?) {
                Log.v(TAG, "onLocDiagnosticMessage locType:$p0 , message:$p2")
            }
        })
    }

    /**
     * 注册定位监听器
     *
     * @param listener 定位监听器
     */
    fun registerLocationListener(listener: BDAbstractLocationListener) {
        locationClient.registerLocationListener(listener)
    }

    /**
     * 注销定位监听器
     * @param listener 定位监听器
     */
    fun unregisterLocationListener(listener: BDAbstractLocationListener) {
        locationClient.unRegisterLocationListener(listener)
    }

    /**
     * 启动定位
     *
     * @param activity 启动定位的activity
     * @param fragment 启动定位的Fragment
     */
    fun start(activity: Activity, fragment: androidx.fragment.app.Fragment? = null) {
        if (CheckAndroidMPermission.checkPermission(activity,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE),
                        activity.getString(R.string.prompt_apply_location_permission),
                        PERMISSIONS_REQUEST_CODE, fragment)) {
            locationClient.start()
        }
    }

    /**
     * 处理权限请求回调
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 只检测定位权限，忽略另外两个
            locationClient.start()
        }
    }
}