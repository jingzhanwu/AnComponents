package com.mti.component.media.audio;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mti.component.media.R;
import com.mti.component.media.entry.AudioLabelEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/27
 * @change
 * @describe 录音标签
 **/
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {


    private Context mContext;

    /**
     * 标签
     */
    private List<AudioLabelEntry> mTags = new ArrayList<>();

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
    private float fontSize = 14f;
    /**
     * 是否显示删除
     */
    private boolean showingDelete = true;
    //监听事件
    private OnAudioClickListener onAudioClickListener;


    /**
     * 背景选择器
     */
    private int backgroundSelectorRes = R.drawable.audio_label_selector;

    public AudioAdapter(Context context, List<AudioLabelEntry> tags) {
        this.mContext = context;
        this.mTags = tags;
        if (mTags == null) {
            mTags = new ArrayList<>();
        }
        this.mInflater = LayoutInflater.from(mContext);
        init();
    }

    private void init() {
        fontColor = mContext.getResources().getColor(R.color.gray_dark);
    }

    public void setOnAudioClickListener(OnAudioClickListener onAudioClickListener) {
        this.onAudioClickListener = onAudioClickListener;
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
    public void setNewData(List<AudioLabelEntry> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (mTags == null) {
            mTags = new ArrayList<>();
        }

        mTags = data;
        notifyDataSetChanged();
    }


    public void addTag(AudioLabelEntry entry) {
        mTags.add(entry);
        notifyItemInserted(getItemCount() - 1);
    }

    public void deleteTag(int position) {
        mTags.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 获取指定实体
     *
     * @param position
     * @return
     */
    public AudioLabelEntry getItemEntry(int position) {
        return mTags.get(position);
    }


    /**
     * 单选--选中某个
     *
     * @param position
     */
    public void selectBySingleChoice(int position) {

        for (int i = 0; i < mTags.size(); i++) {
            mTags.get(i).setChecked(position == i);
        }

        notifyDataSetChanged();
    }

    /**
     * 单选--选中某个
     *
     * @param position
     */
    public void selectByMultipleChoice(int position) {
        AudioLabelEntry tagEntry = mTags.get(position);
        tagEntry.setChecked(!tagEntry.isChecked());
        notifyItemChanged(position);
    }


    /**
     * 获取选中数据
     *
     * @return
     */
    public List<AudioLabelEntry> getSelections() {
        List<AudioLabelEntry> list = new ArrayList<>();
        for (int i = 0; i < mTags.size(); i++) {
            if (mTags.get(i).isChecked()) {
                list.add(mTags.get(i));
            }
        }
        return list;
    }

    protected List<AudioLabelEntry> getData() {
        return mTags;
    }

    public int getBackgroundSelectorRes() {
        return backgroundSelectorRes;
    }

    public void setBackgroundSelectorRes(int backgroundSelectorRes) {
        this.backgroundSelectorRes = backgroundSelectorRes;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_audio_tag, null);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder audioTagViewHolder, int i) {
//        audioTagViewHolder.tvTagName.setTextSize(fontSize);
//        audioTagViewHolder.tvTagName.setTextColor(fontColor);
//        audioTagViewHolder.tvTagName.setBackgroundResource(backgroundResId);
//        audioTagViewHolder.ivDelete.setImageResource(deleteResId);

//        int backgroundRes=mTags.get(i).isChecked()?R.drawable.shape_tag_selected_bg:R.drawable.shape_tag_bg;
        audioTagViewHolder.tvTagName.setSelected(mTags.get(i).isChecked());
        audioTagViewHolder.tvTagName.setBackgroundResource(backgroundSelectorRes);
        audioTagViewHolder.tvTagName.setText(mTags.get(i).getTagName());
        audioTagViewHolder.ivDelete.setVisibility(showingDelete ? View.VISIBLE : View.INVISIBLE);
        audioTagViewHolder.ivDelete.setImageDrawable(audioTagViewHolder.ivDelete.getContext().getDrawable(deleteResId));
        audioTagViewHolder.ivDelete.setOnClickListener(v -> {
            if (onAudioClickListener != null) {
                onAudioClickListener.onItemDelete(audioTagViewHolder.getLayoutPosition());
            }
        });

        audioTagViewHolder.tvTagName.setOnClickListener(v -> {
            if (onAudioClickListener != null) {
                onAudioClickListener.onItemClick(audioTagViewHolder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    class AudioViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTagName;
        private ImageView ivDelete;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTagName = itemView.findViewById(R.id.tvTagName);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

    }


}
