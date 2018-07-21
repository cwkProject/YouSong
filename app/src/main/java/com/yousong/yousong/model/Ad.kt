package com.yousong.yousong.model

/**
 * 广告数据
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 *
 * @property adsId 广告id
 * @property cover 封面
 * @property poster 大图海报
 * @property totalAsset 总金额
 * @property balance 余额
 */
data class Ad(
        val adsId: String,
        val cover: String,
        val poster: String,
        val totalAsset: Int,
        val balance: Int)