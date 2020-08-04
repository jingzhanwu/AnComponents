package com.mti.component.common.nice9;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mti.component.common.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wxy on 2017/5/31.
 */

public class ImageNice9Adapter extends VirtualLayoutAdapter<ImageNice9Adapter.ImageViewHolder> implements Nice9ItemTouchCallback.ItemTouchAdapter {
    private List<String> pictures = new ArrayList<>();
    private Context context;
    private boolean canDrag;
    private boolean openEditModel;
    private int itemMargin;
    private ImageNice9Component.ItemDelegate mItemDelegate;
    private RequestOptions mRequestOptions;
    private Drawable mDelDrawable;

    public ImageNice9Adapter(@NonNull VirtualLayoutManager layoutManager, Context context, boolean canDrag, int itemMargin) {
        super(layoutManager);
        this.context = context;
        this.canDrag = canDrag;
        this.itemMargin = itemMargin;
        mRequestOptions = new RequestOptions();
        mDelDrawable = context.getResources().getDrawable(R.mipmap.ic_del);
    }

    /**
     * 绑定显示数据
     *
     * @param pics
     */
    protected void bindData(List<String> pics) {
        if (pics == null) {
            pics = new ArrayList<>();
        }
        if (this.pictures != null) {
            this.pictures.clear();
        } else {
            this.pictures = new ArrayList<>();
        }
        this.pictures.addAll(pics);
        notifyDataSetChanged();
    }

    protected void openEditModel(boolean editModel) {
        this.openEditModel = editModel;
    }

    /**
     * 设置item 代理
     *
     * @param itemDelegate
     */
    protected void setItemDelegate(ImageNice9Component.ItemDelegate itemDelegate) {
        mItemDelegate = itemDelegate;
    }

    /**
     * 移除指定位置的item
     *
     * @param position
     */
    protected void removeItemAt(int position) {
        if (pictures != null && pictures.size() > 0) {
            int size = pictures.size();
            if (position >= 0 && position < size) {
                pictures.remove(position);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 设置占位图片
     *
     * @param drawable
     */
    protected void setPlaceholderDrawable(Drawable drawable) {
        if (drawable != null) {
            mRequestOptions.placeholder(drawable);
        }
    }

    /**
     * 设置删除view的图标
     *
     * @param drawable
     */
    protected void setDelImageDrawable(Drawable drawable) {
        if (drawable != null) {
            mDelDrawable = drawable;
        }
    }

    private int getDisplayWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mulit_image, null));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int width = 0, height = 0;
        //动态计算或者平分显示
        int imageCount = pictures.size();
        int displayW = getDisplayWidth(context);

        if (imageCount == 1) {
            width = displayW;
            height = width;
        } else if (imageCount == 2) {
            width = displayW / 2;
            height = width;
        } else if (imageCount == 3) {
            if (position == 0) {
                width = (int) (displayW * 0.66);
                height = width;
                layoutParams.rightMargin = itemMargin;
                layoutParams.bottomMargin = itemMargin;
            } else {
                if (position == 1 || position == 2) {
                    if (position == 1) {
                        layoutParams.bottomMargin = itemMargin / 2;
                    } else {
                        layoutParams.bottomMargin = itemMargin;
                    }
                }
                width = (int) ((displayW * 0.33));
                height = width;
            }
        } else if (imageCount == 4) {
            if (position == 0) {
                width = displayW;
                height = (int) (width * 0.5);
            } else {
                width = (int) (displayW * 0.33);
                height = width;
            }
        } else if (imageCount == 5) {
            if (position == 0 || position == 1) {
                width = (int) (displayW * 0.5);
                height = width;
            } else {
                width = (int) (displayW * 0.33);
                height = width;
            }
        } else if (imageCount == 6) {
            if (position == 0) {
                width = (int) (displayW * 0.66);
                height = width;
                layoutParams.rightMargin = 10;
                layoutParams.bottomMargin = 10;
            } else {
                if (position == 1 || position == 2) {
                    if (position == 1) {
                        layoutParams.bottomMargin = itemMargin / 2;
                    } else {
                        layoutParams.bottomMargin = itemMargin;
                    }

                }
                width = (int) (displayW * 0.33);
                height = width;
            }
        } else if (imageCount == 7) {
            if (position == 0) {
                width = displayW;
                height = (int) (width * 0.5);
            } else {
                width = (int) (displayW * 0.33);
                height = width;
            }
        } else if (imageCount == 8) {
            if (position == 0 || position == 1) {
                width = (int) (displayW * 0.5);
                height = width;
            } else {
                width = (int) (displayW * 0.33);
                height = width;
            }
        } else {
            width = (int) (displayW * 0.33);
            height = width;
        }

        layoutParams.width = width;
        layoutParams.height = height;
        System.out.println("item宽高：" + layoutParams.width + "--" + layoutParams.height);
        holder.itemView.setLayoutParams(layoutParams);

        //允许编辑则显示删除view
        holder.mImageDelView.setVisibility(openEditModel ? View.VISIBLE : View.INVISIBLE);

        if (mDelDrawable != null) {
            holder.mImageDelView.setImageDrawable(mDelDrawable);
        }
        holder.mImageDelView.setOnClickListener(v -> {
            //点击了删除,编辑模式下，点击最后一项的删除不回调
            if (mItemDelegate != null) {
                mItemDelegate.onItemRemoveClick(position);
            }
        });

        final String imageUrl = pictures.get(position);
        holder.itemView.setOnClickListener(view -> {
            if (mItemDelegate != null) {
                //编辑模式并且是最后一项，才回调添加方法
                mItemDelegate.onItemClick(position);
            }
        });

        Glide.with(context)
                .applyDefaultRequestOptions(mRequestOptions)
                .load(imageUrl)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(pictures, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(pictures, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<String> getPictures() {
        return pictures == null ? new ArrayList<>() : pictures;
    }

    @Override
    public void onSwiped(int position) {
        notifyItemRemoved(position);
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageView mImageDelView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_mulit_image);
            mImageDelView = itemView.findViewById(R.id.ivDel);
        }
    }
}
