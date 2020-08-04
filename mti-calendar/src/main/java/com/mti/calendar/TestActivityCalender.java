package com.mti.calendar;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.haibin.calendarview.CalendarView;
import com.mti.calendar.base.activity.CalenderBaseActivity;

/**
 * 测试界面
 * Created by huanghaibin on 2018/8/7.
 */

public class TestActivityCalender extends CalenderBaseActivity implements View.OnClickListener {

    private CalendarView mCalendarView;
    public static void show(Context context) {
        context.startActivity(new Intent(context, TestActivityCalender.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        setStatusBarDarkMode();
        findViewById(R.id.iv_next).setOnClickListener(this);
        findViewById(R.id.iv_pre).setOnClickListener(this);
        mCalendarView =  findViewById(R.id.calendar_view);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_next) {
            mCalendarView.scrollToNext(false);
        } else if (id == R.id.iv_pre) {
            mCalendarView.scrollToPre(false);
        }
    }
}
