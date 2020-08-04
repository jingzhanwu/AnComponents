package com.mti.component.master.view.example.chart

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.mti.component.master.R
import com.mti.component.master.adapter.ViewPagerAdapter
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_chart_component.*
import kotlinx.android.synthetic.main.act_chart_doc.*

/**
 * @anthor created by jzw
 * @date 2020/6/2
 * @change
 * @describe 图表使用文档
 **/
class ChartUseDocActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.act_chart_doc
    }

    override fun initViews(savedInstanceState: Bundle?) {
        val title = intent.getStringExtra("title")
        val docName = intent.getStringExtra("docName")
        setToolbarTitle(title)
        showToolbarBackView()
        setMarkdownData(docName, docWebView)
    }
}