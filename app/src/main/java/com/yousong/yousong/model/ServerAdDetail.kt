package com.yousong.yousong.model

import com.yousong.yousong.architecture.databinding.*
import com.yousong.yousong.value.ValueConst

/**
 * 广告详情
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 *
 * @property ads 广告基本信息
 * @property questionAnswer 问题
 * @property adsDirectional 定向条件
 */
data class ServerAdDetail(
        val ads: ServerAd,
        val questionAnswer: ServerQuestion,
        val adsDirectional: ServerDirectional? = null) : ServerLocalConvert<ServerAdDetail, AdDetail> {
    override fun toLocal(): AdDetail {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun AdDetail.toServer(): ServerAdDetail {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

/**
 * 广告数据
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 *
 * @property name 广告名称
 * @property cityCode 城市码，代理商区分
 * @property cover 封面
 * @property img 大图海报
 * @property adsType 广告类型，1推广型，2定向型
 * @property userCount 目标广告人数
 * @property perYellowBoyUser 每位用户所得金额
 * @property needInvoice 是否需要发票
 * @property totalAsset 总金额
 * @property balance 余额
 * @property adsId 广告id
 * @property city 城市名称
 */
data class ServerAd(
        val name: String,
        val cityCode: String,
        val cover: String,
        val img: String,
        val adsType: Int,
        val userCount: Int,
        val perYellowBoyUser: Int,
        val needInvoice: Int,
        val adsId: String = "",
        val totalAsset: Long = 0L,
        val balance: Long = 0L,
        val city: String? = null) : ServerLocalConvert<ServerAd, Ad> {

    override fun toLocal(): Ad {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Ad.toServer(): ServerAd {
        TODO("not implemented")
    }
}

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
        val range: Int? = null) : ServerLocalConvert<ServerDirectional, Directional> {
    override fun toLocal(): Directional {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Directional.toServer(): ServerDirectional {
        TODO("not implemented")
    }
}

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
        val isMulti: Int = ValueConst.SERVER_FALSE) : ServerLocalConvert<ServerQuestion, Question> {
    override fun toLocal(): Question {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Question.toServer(): ServerQuestion {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

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
        val isAnswer: Int = ValueConst.SERVER_FALSE) : ServerLocalConvert<ServerAnswer, Answer> {

    override fun toLocal(): Answer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Answer.toServer(): ServerAnswer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}