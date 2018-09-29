package com.yousong.yousong.third

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yousong.yousong.R
import com.yousong.yousong.global.AppConfig
import com.yousong.yousong.model.server.WxPay
import com.yousong.yousong.value.ValueKey
import org.cwk.android.library.global.Global
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.util.*


/**
 * 发送微信消息
 *
 * @author 超悟空
 * @version 1.0 2017/3/20
 * @since 1.0 2017/3/20
 */
object WechatFunction {

    /**
     * 微信接口
     */
    private val api = WXAPIFactory.createWXAPI(Global.getApplication(), ValueKey.WX_APP_ID)

    /**
     * logo
     */
    private val logoData by lazy {
        val logo = BitmapFactory.decodeResource(Global.getApplication().resources, R.mipmap.ic_launcher)
        ByteArrayOutputStream().use {
            logo.compress(Bitmap.CompressFormat.PNG, 100, it)
            return@lazy it.toByteArray()
        }
    }

    /**
     * 注册到微信
     */
    fun register() {
        api.registerApp(ValueKey.WX_APP_ID)
    }

    /**
     * 发起付款
     *
     * @param wxPay 付款信息
     */
    fun pay(wxPay: WxPay) {
        val request = PayReq()
        request.appId = wxPay.appId
        request.partnerId = wxPay.partnerId
        request.prepayId = wxPay.prepayId
        request.packageValue = wxPay.packageValue
        request.nonceStr = wxPay.nonceStr
        request.timeStamp = wxPay.timeStamp
        request.sign = wxPay.paySign
        request.signType = wxPay.signType
        api.sendReq(request)
    }

    /**
     * 微信分享
     *
     * @param init 初始化设置
     */
    fun share(init: SendBuilder.() -> Unit) {
        SendBuilder().apply {
            init()

            send()
        }
    }

    /**
     * 微信分享构建器
     */
    class SendBuilder {

        companion object {
            /**
             * 发送到微信好友
             */
            const val SEND_TO_FRIENDS = 0

            /**
             * 发送到朋友圈
             */
            const val SEND_TO_MOMENTS = 1

            /**
             * 小程序正式版本
             */
            const val MINI_TYPE_RELEASE = 0

            /**
             * 小程序测试版本
             */
            const val MINI_TYPE_DEBUG = 1

            /**
             * 小程序体验版本
             */
            const val MINI_TYPE_EXPERIENCE = 2

            /**
             * 分享连接
             */
            const val SHARE_TYPE_URL = 0

            /**
             * 分享图片
             */
            const val SHARE_TYPE_IMAGE = 1

            /**
             * 分享小程序
             */
            const val SHARE_TYPE_MINI = 2
        }

        /**
         * 分享内容类型
         */
        var shareType = SHARE_TYPE_URL

        /**
         * 分享的连接地址
         */
        var url: String? = null

        /**
         * 操作的控件
         */
        var sendView: View? = null

        /**
         * 分享标题
         */
        var title: String? = null

        /**
         * 描述
         */
        var description: String? = null

        /**
         * 发送缩略图数据
         */
        var thumbData = logoData

        /**
         * 发送目标
         */
        var target = SEND_TO_FRIENDS

        /**
         * 发送小程序类型type
         */
        var miniType = MINI_TYPE_EXPERIENCE

        /**
         * 分享路径（小程序地址路径或分享图片路劲）
         */
        var path: String? = null

        /**
         * 小程序id
         */
        var miniId: String? = null

        /**
         * 发送分享
         */
        internal fun send() {
            sendView?.isClickable = false
            onSend()
            sendView?.isClickable = true
        }

        /**
         * 执行发送
         **/
        private fun onSend() {
            if (api.isWXAppInstalled) {

                val mediaObject = onMediaObject() ?: return

                val wxMediaMessage = WXMediaMessage(mediaObject)

                wxMediaMessage.title = title
                wxMediaMessage.description = description
                wxMediaMessage.thumbData = thumbData

                val req = SendMessageToWX.Req()

                req.transaction = UUID.randomUUID().toString()
                req.message = wxMediaMessage

                when (target) {
                    SEND_TO_FRIENDS -> req.scene = SendMessageToWX.Req.WXSceneSession
                    SEND_TO_MOMENTS -> req.scene = SendMessageToWX.Req.WXSceneTimeline
                }

                api.sendReq(req)
            } else {
                sendView?.snackbar(R.string.prompt_install_wechat)
            }
        }

        /**
         * 创建微信分享对象
         */
        private fun onMediaObject(): WXMediaMessage.IMediaObject? =
                when (shareType) {
                    SHARE_TYPE_URL -> WXWebpageObject().apply {
                        webpageUrl = url
                    }
                    SHARE_TYPE_MINI -> WXMiniProgramObject().apply {
                        webpageUrl = url
                        userName = miniId
                        path = this@SendBuilder.path

                        when (miniType) {
                            MINI_TYPE_EXPERIENCE -> miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW
                            MINI_TYPE_DEBUG -> miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST
                            MINI_TYPE_RELEASE -> miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE
                        }
                    }
                    else -> null
                }
    }

    /**
     * 微信登录
     *
     * @param init 初始化设置
     */
    fun login(init: LoginBuilder.() -> Unit) {
        LoginBuilder().apply {
            init()

            if (api.isWXAppInstalled) {

                hasListener?.invoke()

                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                AppConfig.wxLoginState = UUID.randomUUID().toString()
                AppConfig.save()
                req.state = AppConfig.wxLoginState
                if (api.sendReq(req)) {
                    openListener?.invoke()
                } else {
                    failedListener?.invoke()

                    if (snackbarView == null) {
                        context?.toast(R.string.failed_open_wx)
                    } else {
                        snackbarView?.snackbar(R.string.failed_open_wx)
                    }
                }
            } else {
                notListener?.invoke()

                if (snackbarView == null) {
                    context?.toast(R.string.prompt_install_wechat)
                } else {
                    snackbarView?.snackbar(R.string.prompt_install_wechat)
                }
            }
        }
    }

    /**
     * 微信登录构建器
     */
    class LoginBuilder {

        /**
         * 用于显示失败[Toast]，如果设置了[snackbarView]则可省略该项
         */
        var context: Context? = null

        /**
         * 检测存在微信客户端时立即调用
         */
        var hasListener: (() -> Unit)? = null

        /**
         * 异常时显示[Snackbar]提示的锚控件，为空使用[Toast]
         */
        var snackbarView: View? = null

        /**
         * 拉起微信成功调用
         */
        var openListener: (() -> Unit)? = null

        /**
         * 拉起微信失败调用
         */
        var failedListener: (() -> Unit)? = null

        /**
         * 检测不存在微信客户端时立即调用
         */
        var notListener: (() -> Unit)? = null
    }
}
