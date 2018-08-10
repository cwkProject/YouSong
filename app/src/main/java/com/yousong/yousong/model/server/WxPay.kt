package com.yousong.yousong.model.server

/**
 * 微信订单数据
 *
 * @author 超悟空
 * @version 1.0 2018/8/10
 * @since 1.0
 *
 * @property appId 应用id
 * @property nonceStr 随机串
 * @property packageValue 交易类型
 * @property prepayId 预支付交易会话标识
 * @property partnerId 商户号
 * @property sign 签名
 */
data class WxPay(
        val appId: String,
        val nonceStr: String,
        val packageValue: String,
        val prepayId: String,
        val partnerId: String,
        val sign: String)