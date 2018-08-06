package com.yousong.yousong.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.yousong.yousong.R
import java.util.*

/**
 * 用于检测android 6.0以后权限
 *
 * @author 超悟空
 * @version 1.0 2017/4/10
 * @since 1.0 2017/4/10
 */
object CheckAndroidMPermission {

    /**
     * 从fragment检查权限，如果传入的fragment是null，则后面请求权限时会调用ActivityCompat
     * 的方法
     * 检查6.0及以上版本时，应用是否拥有某个权限，拥有则返回true，未拥有则再判断上次
     * 用户是否拒绝过该权限的申请（拒绝过则shouldShowRequestPermissionRationale返回
     * true——这里有些手机如红米(红米 pro)永远返回 false
     * 这里的处理是弹一个对话框引导用户去应用的设置界面打开权限，返回false时这里执行
     * requestPermissions方法，此方法会显示系统默认的一个权限授权提示对话框，并在
     * Activity或Fragment的onRequestPermissionsResult得到回调，注意方法中的requestCode
     * 要与此处相同）
     *
     * @param fragment    如果fragment不为null则调用fragment的方法申请权限（因为有些手机上在Fragment调用ActivityCompat的 方法申请权限得不到回调，例如红米手机）
     * @param activity    用于弹出提示窗和获取权限
     * @param permission  对应的权限名称，如：Manifest.permission.CAMERA
     * @param hint        引导用户进入设置界面对话框的提示文字
     * @param requestCode 请求码，对应Activity或Fragment的onRequestPermissionsResult 方法的requestCode
     * @param canceledListener 取消权限授予回调
     *
     * @return true-拥有对应的权限 false：未拥有对应的权限
     */
    fun checkPermission(activity: Activity,
                        permission: String,
                        hint: String,
                        requestCode: Int,
                        fragment: Fragment? = null,
                        canceledListener: () -> Unit = {}): Boolean =
            checkPermission(activity, arrayOf(permission), hint, requestCode, fragment, canceledListener)

    /**
     * 从fragment检查权限，如果传入的fragment是null，则后面请求权限时会调用ActivityCompat
     * 的方法
     * 检查6.0及以上版本时，应用是否拥有某个权限，拥有则返回true，未拥有则再判断上次
     * 用户是否拒绝过该权限的申请（拒绝过则shouldShowRequestPermissionRationale返回
     * true——这里有些手机如红米(红米 pro)永远返回 false
     * 这里的处理是弹一个对话框引导用户去应用的设置界面打开权限，返回false时这里执行
     * requestPermissions方法，此方法会显示系统默认的一个权限授权提示对话框，并在
     * Activity或Fragment的onRequestPermissionsResult得到回调，注意方法中的requestCode
     * 要与此处相同）
     *
     * @param fragment    如果fragment不为null则调用fragment的方法申请权限（因为有些手机
     *                    上在Fragment调用ActivityCompat的 方法申请权限得不到回调，例如红米手机）
     * @param activity    用于弹出提示窗和获取权限
     * @param permissions 对应的权限名称集合
     * @param hint        引导用户进入设置界面对话框的提示文字
     * @param requestCode 请求码，对应Activity或Fragment的onRequestPermissionsResult 方法的requestCode
     * @param canceledListener 取消权限授予回调
     *
     * @return true-拥有对应的权限 false：未拥有对应的权限
     */
    fun checkPermission(activity: Activity,
                        permissions: Array<String>,
                        hint: String,
                        requestCode: Int,
                        fragment: Fragment? = null,
                        canceledListener: () -> Unit = {}): Boolean {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        val permissionList = ArrayList<String>()

        // 是否已显示了提示框
        var show = false

        permissions.forEach {
            //检查权限
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                    show = true
                }
                permissionList += it
            }
        }

        if (show) {
            showPermissionSettingDialog(activity, hint, object : OnPermissionSettingDialogListener {
                override fun onPositive() {
                    //申请权限
                    if (fragment == null) {
                        ActivityCompat.requestPermissions(activity, permissionList.toTypedArray(), requestCode)
                    } else {
                        fragment.requestPermissions(permissionList.toTypedArray(), requestCode)
                    }
                }

                override fun onCancel() {
                    canceledListener()
                }
            })
            return false
        } else {
            return if (permissionList.size > 0) {
                //申请权限
                if (fragment == null) {
                    ActivityCompat.requestPermissions(activity, permissionList.toTypedArray(), requestCode)
                } else {
                    fragment.requestPermissions(permissionList.toTypedArray(), requestCode)
                }
                false
            } else {
                true
            }
        }
    }

    /**
     * 显示权限提示框
     *
     * @param context 上下文
     * @param hint    提示
     */
    private fun showPermissionSettingDialog(context: Context, hint: String,
                                            listener: OnPermissionSettingDialogListener?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.title_apply_permission))
        builder.setMessage(hint)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            listener?.onPositive()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
            listener?.onCancel()
        }

        builder.show()
    }

    /**
     * 提示框处理监听
     */
    interface OnPermissionSettingDialogListener {

        /**
         * 确认
         */
        fun onPositive()

        /**
         * 取消
         */
        fun onCancel()
    }
}
