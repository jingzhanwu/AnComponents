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
import com.mti.component.master.callback.OnAdapterItemClickListener;
import com.mti.component.master.model.ComponentEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by jzw
 * @date 2020/5/22
 * @change
 * @describe 组件列表适配器
 **/
public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder> {

    private Context mContext;
    private List<ComponentEntry> mData;
    private int layoutId = 0;

    private OnAdapterItemClickListener mOnItemClick;

    public ComponentAdapter(Context context, List<ComponentEntry> data, int layoutId) {
        mContext = context;
        this.layoutId = layoutId;
        mData = data == null ? new ArrayList<>() : data;
    }

    public void setOnItemClickListener(OnAdapterItemClickListener listener) {
        mOnItemClick = listener;
    }

    @NonNull
    @Override
    public ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        return new ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentViewHolder holder, int i) {
        ComponentEntry model = mData.get(i);
        holder.tvName.setText(model.getName());
        holder.tvDes.setText(model.getDesc());
        holder.ivCover.setImageResource(model.getImageRes());
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClick != null) {
                mOnItemClick.onItemClick(i, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ComponentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCover;
        private TextView tvName;
        private TextView tvDes;

        public ComponentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvName = itemView.findViewById(R.id.tvName);
            tvDes = itemView.findViewById(R.id.tvDesc);
        }
    }

}
