package com.mti.component.master.view.example

import android.os.Bundle
import android.util.Log
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupPosition
import com.mti.component.common.condition.DrawerConditionPopupComponent
import com.mti.component.common.condition.WaterfallConditionPopupComponent
import com.mti.component.common.condition.WaterfallConditionPopupComponent.OnWaterFallChangedListener
import com.mti.component.common.entry.ScreenGroup
import com.mti.component.common.entry.TagEntry
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.util.toast
import kotlinx.android.synthetic.main.activity_flow_component.webView
import kotlinx.android.synthetic.main.activity_condition_component.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 筛选下拉组件
 **/
class ConditionComponentActivity : BaseActivity() {
    /**
     * 筛选
     */
    private var waterfallPopupView: BasePopupView? = null
    private var drawerPopupView: BasePopupView? = null
    private lateinit var waterfallConditionPopupComponent: WaterfallConditionPopupComponent
    private lateinit var drawerConditionPopupComponent:DrawerConditionPopupComponent

    /**
     * 筛选集合
     */
    private var conditions = mutableListOf<ScreenGroup>()

    private val organizations = arrayOf("社区运营社群", "总经办", "综合支撑部", "生态合作部",
            "数据运营社群", "人力资源部", "自驱运营中心", "集成验证中心", "研发中心",
            "发展研究中心", "技术管理中心", "南京分公司")

    private val components = arrayOf("信息录入", "勤务打卡", "日历", "时间轴",
            "文字水印", "图片九宫格", "广告轮播", "拨号盘", "标签组件",
            "视频播放组件", "蜘蛛网背景", "3d翻转", "3d云标签", "电子签名组件", "下拉筛选组件")


    private val cases = arrayOf("河北廊坊市局和省厅-合成指挥", "四川省交警总队-高速集成指挥平台Android端",
            "浙江温州龙湾分局-出警宝", "江苏南京市公安局-融合通信SDK",
            "重庆九龙坡分局-随行指挥", "四川南充-智慧综治", "四川南充-平安嘉陵", "上海嘉定外冈镇-菊园综合治理")


    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("WaterFallComponent")
        showToolbarBackView()
        layoutCondition.setOnClickListener {
            if (waterfallPopupView == null) {
                initWaterfallScreenUi()
            }
            waterfallPopupView!!.show()
        }

        fabDrawer.setOnClickListener {
            if (drawerPopupView == null) {
                initDrawerScreenUi()
            }
            drawerPopupView!!.show()
        }
        createData()
        initWaterfallScreenUi()
        initDrawerScreenUi()
        setMarkdownData("ConditionComponent.html", webView)
    }

    private fun initWaterfallScreenUi() {

        waterfallConditionPopupComponent = WaterfallConditionPopupComponent(this,conditions)

        waterfallConditionPopupComponent.addWaterFallChangedListener(object :OnWaterFallChangedListener{
            //选中监听，单选，多选时触发该监听，前置条件 optional=true,即可执行选中操作，单选、多选
            override fun onItemCheckedChanged(position: Int, entry: TagEntry?) {
                toast("您点击了第$position 个,${entry?.tagName}")
                layoutCondition.text = "选择结果：${entry?.tagName}"
                Log.d("screen---", "selection:" + waterfallConditionPopupComponent.selections)
            }
            //点击监听
            override fun onItemClickChanged(position: Int, entry: TagEntry?) {

            }
        })

        waterfallPopupView = com.lxj.xpopup.XPopup.Builder(this)
                .atView(layoutCondition)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(waterfallConditionPopupComponent)
    }


    /**
     * 侧滑条件筛选
     */
    private fun initDrawerScreenUi() {

        drawerConditionPopupComponent = DrawerConditionPopupComponent(this,conditions)
        drawerConditionPopupComponent.addOnDrawerChangedListener(object :DrawerConditionPopupComponent.OnDrawerChangedListener{
            //选中监听，单选，多选时触发该监听，前置条件 optional=true,即可执行选中操作，单选、多选
            override fun onItemCheckedChanged(position: Int, entry: TagEntry?) {
                toast("您点击了第$position 个,${entry?.tagName}")
                layoutCondition.text = "选择结果：${entry?.tagName}"
                Log.d("screen---", "selection:" + drawerConditionPopupComponent.selections)
            }
            //点击监听
            override fun onItemClickChanged(position: Int, entry: TagEntry?) {

            }
        })

        drawerPopupView = XPopup.Builder(this) //右边
                .popupPosition(PopupPosition.Right) //启用状态栏阴影
                .hasStatusBarShadow(false)
                .asCustom(drawerConditionPopupComponent)
    }


    private fun createData() {
        //组织机构

        var organizationsGroup = createScreenGroup("组织机构-单选", "organizations", organizations)
        conditions.add(organizationsGroup)

        //组件
        var componentsGroup = createScreenGroup("业务组件-多选", "components", components,multiple = true)
        conditions.add(componentsGroup)
        //应用案例
        var casesGroup = createScreenGroup("应用案例-不可点击", "cases", cases,optional =false)
        conditions.add(casesGroup)

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

    override fun getLayoutId(): Int =R.layout.activity_condition_component

}


