package com.yousong.yousong.global

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.yousong.yousong.common.sendLocalBroadcast
import com.yousong.yousong.third.WechatFunction
import com.yousong.yousong.value.ValueAction
import com.yousong.yousong.value.ValueKey
import com.yousong.yousong.work.common.start
import com.yousong.yousong.work.user.UserCreateTokenWork
import org.cwk.android.library.global.ApplicationAttribute
import org.cwk.android.library.global.Global
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * 自定义Application
 *
 * @author 超悟空
 * @version 1.0 2018/6/26
 * @since 1.0
 */
class MyApplication : Application() {

    private val TAG = this.javaClass.simpleName

    /**
     * 全局缓存
     */
    private val cache: MutableMap<String, Any?> = ConcurrentHashMap()

    /**
     * 数据加载计数器
     */
    private val count = AtomicInteger(1)

    override fun onCreate() {
        super.onCreate()

        if (currentProcessName == packageName) {
            init()
        }
    }

    /**
     * 数据初始化
     */
    private fun init() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // 初始化全局对象
        onInitGlobal()

        onRegisterApi()

        onInitData()
    }

    /**
     * 获取当前进程名称
     *
     * @return 进程名
     */
    private val currentProcessName: String?
        get() {
            val pid = android.os.Process.myPid()
            val mActivityManager = getSystemService(Context
                    .ACTIVITY_SERVICE) as ActivityManager
            return mActivityManager
                    .runningAppProcesses
                    .firstOrNull { it.pid == pid }
                    ?.processName
        }

    /**
     * 初始化全局对象
     */
    private fun onInitGlobal() {
        // 配置全局上下文
        Global.init(this)

        // 初始化系统参数
        ApplicationAttribute.create().desKey(ValueKey.DES_KEY)
    }

    /**
     * 注册第三方api
     */
    private fun onRegisterApi() {
        // 微信开放平台注册
        WechatFunction.register()

        // bugly
        //   Beta.tipsDialogLayoutId = R.layout.layout_tips_dialog
        //   Beta.upgradeDialogLayoutId = R.layout.layout_update_dialog
        //  Bugly.init(this, ValueKey.BUGLY_APP_ID, BuildConfig.DEBUG)

        // 极光
        //   JPushInterface.init(this)
    }

    /**
     * 加入缓存
     *
     * @param tag  标签
     * @param data 数据
     */
    fun put(tag: String, data: Any?) {
        if (data != null) {
            cache[tag] = data
        }
    }

    /**
     * 获取缓存
     *
     * @param tag 标签
     *
     * @return 数据
     */
    operator fun get(tag: String): Any? = cache[tag]

    /**
     * 匹配缓存
     *
     * @param tag 标签
     *
     * @return true表示缓存存在，false表示缓存不存在
     */
    operator fun contains(tag: String): Boolean = cache.contains(tag)

    /**
     * 弹出缓存
     *
     * @param tag 标签
     *
     * @return 被移除的数据
     */
    fun pop(tag: String): Any? = cache.remove(tag)

    /**
     * 判断是否已加载完成
     *
     * @return true表示加载完成
     */
    val isFinish: Boolean
        get() = count.get() == 0

    /**
     * 初始化数据
     */
    fun initData() {
        onInitData()
    }

    /**
     * 初始化数据
     */
    private fun onInitData() {
        count.set(1)

        onAutoLogin()
    }

    /**
     * 自动登陆
     */
    private fun onAutoLogin() {
        if (AppConfig.token != null) {
            UserCreateTokenWork().start {
                onFinish()
            }
        } else {
            onFinish()
        }

//        UserCreateTokenWork().start {
//            onFinish()
//        }
    }

    /**
     * 完成一条数据加载
     */
    private fun onFinish() {
        if (count.decrementAndGet() == 0) {
            sendLocalBroadcast(ValueAction.ACTION_LOAD_DATA_FINISH)
        }
    }
}