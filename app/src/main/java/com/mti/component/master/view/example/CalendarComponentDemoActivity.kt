package com.mti.component.master.view.example

import android.content.Intent
import android.os.Bundle
import com.mti.calendar.CalenderMainActivity
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.activity_calendar_demo.*
import kotlinx.android.synthetic.main.activity_work_punch.webView

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 日历说明文档
 **/
class CalendarComponentDemoActivity : BaseActivity() {
    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("CalendarComponent")
        showToolbarBackView()
        tvEffectShow.setOnClickListener {
            startActivity(Intent(this, CalenderMainActivity::class.java))
        }
        setMarkdownData("CalendarComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_calendar_demo


}