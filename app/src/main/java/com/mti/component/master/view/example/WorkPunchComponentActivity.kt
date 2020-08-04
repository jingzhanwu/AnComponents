package com.mti.component.master.view.example

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 打卡组件测试  上下班打卡
 **/
class WorkPunchComponentActivity : BaseActivity(), WorkPunchComponent.OnWorkPunchClickListener {
    private val TAG = "WorkPunchComponentActivity--"
    private val standardDateFormat = "yyyy-MM-dd HH:mm:ss"
    override fun initViews(savedInstanceState: Bundle?) {
        supportActionBar!!.title = "WorkPunchComponent"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        workPunchLayout.addOnWorkPunchClickListener(this)
        workPunchLayout.updateGoToWorkTitleDes("10点开始上班")
        workPunchLayout.updateGoOffWorkTitleDes("6点下班")
        reset()
        workPunchLayout.startTimer()
        setMarkdownData()
    }

    private fun setMarkdownData() {
//        String content = AssetsUtil.getAssetsFileContent(this, "InputComponent.html");
//        RichText.from(content).autoFix(true).into(tvRichText);
//        webView.loadUrl("file:///android_asset/WorkPunchLayoutComponent.html")
    }

    override fun getLayoutId(): Int = R.layout.activity_work_punch

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
     * 上班
     */
    override fun onGoWork() {
        toast("上班")
        var clockEntry = ClockEntry()
                .apply {
                    status = "1"
                    startAddress = "太和时代广场"
                    startTime = formatCurrentTime(dateFormat = standardDateFormat)

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
                    startAddress = "太和时代广场"
                    startTime = formatCurrentTime(dateFormat = standardDateFormat)
                    endAddress = "太和时代广场2"
                    endTime = "2020-05-21 18:48"
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