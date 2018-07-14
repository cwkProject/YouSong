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
    String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取微信用户信息
     */
    String WX_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    // 以下为应用接口地址


}
