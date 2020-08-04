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
 * @describe 3d翻转组件
 **/
class Flip3DComponentActivity : BaseActivity() {

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("Flip3DComponent")
        showToolbarBackView()

        setMarkdownData("Flip3DComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_flip3d_component


}