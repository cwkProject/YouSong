package com.yousong.yousong.model.server

import com.yousong.yousong.value.ValueConst

/**
 * 认证信息
 *
 * @author 超悟空
 * @version 1.0 2018/8/12
 * @since 1.0
 *
 * @property userAuthId 认证编号
 * @property userId 用户id
 * @property fullName 用户真实姓名
 * @property idCard 用户身份证号
 * @property reviewState 认证状态，1审核中；2拒绝；3通过
 */
data class Auth(val userAuthId: Long,
                val userId: String,
                val fullName: String,
                val idCard: String,
                val reviewState: Int,
                val businessLicenceImgUrl: String?) {

    /**
     * 当前认证信息是否有效
     */
    val valid get() = reviewState == ValueConst.REVIEW_PASS
}