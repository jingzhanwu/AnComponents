package com.mti.component.common.image;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @anthor created by jzw
 * @date 2020/6/15
 * @change
 * @describe 网格布局的分割线
 **/
class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {
    //间距,单位px
    private int space = 5;
    //列数
    private int spanCount = 0;

    public GridLayoutItemDecoration(int space, int spanCount) {
        this.space = space;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;
        //第一个的话左边设置为0
        if (parent.getChildLayoutPosition(view) % spanCount == 0) {
            outRect.left = 0;
        }
    }
}
