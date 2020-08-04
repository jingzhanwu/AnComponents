package com.mti.component.master.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mti.component.master.R;
import com.mti.component.master.callback.OnAdapterItemClickListener;
import com.mti.component.master.model.ContactEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by jzw
 * @date 2020/5/22
 * @change
 * @describe 简易通讯录设计
 **/
public class SimpleContactAdapter extends RecyclerView.Adapter<SimpleContactAdapter.SimpleContactViewHolder> {

    private Context mContext;
    private List<ContactEntry> mData;
    /**
     * 是否正在检索
     */
    private boolean isSearching;
    private String keywords;
    private OnAdapterItemClickListener mOnItemClick;

    public SimpleContactAdapter(Context context, List<ContactEntry> data) {
        mContext = context;
        mData = data == null ? new ArrayList<>() : data;
    }

    public void setOnItemClickListener(OnAdapterItemClickListener listener) {
        mOnItemClick = listener;
    }


    /**
     *
     * @param isSearching
     * @param keywords
     */
    public void setSearching(boolean isSearching,String keywords){
        this.isSearching=isSearching;
        this.keywords=keywords;
    }

    public void setNewData(List<ContactEntry> data){
        mData=data;
        notifyDataSetChanged();
    }

    public boolean isSearching() {
        return isSearching;
    }
    @NonNull
    @Override
    public SimpleContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple_contact, viewGroup, false);
        return new SimpleContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleContactViewHolder holder, int i) {
        ContactEntry entry = mData.get(i);

        if (isSearching){
            holder.tvName.setText(showNameText(entry.getName()));
        }else {
            holder.tvName.setText(entry.getName());
            holder.tvName.setTextColor(holder.tvName.getContext().getResources().getColor(R.color.gray_font_dark));
        }
        String name=formatName(entry.getName());
        holder.tvAvatar.setText(formatName(name));
        holder.tvCode.setText(entry.getJobnumber());
        holder.tvNumber.setText(entry.getMobilenum());

        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClick != null) {
                mOnItemClick.onItemClick(i, entry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    /**
     * 处理检索结果显示
     * @param name
     * @return
     */
    private SpannableString showNameText(String name) {
        if (TextUtils.isEmpty(name)){
            return new SpannableString("");
        }

        int index=name.indexOf(keywords);

        SpannableString spannableString = new SpannableString(name);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0081F9"));
        spannableString.setSpan(colorSpan, index, index+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private String formatName(String name){
        if (TextUtils.isEmpty(name)){
            return "姓名";
        }
        if (name.length()<=2){
            return name;
        }
        return name.substring(name.length()-2);
    }


    public static class SimpleContactViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAvatar;
        private TextView tvCode;
        private TextView tvNumber;

        public SimpleContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvNumber = itemView.findViewById(R.id.tvNumber);
        }
    }

}
