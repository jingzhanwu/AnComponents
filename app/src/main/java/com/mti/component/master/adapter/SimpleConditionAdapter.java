package com.mti.component.master.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mti.component.master.R;
import com.mti.component.master.model.ConditionEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by xuebing
 * @date 2020/6/18
 * @change
 * @describe describe
 **/
public class SimpleConditionAdapter extends RecyclerView.Adapter<SimpleConditionAdapter.SimpleConditionHolder> {

    private List<ConditionEntry> mData = new ArrayList<>();
    private Context mContext;
    private OnConditionItemClickListener onConditionItemClickListener;

    private int lastSelection;

    public interface OnConditionItemClickListener {
        void onConditionItemClick(int pos, ConditionEntry entry);
    }

    public void addOnConditionItemClickListener(OnConditionItemClickListener listener) {
        this.onConditionItemClickListener = listener;
    }

    public SimpleConditionAdapter(Context context, List<ConditionEntry> data) {
        this.mContext = context;
        this.mData.addAll(data == null ? new ArrayList<>() : data);
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getChecked()) {
                lastSelection = i;
                break;
            }
        }
    }


    /**
     * 单选
     *
     * @param position
     */
    public void single(int position) {
        mData.get(lastSelection).setChecked(false);
        notifyItemChanged(lastSelection);

        mData.get(position).setChecked(true);
        notifyItemChanged(position);

        lastSelection = position;
    }

    @NonNull
    @Override
    public SimpleConditionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_condition, viewGroup, false);
        return new SimpleConditionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleConditionHolder holder, int position) {

        ConditionEntry conditionEntry = mData.get(position);
        holder.tvTitle.setText(conditionEntry.getTitle());
        int textColor = lastSelection == position ?
                mContext.getResources().getColor(R.color.colorPrimary) :
                mContext.getResources().getColor(R.color.black_subtitle);
        holder.tvTitle.setTextColor(textColor);
        holder.ivMark.setVisibility(conditionEntry.getChecked() ? View.VISIBLE : View.INVISIBLE);
        holder.itemView.setOnClickListener(v -> {
            if (onConditionItemClickListener != null) {
                onConditionItemClickListener.onConditionItemClick(holder.getLayoutPosition(), conditionEntry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class SimpleConditionHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivMark;

        public SimpleConditionHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivMark = itemView.findViewById(R.id.ivMark);
        }
    }
}
