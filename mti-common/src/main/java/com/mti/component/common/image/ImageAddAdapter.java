package com.mti.component.common.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.HardwarePropertiesManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mti.component.common.R;
import com.mti.component.common.entry.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2020/6/15
 * @change
 * @describe 图片添加显示适配器
 **/
class ImageAddAdapter extends RecyclerView.Adapter<ImageAddAdapter.ImageHolder> {

    private List<ImageData> mData = new ArrayList<>();
    private Context mContext;
    private RequestOptions mRequestOptions;
    private Drawable mDelDrawable;
    private Drawable mAddDrawable;
    private Drawable mVideoPlayDrawable;

    private ImageAddComponent.OnImageComponentItemListener mListener;
    //item的宽高，默认100dp
    private int itemW = 100;
    private int itemH = 100;

    //是否开启了编辑模式
    private boolean enableEditMode = false;
    //是否显示播放视频的按钮
    private boolean showPlayVideoButton = false;

    public ImageAddAdapter(Context context, List<ImageData> data) {
        this.mContext = context;
        this.mData.addAll(data == null ? new ArrayList<>() : data);
        this.mRequestOptions = new RequestOptions();
        this.mDelDrawable = context.getResources().getDrawable(R.mipmap.ic_del);
        this.mAddDrawable = context.getResources().getDrawable(R.mipmap.ic_add_image);
        this.itemW = dip2px(mContext, 100);
        this.itemH = dip2px(mContext, 100);
    }

    /**
     * 设置item的监听器
     *
     * @param listener
     */
    void setOnImageComponentItemListener(ImageAddComponent.OnImageComponentItemListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置添加按钮
     *
     * @param drawable
     */
    void setAddDrawable(Drawable drawable) {
        if (drawable != null) {
            this.mAddDrawable = drawable;
        }
    }

    /**
     * 设置视频播放的按钮
     *
     * @param drawable
     */
    void setVideoPlayDrawable(Drawable drawable) {
        if (drawable != null) {
            this.mVideoPlayDrawable = drawable;
        }
    }

    /**
     * 返回当前数据列表
     *
     * @return
     */
    List<ImageData> getData() {
        return mData;
    }

    /**
     * 是否显示播放视频的播放按钮
     * 只有在显示的是视频缩略图的时候使用
     *
     * @param playVideo
     */
    void showPlayVideoButton(boolean playVideo) {
        this.showPlayVideoButton = playVideo;
    }

    /**
     * 是否开启编辑模式
     * 开启后，最后一个item为添加按钮，显示删除按钮
     *
     * @param edit
     */
    void enableEditMode(boolean edit) {
        this.enableEditMode = edit;
    }

    void setItemWH(int w, int h) {
        this.itemW = dip2px(mContext, w);
        this.itemH = dip2px(mContext, h);
    }

    void addData(List<ImageData> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    void addItem(ImageData data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    void removeItem(int position) {
        if (mData != null && mData.size() > 0) {
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null, false);
        return new ImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.ivImage.getLayoutParams();
        lp.width = itemW;
        lp.height = itemH;
        holder.ivImage.setLayoutParams(lp);

        if (mVideoPlayDrawable != null && showPlayVideoButton) {
            holder.ivVideoPlay.setImageDrawable(mVideoPlayDrawable);
        }
        holder.ivVideoPlay.setVisibility(showPlayVideoButton ? View.VISIBLE : View.INVISIBLE);

        if (mDelDrawable != null) {
            holder.ivDel.setImageDrawable(mDelDrawable);
        }
        holder.ivDel.setVisibility((enableEditMode && position != getItemCount() - 1) ? View.VISIBLE : View.GONE);
        if (enableEditMode && position == getItemCount() - 1) {
            holder.ivImage.setImageDrawable(mAddDrawable);
            holder.ivImage.setScaleType(ImageView.ScaleType.CENTER);
            holder.ivVideoPlay.setVisibility(View.INVISIBLE);
        } else {
            ImageData item = mData.get(position);
            Glide.with(mContext)
                    .applyDefaultRequestOptions(mRequestOptions)
                    .load(item.getUrl())
                    .into(holder.ivImage);
        }
        //点击事件
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                if (enableEditMode && position == getItemCount() - 1) {
                    mListener.onAddViewClick();
                } else {
                    mListener.onItemClick(position);
                }
            }
        });

        //删除
        holder.ivDel.setOnClickListener(v -> {
            if (mListener != null) {
                if (enableEditMode && position == getItemCount() - 1) {
                    return;
                }
                mListener.onItemDeleteClick(position);
            }
        });

        //播放按钮
        holder.ivVideoPlay.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onPlayViewClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        //如果是编辑模式下，则在末尾额外增加一项添加的按钮
        int count = mData.size();
        if (enableEditMode) {
            count++;
        }
        return count;
    }


    static class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private ImageView ivDel;
        private ImageView ivVideoPlay;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivVideoPlay = itemView.findViewById(R.id.ivVideoPlay);
        }
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
