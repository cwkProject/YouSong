package com.yousong.yousong.global

import android.content.Context
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * 登录成功后加载用户相关数据
 *
 * @author 超悟空
 * @version 1.0 2017/3/30
 * @since 1.0 2017/3/30
 */
object LoadUserData {

    /**
     * 启动数据加载
     *
     * @param listener 加载完成监听器
     */
    inline fun loadBegin(context: Context, crossinline listener: () -> Unit) {
        val count = CountDownLatch(7)

        thread {
            count.await()

            listener()
        }
    }

    /**
     * 完成一条数据加载
     *
     * @param listener 加载完成监听器
     */
    inline fun finish(count: AtomicInteger, listener: () -> Unit) {
        if (count.decrementAndGet() == 0) {
            listener()
        }
    }
}
