package com.mti.component.master.view.example.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.mti.component.extend.chart.LineChartRender
import com.mti.component.master.R
import com.mti.component.master.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cubic_line_chart.*
import java.util.*
import kotlin.math.roundToInt

/**
 * @anthor created by jzw
 * @date 2020/6/4
 * @change
 * @describe 平滑曲线图表demo
 **/
class CubicLineChartFragment : BaseFragment() {

    //线1和线2的数据
    private val data1 = listOf(12F, 18F, 15F, 17F, 8F)
    private val data2 = listOf(14F, 15F, 20F, 10F, 12F)

    //线
    private val lines = listOf(data1, data2)

    //是否已经初始化过
    private var isInit: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_cubic_line_chart
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
        if (userVisibleHint && isInit && cubicLineChartView != null) {
            for ((index, line) in cubicLineChartView.lineChartData.lines.withIndex()) {
                for (value in line.values) {
                    value.setTarget(value.x, (Math.random() * (lines[index].size - 1)).roundToInt().toFloat())
                }
            }
            cubicLineChartView.startDataAnimation()
        }
    }

    /**
     * 曲线图
     */
    private fun render() {
        val xdata: MutableList<String> = ArrayList()
        xdata.add("4/21")
        xdata.add("4/30")
        xdata.add("5/08")
        xdata.add("6/01")
        xdata.add("6/03")

        val color = intArrayOf(Color.parseColor("#00EAFF"), Color.parseColor("#ff99ff"))
        //渲染图标
        LineChartRender.Builder()
                .chartView(cubicLineChartView)
                .lineData(arrayListOf(data1, data2))
                .lineColor(color)
                .xName("时间/天")
                .yName("预警数量/起")
                .cubic(true)
                .axisTextColor(Color.parseColor("#ff66ff"))
                .xData(xdata)
                .hasPoints(true)
                .pointRadius(4)
                .strokeWidth(1)
                .build()
                .render()
    }
}