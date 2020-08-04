package com.mti.component.master.view.example.timeline

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mti.component.common.TimeLineComponent
import com.mti.component.common.util.DisplayTool
import com.mti.component.master.R
import com.mti.component.master.adapter.TimeLineComponentAdapter
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.model.TimeLineEntry
import kotlinx.android.synthetic.main.act_timeline_component.*

/**
 * @anthor created by jzw
 * @date 2020/5/22
 * @change
 * @describe 时间轴组件测试
 **/
class TimeLineComponentActivity : BaseActivity() {

    //时间轴的数据列表
    private val mDataList = ArrayList<TimeLineEntry>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAttributes: TimelineAttributes

    override fun getLayoutId(): Int {
        return R.layout.act_timeline_component
    }


    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("TimeLineComponent")
        showToolbarBackView()
        mAttributes = TimelineAttributes(
                markerSize = DisplayTool.dip2px(this, 20f),
                markerColor = getColorCompat(R.color.gray),
                markerInCenter = true,
                markerLeftPadding = DisplayTool.dip2px(this, 0f),
                markerTopPadding = DisplayTool.dip2px(this, 0f),
                markerRightPadding = DisplayTool.dip2px(this, 0f),
                markerBottomPadding = DisplayTool.dip2px(this, 0f),
                linePadding = DisplayTool.dip2px(this, 2f),
                startLineColor = getColorCompat(R.color.colorAccent),
                endLineColor = getColorCompat(R.color.colorAccent),
                lineStyle = TimeLineComponent.LineStyle.DASHED,
                lineWidth = DisplayTool.dip2px(this, 2f),
                lineDashWidth = DisplayTool.dip2px(this, 4f),
                lineDashGap = DisplayTool.dip2px(this, 2f)
        )
        setDataListItems()
        initRecyclerView()
        mAttributes.onOrientationChanged = { oldValue, newValue ->
            if (oldValue != newValue) initRecyclerView()
        }

        setClickHorizontal()
        setClickVertical()

//        setMarkdownData("TimeLineComponent.html", webView);
    }

    private fun setClickVertical() {
        btnVertical.setOnClickListener { setTimeLineStyleVertical() }
    }


    private fun setClickHorizontal() {
        btnHorizontal.setOnClickListener { setTimeLineStyleHorizontal() }
    }

    /**
     * 设置时间轴为水平样式
     */
    private fun setTimeLineStyleHorizontal() {
        mAttributes.orientation = Orientation.HORIZONTAL
    }

    /**
     * 设置时间轴为垂直样式
     */
    private fun setTimeLineStyleVertical() {
        mAttributes.orientation = Orientation.VERTICAL
    }

    private fun setDataListItems() {
        mDataList.add(TimeLineEntry("指令下发", "2020-05-22 07:00", OrderStatus.INACTIVE))
        mDataList.add(TimeLineEntry("编号007号警员已签收", "2020-05-22 08:00", OrderStatus.ACTIVE))
        mDataList.add(TimeLineEntry("民警到达事发现场", "2020-05-22 21:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineEntry("现场证据已反馈指挥中心", "2020-05-22 18:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineEntry("已结案", "2020-05-22 14:00", OrderStatus.COMPLETED))
    }

    private fun initRecyclerView() {
        initAdapter()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("LongLogTag")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun initAdapter() {
        mLayoutManager = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = TimeLineComponentAdapter(mDataList, mAttributes)
        }
    }

    fun getColorCompat(resId: Int) = ContextCompat.getColor(this, resId)

    fun View.setVisible() {
        visibility = View.VISIBLE
    }

    fun View.setInvisible() {
        visibility = View.INVISIBLE
    }

    fun View.setGone() {
        visibility = View.GONE
    }
}