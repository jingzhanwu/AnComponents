package com.mti.component.master.view.example

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_image_nice9.webView
import kotlinx.android.synthetic.main.activity_loading_component.*

/**
 * @anthor created by 薛兵
 * @date 2020/5/25
 * @change
 * @describe 进度加载组件，包括直接加载组件和背景加载组件
 **/
class LoadingComponentActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_loading_component
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("进度加载组件")
        showToolbarBackView()
        reset()
        setMarkdownData("LoadingComponent.html", webView)
    }

    /**
     * 重置
     */
    fun reset(view: View?) {
        view?.run {
            val rotationObjectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0F, 360F)
            val alphaObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1F, 0.6F, 1F)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(rotationObjectAnimator, alphaObjectAnimator)
            animatorSet.duration = 1000
            animatorSet.start()
        }
        reset()
    }

    private fun reset() {
        backgroundLoadingEffectComponent.startProgress()
        pathDivider.startLoading(9000)
    }


}