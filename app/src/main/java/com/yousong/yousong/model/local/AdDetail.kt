package com.yousong.yousong.model.local

/**
 * 广告详情
 *
 * @author 超悟空
 * @version 1.0 2018/7/29
 * @since 1.0
 *
 * @property ad 广告基础数据
 * @property question 广告问题
 * @property directional 定向信息
 */
data class AdDetail(
        val ad: Ad = Ad(),
        val question: Question = Question(),
        val directional: Directional = Directional())