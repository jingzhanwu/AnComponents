package com.mti.component.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mti.component.master.R;
import com.mti.component.master.callback.OnAdapterItemClickListener;
import com.mti.component.master.model.ComponentEntry;
import com.mti.component.master.model.ComponentSection;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2020/6/23
 * @change
 * @describe 组件楼层适配器
 **/
public class ComponentSectionAdapter extends RecyclerView.Adapter<ComponentSectionAdapter.SectionViewHolder> {

    private List<ComponentSection> mData = new ArrayList<>();
    private Context mContext;

    public ComponentSectionAdapter(Context context, List<ComponentSection> data) {
        this.mContext = context;
        this.mData.addAll(data == null ? new ArrayList<>() : data);
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_component_section, viewGroup, false);
        return new SectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int i) {
        ComponentSection section = mData.get(i);
        holder.tvTitle.setText(section.getTitle());

        //组件水平列表
        ComponentAdapter componentAdapter = new ComponentAdapter(mContext, section.getComponent(), R.layout.item_section_component);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        holder.recyclerView.setAdapter(componentAdapter);
        componentAdapter.setOnItemClickListener((OnAdapterItemClickListener<ComponentEntry>) (position, item) -> {
            Intent intent = new Intent();
            intent.setClassName(mContext, item.getTag());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private RecyclerView recyclerView;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            recyclerView = itemView.findViewById(R.id.rcvComponent);
        }
    }
}
