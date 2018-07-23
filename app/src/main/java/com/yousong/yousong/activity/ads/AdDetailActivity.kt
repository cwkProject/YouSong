package com.yousong.yousong.activity.ads

import android.os.Bundle
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import kotlinx.android.synthetic.main.activity_ad_detail.*
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 广告详情页
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class AdDetailActivity : BaseActivity() {

    override val rootViewId = R.layout.activity_ad_detail

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_detail)

        initShare()
    }

    /**
     * 初始化分享按钮
     */
    private fun initShare(){
        wechat.setOnClickListener{

        }
    }
}