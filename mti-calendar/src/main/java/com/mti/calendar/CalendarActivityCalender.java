package com.mti.calendar;

import android.content.Context;
import android.content.Intent;

import com.mti.calendar.base.activity.CalenderBaseActivity;


/**
 * Only calendar
 * Created by haibin on 2019/6/12.
 */

public class CalendarActivityCalender extends CalenderBaseActivity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, CalendarActivityCalender.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initView() {
        setStatusBarDarkMode();
    }

    @Override
    protected void initData() {

    }
}
