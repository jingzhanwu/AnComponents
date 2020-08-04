package com.mti.component.common.dial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 等份容器基类，子控件之间可以设置间距，以及设置子控件的高度宽度比
 */

public class BaseUniformViewGroup extends ViewGroup {

    private int rankNum = 5;//等份数
    private int horizontalDpVal = 10;//水平控件间隔
    private int verticalDpVal = 12;//垂直控件间隔
    private float ratio = 5 / 12f;//高度和宽度的比例
    private boolean isShowLine = false;//是否显示线
    private String lineColor = "#e3e3e3";//线的颜色
    private int lineWidth = 2;//线的宽度
    private int skewingY = 10;//轴偏移量
    private int itemContentHeight;//单元格高度
    private boolean isShowEndLine = false;

    public void setShowEndLine(boolean showEndLine) {
        isShowEndLine = showEndLine;
//        requestLayout();
    }

    public BaseUniformViewGroup(Context context) {
        this(context, null);
    }

    public BaseUniformViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        horizontalDpVal = Math.round(dip2px(getContext(), horizontalDpVal));
        verticalDpVal = Math.round(dip2px(getContext(), verticalDpVal));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        int childMeasureWidth = 0, childMeasureHeight = 0;
        childMeasureWidth = (getMeasuredWidth() - horizontalDpVal * (rankNum - 1)) / rankNum;
//        childMeasureHeight = Math.round(childMeasureWidth * ratio);
        childMeasureHeight = itemContentHeight > 0 ? itemContentHeight : Math.round(childMeasureWidth * ratio);

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(childMeasureWidth, childMeasureHeight);
        }

        int rows = 0;//行数
        int measureHeight = 0;
        rows = childCount / rankNum + ((childCount % rankNum) > 0 ? 1 : 0);
        measureHeight = rows * (childMeasureHeight + verticalDpVal) - 1 * verticalDpVal;
        setMeasuredDimension(getMeasuredWidth(), measureHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childWidth = 0, childHeight = 0;
        childWidth = (getMeasuredWidth() - horizontalDpVal * (rankNum - 1)) / rankNum;
//        childHeight = (int) (childWidth * ratio);
        childHeight = itemContentHeight > 0 ? itemContentHeight : Math.round(childWidth * ratio);
        int column = 0, row = 0;
        int left = 0, top = 0, right = 0, bottom = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            column = i % rankNum;//第几列
            row = i / rankNum;//第几行
            left = column * (childWidth + horizontalDpVal);
            top = row * (childHeight + verticalDpVal);

            right = childWidth + left;
            bottom = childHeight + top;
            child.layout(left, top, right, bottom);
        }
    }


    public void setRatio(float ratio) {
        this.ratio = ratio;
//        requestLayout();
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
//        requestLayout();
    }

    public void setHorizontalDpVal(int horizontalDpVal) {
        this.horizontalDpVal = Math.round(dip2px(getContext(), horizontalDpVal));
//        requestLayout();
    }

    public void setVerticalDpVal(int verticalDpVal) {
        this.verticalDpVal = Math.round(dip2px(getContext(), verticalDpVal));
//        requestLayout();
    }

    public void setItemContentHeight(int itemContentHeight) {
        this.itemContentHeight = Math.round(dip2px(getContext(), itemContentHeight));
//        requestLayout();
    }

    /**
     * 单选操作
     */
    public interface OnSingleSelectListener {
        void onSingleSelect(int position);
    }


    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
        invalidate();
    }

    public void setSkewingY(int skewingY) {
        this.skewingY = skewingY;
        invalidate();
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
        invalidate();
    }

    public float dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isShowLine) {
            int itemWidth, itemHeight, currentX, currentY, currentEndY;
            //每一个Item的宽高
            itemWidth = getWidth() / rankNum;
            itemHeight = getHeight() / (getChildCount() / rankNum);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            paint.setColor(Color.parseColor(lineColor));
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(lineWidth);
            for (int i = 0; i < getChildCount(); i++) {
                currentX = (i % rankNum) * itemWidth + itemWidth;
                currentY = (i / rankNum) * itemHeight;
                currentEndY = (i / rankNum) * itemHeight + itemHeight;
                canvas.drawLine(currentX, currentY + skewingY, currentX, currentEndY - skewingY, paint);
            }
            if (isShowEndLine) {
                //画最后一行横线
                canvas.drawLine(0, (getChildCount() / rankNum) * itemHeight + skewingY, rankNum * itemWidth, (getChildCount() / rankNum) * itemHeight + skewingY, paint);
            }
        }
    }

}
