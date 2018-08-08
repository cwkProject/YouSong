package com.yousong.yousong.third

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yousong.yousong.R
import com.yousong.yousong.global.AppConfig
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
    private val api = WXAPIFactory.createWXAPI(Global.getApplication(), ValueKey.WX_APP_ID, true)

    /**
     * logo
     */
    private val thumbData by lazy {
        val logo = BitmapFactory.decodeResource(Global.getApplication().resources, R.mipmap.ic_launcher)
        ByteArrayOutputStream().use {
            logo.compress(Bitmap.CompressFormat.PNG, 100, it)
            return@lazy it.toByteArray()
        }
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

        /**
         * 发送到微信好友
         */
        val SEND_TO_FRIENDS = 0

        /**
         * 发送到朋友圈
         */
        val SEND_TO_MOMENTS = 1

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
         * 发送目标
         */
        var target: Int = SEND_TO_FRIENDS

        /**
         * 发送分享
         */
        internal fun send() {
            sendView?.isClickable = false
            onSend()
        }

        /**
         * 执行发送
         **/
        private fun onSend() {
            if (api.isWXAppInstalled) {
                val wxWebpageObject = WXWebpageObject()

                wxWebpageObject.webpageUrl = url
                val wxMediaMessage = WXMediaMessage(wxWebpageObject)

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
                sendView?.let { snackbar(it, R.string.prompt_install_wechat) }
            }

            sendView?.isClickable = true
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
                        snackbar(snackbarView!!, R.string.failed_open_wx)
                    }
                }
            } else {
                notListener?.invoke()

                if (snackbarView == null) {
                    context?.toast(R.string.prompt_install_wechat)
                } else {
                    snackbar(snackbarView!!, R.string.prompt_install_wechat)
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
