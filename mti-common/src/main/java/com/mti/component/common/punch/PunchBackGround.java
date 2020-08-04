package com.mti.component.common.punch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 自定义打卡背景
 **/
public class PunchBackGround extends Drawable {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private int color ;

    public PunchBackGround() {
        this.color = Color.parseColor("#FA3F7F");
        mPaint.setColor(color);
    }

    public PunchBackGround(int color) {
        this.color = color;
        mPaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        canvas.drawCircle(rect.exactCenterX(),
                rect.exactCenterY(),
                Math.min(rect.exactCenterX(), rect.exactCenterY()),
                mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
