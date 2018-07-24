package com.yousong.yousong.model

import com.yousong.yousong.value.ValueConst

/**
 * 广告详情
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 *
 * @property outTradeNo 充值订单号
 * @property cover 封面url
 * @property img 海报url
 * @property adsType 广告类型，1推广型，2定向型
 * @property userCount 目标广告人数
 * @property perYellowBoyUser 每位用户所得金额
 * @property needInvoice 是否需要发票
 * @property questionAnswer 问题
 * @property adsDirectional 定向条件
 * @property cityCode 城市码，代理商区分
 * @property adsId 广告id
 * @property name 广告名称
 */
data class ServerAdDetail(
        val outTradeNo: String,
        val cover: String,
        val img: String,
        val adsType: Int,
        val userCount: Int,
        val perYellowBoyUser: Int,
        val needInvoice: Int,
        val questionAnswer: ServerQuestion,
        val adsDirectional: ServerDirectional? = null,
        val cityCode: String? = null,
        val adsId: String = "",
        val name: String? = null)

/**
 * 定向筛选条件
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 *
 * @property sex 性别，1男，2女，3不限
 * @property minAge 最小年龄
 * @property minAge 最大年龄
 * @property latitude 纬度
 * @property longitude 经度
 * @property cityIds 城市代码
 * @property range 周边范围单位米
 */
data class ServerDirectional(
        val sex: Int = 3,
        val minAge: Int? = null,
        val maxAge: Int? = null,
        val latitude: Int? = null,
        val longitude: Int? = null,
        val cityIds: List<Int>? = null,
        val range: Int? = null)

/**
 * 问题
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 *
 * @property content 问题内容
 * @property reAnswerCount 重答次数
 * @property answers 答案选项
 * @property isMulti 是否多选
 */
data class ServerQuestion(
        val content: String,
        val reAnswerCount: Int,
        val answers: List<ServerAnswer>,
        val isMulti: Int = ValueConst.SERVER_FALSE)

/**
 * 答案选项
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 *
 * @property order 选项序号
 * @property content 答案内容
 * @property isAnswer 是否正确答案
 */
data class ServerAnswer(
        val order: Int,
        val content: String,
        val isAnswer: Int = ValueConst.SERVER_FALSE)