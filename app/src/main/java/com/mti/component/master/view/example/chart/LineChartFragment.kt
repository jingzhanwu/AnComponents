package com.mti.component.master.view.example.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.mti.component.extend.chart.LineChartRender
import com.mti.component.master.R
import com.mti.component.master.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_line_chart.lineChartView
import java.util.ArrayList
import kotlin.math.roundToInt

/**
 * @anthor created by jzw
 * @date 2020/6/4
 * @change
 * @describe 折线图表demo
 **/
class LineChartFragment : BaseFragment() {
    //线数据
    private val lineData = listOf(17F, 12F, 25F, 28F, 23F, 30F, 15F)

    //是否已经初始化过
    private var isInit: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_line_chart
    }


    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        render()
        isInit = true
    }

    override fun loadData() {
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        updateValue()
    }

    private fun updateValue() {
        if (userVisibleHint && isInit && lineChartView != null) {
            for ((index, line) in lineChartView.lineChartData.lines.withIndex()) {
                for (value in line.values) {
                    value.setTarget(value.x, (Math.random() * (lineData.size - 1)).roundToInt().toFloat())
                }
            }
            lineChartView.startDataAnimation()
        }
    }

    /**
     * 折线图
     */
    private fun render() {
        val xData = listOf<String>("4/21", "4/30", "5/08", "6/01", "6/03", "6/04", "6/06")
        val color = intArrayOf(Color.parseColor("#00EAFF"))
        LineChartRender.Builder()
                .chartView(lineChartView)
                .lineData(arrayListOf(lineData))
                .lineColor(color)
                .xName("时间/天")
                .yName("预警数量/起")
                .axisTextColor(Color.parseColor("#6699ff"))
                .xData(xData)
                .hasAxes(true)
                .hasPoints(false)
                .strokeWidth(2)
                .areaTransparency(40)
                .build()
                .render()
    }
}