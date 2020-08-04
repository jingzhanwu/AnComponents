package com.mti.component.master.view.example

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_alram_component.*

/**
 * @anthor created by jzw
 * @date 2020/5/21
 * @change
 * @describe InputComponent组件示例
 **/
class InputComponentActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.act_alram_component
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("InputComponent")
        showToolbarBackView()
        //右边ImageView点击事件
        inputAlarmPerson.setOnRightImageClickListener(View.OnClickListener {
            Toast.makeText(this, "扫描身份证", Toast.LENGTH_SHORT).show()
        })
        //右边整个输入区域点击事件
        inputAlarmMode.setOnRightImageClickListener(View.OnClickListener {
            Toast.makeText(this, "选择报警方式", Toast.LENGTH_SHORT).show()
        })
        setMarkdownData("InputComponent.html", webView);
    }
}