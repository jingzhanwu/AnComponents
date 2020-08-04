package com.mti.component.master.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mti.calendar.group.GroupRecyclerAdapter;
import com.mti.component.common.TimeLineComponent;
import com.mti.component.master.R;
import com.mti.component.master.model.ScheduleEntry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * 勤务排班
 *
 * @author 薛兵
 */

public class ScheduleAdapter extends GroupRecyclerAdapter<String, ScheduleEntry> {

    private Context mContext;

    public ScheduleAdapter(Context context, List<String> titles, LinkedHashMap data) {
        super(context);
        this.mContext = context;
        resetGroups(data, titles);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ScheduleViewHolder(mInflater.inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, ScheduleEntry item, int position) {
        ScheduleViewHolder viewHolder = (ScheduleViewHolder) holder;

        viewHolder.timeline.initLine(TimeLineComponent.getTimeLineComponentType(position, mItems.size()));
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvTimePeriod.setText(item.getPeriodTime());
//        viewHolder.tvPositionType.setText(item.getPositionType());
        viewHolder.tvServiceType.setText(item.getServiceType());
        viewHolder.tvAddress.setText(item.getAddress());
        viewHolder.tvLeader.setText(item.getLeader());
        String member = Stream.of(item.getMembers())
                .reduce("", (s1, s2) -> TextUtils.isEmpty(s1) ? s2 : s1 + "," + s2);
        viewHolder.tvMember.setText(member);
        viewHolder.tvRequirement.setText(item.getRequirement());
    }

    private static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TimeLineComponent timeline;
        private TextView tvTimePeriod,//时间段
                tvPositionType,//岗位类
                tvServiceType,//勤务类型
                tvName,//名称
                tvAddress,//地址
                tvLeader,//领导
                tvMember,//成员
                tvRequirement;//岗位要求

        private ScheduleViewHolder(View itemView) {
            super(itemView);
            timeline = itemView.findViewById(R.id.timeline);
            tvTimePeriod = itemView.findViewById(R.id.tvTimePeriod);
            tvPositionType = itemView.findViewById(R.id.tvPositionType);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvLeader = itemView.findViewById(R.id.tvLeader);
            tvMember = itemView.findViewById(R.id.tvMember);
            tvRequirement = itemView.findViewById(R.id.tvRequirement);

        }
    }

}
