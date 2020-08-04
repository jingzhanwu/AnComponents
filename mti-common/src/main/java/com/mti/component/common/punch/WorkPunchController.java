package com.mti.component.common.punch;

import android.text.TextUtils;

import com.mti.component.common.entry.ClockEntry;


/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2018/7/11
 * @change
 * @describe 上下班打卡控制类
 **/
public class WorkPunchController {

    private PunchComponent goToWorkClockLayout;
    private PunchComponent goOffWorkClockLayout;

    /**
     * 上班打卡
     */
    private final String STATUS_ON_CLOCK = "0";

    /**
     * 下班打卡
     */
    private final String STATUS_OFF_CLOCK = "1";

    /**
     * 已经下班打过卡
     */
    private final String STATUS_NORMAL_CLOCK = "2";

    public WorkPunchController(PunchComponent goToWorkClockLayout, PunchComponent goOffWorkClockLayout) {
        this.goToWorkClockLayout = goToWorkClockLayout;
        this.goOffWorkClockLayout = goOffWorkClockLayout;
        init();
    }

    private void init() {
        goToWorkClockLayout.getDesTextView().setText("上班时间09：00");
        goOffWorkClockLayout.getDesTextView().setText("下班时间05：00");
    }

    public void renderView(ClockEntry clockInfo) {
        if (null == clockInfo) {
            return;
        }

        String status = clockInfo.getStatus();

        if (TextUtils.equals(status, STATUS_ON_CLOCK)) {
            //未上班打卡
            goToWorkClockLayout.punchClock("上班打卡");
            goOffWorkClockLayout.defaultAction();
        } else if (TextUtils.equals(status, STATUS_OFF_CLOCK)) {
            //上班打卡已结，下班未打卡
            goToWorkClockLayout.normal("打卡时间" + clockInfo.getStartTime(), clockInfo.getStartAddress());
            goOffWorkClockLayout.punchClock("下班打卡");
        } else if (TextUtils.equals(status, STATUS_NORMAL_CLOCK)) {
            //下班打卡结束
            goToWorkClockLayout.normal("打卡时间" + clockInfo.getStartTime(), clockInfo.getStartAddress());
            goOffWorkClockLayout.normal("打卡时间" + clockInfo.getEndTime(), clockInfo.getEndAddress());
        }

    }


    public void updateTime() {
        goToWorkClockLayout.updateTime();
        goOffWorkClockLayout.updateTime();
    }


    /**
     * 结束动画
     */
    public void endAnimation(){
        goToWorkClockLayout.endAnimation();
        goOffWorkClockLayout.endAnimation();
    }



}
