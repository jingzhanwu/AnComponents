package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.common.WaterMarkComponent
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_waterkark.*

/**
 * @anthor created by jzw
 * @date 2020/5/25
 * @change
 * @describe 水印组件
 **/
class WaterMarkComponentActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.act_waterkark
    }

    override fun initViews(savedInstanceState: Bundle?) {
        WaterMarkComponent.showWaterMarkView(this, "道枢MTI")
        setToolbarTitle("WaterMark")
        showToolbarBackView()
        setMarkdownData("WaterMarkComponent.html", webView)
    }
}