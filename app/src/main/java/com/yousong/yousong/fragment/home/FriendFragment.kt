package com.yousong.yousong.fragment.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.yousong.yousong.R
import com.yousong.yousong.activity.ads.ShareFriendActivity
import com.yousong.yousong.architecture.viewmodel.UserViewModel
import com.yousong.yousong.databinding.FragmentFriendBinding
import com.yousong.yousong.fragment.common.BaseFragment
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.support.v4.startActivity
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * 好友页面
 *
 * @author 超悟空
 * @version 1.0 2018/6/27
 * @since 1.0
 */
class FriendFragment : BaseFragment() {

    /**
     * 用户数据
     */
    private val userViewModel by lazy {
        ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    /**
     * 绑定器
     */
    private val binding by lazy {
        FragmentFriendBinding.bind(rootView)
    }

    override val rootViewId = R.layout.fragment_friend

    override fun onInitView(savedInstanceState: Bundle?) {
        initAction()
        binding.viewModel = userViewModel
        binding.setLifecycleOwner(this)
    }

    /**
     * 初始化动作事件
     */
    private fun initAction() {

        lateinit var subscription: Subscription

        val publish = PublishSubject.create<Int>()

        publish.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(Schedulers.newThread())
                .subscribe(object :Subscriber<Int>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(s: Subscription) {
                        subscription=s
                    }

                    override fun onNext(t: Int?) {
                        Log.v("Flowable","receive:$t")
                    }

                    override fun onError(t: Throwable?) {
                    }
                })

        var count = 1

        binding.inviteCountLayout.setOnClickListener {
           // userViewModel.refreshInviteCount()

            (0..1000).forEach {
                publish.onNext(count++)
            }
        }


        binding.totalIncomingLayout.setOnClickListener {
           // userViewModel.refreshTotalIncome()
            subscription.request(100)
        }

        binding.shareFriendLayout.setOnClickListener {
            startActivity<ShareFriendActivity>()
        }
    }
}