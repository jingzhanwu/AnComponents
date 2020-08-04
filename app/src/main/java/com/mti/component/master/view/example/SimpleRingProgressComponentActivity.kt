package com.mti.component.master.view.example

import android.annotation.SuppressLint
import android.os.Bundle
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.activity_simple_ring_progress_component.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/6/15
 * @change
 * @describe 简易圆环进度
 **/
class SimpleRingProgressComponentActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_simple_ring_progress_component

    override fun initViews(savedInstanceState: Bundle?) {
        tvNumProgress.setText("80")
        simpleRingProgressComponent.setProgressColor(R.color.start_color, R.color.first_color)
        simpleRingProgressComponent.setProgress(80, 2000)
    }

}
