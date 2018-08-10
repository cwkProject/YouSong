package com.yousong.yousong.model.server

/**
 * 轮播广告数据
 *
 * @author 超悟空
 * @version 1.0 2018/6/28
 * @since 1.0
 *
 * @property carouselId 广告组id
 * @property cityCode 城市码
 * @property items 广告项列表
 */
data class BannerAds(
        val carouselId: Long,
        val cityCode: String,
        val items: List<BannerAdsItem>)

/**
 * 轮播广告子项数据
 *
 * @author 超悟空
 * @version 1.0 2018/6/28
 * @since 1.0
 *
 * @property carouselItemId 广告id
 * @property imgUrl 图片url
 * @property link 链接地址
 */
data class BannerAdsItem(
        val carouselItemId: Long,
        val imgUrl: String,
        val link: String?)