package com.yousong.yousong.activity.ads

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.CreateAdsViewModel
import com.yousong.yousong.databinding.ActivityPublishAdsBinding
import com.yousong.yousong.model.local.Option
import com.yousong.yousong.operator.OnCreateAdsOperator
import com.yousong.yousong.util.CheckAndroidMPermission
import com.yousong.yousong.util.FileUtil
import org.cwk.android.library.util.ToolbarInitialize.initToolbar
import java.io.File
import java.math.BigDecimal


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
    private val COVER_HEIGHT = 480

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
        ActivityPublishAdsBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CreateAdsViewModel::class.java)
    }

    override val rootViewId = R.layout.activity_publish_ads

    override fun onInitView(savedInstanceState: Bundle?) {
        initToolbar(this, R.string.title_publish_ads)

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

    override fun onAddOptionClick(view: View) {
        val options = viewModel.adsDetail.question.option
        if (options.size < 4) {
            options.add(Option(options.size + 1))
            binding.invalidateAll()
        }
    }

    override fun onRemoveOptionClick(view: View) {
        val options = viewModel.adsDetail.question.option
        if (options.size > 2) {
            options.removeAt(options.size - 1).takeIf { it.answer }?.let {
                options[0].answer = true
            }
            binding.invalidateAll()
        }
    }

    override fun onMoneyChanged(edt: Editable) {
        val posDot = edt.indexOf('.')

        // 最小值1，保留两位小数
        when {
            edt.isEmpty() || posDot == 0 || edt.startsWith('0') -> edt.replace(0, edt.length, "1")
            posDot == edt.length - 1 -> return
            posDot > 0 && edt.length - posDot - 1 > 2 -> edt.delete(posDot + 3, edt.length)
        }

        viewModel.adsDetail.ads.userUnitPrice = BigDecimal(edt.toString())
    }

    override fun onTargetCountChanged(edt: Editable) {
        // 最小值1
        if (edt.isEmpty() || edt.startsWith('0')) {
            edt.replace(0, edt.length, "1")
        }

        viewModel.adsDetail.ads.targetCount = edt.toString().toInt()
    }

    override fun onSubmit(view: View) {
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
     * 创建临时图片路径
     *
     * @return 生成的路径
     */
    private fun createImagePath(): String {
        val dir = externalCacheDir ?: cacheDir
        val path = dir.path + File.separator + System.currentTimeMillis() + ".jpg"
        viewModel.coverTempPath = path
        return path
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
        intent.putExtra("aspectX", 4)
        intent.putExtra("aspectY", 3)
        intent.putExtra("outputX", COVER_WIDTH)
        intent.putExtra("outputY", COVER_HEIGHT)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(createImagePath())))
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