package com.yousong.yousong.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.yousong.yousong.model.convert.*
import com.yousong.yousong.model.local.*

/**
 * Gson集合和扩展
 *
 * @author 超悟空
 * @version 1.0 2017/9/19
 * @since 1.0 2017/9/19
 **/
object GsonUtil {
    /**
     * gson
     */
    val gson = GsonBuilder()
            .registerTypeAdapter(Option::class.java, OptionTypeAdapter().nullSafe())
            .registerTypeAdapter(Question::class.java, QuestionTypeAdapter().nullSafe())
            .registerTypeAdapter(Directional::class.java, DirectionalTypeAdapter().nullSafe())
            .registerTypeAdapter(Ads::class.java, AdsTypeAdapter().nullSafe())
            .registerTypeAdapter(AdsDetail::class.java, AdsDetailTypeAdapter().nullSafe())
            .registerTypeAdapterFactory(MyCollectionTypeAdapterFactory())
            .create()
}

/**
 * 将对象转换为json字符串
 */
fun <T> T?.toJson(): String = GsonUtil.gson.toJson(this)

/**
 * 将json字符串转为对象
 */
inline fun <reified T> Gson.fromJson(json: String?): T = fromJson(json, T::class.java)

/**
 * 将json字符串转为对象
 */
inline fun <reified T> String.jsonToObject(): T = GsonUtil.gson.fromJson(this, T::class.java)

/**
 * 将json字符串转为有泛型的集合
 */
inline fun <reified T> String.jsonToCollection(): T = GsonUtil.gson.fromJson(this, object : TypeToken<T>() {}.type)