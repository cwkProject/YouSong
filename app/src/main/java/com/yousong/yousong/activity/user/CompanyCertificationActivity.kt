package com.yousong.yousong.activity.user

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.CompanyAuthViewModel
import com.yousong.yousong.databinding.ActivityCompanyCertificationBinding
import com.yousong.yousong.function.SubmitResultUtil
import com.yousong.yousong.operator.OnCompanyCertificationOperator
import com.yousong.yousong.util.CheckAndroidMPermission
import com.yousong.yousong.util.FileUtil
import org.cwk.android.library.util.ToolbarInitialize.initToolbar

/**
 * 企业认证页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class CompanyCertificationActivity : BaseActivity(), OnCompanyCertificationOperator {

    /**
     * 选择营业执照
     */
    private val BUSINESS_LICENCE_SELECT_REQUEST_CODE = 100

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityCompanyCertificationBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CompanyAuthViewModel::class.java)
    }

    override val rootViewId = R.layout.activity_company_certification

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_company_certification)

        viewModel.submitResult.observe(this, Observer {
            if (it != null) {
                SubmitResultUtil.show(this, it)
            }
        })

        binding.data = viewModel
        binding.holder = this
    }

    override fun onBusinessLicenseClick(view: View) {
        openGallery(BUSINESS_LICENCE_SELECT_REQUEST_CODE)
    }

    /**
     * 打开相册
     */
    private fun openGallery(requestCode: Int) {
        if (CheckAndroidMPermission.checkPermission(this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, getString(R.string.prompt_gallery_storage), requestCode)) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(intent, requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery(requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                BUSINESS_LICENCE_SELECT_REQUEST_CODE -> data?.data?.let { FileUtil.getPath(this, it) }?.let { viewModel.businessLicenceSelected(it) }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}