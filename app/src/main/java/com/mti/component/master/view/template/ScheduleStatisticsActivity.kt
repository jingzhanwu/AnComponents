package com.mti.component.master.view.template

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.mti.component.extend.chart.ColumnChartRender
import com.mti.component.extend.chart.ComboLineColumnChartRender
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.view.template.punch.WorkPunchStatisticsTemplateActivity
import com.mti.component.master.view.template.punch.WorkPunchTemplateActivity
import kotlinx.android.synthetic.main.activity_schedule_statistics.*
import lecho.lib.hellocharts.view.ColumnChartView

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/6/12
 * @change
 * @describe 勤务排班统计
 **/
class ScheduleStatisticsActivity : BaseActivity() {
    private var normalData = mutableListOf<Map<Float, Int>>()
    private var specialData = mutableListOf<Map<Float, Int>>()
    private val normalXTitles = listOf<String>("街面", "社区", "楼宇", "窗口", "指挥调度", "执法办案")
    private val specialXTitles = listOf<String>("等级勤务", "警务勤务", "大型活动", "集中行动", "政府协助", "其他")
    private val normalColor = Color.parseColor("#56C0FC")
    private val specialColor = Color.parseColor("#56DBAB")
    override fun getLayoutId(): Int = R.layout.activity_schedule_statistics

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("勤务统计")
        showToolbarBackView()
        initData()
        render()
    }

    private fun initData() {

        normalData.add(mapOf(50F to normalColor))
        normalData.add(mapOf(23F to normalColor))
        normalData.add(mapOf(10F to normalColor))
        normalData.add(mapOf(78F to normalColor))
        normalData.add(mapOf(45F to normalColor))
        normalData.add(mapOf(39F to normalColor))


        specialData.add(mapOf(8F to specialColor))
        specialData.add(mapOf(14F to specialColor))
        specialData.add(mapOf(16F to specialColor))
        specialData.add(mapOf(22F to specialColor))
        specialData.add(mapOf(15F to specialColor))
        specialData.add(mapOf(12F to specialColor))
    }

    /**
     * 柱状图
     */
    private fun render() {

        ivSeeMoreSchedule.setOnClickListener {
            startActivity(Intent(this@ScheduleStatisticsActivity, WorkPunchTemplateActivity::class.java))
        }

        ColumnChartRender.Builder()
                .chartView(normalScheduleChartView)
                .columnData(normalData)
                .xTitles(normalXTitles)
                .hasAxes(true)
                .hasLines(true)
                .hasLabels(true)
                .yAxisName("时间/h")
                .hasLabelForSelected(true)
                .axisTextColor(Color.parseColor("#666666"))
                .build()
                .render()

        ComboLineColumnChartRender.Builder()
                .chartView(specialScheduleChartView)
                .columnData(specialData)
                .xTitles(specialXTitles)
                .lineData(listOf(8f, 14f, 16f, 22f, 15f, 12f))
                .hasAxes(true)
                .hasLines(true)
                .hasLabels(true)
                .isCubic(true)
                .hasPoints(true)
                .yAxisName("时间/h")
                .lineColor(Color.parseColor("#EEA236"))
                .hasLabelForSelected(true)
                .axisTextColor(Color.parseColor("#666666"))
                .build()
                .render()


    }

}