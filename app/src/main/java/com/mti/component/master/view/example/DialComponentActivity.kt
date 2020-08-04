package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.common.dial.DialPlateHelper
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.util.toast
import kotlinx.android.synthetic.main.activity_calendar_demo.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 拨号盘
 **/
class DialComponentActivity : BaseActivity() {
    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("DialComponent")
        showToolbarBackView()
        tvEffectShow.setOnClickListener {
            DialPlateHelper.create()?.run {
                addOnVoiceCLickListener { toast("语音") }
                addOnVideoClickListener { toast("视频") }
                /*//设置字符最大12个
                maxCharacters = 12
                //拨号盘底部左边图标
                leftResId=R.drawable.dropshadow
                //拨号盘底部右边图标
                rightResId=R.drawable.black_background
                //实际创建拨号盘面板前，先设置属性、监听事件*/
                createDialPlate(supportFragmentManager)
            }

        }
        setMarkdownData("DialComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_dial_component


}