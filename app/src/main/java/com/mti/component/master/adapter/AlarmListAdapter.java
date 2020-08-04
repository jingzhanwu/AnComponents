package com.mti.component.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mti.component.master.R;
import com.mti.component.master.model.AlarmEntry;
import com.mti.component.master.view.template.AlarmFeedbackTemplateActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2020/6/18
 * @change
 * @describe describe
 **/
public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmListHolder> {

    private List<AlarmEntry> mData = new ArrayList<>();
    private Context mContext;

    public AlarmListAdapter(Context context, List<AlarmEntry> data) {
        this.mContext = context;
        this.mData.addAll(data == null ? new ArrayList<>() : data);
    }

    @NonNull
    @Override
    public AlarmListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_alarm_list, viewGroup, false);
        return new AlarmListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmListHolder holder, int position) {
        AlarmEntry data = mData.get(position);
        holder.tvContent.setText(data.getAlarmContent());
        holder.tvTime.setText(data.getAlarmTime());
        holder.tvLevel.setText(String.valueOf(data.getAlarmLevel()));
        holder.tvType.setText(data.getAlarmType());
        holder.tvFeedback.setText(data.getFeedback());

        switch (data.getAlarmLevel()) {
            case 1:
                holder.tvLevel.setBackgroundResource(R.drawable.bg_level1_red);
                break;
            case 2:
                holder.tvLevel.setBackgroundResource(R.drawable.bg_level2_orange);
                break;
            case 3:
                holder.tvLevel.setBackgroundResource(R.drawable.bg_level3_yellow);
                break;
            case 4:
                holder.tvLevel.setBackgroundResource(R.drawable.bg_level4_blue);
                break;

        }

        switch (data.getFeedback()) {
            case "待接收":
                holder.tvFeedback.setBackgroundResource(R.drawable.bg_alarm1_not_received_red);
                break;
            case "已接收":
                holder.tvFeedback.setBackgroundResource(R.drawable.bg_alarm2_received_green);
                break;
            case "已出警":
                holder.tvFeedback.setBackgroundResource(R.drawable.bg_alarm3_treat_the_cases_orange);
                break;
            case "已到达":
                holder.tvFeedback.setBackgroundResource(R.drawable.bg_alarm4_arrived_violet);
                break;
            case "已处置":
                holder.tvFeedback.setBackgroundResource(R.drawable.bg_alarm5_disposed_blue);
                break;
            case "已反馈":
                holder.tvFeedback.setBackgroundResource(R.drawable.bg_alarm6_feedback_received_gray);
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AlarmFeedbackTemplateActivity.class);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class AlarmListHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;
        private TextView tvLevel;
        private TextView tvType;
        private TextView tvFeedback;
        private TextView tvTime;

        public AlarmListHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvAlarmContent);
            tvLevel = itemView.findViewById(R.id.tvAlarmLevel);
            tvType = itemView.findViewById(R.id.tvAlarmType);
            tvTime = itemView.findViewById(R.id.tvAlarmTime);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
        }
    }
}
