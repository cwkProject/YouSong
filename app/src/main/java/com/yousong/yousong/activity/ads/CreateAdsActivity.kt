package com.yousong.yousong.activity.ads

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.CreateAdsViewModel
import com.yousong.yousong.databinding.ActivityCreateAdsBinding
import com.yousong.yousong.function.SubmitResultUtil
import com.yousong.yousong.operator.OnCreateAdsOperator
import com.yousong.yousong.util.CheckAndroidMPermission
import com.yousong.yousong.util.FileUtil
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import java.io.File


/**
 * 创建广告的页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class CreateAdsActivity : BaseActivity(), OnCreateAdsOperator {

    /**
     * 封面宽度
     */
    private val COVER_WIDTH = 640

    /**
     * 封面高度
     */
    private val COVER_HEIGHT = 400

    /**
     * 选择封面图
     */
    private val COVER_SELECT_REQUEST_CODE = 100

    /**
     * 选择海报图
     */
    private val POSTER_SELECT_REQUEST_CODE = 102

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityCreateAdsBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CreateAdsViewModel::class.java)
    }

    override val rootViewId = R.layout.activity_create_ads

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_publish_ads)

        viewModel.submitResult.observe(this, Observer {
            if (it != null) {
                SubmitResultUtil.show(this, it)
            }
        })

        binding.data = viewModel.adsDetail
        binding.viewModel = viewModel
        binding.holder = this
    }

    override fun onCoverClick(view: View) {
        openGallery(COVER_SELECT_REQUEST_CODE)
    }

    override fun onPosterClick(view: View) {
        openGallery(POSTER_SELECT_REQUEST_CODE)
    }

    /**
     * 打开相册
     */
    private fun openGallery(requestCode: Int) {
        if (CheckAndroidMPermission.checkPermission(this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, getString(R.string.prompt_gallery_storage), requestCode)) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            if (requestCode == COVER_SELECT_REQUEST_CODE) {
                setCropIntent(intent)
            }

            startActivityForResult(intent, requestCode)
        }
    }

    /**
     * 设置裁剪intent
     *
     * @param intent 裁剪意图
     */
    private fun setCropIntent(intent: Intent) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra("crop", "true")
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("scale", true)
        intent.putExtra("scaleUpIfNeeded", true)
        intent.putExtra("return-data", false)
        intent.putExtra("noFaceDetection", true)
        intent.putExtra("aspectX", 16)
        intent.putExtra("aspectY", 10)
        intent.putExtra("outputX", COVER_WIDTH)
        intent.putExtra("outputY", COVER_HEIGHT)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(viewModel.createImagePath())))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery(requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                COVER_SELECT_REQUEST_CODE -> viewModel.coverSelected()
                POSTER_SELECT_REQUEST_CODE -> data?.data?.let { FileUtil.getPath(this, it) }?.let { viewModel.posterSelected(it) }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}