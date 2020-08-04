package com.mti.component.master.view.example.chart

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.mti.component.master.R
import com.mti.component.master.adapter.ViewPagerAdapter
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_chart_component.*

/**
 * @anthor created by jzw
 * @date 2020/6/2
 * @change
 * @describe 统计图表相关组件demo
 **/
class ChartComponentActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.act_chart_component
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("统计图表")
        showToolbarBackView()
        initTabLayout()

        floatLineDoc.setOnClickListener(View.OnClickListener {
            openDoc("LineChartComponent.html", "折线图")
        })
        floatPieDoc.setOnClickListener(View.OnClickListener {
            openDoc("PieChartComponent.html", "饼图")
        })
        floatColumnDoc.setOnClickListener(View.OnClickListener {
            openDoc("ColumnChartComponent.html", "柱状图")
        })
    }

    private fun openDoc(docName: String, title: String) {
        val intent = Intent(this, ChartUseDocActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("docName", docName)
        startActivity(intent)
    }

    private fun initTabLayout() {
        val fragments = listOf(
                PieChartFragment(),
                CubicLineChartFragment(),
                LineChartFragment(),
                ColumnChartFragment()
        )
        val pageAdapter = ViewPagerAdapter(supportFragmentManager)
        pageAdapter.setFragments(fragments)
        pageAdapter.setTitle(listOf("饼状图", "曲线图", "折线图", "柱状图"))
        viewPager.apply {
            adapter = pageAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(p0: Int) {
                }

                override fun onPageScrollStateChanged(p0: Int) {
                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                }

            })
//            viewPager.currentItem = 0
        }
        tabLayout.setupWithViewPager(viewPager)
    }
}