package com.mti.component.master.view.example

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mti.component.common.entry.ImageData
import com.mti.component.common.image.ImageAddComponent
import com.mti.component.common.nice9.ImageNice9Component
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_image_nice9.*

/**
 * @anthor created by jzw
 * @date 2020/5/25
 * @change
 * @describe 可拖拽的九宫格 图片显示组件
 **/
class ImageNice9ComponentActivity : BaseActivity() {
    private lateinit var mData: ArrayList<String>

    private var num = 8
    override fun getLayoutId(): Int {
        return R.layout.act_image_nice9
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("ImageNice9Component")
        showToolbarBackView()
        initUrlData()
        btnReduce.setOnClickListener(View.OnClickListener { reduceClick() })
        btnPlus.setOnClickListener(View.OnClickListener { plusClick() })
        ivNice9.setItemDelegate(object : ImageNice9Component.ItemDelegate {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@ImageNice9ComponentActivity, "第${position}项被点击了", Toast.LENGTH_SHORT).show()
            }

            override fun onItemRemoveClick(position: Int) {
                ivNice9.removeItemAt(position)
                Toast.makeText(this@ImageNice9ComponentActivity, "第${position}项被移除了", Toast.LENGTH_SHORT).show()
            }
        })
        //设置占位图片
        var placeholderDrawable = ColorDrawable(Color.parseColor("#FFD5F6F3"))
        ivNice9.setPlaceholderDrawable(placeholderDrawable)

        var data = createData();
        ivNice9.openEditModel(true)
        ivNice9.setNewData(data)

        initImageAddComponent()

        setMarkdownData("ImageNice9Component.html", webView)
    }

    private fun initImageAddComponent() {
        imageAddComponent
                .enableEditMode(true)
                .showPlayVideoButton(false)
                .setNewData(initImageData())
                .setOnImageComponentItemListener(object : ImageAddComponent.OnImageComponentItemListener {
                    override fun onPlayViewClick(position: Int) {
                        println("点击了播放:$position")
                    }

                    override fun onAddViewClick() {
                        println("点击了添加")
                    }

                    override fun onItemDeleteClick(position: Int) {
                        println("点击了删除：$position")
                        imageAddComponent.deleteItem(position)
                    }

                    override fun onItemClick(position: Int) {
                        println("点击了item:$position")
                    }
                })
    }

    private fun reduceClick() {
        num--
        if (num <= 1) num = 1
        ivNice9.setNewData(createData())
    }

    private fun plusClick() {
        num++
        if (num >= 9) num = 9
        ivNice9.setNewData(createData())
    }

    private fun createData(): ArrayList<String> {
        var urls = ArrayList<String>()
        for (i in 1..num) {
            println("构建数据:" + mData[i - 1])
            urls.add(mData[i - 1]);
        }
        return urls
    }

    private fun initUrlData(): ArrayList<String> {
        mData = ArrayList()
        mData.add("https://cdn.pixabay.com/photo/2015/05/22/05/52/cat-778315__480.jpg")
        mData.add("https://cdn.pixabay.com/photo/2012/11/26/13/58/cat-67345__480.jpg")
        mData.add("https://cdn.pixabay.com/photo/2013/11/08/21/12/cat-207583__480.jpg")
        mData.add("https://cdn.pixabay.com/photo/2015/03/27/13/16/cat-694730__480.jpg")
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590407381968&di=8818c4940a9fcbd39ae536bb1529aa6e&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F43%2F74%2F01300000164151121808741085971.jpg")

        mData.add("https://cdn.pixabay.com/photo/2013/09/29/12/38/cat-188088__480.jpg")
        mData.add("https://cdn.pixabay.com/photo/2016/07/10/21/47/cat-1508613__480.jpg")
        mData.add("https://cdn.pixabay.com/photo/2013/05/30/18/21/tabby-114782__480.jpg")
        mData.add("https://cdn.pixabay.com/photo/2014/01/17/14/53/cat-246933__480.jpg")

        return mData;
    }

    private fun initImageData(): ArrayList<ImageData> {
        var mData = ArrayList<ImageData>()
        mData.add(ImageData("https://cdn.pixabay.com/photo/2015/05/22/05/52/cat-778315__480.jpg", ""))
        mData.add(ImageData("https://cdn.pixabay.com/photo/2012/11/26/13/58/cat-67345__480.jpg", ""))
        mData.add(ImageData("https://cdn.pixabay.com/photo/2013/11/08/21/12/cat-207583__480.jpg", ""))
        mData.add(ImageData("https://cdn.pixabay.com/photo/2015/03/27/13/16/cat-694730__480.jpg", ""))

        mData.add(ImageData("https://cdn.pixabay.com/photo/2013/09/29/12/38/cat-188088__480.jpg", ""))
        mData.add(ImageData("https://cdn.pixabay.com/photo/2016/07/10/21/47/cat-1508613__480.jpg", ""))
        mData.add(ImageData("https://cdn.pixabay.com/photo/2013/05/30/18/21/tabby-114782__480.jpg", ""))
        mData.add(ImageData("https://cdn.pixabay.com/photo/2014/01/17/14/53/cat-246933__480.jpg", ""))

        return mData;
    }
}