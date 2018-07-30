package com.yousong.yousong.convert

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.yousong.yousong.architecture.databinding.*
import com.yousong.yousong.common.GsonUtil
import com.yousong.yousong.model.ServerLocalConvert
import com.yousong.yousong.value.ValueConst
import java.math.BigDecimal

/**
 * 广告详情转换器
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
 * 广告数据转换器
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
 * 定向筛选条件转换器
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
class DirectionalTypeAdapter(
        val sex: Int = 3,
        val minAge: Int? = null,
        val maxAge: Int? = null,
        val latitude: Int? = null,
        val longitude: Int? = null,
        val cityIds: List<Int>? = null,
        val range: Int? = null) : TypeAdapter<Directional>() {
    override fun write(out: JsonWriter, value: Directional) {
        out.apply {
            beginObject()
            name("sex").value(value.sex)
            name("minAge").value(value.minAge)
            name("maxAge").value(value.maxAge)
            name("latitude").value(value.latitude)
            name("longitude").value(value.longitude)
            name("cityIds")
            beginArray()
            value.cityIds.forEach {
                value(it)
            }
            endArray()
            name("range").value(value.range)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): Directional {
        val directional = Directional()
        `in`.apply {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "sex" -> directional.sex = nextInt()
                    "minAge" -> directional.minAge = nextInt()
                    "maxAge" -> directional.maxAge = nextInt()
                    "latitude" -> directional.latitude = BigDecimal(nextString()).setScale(6, BigDecimal.ROUND_HALF_UP)
                    "longitude" -> directional.longitude = BigDecimal(nextString()).setScale(6, BigDecimal.ROUND_HALF_UP)
                    "range" -> directional.range = nextInt()
                    "cityIds" -> {
                        beginArray()
                        while (hasNext()) {
                            directional.cityIds.add(nextInt())
                        }
                        endArray()
                    }
                }
            }
            endObject()
        }

        return directional
    }
}

/**
 * 问题转换器
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class QuestionTypeAdapter : TypeAdapter<Question>() {

    /**
     * 答案类解析器
     */
    val answerTypeAdapter by lazy {
        GsonUtil.gson.getAdapter(Answer::class.java)
    }

    override fun write(out: JsonWriter, value: Question) {
        out.apply {
            beginObject()
            name("content").value(value.content)
            name("reAnswerCount").value(value.reAnswerCount)
            name("answers")
            beginArray()
            value.answers.forEach {
                answerTypeAdapter.write(this, it)
            }
            endArray()
            name("isMulti").value(ValueConst.SERVER_FALSE)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): Question {
        val question = Question()
        `in`.apply {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "content" -> question.content = nextString()
                    "reAnswerCount" -> question.reAnswerCount = nextInt()
                    "answers" -> {
                        beginArray()
                        while (hasNext()) {
                            question.answers.add(answerTypeAdapter.read(this))
                        }
                        endArray()
                    }
                }
            }
            endObject()
        }

        return question
    }
}

/**
 * 答案选项转换器
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class AnswerTypeAdapter : TypeAdapter<Answer>() {
    override fun write(out: JsonWriter, value: Answer) {
        out.apply {
            beginObject()
            name("order").value(value.order)
            name("content").value(value.content)
            name("isAnswer").value(value.isAnswer)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): Answer {
        val answer = Answer()
        `in`.apply {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "order" -> answer.order = nextInt()
                    "content" -> answer.content = nextString()
                    "isAnswer" -> answer.isAnswer = nextInt() == ValueConst.SERVER_TRUE
                }
            }

            endObject()
        }

        return answer
    }
}