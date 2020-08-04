package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity

/**
 * @anthor created by jzw
 * @date 2020/6/16
 * @change
 * @describe 警情统计模板
 **/
class HorizontalProgressComponentActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.act_horizontal_component

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("警情统计")
        showToolbarBackView()
    }
}