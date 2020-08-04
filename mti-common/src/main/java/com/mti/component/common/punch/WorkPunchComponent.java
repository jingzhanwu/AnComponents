package com.mti.component.common.punch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.mti.component.common.R;
import com.mti.component.common.entry.ClockEntry;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/20
 * @change
 * @describe 提供一个上下班打卡的组件集合
 * 包括上班打卡，下班打卡
 * 使用步骤
 * 1.添加组件
 * 2.设置操作监听addOnWorkPunchClickListener
 * 1）执行上班打卡onGoWork方法 ，即提交上班打卡数据至后台，结束后执行cancelWorkPunchPerformance
 * 2）执行下班打卡onGoOffWork方法 ，即提交下班打卡数据至后台，结束后执行cancelWorkPunchPerformance
 * 3.执行开启时间方法startTimer
 * 4.若需要渲染页面上下班状态数据
 * 5.所在组件页面结束时，调用停止时间方法
 **/
public class WorkPunchComponent extends LinearLayout {

    private static final String TAG = "WorkPunchLayout--";
    /**
     * 上班打卡控件
     */
    private PunchComponent goToWorkClockLayout;

    /**
     * 下班打卡控件
     */
    private PunchComponent goOffWorkClockLayout;
    private WorkPunchController punchController;

    /**
     * 上下班打卡监听
     */
    private OnWorkPunchClickListener workPunchClickListener;

    public interface OnWorkPunchClickListener {
        /**
         * 上班打卡
         */
        void onGoWork();

        /**
         * 下班打卡
         */
        void onGoOffWork();
    }

    private Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "时间打印了。。。。");
            punchController.updateTime();
        }
    };

    public WorkPunchComponent(Context context) {
        this(context, null);
    }

    public WorkPunchComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.work_punch_layout, this);
        goToWorkClockLayout = findViewById(R.id.goToWorkClockLayout);
        goOffWorkClockLayout = findViewById(R.id.goOffWorkClockLayout);
        punchController = new WorkPunchController(goToWorkClockLayout, goOffWorkClockLayout);
        goToWorkClockLayout.addOnPunchClockListener(() -> {
            if (workPunchClickListener != null) {
                workPunchClickListener.onGoWork();
            }
        });

        goOffWorkClockLayout.addOnPunchClockListener(() -> {
            if (workPunchClickListener != null) {
                workPunchClickListener.onGoOffWork();
            }
        });
    }

    /**
     * 设置加载和背景颜色
     * @param indicatorColor
     */
    public void setIndicatorColor(int indicatorColor){
        goToWorkClockLayout.setIndicatorColor(indicatorColor);
        goOffWorkClockLayout.setIndicatorColor(indicatorColor);
    }

    /**
     * 更新上班描述
     * @param title
     */
    public void updateGoToWorkTitleDes(String title){
        goToWorkClockLayout.getDesTextView().setText(title);
    }

    /**
     * 更新下班描述
     * @param title
     */
    public void updateGoOffWorkTitleDes(String title){
        goOffWorkClockLayout.getDesTextView().setText(title);
    }

    /**
     * 描述文本大小
     * @param fontSize
     */
    public void setTitleDesSize(float fontSize){
        goToWorkClockLayout.getDesTextView().setTextSize(fontSize);
        goOffWorkClockLayout.getDesTextView().setTextSize(fontSize);
    }

    /**
     * 描述文本颜色
     * @param color
     */
    public void setTitleDesColor(int color){
        goToWorkClockLayout.getDesTextView().setTextColor(color);
        goOffWorkClockLayout.getDesTextView().setTextColor(color);
    }


    /**
     * 打卡时间 文本大小
     * @param fontSize
     */
    public void setPunchTimeFontSize(float fontSize){
        goToWorkClockLayout.getPunchTimeTextView().setTextSize(fontSize);
        goOffWorkClockLayout.getPunchTimeTextView().setTextSize(fontSize);
    }

    /**
     * 打卡时间 文本颜色
     * @param color
     */
    public void setPunchTimeFontColor(int color){
        goToWorkClockLayout.getPunchTimeTextView().setTextColor(color);
        goOffWorkClockLayout.getPunchTimeTextView().setTextColor(color);
    }


    /**
     * 位置 文本大小
     * @param fontSize
     */
    public void setLocationFontSize(float fontSize){
        goToWorkClockLayout.getLocationTextView().setTextSize(fontSize);
        goOffWorkClockLayout.getLocationTextView().setTextSize(fontSize);
    }

    /**
     * 位置 文本颜色
     * @param color
     */
    public void setLocationFontColor(int color){
        goToWorkClockLayout.getLocationTextView().setTextColor(color);
        goOffWorkClockLayout.getLocationTextView().setTextColor(color);
    }

    /**
     * 添加监听事件
     *
     * @param workPunchClickListener
     */
    public void addOnWorkPunchClickListener(OnWorkPunchClickListener workPunchClickListener) {
        this.workPunchClickListener = workPunchClickListener;
    }

    /**
     * 渲染接口数据
     *
     * @param clockInfo
     */
    public void rendView(ClockEntry clockInfo) {
        punchController.renderView(clockInfo);
    }

    /**
     * 结束打卡操作
     */
    public void cancelWorkPunchPerformance() {
        punchController.endAnimation();
    }


    /**
     * 开启时间
     */
    public void startTimer() {
        if (timer == null) {
            return;
        }
        timer.schedule(timerTask,100,1000);
    }

    /**
     * 取消时间
     */
    public void stopTimer() {
        if (timer == null) {
            return;
        }
        timer.cancel();
        handler.removeCallbacksAndMessages(null);
        timer=null;
        handler = null;
    }
}
