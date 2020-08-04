package com.mti.component.master.view.example.chart

import android.os.Bundle
import android.view.View
import com.mti.component.extend.chart.ColumnChartRender
import com.mti.component.master.R
import com.mti.component.master.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_column_line_chart.*
import lecho.lib.hellocharts.util.ChartUtils

/**
 * @anthor created by jzw
 * @date 2020/6/4
 * @change
 * @describe 柱状图图表demo
 **/
class ColumnChartFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_column_line_chart
    }

    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        render()
    }

    override fun loadData() {

    }

    /**
     * 柱状图
     */
    private fun render() {
        var data = mutableListOf<Map<Float, Int>>()
        data.add(mapOf(50F to ChartUtils.pickColor(), 12F to ChartUtils.pickColor(), 25F to ChartUtils.pickColor()))
        data.add(mapOf(23F to ChartUtils.pickColor()))
        data.add(mapOf(10F to ChartUtils.pickColor()))
        data.add(mapOf(78F to ChartUtils.pickColor(), 42F to ChartUtils.pickColor(), 30F to ChartUtils.pickColor()))
        data.add(mapOf(45F to ChartUtils.pickColor()))
        data.add(mapOf(39F to ChartUtils.pickColor()))
        data.add(mapOf(21F to ChartUtils.pickColor()))
        data.add(mapOf(66F to ChartUtils.pickColor()))
        data.add(mapOf(38F to ChartUtils.pickColor()))
        data.add(mapOf(29F to ChartUtils.pickColor()))

        ColumnChartRender.Builder()
                .chartView(columnChartView)
                .columnData(data)
                .hasAxes(true)
                .hasLines(true)
                .hasLabels(true)
                .hasLabelForSelected(true)
                .build()
                .render()
    }
}