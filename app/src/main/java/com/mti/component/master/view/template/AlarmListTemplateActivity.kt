package com.mti.component.master.view.template

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupPosition
import com.mti.component.common.condition.WaterfallConditionPopupComponent
import com.mti.component.common.entry.ScreenGroup
import com.mti.component.common.entry.TagEntry
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mti.component.master.R
import com.mti.component.master.adapter.AlarmListAdapter
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.condition.SimpleWaterfallConditionPopup
import com.mti.component.master.model.AlarmEntry
import com.mti.component.master.model.ConditionEntry
import com.mti.component.master.util.toast
import kotlinx.android.synthetic.main.act_alarm_list_template.*
import java.util.stream.Stream

/**
 * @anthor created by jzw
 * @date 2020/6/18
 * @change
 * @describe 警情列表模板
 **/
class AlarmListTemplateActivity : BaseActivity() {

    /**
     * 风险等级
     */
    private var riskLevelPopupView: BasePopupView? = null

    /**
     * 风险数据
     */
    private var riskLevelConditions = mutableListOf<ConditionEntry>()


    /**
     * 时间排序
     */
    private var timeSortsPopupView: BasePopupView? = null

    /**
     * 时间排序数据
     */
    private var timeSortsConditions = mutableListOf<ConditionEntry>()


    /**
     * 风险状态
     */
    private var riskStatusPopupView: BasePopupView? = null

    /**
     * 风险状态数据
     */
    private var riskStatusConditions = mutableListOf<ConditionEntry>()


    /**
     * 筛选
     */
    private var waterfallPopupView: BasePopupView? = null

    /**
     * 筛选集合
     */
    private var riskTypeConditions = mutableListOf<ScreenGroup>()

    private val riskTypes = arrayOf("医患纠纷", "居民纠纷", "居民报修", "道路抢险",
            "入室行凶", "线路安保", "电话诈骗", "黄赌毒", "群体事件",
            "卡口预警", "人员布控", "人员预警")

    override fun getLayoutId() = R.layout.act_alarm_list_template

    @SuppressLint("RestrictedApi")
    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("警情列表")
        showToolbarBackView()
        createData()
        initConditions()
        initUi()
        initListAdapter()
    }

    /**
     * 初始化条件
     */
    private fun initConditions() {
        initRiskLevelConditions()
        initTimeSortsConditions()
        initRiskTypeCondition()
        initRiskStatusConditions()
    }

    /**
     * 风险条件
     */
    private fun initRiskLevelConditions() {
        var simpleWaterfallConditionPopup = SimpleWaterfallConditionPopup(this, riskLevelConditions)
        simpleWaterfallConditionPopup.addOnSimpleConditionChangedListener { _, entry ->
            tvRiskLevelCondition.text = "${entry?.title}"
        }

        riskLevelPopupView = com.lxj.xpopup.XPopup.Builder(this)
                .atView(header)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(simpleWaterfallConditionPopup)
    }


    /**
     * 风险条件
     */
    private fun initTimeSortsConditions() {
        var simpleWaterfallConditionPopup = SimpleWaterfallConditionPopup(this, timeSortsConditions)
        simpleWaterfallConditionPopup.addOnSimpleConditionChangedListener { _, entry ->
            tvTimeCondition.text = "${entry?.title}"
        }

        timeSortsPopupView = com.lxj.xpopup.XPopup.Builder(this)
                .atView(header)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(simpleWaterfallConditionPopup)
    }


    /**
     * 风险条件
     */
    private fun initRiskStatusConditions() {
        var simpleWaterfallConditionPopup = SimpleWaterfallConditionPopup(this, riskStatusConditions)
        simpleWaterfallConditionPopup.addOnSimpleConditionChangedListener { _, entry ->
            tvRiskStatusCondition.text = "${entry?.title}"
        }

        riskStatusPopupView = com.lxj.xpopup.XPopup.Builder(this)
                .atView(header)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(simpleWaterfallConditionPopup)
    }

    private fun initUi() {
        //风险等级
        layoutRiskLevelCondition.setOnClickListener {
            if (riskLevelPopupView == null) {
                initRiskLevelConditions()
            }
            riskLevelPopupView?.show()
        }

        //时间排序
        layoutTimeCondition.setOnClickListener {
            if (timeSortsPopupView == null) {
                initTimeSortsConditions()
            }
            timeSortsPopupView?.show()
        }

        //风险类型
        layoutRiskTypeCondition.setOnClickListener {
            if (waterfallPopupView == null) {
                initRiskTypeCondition()
            }
            waterfallPopupView!!.show()
        }

        //风险状态
        layoutRiskStatusCondition.setOnClickListener {
            if (riskStatusPopupView == null) {
                initRiskStatusConditions()
            }
            riskStatusPopupView?.show()
        }

    }

    private fun initRiskTypeCondition() {

        var waterfallConditionPopupComponent = WaterfallConditionPopupComponent(this, riskTypeConditions)

        waterfallConditionPopupComponent.addWaterFallChangedListener(object : WaterfallConditionPopupComponent.OnWaterFallChangedListener {
            //选中监听，单选，多选时触发该监听，前置条件 optional=true,即可执行选中操作，单选、多选
            override fun onItemCheckedChanged(position: Int, entry: TagEntry?) {
                var result=formatResult(waterfallConditionPopupComponent.selections)
                tvRiskTypeCondition.text = result
                Log.d("screen---", "selection:$result")
            }

            //点击监听
            override fun onItemClickChanged(position: Int, entry: TagEntry?) {

            }
        })

        waterfallPopupView = com.lxj.xpopup.XPopup.Builder(this)
                .atView(header)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(waterfallConditionPopupComponent)
    }

    private fun formatResult(selections: List<ScreenGroup>): String {
        if (selections.isNotEmpty()){
           return selections[0].tags.joinToString { it.tagName }
        }
        return ""
    }

    private fun createData() {

        //构造风险等级筛选数据
        riskLevelConditions.add(ConditionEntry(title = "全部", checked = true))
        riskLevelConditions.add(ConditionEntry(title = "风险一级"))
        riskLevelConditions.add(ConditionEntry(title = "风险二级"))
        riskLevelConditions.add(ConditionEntry(title = "风险三级"))
        riskLevelConditions.add(ConditionEntry(title = "风险四级"))

        //构造时间排序筛选数据
        timeSortsConditions.add(ConditionEntry(title = "正序"))
        timeSortsConditions.add(ConditionEntry(title = "倒序", checked = true))

        //构造风险状态筛选数据
        riskStatusConditions.add(ConditionEntry(title = "全部", checked = true))
        riskStatusConditions.add(ConditionEntry(title = "待接收"))
        riskStatusConditions.add(ConditionEntry(title = "已接收"))
        riskStatusConditions.add(ConditionEntry(title = "已出警"))
        riskStatusConditions.add(ConditionEntry(title = "已到达"))
        riskStatusConditions.add(ConditionEntry(title = "已处置"))
        riskStatusConditions.add(ConditionEntry(title = "已反馈"))

        //组件
        var componentsGroup = createScreenGroup("风险类型-多选", "components", riskTypes, multiple = true)
        riskTypeConditions.add(componentsGroup)

    }

    /**
     * 构建条件
     */
    private fun createScreenGroup(title: String,
                                  key: String,
                                  components: Array<String>,
                                  showingDelete: Boolean = false,
                                  optional: Boolean = true,
                                  multiple: Boolean = false): ScreenGroup {
        //条件
        var group = ScreenGroup()
                .title(title)
                .key(key)
                .showingDelete(showingDelete)
                .optional(optional)
                .multiple(multiple)
        var tags = components.map {
            var tag = TagEntry(it)
            tag.groupId = group.key
            tag.tagValue = it
            return@map tag
        }.toList()
        return group.tags(tags)
    }


    private fun initListAdapter() {
        alarmRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AlarmListAdapter(this, fetchAlarmData())
        alarmRecyclerView.adapter = adapter
    }

    private fun fetchAlarmData(): List<AlarmEntry> {
        val data = mutableListOf<AlarmEntry>()
        data.add(AlarmEntry(alarmContent = "沪昆高速125国道小车抛锚", alarmLevel = 2, alarmType = "交通", feedback = "待接收", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "世纪公园发生抢劫致人死亡", alarmLevel = 1, alarmType = "刑事", feedback = "已到达", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "恒大城15栋2号楼301室房间漏水", alarmLevel = 3, alarmType = "求助", feedback = "已出警", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "医生遭到患者孙某某殴打", alarmLevel = 4, alarmType = "纠纷", feedback = "已反馈", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "公园路有人爬树卡主", alarmLevel = 1, alarmType = "求助", feedback = "已处置", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "天沁园小区2栋5号楼发生入室抢劫", alarmLevel = 2, alarmType = "刑事", feedback = "已接收", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "医生遭到患者孙某某殴打", alarmLevel = 1, alarmType = "刑事", feedback = "已到达", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "沪昆高速125国道小车抛锚", alarmLevel = 2, alarmType = "交通", feedback = "待接收", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "医生遭到患者孙某某殴打", alarmLevel = 4, alarmType = "纠纷", feedback = "已反馈", alarmTime = "2020-06-18 15:30"))
        data.add(AlarmEntry(alarmContent = "世纪公园发生抢劫致人死亡", alarmLevel = 1, alarmType = "刑事", feedback = "已到达", alarmTime = "2020-06-18 15:30"))
        return data
    }

    /**
     * 查看警情统计
     */
     fun openStatistics(view: View?) {
        val intent = Intent(this, AlarmStatisticsTemplateActivity::class.java)
        startActivity(intent)
    }

}


