package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.common.entry.TagEntry
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.util.toast
import kotlinx.android.synthetic.main.activity_flow_component.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 标签组件
 **/
class FlowComponentActivity : BaseActivity() {

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("FlowComponent")
        showToolbarBackView()
        val interests = listOf<String>("唱歌", "看书", "划船", "饲养宠物",
                "保龄球", "登山", "摄影", "下各种棋", "吃美食",
                "书法", "看新闻", "做饭")

        val interestData = interests.map { TagEntry("$it") }.toList()

        val plants = listOf<String>("马尾松", "南天竹", "日本五针松", "马醉木",
                "天目琼花", "珊瑚树", "杜鹃", "旱柳", "万年青",
                "苦楝", "黄连木", "凌霄")

        val plantsData = plants.map { TagEntry("$it") }.toList()

        interestFlowComponent?.run {
            isShowingDelete = false
            isOptional=true
            isMultiple=true
            backgroundSelectorRes=R.drawable.label_test_selector
        }

        interestFlowComponent.initAddListener(interestData) {
            position,_ -> toast("点击了${interestData[position].tagName}") }

        plantFlowComponent.isShowingDelete = false
        plantFlowComponent.initAddListener(plantsData) {
            position,_ -> toast("点击了${plantsData[position].tagName}") }
        setMarkdownData("FlowComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_flow_component

}


