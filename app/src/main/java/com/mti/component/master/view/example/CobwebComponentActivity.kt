package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cobweb_component.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 蜘蛛网背景组件
 **/
class CobwebComponentActivity : BaseActivity() {

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("CobwebComponent")
        showToolbarBackView()
        cobWebComponent.lineWidth = 5
        setMarkdownData("CobwebComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_cobweb_component


}