package com.mti.component.master.view.example

import android.os.Bundle
import android.view.View
import com.mti.component.extend.SignatureComponent
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_signature.*
import kotlinx.android.synthetic.main.act_video_record.webView

/**
 * @anthor created by jzw
 * @date 2020/5/29
 * @change
 * @describe 电子签名组件demo
 **/
class SignatureComponentActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.act_signature
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("电子签名")
        showToolbarBackView()
        signatureView.backgroundMode = SignatureComponent.BACKGROUND_STYLE_GRAPH_PAPER
        floatingButtonClick()
//        setMarkdownData("SignatureComponent.html", webView)
    }

    private fun floatingButtonClick() {
        floatUndo.setOnClickListener(View.OnClickListener {
            println("点击了撤销")
            signatureView.undo()
        })
        floatClean.setOnClickListener(View.OnClickListener {
            println("点击了清空")
            signatureView.cleanPage()
        })
        floatSave.setOnClickListener {
            println("点击了保存")
            var result = signatureView.canvasBitmap
            if (result != null) {
                layoutResult.visibility = View.VISIBLE
                signatureBitmap.setImageBitmap(result)
            } else {
                layoutResult.visibility = View.GONE
            }
            signatureView.markSaved()
            signatureView.cleanPage()
        }

        signatureView.setDeletionListener { println("对象被删除$it") }
        signatureView.setScaleRotateListener(object : SignatureComponent.ScaleRotateListener {
            override fun endRotate() {
                println("结束旋转")
            }

            override fun startRotate(): Boolean {
                println("开始旋转")
                return true
            }
        })
    }
}