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
class AdsDetailTypeAdapter : TypeAdapter<AdsDetail>() {

    /**
     * 广告类解析器
     */
    private val adsTypeAdapter by lazy {
        GsonUtil.gson.getAdapter(Ads::class.java)
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

    override fun write(out: JsonWriter, value: AdsDetail) {
        out.apply {
            beginObject()
            name("ads")
            adsTypeAdapter.write(out, value.ads)
            name("questionAnswer")
            questionTypeAdapter.write(out, value.question)
            name("adsDirectional")
            directionalTypeAdapter.write(out, value.directional)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): AdsDetail {

        var ads: Ads? = null
        var question: Question? = null
        var directional: Directional? = null
        var canAnswer = true
        var answered = false

        `in`.apply {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "ads" -> ads = adsTypeAdapter.read(this)
                    "questionAnswer" -> question = questionTypeAdapter.read(this)
                    "adsDirectional" -> directional = directionalTypeAdapter.read(this)
                    "canAnswer" -> canAnswer = nextInt() == ValueConst.SERVER_TRUE
                    "answeredFrequency" -> answered = nextInt() > 0
                    else -> skipValue()
                }
            }
            endObject()
        }

        question = (question ?: Question()).apply {
            this.canAnswer = canAnswer
            this.answered = answered
        }

        return AdsDetail(ads ?: Ads(), question!!, directional ?: Directional())
    }
}

/**
 * 广告数据转换器
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class AdsTypeAdapter : TypeAdapter<Ads>() {

    override fun write(out: JsonWriter, value: Ads) {
        out.apply {
            beginObject()
            name("name").value(value.name)
            name("cover").value(value.cover)
            name("img").value(value.poster)
            name("adsType").value(if (value.type) 1 else 2)
            name("userCount").value(value.targetCount)
            name("perYellowBoyUser").value(value.userUnitPrice * BigDecimal(100))
            name("needInvoice").value(if (value.needInvoice) ValueConst.SERVER_TRUE else ValueConst.SERVER_FALSE)
            //            name("adsId").value(value.id)
            //            name("totalAsset").value(value.totalAmount * BigDecimal(100))
            //            name("balance").value(value.balance * BigDecimal(100))
            //            name("city").value(value.city)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): Ads {
        val ads = Ads()
        `in`.apply {
            beginObject()

            while (hasNext()) {
                when (nextName()) {
                    "name" -> ads.name = nextString()
                    "cover" -> ads.cover = nextString()
                    "img" -> ads.poster = nextString()
                    "adsType" -> ads.type = nextInt() == 1
                    "userCount" -> ads.targetCount = nextInt()
                    "perYellowBoyUser" -> ads.userUnitPrice = BigDecimal(nextInt()) / BigDecimal(100)
                    "needInvoice" -> ads.needInvoice = nextInt() == ValueConst.SERVER_TRUE
                    "adsId" -> ads.id = nextString()
                    "totalAsset" -> ads.totalAmount = BigDecimal(nextInt()) / BigDecimal(100)
                    "balance" -> ads.balance = BigDecimal(nextInt()) / BigDecimal(100)
                    "city" -> ads.city = nextString()
                    "publishState" -> ads.publishState = nextInt()
                    "reviewState" -> ads.reviewState = nextInt()
                    else -> skipValue()
                }
            }
            endObject()
        }

        return ads
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
            name("cityCodes")
            beginArray()
            value.addresses.forEach {
                value(it.addressCode)
            }
            endArray()
            name("range").value(value.range?.times(1000))
            name("locationChoose").value(value.locationType)
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
                    "latitude" -> directional.latitude = nextDouble()
                    "longitude" -> directional.longitude = nextDouble()
                    "range" -> directional.range = nextInt() / 1000
                    "cityCodes" -> {
                        beginArray()
                        while (hasNext()) {
                            directional.addresses.add(Address(nextString()))
                        }
                        endArray()
                    }
                    "locationChoose" -> directional.locationType = nextInt()
                    else -> skipValue()
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
        GsonUtil.gson.getAdapter(Option::class.java)
    }

    override fun write(out: JsonWriter, value: Question) {
        out.apply {
            beginObject()
            name("content").value(value.content)
            name("reAnswerCount").value(value.retries)
            name("answers")
            beginArray()
            value.option.forEach {
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
                    "reAnswerCount" -> question.retries = nextInt()
                    "answers" -> {
                        beginArray()
                        while (hasNext()) {
                            question.option.add(answerTypeAdapter.read(this))
                        }
                        endArray()
                    }
                    else -> skipValue()
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
class OptionTypeAdapter : TypeAdapter<Option>() {
    override fun write(out: JsonWriter, value: Option) {
        out.apply {
            beginObject()
            name("ordinal").value(value.order)
            name("content").value(value.content)
            name("isAnswer").value(if (value.answer) ValueConst.SERVER_TRUE else ValueConst.SERVER_FALSE)
            endObject()
        }
    }

    override fun read(`in`: JsonReader): Option {
        val answer = Option()
        `in`.apply {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "ordinal" -> answer.order = nextInt()
                    "content" -> answer.content = nextString()
                    "isAnswer" -> answer.answer = nextInt() == ValueConst.SERVER_TRUE
                    "adsAnswerOptionId" -> answer.id = nextLong()
                    else -> skipValue()
                }
            }

            endObject()
        }

        return answer
    }
}