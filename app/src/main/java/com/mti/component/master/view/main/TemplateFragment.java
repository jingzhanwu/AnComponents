package com.mti.component.master.view.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mti.component.master.R;
import com.mti.component.master.adapter.ComponentAdapter;
import com.mti.component.master.base.BaseFragment;
import com.mti.component.master.callback.OnAdapterItemClickListener;
import com.mti.component.master.model.ComponentEntry;
import com.mti.component.master.view.example.InputComponentActivity;
import com.mti.component.master.view.template.AlarmFeedbackTemplateActivity;
import com.mti.component.master.view.template.AlarmListTemplateActivity;
import com.mti.component.master.view.template.AlarmStatisticsTemplateActivity;
import com.mti.component.master.view.template.ScheduleStatisticsActivity;
import com.mti.component.master.view.template.ServiceSchedulingActivity;
import com.mti.component.master.view.template.SimpleContactTemplateActivity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @anthor created by jzw
 * @date 2020/5/21
 * @change
 * @describe 组件模板
 **/
@SuppressLint("NewApi")
public class TemplateFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ComponentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_template;
    }

    @Override
    public void initViews(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        initAdapter();
    }

    @Override
    public void loadData() {

    }

    private void initAdapter() {
        mAdapter = new ComponentAdapter(getActivity(), createData(),R.layout.item_component);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((OnAdapterItemClickListener<ComponentEntry>) (position, item) -> {
            Intent intent = new Intent();
            intent.setClassName(getActivity(), item.getTag());
            startActivity(intent);
        });
    }


    /**
     * 构造组件model
     *
     * @return List<ComponentModel>
     */
    private List<ComponentEntry> createData() {
        ComponentEntry input = new ComponentEntry("勤务排班", R.drawable.picture_service_scheduling, "这里给出通常情况的勤务排班模板", ServiceSchedulingActivity.class.getName());
        ComponentEntry feedback = new ComponentEntry("警情反馈", R.drawable.picture_alarm_feedback, "警情处置流程的反馈模板", AlarmFeedbackTemplateActivity.class.getName());
        ComponentEntry scheduleStatistics = new ComponentEntry("勤务统计", R.drawable.picture_schedule_statistics, "勤务统计", ScheduleStatisticsActivity.class.getName());
        ComponentEntry alarmStatistics = new ComponentEntry("警情统计", R.drawable.picture_schedule_statistics, "几种常见的警情统计场景", AlarmStatisticsTemplateActivity.class.getName());
        ComponentEntry alarmList = new ComponentEntry("警情列表", R.drawable.picture_schedule_statistics, "基础的警情列表", AlarmListTemplateActivity.class.getName());
        ComponentEntry simpleContactTemplate = new ComponentEntry("简易通讯录模板", R.drawable.picture_simple_contact, "最简易的通讯录模板", SimpleContactTemplateActivity.class.getName());

        return Stream.of(input, feedback, scheduleStatistics, alarmStatistics, alarmList, simpleContactTemplate).collect(Collectors.toList());
    }

}
