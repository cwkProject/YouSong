package com.yousong.yousong.model.server

/**
 * 答题结果
 *
 * @author 超悟空
 * @version 1.0 2018/8/20
 * @since 1.0 2018/8/20
 *
 * @property isAnswer 是否答对
 * @property canAnswer 是否可以答题
 * @property answeredFrequency 已答题次数
 * @property myBudget 我的收益
 * @property refereeUserBudget 推荐人收益
 **/
data class AnswerResult(
        val isAnswer: Int,
        val canAnswer: Int,
        val answeredFrequency: Int,
        val myBudget: Int,
        val refereeUserBudget: Int)