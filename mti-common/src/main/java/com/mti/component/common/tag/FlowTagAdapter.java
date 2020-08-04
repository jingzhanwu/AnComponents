package com.mti.component.common.tag;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mti.component.common.R;
import com.mti.component.common.entry.TagEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/27
 * @change
 * @describe 流式标签
 **/
public class FlowTagAdapter extends RecyclerView.Adapter<FlowTagAdapter.AudioTagViewHolder> {


    private Context mContext;

    /**
     * 标签
     */
    private List<TagEntry> mTags = new ArrayList<>();
    /**
     * 原始数据
     */
    private List<TagEntry> mOriginalTags;

    private LayoutInflater mInflater;


    /**
     * 删除图片
     */
    private @DrawableRes
    int deleteResId = R.drawable.ic_audio_delete;
    /**
     * 文本颜色
     */
    private int fontColor;
    /**
     * 文本大小
     */
    private float fontSize=14f;
    /**
     * 是否显示删除
     */
    private boolean showingDelete=true;
    //监听事件
    private OnTagClickListener mOnItemClickListener;


    /**
     * 背景选择器
     */
    private int backgroundSelectorRes=R.drawable.label_tag_selector;

    public FlowTagAdapter(Context context, List<TagEntry> tags) {
        this.mContext = context;
        this.mTags = tags;
        if (mTags == null) {
            mTags = new ArrayList<>();
        }
        mOriginalTags=new ArrayList<>(Arrays.asList(new TagEntry[mTags.size()]));
        Collections.copy(mOriginalTags,mTags);
        this.mInflater = LayoutInflater.from(mContext);
        init();
    }

    private void init() {
        fontColor= mContext.getResources().getColor(R.color.gray_font_dark);
    }

    public void setOnItemClickListener(OnTagClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setDeleteResId(int deleteResId) {
        this.deleteResId = deleteResId;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isShowingDelete() {
        return showingDelete;
    }

    public void setShowingDelete(boolean showingDelete) {
        this.showingDelete = showingDelete;
    }

    /**
     * 重置数据
     *
     * @param data
     */
    public void setNewData(List<TagEntry> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (mTags == null) {
            mTags = new ArrayList<>();
        }

        mTags = data;
        notifyDataSetChanged();
    }


    public void addTag(TagEntry entry) {
        mTags.add(entry);
        notifyItemInserted(getItemCount() - 1);
    }

    public void deleteTag(int position) {
        mTags.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 获取指定实体
     * @param position
     * @return
     */
    public TagEntry getItemEntry(int position){
        return mTags.get(position);
    }


    /**
     * 单选--选中某个
     * @param position
     */
    public void selectBySingleChoice(int position){

        for (int i = 0; i < mTags.size(); i++) {
            mTags.get(i).setChecked(position==i);
        }

        notifyDataSetChanged();
    }

    /**
     * 多选--选中某个
     * @param position
     */
    public void selectByMultipleChoice(int position){
        TagEntry tagEntry = mTags.get(position);
        tagEntry.setChecked(!tagEntry.isChecked());
        notifyItemChanged(position);
    }




    /**
     * 获取选中数据
     * @return
     */
    public List<TagEntry> getSelections(){
        List<TagEntry> list=new ArrayList<>();
        for (int i = 0; i < mTags.size(); i++) {
            if (mTags.get(i).isChecked()){
                list.add(mTags.get(i));
            }
        }
        return list;
    }


    public int getBackgroundSelectorRes() {
        return backgroundSelectorRes;
    }

    public void setBackgroundSelectorRes(int backgroundSelectorRes) {
        this.backgroundSelectorRes = backgroundSelectorRes;
    }

    @NonNull
    @Override
    public AudioTagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_flow_tag, null);
        return new AudioTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioTagViewHolder audioTagViewHolder, int i) {
//        audioTagViewHolder.tvTagName.setTextSize(fontSize);
//        audioTagViewHolder.tvTagName.setTextColor(fontColor);
//        audioTagViewHolder.tvTagName.setBackgroundResource(backgroundResId);
//        audioTagViewHolder.ivDelete.setImageResource(deleteResId);

//        int backgroundRes=mTags.get(i).isChecked()?R.drawable.shape_tag_selected_bg:R.drawable.shape_tag_bg;
        audioTagViewHolder.tvTagName.setSelected(mTags.get(i).isChecked());
        audioTagViewHolder.tvTagName.setBackgroundResource(backgroundSelectorRes);
        audioTagViewHolder.tvTagName.setText(mTags.get(i).getTagName());
        audioTagViewHolder.ivDelete.setVisibility(showingDelete?View.VISIBLE:View.INVISIBLE);
        audioTagViewHolder.ivDelete.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemDelete(audioTagViewHolder.getLayoutPosition(),mTags.get(audioTagViewHolder.getLayoutPosition()));
            }
        });

        audioTagViewHolder.tvTagName.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(audioTagViewHolder.getLayoutPosition(),mTags.get(audioTagViewHolder.getLayoutPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    class AudioTagViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTagName;
        private ImageView ivDelete;

        public AudioTagViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTagName = itemView.findViewById(R.id.tvTagName);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

    }


}
