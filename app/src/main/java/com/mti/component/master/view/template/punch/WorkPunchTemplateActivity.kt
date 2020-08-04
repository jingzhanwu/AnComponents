package com.mti.component.master.view.template.punch

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mti.component.common.entry.ClockEntry
import com.mti.component.common.punch.WorkPunchComponent
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.util.formatCurrentTime
import com.mti.component.master.util.toast
import kotlinx.android.synthetic.main.activity_work_punch.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/6/17
 * @change
 * @describe 打卡模板
 **/
class WorkPunchTemplateActivity : BaseActivity(), WorkPunchComponent.OnWorkPunchClickListener {
    private val TAG = "WorkPunchTemplateActivity--"
    private val standardDateFormat = "yyyy-MM-dd HH:mm:ss"
    private var firstTime=""
    override fun getLayoutId() = R.layout.activity_work_punch_template
    override fun initViews(savedInstanceState: Bundle?) {
        supportActionBar!!.title = "打卡模板"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        workPunchLayout.addOnWorkPunchClickListener(this)
        workPunchLayout.updateGoToWorkTitleDes("上班时间09：00")
        workPunchLayout.updateGoOffWorkTitleDes("下班时间18：15")
        reset()
        workPunchLayout.startTimer()
    }

    /**
     * 重置
     */
    fun reset(view: View?) {
        view?.run {
            val rotationObjectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0F, 360F)
            val alphaObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1F, 0.6F, 1F)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(rotationObjectAnimator, alphaObjectAnimator)
            animatorSet.duration = 1000
            animatorSet.start()
        }
        reset()
    }


    /**
     * 查看更多
     */
    fun seeMore(view: View?) {
        startActivity(Intent(this,WorkPunchStatisticsTemplateActivity::class.java))
    }

    /**
     * 上班
     */
    override fun onGoWork() {
        toast("上班")
        var clockEntry = ClockEntry()
                .apply {
                    status = "1"
                    startAddress = "太和时代广场"
                    startTime = formatCurrentTime(dateFormat = standardDateFormat)
                    firstTime=startTime
                }

        renderView(clockEntry)

    }

    /**
     * 下班
     */
    override fun onGoOffWork() {
        toast("下班")
        var clockEntry = ClockEntry()
                .apply {
                    status = "2"
                    startTime=firstTime
                    startAddress = "太和时代广场"
                    endAddress = "太和时代广场2"
                    endTime = formatCurrentTime(dateFormat = standardDateFormat)
                }
        renderView(clockEntry)


    }

    /**
     * 重置
     */
    private fun reset() {
        var clockEntry = ClockEntry()
                .apply {
                    status = "0"
                    startAddress = "太和时代广场"
                }
        renderView(clockEntry)
    }

    /**
     * 渲染视图
     */
    private fun renderView(clockEntry: ClockEntry) {
        this.workPunchLayout?.run {
            rendView(clockEntry)
            cancelWorkPunchPerformance()
        }
    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "时间结束");
        workPunchLayout.stopTimer();
    }

}