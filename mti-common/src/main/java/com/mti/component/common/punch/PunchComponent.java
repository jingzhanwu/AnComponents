package com.mti.component.common.punch;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mti.component.common.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/20
 * @change
 * @describe 打卡组件(上下班共用一个打卡组件)
 *  未打卡，打卡状态切换（有定位信息显示）
 **/
public class PunchComponent extends LinearLayout {

    /**
     * 上班状态时间描述
     */
    private TextView desTextView;

    /**
     * 打卡时间
     */
    private TextView punchTimeTextView;

    /**
     * 位置
     */
    private TextView locationTextView;
    private FrameLayout punchClockLayout;

    /**
     * 上下班状态
     */
    private TextView titleTextView;

    /**
     * 运行时间
     */
    private TextView timeTextView;

    private AVLoadingIndicatorView loadingView;

    /**
     * 加载颜色
     */
    private int indicatorColor= Color.parseColor("#FA3F7F");
    /**
     * 时间格式化
     */
    private static final String DATE_FORMAT="HH:mm:ss";
    private SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
    private static final TimeZone TIME_ZONE_GMT_8 = TimeZone.getTimeZone("GMT+8");
    private OnPunchClockListener onPunchClockListener;
    /**
     * 位置布局
     */
    private LinearLayout locLayout;


    public interface OnPunchClockListener {
        /**
         * 打卡
         */
        void onPunchClock();
    }

    public PunchComponent(Context context) {
        this(context,null);
    }


    public PunchComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.punch_component, this);
        initView();
        loadingView.setIndicatorColor(indicatorColor);
        loadingView.hide();
    }

    private void initView() {
        //上班时间描述
        desTextView= findViewById(R.id.desTextView);
        //打卡时间
        punchTimeTextView= findViewById(R.id.punchTimeTextView);

        //位置布局
        locLayout= findViewById(R.id.locLayout);

        //位置信息
        locationTextView= findViewById(R.id.locationTextView);
        //打卡布局
        punchClockLayout= findViewById(R.id.punchClockLayout);
        //加载动画
        loadingView= findViewById(R.id.loadingView);
        //打卡显示标题
        titleTextView= findViewById(R.id.titleTextView);
        //打卡显示当前时间
        timeTextView= findViewById(R.id.timeTextView);

        punchClockLayout.setOnClickListener(this::punch);
        punchClockLayout.setBackground(new PunchBackGround());
    }

    /**
     * 设置打卡和加载颜色
     * @param indicatorColor
     */
    public void setIndicatorColor(int indicatorColor){
        this.indicatorColor=indicatorColor;
        loadingView.setIndicatorColor(indicatorColor);
        punchClockLayout.setBackground(new PunchBackGround(indicatorColor));
    }

    /**
     * 设置打卡监听
     * @param onPunchClockListener
     */
    public void addOnPunchClockListener(OnPunchClockListener onPunchClockListener) {
        this.onPunchClockListener = onPunchClockListener;
    }

    /**
     * 打卡
     * @param view
     */
    private void punch(View view) {
        if (onPunchClockListener == null) {
            return;
        }
        startAnimation();
        onPunchClockListener.onPunchClock();
    }


    /**
     * 定位中
     */
    public void locating() {
        punchTimeTextView.setVisibility(GONE);
        locLayout.setVisibility(GONE);

        punchClockLayout.setVisibility(VISIBLE);
        //执行显示定位状态
        titleTextView.setVisibility(VISIBLE);
        titleTextView.setText("定位中...");

        timeTextView.setVisibility(GONE);
    }

    /**
     * 打卡完成（获取数据完成）
     */
    public void normal(String time, String location) {
        punchTimeTextView.setVisibility(VISIBLE);
        locLayout.setVisibility(VISIBLE);

        punchTimeTextView.setText(time);
        locationTextView.setText(location);

        punchClockLayout.setVisibility(GONE);

    }

    /**
     * 待打卡（时间显示中）
     */
    public void punchClock(String statusContent) {
        punchTimeTextView.setVisibility(GONE);
        locLayout.setVisibility(GONE);

        //可执行打卡操作
        punchClockLayout.setVisibility(VISIBLE);
        titleTextView.setVisibility(VISIBLE);
        timeTextView.setVisibility(VISIBLE);

        titleTextView.setText(statusContent);

    }


    /**
     * 未执行动作
     */
    public void defaultAction() {
        punchTimeTextView.setVisibility(GONE);
        locLayout.setVisibility(GONE);
        punchClockLayout.setVisibility(GONE);
    }

    /**
     * 更新时间
     */
    public void updateTime() {
        String time=formatCurrentTime();
        timeTextView.setText(time);
    }



    /**
     * 打卡标题
     * @return
     */
    public TextView getDesTextView() {
        return desTextView;
    }

    /**
     * 打卡时间
     * @return
     */
    public TextView getPunchTimeTextView() {
        return punchTimeTextView;
    }

    /**
     * 位置
     * @return
     */
    public TextView getLocationTextView() {
        return locationTextView;
    }

    /**
     * 打卡操作上的描述
     * @return
     */
    public TextView getTitleTextView() {
        return titleTextView;
    }

    /**
     * 当前时间
     * @return
     */
    public TextView getTimeTextView() {
        return timeTextView;
    }

    /**
     * 开始动画
     */
    public void startAnimation(){
        loadingView.smoothToShow();
    }

    public void endAnimation(){
        loadingView.smoothToHide();
    }


    /**
     * 格式化时间
     * @return
     */
    private String formatCurrentTime(){
        try {
            long currentTime=System.currentTimeMillis();
            Calendar calendar= Calendar.getInstance(TIME_ZONE_GMT_8);
            calendar.setTimeInMillis(currentTime);

            dateFormat.setTimeZone(TIME_ZONE_GMT_8);
            return dateFormat.format(calendar.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
       return "";
    }


}
