package com.yousong.yousong.value;

/**
 * 常量枚举
 *
 * @author 超悟空
 * @version 1.0 2018/7/24
 * @since 1.0
 */
public interface ValueConst {

    /**
     * 服务器true
     */
    int SERVER_TRUE = 1;

    /**
     * 服务器false
     */
    int SERVER_FALSE = 0;

    /**
     * 传输进度未开始数值
     */
    int PROGRESS_IDLE = -1;

    /**
     * 传输失败数值
     */
    int PROGRESS_FAILED = -2;

    /**
     * 传输成功数值
     */
    int PROGRESS_SUCCESS = 101;
}
