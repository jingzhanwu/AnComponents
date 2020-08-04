package com.mti.component.common.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mti.component.common.entry.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2020/6/15
 * @change
 * @describe 九宫格图片添加，编辑，显示组件
 **/
public class ImageAddComponent extends LinearLayout {

    private RecyclerView mRecyclerView;
    //item的间隙，默认5dp
    private int mItemSpace = 5;
    ///列，默认3列
    private int mSpanCount = 3;
    private ImageAddAdapter mAdapter;
    //item的宽高，默认100dp
    private int itemW = 100;
    private int itemH = 100;

    //添加按钮与播放按钮
    private Drawable mAddDrawable;
    private Drawable mVideoPlayDrawable;
    //是否开启了编辑模式
    private boolean enableEditMode = false;
    //是否显示播放视频的按钮
    private boolean showPlayVideoButton = false;
    private OnImageComponentItemListener mListener;

    public ImageAddComponent(Context context) {
        this(context, null);
    }

    public ImageAddComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        removeAllViews();
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        GridLayoutManager layoutManager = new GridLayoutManager(context, mSpanCount);
        mRecyclerView.setLayoutManager(layoutManager);
        setSpace(mItemSpace);
        addView(mRecyclerView);
    }


    /**
     * 设置item的点击事件
     *
     * @param listener
     */
    public void setOnImageComponentItemListener(OnImageComponentItemListener listener) {
        mListener = listener;
        if (mAdapter != null) {
            mAdapter.setOnImageComponentItemListener(listener);
        }
    }

    /**
     * 设置添加按钮
     *
     * @param drawable
     */
    public ImageAddComponent setAddDrawable(Drawable drawable) {
        this.mAddDrawable = drawable;
        return this;
    }

    /**
     * 设置视频播放的按钮
     *
     * @param drawable
     */
    public ImageAddComponent setVideoPlayDrawable(Drawable drawable) {
        this.mVideoPlayDrawable = drawable;
        return this;
    }

    /**
     * 是否显示播放视频的播放按钮
     * 只有在显示的是视频缩略图的时候使用
     *
     * @param playVideo
     */
    public ImageAddComponent showPlayVideoButton(boolean playVideo) {
        this.showPlayVideoButton = playVideo;
        return this;
    }

    /**
     * 是否开启编辑模式
     * 开启后，最后一个item为添加按钮，显示删除按钮
     *
     * @param edit
     */
    public ImageAddComponent enableEditMode(boolean edit) {
        this.enableEditMode = edit;
        return this;
    }

    /**
     * item的水平和垂直间隙，默认5dp
     * 单位dp
     *
     * @param space
     */
    public ImageAddComponent setSpace(int space) {
        this.mItemSpace = space;
        mRecyclerView.addItemDecoration(new GridLayoutItemDecoration(dip2px(getContext(), mItemSpace), mSpanCount));
        return this;
    }

    /**
     * 设置item的显示宽高，默认为100dp
     * 单位dp
     *
     * @param w
     * @param h
     */
    public ImageAddComponent setItemWH(int w, int h) {
        this.itemW = w;
        this.itemH = h;
        //如果已经初始化过适配器，则设置宽高之后刷新
        if (mAdapter != null) {
            mAdapter.setItemWH(w, h);
            mAdapter.notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 设置新的数据
     *
     * @param data
     */
    public ImageAddComponent setNewData(List<ImageData> data) {
        mAdapter = new ImageAddAdapter(getContext(), data);
        mAdapter.setItemWH(itemW, itemH);
        mAdapter.setAddDrawable(mAddDrawable);
        mAdapter.setVideoPlayDrawable(mVideoPlayDrawable);
        mAdapter.setOnImageComponentItemListener(mListener);
        mAdapter.enableEditMode(enableEditMode);
        mAdapter.showPlayVideoButton(showPlayVideoButton);
        mRecyclerView.setAdapter(mAdapter);
        return this;
    }

    /**
     * 批量添加数据
     *
     * @param data
     */
    public ImageAddComponent addData(List<ImageData> data) {
        if (mAdapter == null) {
            setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        return this;
    }

    /**
     * 添加一个数据
     *
     * @param data
     */
    public ImageAddComponent addData(ImageData data) {
        if (mAdapter == null) {
            List<ImageData> list = new ArrayList<>();
            list.add(data);
            setNewData(list);
        } else {
            mAdapter.addItem(data);
        }
        return this;
    }

    /**
     * 删除一个item
     *
     * @param position
     */
    public void deleteItem(int position) {
        if (mAdapter != null) {
            mAdapter.removeItem(position);
        }
    }

    /**
     * 返回当前的数据
     *
     * @return
     */
    public List<ImageData> getImageData() {
        if (mAdapter == null) return null;

        return mAdapter.getData();
    }

    /**
     * 返回一个item 数据
     *
     * @param position
     * @return
     */
    public ImageData getData(int position) {
        if (getImageData() == null) return null;
        if (getImageData().size() == 0) {
            return null;
        }
        return getImageData().get(position);
    }

    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface OnImageComponentItemListener {
        default void onPlayViewClick(int position) {
        }


        default void onItemDeleteClick(int position) {
        }


        void onItemClick(int position);

        default void onAddViewClick() {
        }
    }
}
