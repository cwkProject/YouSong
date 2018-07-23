package com.yousong.yousong.common

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import com.yousong.yousong.global.MyApplication
import org.cwk.android.library.architecture.broadcast.BroadcastUtil
import org.cwk.android.library.architecture.recycler.RecyclerViewAdapterFunction
import org.cwk.android.library.architecture.recycler.RecyclerViewHolderManager
import org.cwk.android.library.global.Global
import java.io.File
import java.security.MessageDigest


/**
 * 扩展方法集合
 *
 * @author 超悟空
 * @version 1.0 2017/6/30
 * @since 1.0 2017/6/30
 **/

/**
 * 获取文件的MD5
 */
fun File?.md5(): String = if (this == null || !this.exists() || !this.isFile) "" else getFileMD5(this)

/**
 * 计算MD5
 *
 * @param file 文件
 */
private fun getFileMD5(file: File): String {
    val buffer = ByteArray(8 * 1024)
    val digest = MessageDigest.getInstance("MD5")
    file.inputStream().use {
        var bytes = it.read(buffer)
        while (bytes >= 0) {
            digest.update(buffer, 0, bytes)
            bytes = it.read(buffer)
        }
    }

    return bytesToHexString(digest.digest())
}

/**
 * 转换字节数组为16进制
 * @param src 源
 */
private fun bytesToHexString(src: ByteArray): String {
    val stringBuilder = StringBuilder()
    src.forEach {
        val hv = Integer.toHexString(it.toInt() and 0xFF)
        if (hv.length < 2) {
            stringBuilder.append(0)
        }
        stringBuilder.append(hv)
    }
    return stringBuilder.toString()
}

/**
 * 加入缓存
 *
 * @param pair <标签 to 数据>
 */
@Suppress("IfThenToSafeAccess")
operator fun Application.plusAssign(pair: Pair<String, Any>) {
    if (this is MyApplication) put(pair.first, pair.second)
}

/**
 * 加入缓存
 *
 * @param key  标签
 * @param value 数据
 */
@Suppress("IfThenToSafeAccess")
operator fun Application.set(key: String, value: Any?) {
    if (this is MyApplication) put(key, value)
}

/**
 * 获取缓存
 *
 * @param key 标签
 *
 * @return 数据
 */
inline operator fun <reified T> Application.get(key: String): T? = if (this is MyApplication) get(key) as? T else null

/**
 * 弹出缓存
 *
 * @param key 标签
 *
 * @return 被移除的数据
 */
inline fun <reified T> Application.pop(key: String): T? = if (this is MyApplication) pop(key) as? T else null

/**
 * 移除指定位置的数据
 *
 * @param key 标签
 */
operator fun Application.minusAssign(key: String) {
    (this as? MyApplication)?.pop(key)
}

/**
 * 匹配缓存
 *
 * @param tag 标签
 *
 * @return true表示缓存存在，false表示缓存不存在
 */
operator fun Application.contains(tag: String): Boolean = (this as? MyApplication)?.contains(tag) == true

/**
 * 在末尾增加一条数据
 *
 * @param data 数据
 */
operator fun <T> RecyclerViewAdapterFunction<T>.plusAssign(data: T?) = add(data)

/**
 * 在末尾增加一组数据
 *
 * @param data 数据集合
 */
operator fun <T> RecyclerViewAdapterFunction<T>.plusAssign(data: Collection<T>?) = add(data)

/**
 * 在指定位置插入一条数据
 *
 * @param pair <index,data>
 */
operator fun <T> RecyclerViewAdapterFunction<T>.plusAssign(pair: Pair<Int, T?>) = add(pair.first, pair.second)

/**
 * 替换指定位置的数据
 *
 * @param index 索引
 * @param data 新数据
 */
operator fun <T> RecyclerViewAdapterFunction<T>.set(index: Int, data: T?) {
    change(index, data)
}

/**
 * 移除指定位置的数据
 *
 * @param index 索引
 */
operator fun <T> RecyclerViewAdapterFunction<T>.minusAssign(index: Int) {
    remove(index)
}

/**
 * 移除符合条件的数据
 *
 * @param filter 筛选条件
 */
fun <T> RecyclerViewAdapterFunction<T>.removeAll(filter: (T) -> Boolean) {
    val removeIndex: MutableList<Int> = ArrayList()

    list.forEachIndexed { index, t ->
        if (filter(t)) {
            removeIndex.add(index)
        }
    }

    removeIndex.forEachIndexed { index, i ->
        remove(i - index)
    }
}

/**
 * 更新符合条件的数据
 *
 * @param filter 筛选条件
 */
fun <T> RecyclerViewHolderManager<T, *>.notifyChangeAll(filter: (T) -> Boolean) {
    val changeIndex: MutableList<Int> = ArrayList()

    list.forEachIndexed { index, t ->
        if (filter(t)) {
            changeIndex.add(index)
        }
    }

    changeIndex.forEach {
        notifyItemChanged(it)
    }
}

/**
 * 更新符合条件的数据
 *
 * @param filter 筛选条件
 */
fun <T> RecyclerViewAdapterFunction<T>.notifyChangeAll(filter: (T) -> Boolean) {
    val changeIndex: MutableList<Int> = ArrayList()

    list.forEachIndexed { index, t ->
        if (filter(t)) {
            changeIndex.add(index)
        }
    }

    changeIndex.forEach {
        change(it, get(it))
    }
}

/**
 * 发送广播
 *
 * @param action  动作字符串
 */
fun sendLocalBroadcast(action: String) {
    BroadcastUtil.sendBroadcast(Global.getApplication(), action)
}

/**
 * 发送广播
 *
 * @param intent  包含一组动作字符串的意图
 */
fun sendLocalBroadcast(intent: Intent) {
    BroadcastUtil.sendBroadcast(Global.getApplication(), intent)
}

/**
 * 强制转换为[T]类型
 */
inline fun <reified T> Any?.asOther(): T? = this as? T

/**
 * 为意图过滤器添加动作
 *
 * @param action 动作字符串
 */
operator fun IntentFilter.plusAssign(action: String) = addAction(action)