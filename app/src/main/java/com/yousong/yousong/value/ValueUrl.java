package com.yousong.yousong.value;

/**
 * 网络接口地址
 *
 * @author 超悟空
 * @version 1.0 2018/7/13
 * @since 1.0
 */
public interface ValueUrl {

    // 以下为第三方接口地址

    /**
     * 微信access_token交换
     */
    String URL_WX_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取微信用户信息
     */
    String URL_WX_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";

    // 以下为应用接口地址

    /**
     * 根地址
     */
    String URL_BASE = "";

    /**
     * 用户路由
     */
    String URL_BASE_USER = URL_BASE + "user/";

    /**
     * 文件路由
     */
    String URL_BASE_FILE = URL_BASE + "file/";

    /**
     * 广告路由
     */
    String URL_BASE_ADS = URL_BASE + "ads/";

    // 一些为用户相关接口地址

    /**
     * 创建token
     */
    String URL_USER_CREATE_TOKEN = URL_BASE_USER + "createToken";

    /**
     * 微信登录
     */
    String URL_USER_WECHAT_LOGIN = URL_BASE_USER + "login";

    /**
     * 获取手机验证码
     */
    String URL_USER_GET_MOBILE_VERIFY_CODE = URL_BASE_USER + "getMobileVerifyCode";

    /**
     * 获取好友数量
     */
    String URL_USER_REFEREE_COUNT = URL_BASE_USER + "refereeCount";

    /**
     * 上传用户位置
     */
    String URL_USER_LOCATION = URL_BASE_USER + "location";

    /**
     * 获取总收益
     */
    String URL_USER_GET_TOTAL_INCOME = URL_BASE_USER + "getMyBudget";

    /**
     * 创建微信支付订单
     */
    String URL_USER_WECHAT_PAY = URL_BASE_USER + "pay";

    // 以下为文件相关接口

    /**
     * 上传文件
     */
    String URL_FILE_UPLOAD = URL_BASE_FILE + "upload";

    /**
     * 创建广告提交审核
     */
    String URL_ADS_CREATE = URL_BASE_ADS + "create";

    /**
     * 获取广告详情
     */
    String URL_ADS_GET_ADS_DETAIL = URL_BASE_ADS + "getAdsDetail";

    /**
     * 广告充值
     */
    String URL_ADS_RECHARGE = URL_BASE_ADS + "recharge";

    /**
     * 答题
     */
    String URL_ADS_ANSWER = URL_BASE_ADS + "answer";

    /**
     * 审核通过后发布广告
     */
    String URL_ADS_PUBLISH = URL_BASE_ADS + "publish";

    /**
     * 获取首页广告列表
     */
    String URL_ADS_GET_ADS_LIST = URL_BASE_ADS + "getHomeList";
}