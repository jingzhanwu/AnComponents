package com.mti.component.master.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mti.calendar.group.GroupRecyclerAdapter;
import com.mti.component.master.R;
import com.mti.component.master.model.PunchInfoEntry;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 打卡信息列表
 *
 * @author 薛兵
 */

public class PunchInfoAdapter extends GroupRecyclerAdapter<String, PunchInfoEntry> {

    private Context mContext;

    public PunchInfoAdapter(Context context, List<String> titles, LinkedHashMap data) {
        super(context);
        this.mContext = context;
        resetGroups(data, titles);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ScheduleViewHolder(mInflater.inflate(R.layout.item_punch_info, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, PunchInfoEntry item, int position) {
        ScheduleViewHolder viewHolder = (ScheduleViewHolder) holder;
        viewHolder.tvContent.setText(String.format("今日打卡%d次，共计%d小时", item.getPunchCount(), item.getPunchDuration()));
        viewHolder.tvPunchOrder.setText(item.getPunchOrder());
        viewHolder.tvPunchTime.setText("打卡时间 "+item.getPunchTime());
        viewHolder.tvPunchDes.setText(item.getPunchDes());
        viewHolder.tvAddress.setText(item.getAddress());
        viewHolder.tvPunchStatus.setText(item.getPunchStatus());
    }

    private static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent,//标题内容
                tvPunchOrder,//班次 上班 下班
                tvPunchTime,//打卡时间
                tvPunchDes,//打卡描述
                tvAddress,//地址
                tvPunchStatus;//打卡状态

        private ScheduleViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvPunchOrder = itemView.findViewById(R.id.tvPunchOrder);
            tvPunchTime = itemView.findViewById(R.id.tvPunchTime);
            tvPunchDes = itemView.findViewById(R.id.tvPunchDes);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPunchStatus = itemView.findViewById(R.id.tvPunchStatus);

        }
    }

}
