package com.yousong.yousong.model.convert

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.yousong.yousong.common.GsonUtil
import com.yousong.yousong.model.local.*
import com.yousong.yousong.value.ValueConst
import java.math.BigDecimal

/**
 * 广告详情转换器
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class AdDetailTypeAdapter : TypeAdapter<AdDetail>() {

    /**
     * 广告类解析器
     */
    private val adTypeAdapter by lazy {
        GsonUtil.gson.getAdapter(Ad::class.java)
    }

    /**
     * 选项类解析器
     */
    private val questionTypeAdapter by lazy {
        GsonUtil.gson.getAdapter(Question::class.java)
    }

    /**
     * 定向类解析器
     */
    private val directionalTypeAdapter by lazy {
        GsonUtil.gson.getAdapter(Directional::class.java)
    }

    override fun write(out: JsonWriter, value: AdDetail) {
        out.apply {
            beginObject()
            name("ads")
            adTypeAdapter.write(out, value.ad)
            name("questionAnswer")
            questionTypeAdapter.write(out, value.question)
            name("adsDirectional")
            directionalTypeAdapter.write(out, value.directional)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): AdDetail {

        var ad: Ad? = null
        var question: Question? = null
        var directional: Directional? = null

        `in`.apply {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "ads" -> ad = adTypeAdapter.read(this)
                    "questionAnswer" -> question = questionTypeAdapter.read(this)
                    "adsDirectional" -> directional = directionalTypeAdapter.read(this)
                }
            }
            endObject()
        }

        return AdDetail(ad
                ?: Ad(), question
                ?: Question(), directional
                ?: Directional())
    }
}

/**
 * 广告数据转换器
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class AdTypeAdapter : TypeAdapter<Ad>() {

    override fun write(out: JsonWriter, value: Ad) {
        out.apply {
            beginObject()
            name("name").value(value.name)
            name("cityCode").value(value.cityCode)
            name("cover").value(value.cover)
            name("img").value(value.poster)
            name("adsType").value(if (value.type) 1 else 2)
            name("userCount").value(value.targetCount)
            name("perYellowBoyUser").value(value.userUnitPrice * BigDecimal(100))
            name("needInvoice").value(if (value.needInvoice) ValueConst.SERVER_TRUE else ValueConst.SERVER_FALSE)
            name("adsId").value(value.id)
            name("totalAsset").value(value.totalAmount * BigDecimal(100))
            name("balance").value(value.balance * BigDecimal(100))
            name("city").value(value.city)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): Ad {
        val ad = Ad()
        `in`.apply {
            beginObject()

            while (hasNext()) {
                when (nextName()) {
                    "name" -> ad.name = nextString()
                    "cityCode" -> ad.cityCode = nextString()
                    "cover" -> ad.cover = nextString()
                    "img" -> ad.poster = nextString()
                    "adsType" -> ad.type = nextInt() == 1
                    "userCount" -> ad.targetCount = nextInt()
                    "perYellowBoyUser" -> ad.userUnitPrice = BigDecimal(nextInt()) / BigDecimal(100)
                    "needInvoice" -> ad.needInvoice = nextInt() == ValueConst.SERVER_TRUE
                    "adsId" -> ad.id = nextString()
                    "totalAsset" -> ad.totalAmount = BigDecimal(nextInt()) / BigDecimal(100)
                    "balance" -> ad.balance = BigDecimal(nextInt()) / BigDecimal(100)
                    "city" -> ad.city = nextString()
                }
            }
            endObject()
        }

        return ad
    }
}

/**
 * 定向筛选条件转换器
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
class DirectionalTypeAdapter : TypeAdapter<Directional>() {
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
    private val answerTypeAdapter by lazy {
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
            name("isAnswer").value(if (value.isAnswer) ValueConst.SERVER_TRUE else ValueConst.SERVER_FALSE)
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