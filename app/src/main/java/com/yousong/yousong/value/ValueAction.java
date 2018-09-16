package com.yousong.yousong.value;

/**
 * 广播常量
 *
 * @author 超悟空
 * @version 1.0 2018/6/26
 * @since 1.0
 */
public interface ValueAction {

    /**
     * 数据加载完成
     */
    String ACTION_LOAD_DATA_FINISH = "action_load_data_finish";

    /**
     * 登录状态改变
     */
    String ACTION_LOGIN_STATE_CHANGE = "action_login_state_change";

    /**
     * 用户信息发生改变
     */
    String ACTION_USER_INFO_CHANGED = "action_user_info_changed";

    /**
     * 微信登录成功
     */
    String ACTION_WX_LOGIN_SUCCESS = "action_wx_login_success";

    /**
     * 微信登录失败
     */
    String ACTION_WX_LOGIN_FAILED = "action_wx_login_failed";

    /**
     * 注销登录
     */
    String ACTION_USER_LOGOUT = "action_user_logout";

    /**
     * 支付成功
     */
    String ACTION_PAY_SUCCESS = "action_pay_success";
}
