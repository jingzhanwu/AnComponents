package com.mti.component.master.view.example.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.mti.component.extend.chart.PieChartRender
import com.mti.component.master.R
import com.mti.component.master.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_pie_line_chart.*
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener
import lecho.lib.hellocharts.model.SliceValue
import kotlin.math.roundToInt

/**
 * @anthor created by jzw
 * @date 2020/6/4
 * @change
 * @describe 饼图图表demo
 **/
class PieChartFragment : BaseFragment() {

    //图表数据
    private val pieData = floatArrayOf(28f, 18f, 20f, 16f, 22f)
    private val ringData = floatArrayOf(35.5f, 89.2f, 58.9f, 25.2f)

    //是否已经初始化过
    private var isInit: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_pie_line_chart
    }

    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        pieChartViewRender()
        ringChartViewRender()
        isInit = true
    }

    override fun loadData() {
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        updateValue()
    }


    /**
     * 饼状图表
     */
    private fun pieChartViewRender() {
        var data = mutableMapOf<Float, Int>(
                pieData[0] to Color.parseColor("#ffac41"),
                pieData[1] to Color.parseColor("#2a6dbf"),
                pieData[2] to Color.parseColor("#29cece"),
                pieData[3] to Color.parseColor("#379cf8"),
                pieData[4] to Color.parseColor("#01a968")
        )
        PieChartRender.Builder()
                .chartView(pieChartView)
                .sliceValues(data)
                .hasLabels(false)
                .hasCenterCircle(false)
                .hasLabelForSelected(true)
                .build()
                .render()

        pieChartView.onValueTouchListener = object : PieChartOnValueSelectListener {
            override fun onValueSelected(arcIndex: Int, value: SliceValue?) {

            }

            override fun onValueDeselected() {
            }
        }
    }

    /**
     * 环形图表
     */
    private fun ringChartViewRender() {
        var data = mutableMapOf<Float, Int>(
                ringData[0] to Color.parseColor("#FF0033"),
                ringData[1] to Color.parseColor("#ff6633"),
                ringData[2] to Color.parseColor("#33ff99"),
                ringData[3] to Color.parseColor("#cc00ff")
        )
        PieChartRender.Builder()
                .chartView(ringChartView)
                .sliceValues(data)
                .hasLabels(true)
                .hasCenterCircle(true)
                .hasCenterText1(true)
                .hasCenterText2(true)
                .centerText1("MTI")
                .centerText2("警情统计")
                .hasLabelForSelected(false)
                .build()
                .render()
    }

    /**
     * 随机更新图表值,动画加载
     */
    private fun updateValue() {
        if (userVisibleHint && isInit) {
            //饼图
            if (pieChartView != null) {
                for (value in pieChartView.pieChartData.values) {
                    value.setTarget((Math.random() * (pieData.size - 1)).roundToInt().toFloat())
                }
                pieChartView.startDataAnimation()
            }

            //环形
            if (ringChartView != null) {
                for (value in ringChartView.pieChartData.values) {
                    value.setTarget((Math.random() * (ringData.size - 1)).roundToInt().toFloat())
                }
                ringChartView.startDataAnimation()
            }
        }
    }
}