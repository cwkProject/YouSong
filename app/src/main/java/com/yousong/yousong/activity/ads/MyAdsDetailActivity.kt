package com.yousong.yousong.activity.ads

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.ads.MyAdsDetailViewModel
import com.yousong.yousong.databinding.ActivityMyAdsDetailBinding
import com.yousong.yousong.value.ValueConst
import com.yousong.yousong.value.ValueTag
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.startActivityForResult

/**
 * 我的广告详情页面
 *
 * @author 超悟空
 * @version 1.0 2018/9/27
 * @since 1.0 2018/9/27
 **/
class MyAdsDetailActivity : BaseActivity() {

    /**
     * 修改广告
     */
    private val REQUEST_MODIFY_ADS = 101

    /**
     * 支付广告
     */
    private val REQUEST_PAY_ADS = 102

    /**
     * 支付成功
     */
    private val REQUEST_PAY_SUCCESS = 103

    /**
     * 数据模型
     */
    private val viewModel by viewModels<MyAdsDetailViewModel>()

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityMyAdsDetailBinding.bind(rootView)
    }

    override val rootViewId = R.layout.activity_my_ads_detail

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_ads_detail)
        initAction()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.submitResult.observe(this, Observer { it?.show(this) })
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        viewModel.loadAds(intent.getStringExtra(ValueTag.TAG_ADS_ID))

        viewModel.adsDetail.observe(this, Observer {
            if (it != null && !it.ads.type) {
                when (it.directional.locationType) {
                    ValueConst.LOCATION_TYPE_LOCAL_REGION -> viewModel.getLocation()
                    ValueConst.LOCATION_TYPE_TARGET_CITY -> viewModel.matchCity()
                }
            }
        })
    }

    /**
     * 初始化功能按钮
     */
    private fun initAction() {
        binding.submitButton.setOnClickListener {
            when {
                viewModel.adsDetail.value?.ads?.reviewState == ValueConst.REVIEW_REFUSE ->
                    startActivityForResult<CreateAdsActivity>(REQUEST_MODIFY_ADS, ValueTag.TAG_ADS_DETAIL to viewModel.adsDetail.value)
                viewModel.adsDetail.value?.ads?.reviewState == ValueConst.REVIEW_PASS &&
                        viewModel.adsDetail.value?.ads?.payState == ValueConst.PAY_UNPAID ->
                    startActivityForResult<AdsPayActivity>(REQUEST_PAY_ADS, ValueTag.TAG_ADS to viewModel.adsDetail.value?.ads)
                viewModel.adsDetail.value?.ads?.reviewState == ValueConst.REVIEW_PASS &&
                        viewModel.adsDetail.value?.ads?.payState == ValueConst.PAY_PAID ->
                    alert(R.string.prompt_confirm_publish) {
                        okButton {
                            viewModel.publish()
                        }

                        cancelButton {}
                    }.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            viewModel.loadAds(intent.getStringExtra(ValueTag.TAG_ADS_ID))
            when (requestCode) {
                REQUEST_PAY_ADS -> startActivityForResult<AdsPaySuccessActivity>(REQUEST_PAY_SUCCESS, ValueTag.TAG_ADS_ID to viewModel.adsDetail.value?.ads?.id)
            }
        }
    }
}