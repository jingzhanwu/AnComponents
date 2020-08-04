package com.mti.component.master.view.template

import android.graphics.Color
import android.os.Bundle
import com.mti.component.extend.chart.PieChartRender
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_alarm_statistics_template.*
import kotlinx.android.synthetic.main.alarm_level_layout.*
import kotlin.random.Random

/**
 * @anthor created by jzw
 * @date 2020/6/16
 * @change
 * @describe 警情统计模板
 **/
class AlarmStatisticsTemplateActivity : BaseActivity() {

    private lateinit var random: Random
    override fun getLayoutId() = R.layout.act_alarm_statistics_template

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("警情统计")
        showToolbarBackView()
        //初始化random，产生0-100的随机数
        random = Random(100)
        alarmLevelDetailChartRender(createNewLevelDetailData())
        addAlarmChartClick()
    }

    private fun addAlarmChartClick() {
        alarm1.setOnClickListener {
            alarmLevelDetailChartRender(createNewLevelDetailData())
        }
        alarm2.setOnClickListener {
            alarmLevelDetailChartRender(createNewLevelDetailData())
        }
        alarm3.setOnClickListener {
            alarmLevelDetailChartRender(createNewLevelDetailData())
        }
        alarm4.setOnClickListener {
            alarmLevelDetailChartRender(createNewLevelDetailData())
        }
    }

    /**
     * 产生一组新的警情等级详情统计数据
     */
    private fun createNewLevelDetailData(): FloatArray {
        return floatArrayOf(getRandom(), getRandom(), getRandom(), getRandom())
    }

    /**
     * 产生一个10--100之间的随机float数
     */
    private fun getRandom(): Float {
        return random.nextFloat() + 10
    }

    /**
     * 警情等级详情统计
     */
    private fun alarmLevelDetailChartRender(data: FloatArray) {
        var data = mutableMapOf<Float, Int>(
                data[0] to Color.parseColor("#FF0033"),
                data[1] to Color.parseColor("#ff6633"),
                data[2] to Color.parseColor("#33ff99"),
                data[3] to Color.parseColor("#cc00ff")
        )
        PieChartRender.Builder()
                .chartView(alarmLevelDetailChartView)
                .sliceValues(data)
                .slicesSpacing(0)
                .hasLabels(false)
                .hasCenterCircle(true)
                .hasCenterText1(false)
                .hasCenterText2(false)
                .hasLabelForSelected(true)
                .build()
                .render()

    }
}