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
    String ACTION_LOAD_DATA_FINISH = "load_data_finish";

    /**
     * 登录状态改变
     */
    String ACTION_LOGIN_STATE_CHANGE = "login_state_change";

    /**
     * 用户信息发生改变
     */
    String ACTION_USER_INFO_CHANGED = "user_info_changed";

    /**
     * 微信登录成功
     */
    String ACTION_WX_LOGIN_SUCCESS = "wx_login_success";

    /**
     * 微信登录失败
     */
    String ACTION_WX_LOGIN_FAILED = "wx_login_failed";

    /**
     * 注销登录
     */
    String ACTION_USER_LOGOUT = "user_logout";
}
