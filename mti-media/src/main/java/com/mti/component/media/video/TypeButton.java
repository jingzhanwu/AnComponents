package com.mti.component.media.video;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;


import com.mti.component.media.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * =====================================
 * 作    者: jingzhanwu
 * 版    本：1.0.4
 * 创建日期：2018/1/26
 * 描    述：拍照或录制完成后弹出的确认和返回按钮
 * =====================================
 */
public class TypeButton extends View {
    private int button_type;
    private int button_size;

    private float center_X;
    private float center_Y;
    private float button_radius;

    private Paint mPaint;
    private Path path;
    private float strokeWidth;

    private float index;
    private RectF rectF;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ButtonType.TYPE_CANCEL, ButtonType.TYPE_CONFIRM})
    public @interface ButtonType {
        int TYPE_CANCEL = 0;
        int TYPE_CONFIRM = 1;
    }


    public TypeButton(Context context) {
        super(context);
        int type = ButtonType.TYPE_CANCEL;
        int size = dip2px(context, 80);
        init(type, size);
    }

    public TypeButton(Context context, AttributeSet attributes) {
        super(context, attributes);
        setWillNotDraw(false);
        clearAnimation();
        int type = ButtonType.TYPE_CANCEL;
        int size = dip2px(context, 80);
        if (attributes != null) {
            TypedArray attr = context.obtainStyledAttributes(attributes, R.styleable.TypeButton);
            size = attr.getDimensionPixelSize(R.styleable.TypeButton_size, size);
            type = attr.getInteger(R.styleable.TypeButton_type, ButtonType.TYPE_CANCEL);
            attr.recycle();
        }
        init(type, size);
    }

    public TypeButton(Context context, int type, int size) {
        super(context);
        init(type, size);
    }

    private void init(int type, int size) {
        this.button_type = type;
        button_size = size;
        button_radius = size / 2.0f;
        center_X = size / 2.0f;
        center_Y = size / 2.0f;

        mPaint = new Paint();
        path = new Path();
        strokeWidth = size / 50f;
        index = button_size / 12f;
        rectF = new RectF(center_X, center_Y - index, center_X + index * 2, center_Y + index);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(button_size, button_size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果类型为取消，则绘制内部为返回箭头
        if (button_type == ButtonType.TYPE_CANCEL) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(0xEEDCDCDC);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(center_X, center_Y, button_radius, mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);

            path.moveTo(center_X - index / 7, center_Y + index);
            path.lineTo(center_X + index, center_Y + index);

            path.arcTo(rectF, 90, -180);
            path.lineTo(center_X - index, center_Y - index);
            canvas.drawPath(path, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            path.reset();
            path.moveTo(center_X - index, (float) (center_Y - index * 1.5));
            path.lineTo(center_X - index, (float) (center_Y - index / 2.3));
            path.lineTo((float) (center_X - index * 1.6), center_Y - index);
            path.close();
            canvas.drawPath(path, mPaint);

        }else  if (button_type == ButtonType.TYPE_CONFIRM) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(0xFFFFFFFF);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(center_X, center_Y, button_radius, mPaint);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xFF00CC00);
            mPaint.setStrokeWidth(strokeWidth);

            path.moveTo(center_X - button_size / 6f, center_Y);
            path.lineTo(center_X - button_size / 21.2f, center_Y + button_size / 7.7f);
            path.lineTo(center_X + button_size / 4.0f, center_Y - button_size / 8.5f);
            path.lineTo(center_X - button_size / 21.2f, center_Y + button_size / 9.4f);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
