package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.master.R
import com.mti.component.master.adapter.TextTagsAdapter
import com.mti.component.master.adapter.VectorTagsAdapter
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cloud_tag3d_component.*


/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 3d云图标签
 **/
class CloudTag3DComponentActivity : BaseActivity() {

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("CloudTag3D")
        showToolbarBackView()
        val textTagsAdapter = TextTagsAdapter(*arrayOfNulls(20))
        val vectorTagsAdapter = VectorTagsAdapter()
        tagCloudView.setAdapter(textTagsAdapter)
        btnText.setOnClickListener {
            tagCloudView.setAdapter(textTagsAdapter)
        }
        btnPicture.setOnClickListener {
            tagCloudView.setAdapter(vectorTagsAdapter)
        }
        setMarkdownData("CLoudTag3DComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_cloud_tag3d_component


}