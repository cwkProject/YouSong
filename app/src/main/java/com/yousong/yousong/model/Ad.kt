package com.yousong.yousong.model

/**
 * 广告数据
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
data class Ad(
        val id: Long,
        val type: Int,
        val cover: String,
        val poster: String,
        val question: String,
        val options: List<String>,
        val answer: Int,
        val state: Int)