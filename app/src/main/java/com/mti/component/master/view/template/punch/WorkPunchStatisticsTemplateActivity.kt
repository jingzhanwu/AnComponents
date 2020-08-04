package com.mti.component.master.view.template.punch

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.mti.calendar.Article
import com.mti.calendar.group.GroupItemDecoration
import com.mti.component.master.R
import com.mti.component.master.adapter.PunchInfoAdapter
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.model.PunchInfoEntry
import kotlinx.android.synthetic.main.activity_service_scheduling_template.*
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.List
import kotlin.collections.MutableMap
import kotlin.collections.listOf
import kotlin.collections.mutableListOf
import kotlin.collections.set

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/6/17
 * @change
 * @describe 打卡模板统计
 **/
class WorkPunchStatisticsTemplateActivity : BaseActivity(), CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {
    private var mYear = 0
    override fun getLayoutId() = R.layout.activity_work_punch_statistics_template
    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("打卡模板统计")
        showToolbarBackView()
        initUI()
        initData()
    }


    private fun initUI() {

        tvMonthDay.setOnClickListener {
            if (!calendarLayout.isExpand) {
                calendarLayout.expand()
            }
            calendarView.showYearSelectLayout(mYear)
            tvLunar.visibility = View.GONE
            tvYear.visibility = View.GONE
            tvMonthDay.text = mYear.toString()
        }

        flCurrent.setOnClickListener { calendarView.scrollToCurrent() }
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnYearChangeListener(this)


        tvYear.text = calendarView.curYear.toString()
        mYear = calendarView.curYear
        tvMonthDay.text = calendarView.curMonth.toString() + "月" + calendarView.curDay + "日"
        tvLunar.text = "今日"
        tvCurrentDay.text = calendarView.curDay.toString()
    }


    private fun initData() {
        val year: Int = calendarView.curYear
        val month: Int = calendarView.curMonth
        val map: MutableMap<String, Calendar?> = HashMap()
        val scheme1 = getSchemeCalendar(year, month, 3, -0xec5310, "班")
        val scheme2 = getSchemeCalendar(year, month, 6, -0xec5310, "班")
        val scheme3 = getSchemeCalendar(year, month, 9, -0xec5310, "班")
        val scheme4 = getSchemeCalendar(year, month, 13, -0xec5310, "班")
        val scheme5 = getSchemeCalendar(year, month, 14, -0xec5310, "班")
        val scheme6 = getSchemeCalendar(year, month, 15, -0x123a93, "假")
        val scheme7 = getSchemeCalendar(year, month, 18, -0x43ec10, "记")
        val scheme8 = getSchemeCalendar(year, month, 25, -0x123a93, "假")
        val scheme9 = getSchemeCalendar(year, month, 27, -0x123a93, "假")
        map[scheme1.toString()] = scheme1
        map[scheme2.toString()] = scheme2
        map[scheme3.toString()] = scheme3
        map[scheme4.toString()] = scheme4
        map[scheme5.toString()] = scheme5
        map[scheme6.toString()] = scheme6
        map[scheme7.toString()] = scheme7
        map[scheme8.toString()] = scheme8
        map[scheme9.toString()] = scheme9
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(GroupItemDecoration<String, Article>())
        generateData()
        recyclerView.adapter = PunchInfoAdapter(this, listOf("班次：B 09：00-18：15"), generateData())
        recyclerView.notifyDataSetChanged()

    }

    private fun generateData(): LinkedHashMap<String, List<PunchInfoEntry>> {
        var data = LinkedHashMap<String, List<PunchInfoEntry>>()
        var list = mutableListOf<PunchInfoEntry>()
        list.add(PunchInfoEntry(punchCount = 1,
                punchDuration = 8,
                punchOrder = "上",
                punchTime = "08:56",
                punchDes = "（ 上班 时间 09：00 ）",
                address = "丈八北路",
                punchStatus = "正常"))

        data["班次：B 09：00-18：15"] = list
        return data
    }


    /**
     * 构建日期
     */
    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar? {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        calendar.addScheme(Calendar.Scheme())
        calendar.addScheme(-0xff7800, "假")
        calendar.addScheme(-0xff7800, "节")
        return calendar
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        tvLunar.visibility = View.VISIBLE
        tvYear.visibility = View.VISIBLE
        tvMonthDay.text = calendar!!.month.toString() + "月" + calendar!!.day + "日"
        tvYear.text = calendar.year.toString()
        tvLunar.text = calendar.lunar
        mYear = calendar.year

        Log.e("onDateSelected", "  -- " + calendar!!.year +
                "  --  " + calendar!!.month +
                "  -- " + calendar!!.day +
                "  --  " + isClick + "  --   " + calendar!!.scheme)
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {

    }

    override fun onYearChange(year: Int) {
        tvMonthDay.text = year.toString()
    }

}