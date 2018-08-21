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
     * 设备类型
     */
    String DEVICE_TYPE = "android";

    /**
     * 封面宽度
     */
    int COVER_WIDTH = 640;

    /**
     * 封面高度
     */
    int COVER_HEIGHT = 400;

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

    /**
     * 未提交
     */
    int REVIEW_UNSUBMITTED = 0;

    /**
     * 审核中
     */
    int REVIEW_UNDER_REVIEW = 1;

    /**
     * 已拒绝
     */
    int REVIEW_REFUSE = 2;

    /**
     * 已通过
     */
    int REVIEW_PASS = 3;

    /**
     * 未发布
     */
    int PUBLISH_UNPUBLISH = 1;

    /**
     * 已发布
     */
    int PUBLISH_PUBLISHED = 2;

    /**
     * 已完成
     */
    int PUBLISH_FINISH = 3;

    /**
     * 已取消
     */
    int PUBLISH_CANCEL = 4;

    /**
     * 定向位置类型，不限
     */
    int LOCATION_TYPE_NOT_LIMITED = 0;

    /**
     * 定向位置类型，本地周边
     */
    int LOCATION_TYPE_LOCAL_REGION = 1;

    /**
     * 定向位置类型，目标城市
     */
    int LOCATION_TYPE_TARGET_CITY = 2;
}
