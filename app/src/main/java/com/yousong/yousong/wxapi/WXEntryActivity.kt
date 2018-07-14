package com.yousong.yousong.wxapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yousong.yousong.R
import com.yousong.yousong.activity.BaseActivity
import com.yousong.yousong.global.AppConfig
import com.yousong.yousong.global.LoadUserData
import com.yousong.yousong.model.WxResponse
import com.yousong.yousong.value.ValueAction
import com.yousong.yousong.value.ValueKey
import com.yousong.yousong.value.ValueTag
import com.yousong.yousong.work.*
import kotlinx.android.synthetic.main.activity_wx_entry.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 处理微信响应结果
 *
 * @author 超悟空
 * @version 1.0 2017/3/1
 * @since 1.0 2017/3/1
 */
class WXEntryActivity : BaseActivity(), IWXAPIEventHandler {

    /**
     * 微信接口
     */
    private val api: IWXAPI by lazy {
        WXAPIFactory.createWXAPI(this, ValueKey.WX_APP_ID, false)
    }

    override val rootViewId = R.layout.activity_wx_entry

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.app_name, true, false)

        try {
            if (!api.handleIntent(intent, this)) {
                finish()
            }
        } catch (e: Exception) {
            Log.e(TAG, "onInitView handleIntent error", e)
            finish()
        }

    }

    /**
     * 处理请求结果
     *
     * @param baseReq 请求数据集
     */
    override fun onReq(baseReq: BaseReq) {
        // 暂时不对发送的分享做处理
        finish()
    }

    /**
     * 处理响应结果
     *
     * @param baseResp 结果数据集
     */
    override fun onResp(baseResp: BaseResp) {
        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
            when (baseResp.type) {
                ConstantsAPI.COMMAND_SENDAUTH ->
                    // 来自微信登录
                    onWXLogin(baseResp)
                else -> finish()
            }
        } else {
            // 暂时不对失败和取消做处理
            finish()
        }
    }

    /**
     * 微信登录成功
     */
    private fun onWXLogin(baseResp: BaseResp) {
        state_textView.setText(R.string.prompt_login_loading)
        if (baseResp is SendAuth.Resp) {
            if (baseResp.state != null && baseResp.state == AppConfig.wxLoginState && baseResp.code != null) {
                // 简单校验通过
                // 发起密钥交换
                GetWXAccessTokenWork().start(baseResp.code) {
                    if (it.isSuccess) {
                        loadUserInfo(it.result)
                    } else {
                        loginFailed()
                    }
                }

                return
            }
        }

        loginFailed()
    }

    /**
     * 登录成功，获取用户信息
     *
     * @param wxResponse 结果数据
     */
    private fun loadUserInfo(wxResponse: WxResponse?) {
        GetWXUserInfoWork().start(wxResponse?.access_token, wxResponse?.openid) { (state, result) ->

            if (state && result != null) {
                WxLoginWork().start(result.unionid, result.nickname, result.headimgurl) {
                    if (it.isSuccess) {
                        LoadUserData.loadBegin(this) {
                            sendBroadcast(Intent(ValueAction.WX_LOGIN_SUCCESS))
                            finish()
                        }
                    } else {
                        loginFailed(ErrorCodeTable[it.code])
                    }
                }
            } else {
                loginFailed()
            }
        }
    }

    /**
     * 登录失败
     *
     * @param message 失败消息
     */
    private fun loginFailed(message: String? = null) {
        val error = message ?: getString(R.string.failed_login_required)
        sendBroadcast(Intent(ValueAction.WX_LOGIN_FAILED).putExtra(ValueTag.ERROR_MESSAGE_TAG, error))

        finish()
    }
}
