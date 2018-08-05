package com.yousong.yousong.activity.ads

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.yousong.yousong.R
import com.yousong.yousong.activity.common.BaseActivity
import com.yousong.yousong.architecture.viewmodel.PublishAdViewModel
import com.yousong.yousong.databinding.ActivityPublishAdBinding
import com.yousong.yousong.model.local.Option
import com.yousong.yousong.operator.OnPublishAdOperator


/**
 * 发布广告的页面
 *
 * @author 超悟空
 * @version 1.0 2018/7/1
 * @since 1.0
 */
class PublishAdActivity : BaseActivity(), OnPublishAdOperator {

    /**
     * 绑定器
     */
    private val binding by lazy {
        ActivityPublishAdBinding.bind(rootView)
    }

    /**
     * 数据模型
     */
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PublishAdViewModel::class.java)
    }

    override val rootViewId = R.layout.activity_publish_ad

    override fun onInitView(savedInstanceState: Bundle?) {
        binding.data = viewModel.adDetail
        binding.holder = this
    }

    override fun onCoverClick(view: View) {
    }

    override fun onPosterClick(view: View) {
    }

    override fun onAddOptionClick(view: View) {
        val options = viewModel.adDetail.question.option
        if (options.size < 4) {
            options.add(Option(options.size + 1))
            binding.invalidateAll()
        }
    }

    override fun onRemoveOptionClick(view: View) {
        val options = viewModel.adDetail.question.option
        if (options.size > 2) {
            options.removeAt(options.size - 1)
            binding.invalidateAll()
        }
    }

    override fun onMoneyChanged(edt: Editable) {
        val temp = edt.toString()
        val posDot = temp.indexOf(".")
        if (posDot <= 0) return
        if (temp.length - posDot - 1 > 2) {
            edt.delete(posDot + 3, posDot + 4)
        }
    }
}