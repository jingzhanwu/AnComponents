package com.mti.component.common;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/6/15
 * @change
 * @describe 简单的环形进度条
 **/
public class SimpleRingProgressComponent extends View {

    /**
     * 绘制画笔
     */
    private Paint mBackPaint, mProgressPaint;
    /**
     * 绘制区域
     */
    private RectF mRectF;
    /**
     * 圆环渐变色
     */
    private int[] mColorArray;
    /**
     * 圆环进度(0-100)
     */
    private int mProgress;

    public SimpleRingProgressComponent(Context context) {
        this(context, null);
    }


    public SimpleRingProgressComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SimpleRingProgressComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleRingProgressComponent);

        // 初始化背景圆环画笔
        mBackPaint = new Paint();
        // 只描边，不填充
        mBackPaint.setStyle(Paint.Style.STROKE);
        // 设置圆角
        mBackPaint.setStrokeCap(Paint.Cap.ROUND);
        // 设置抗锯齿
        mBackPaint.setAntiAlias(true);
        // 设置抖动
        mBackPaint.setDither(true);
        mBackPaint.setStrokeWidth(typedArray.getDimension(R.styleable.SimpleRingProgressComponent_backWidth, 5));
        mBackPaint.setColor(typedArray.getColor(R.styleable.SimpleRingProgressComponent_backColor, Color.LTGRAY));

        // 初始化进度圆环画笔
        mProgressPaint = new Paint();
        // 只描边，不填充
        mProgressPaint.setStyle(Paint.Style.STROKE);
        // 设置圆角
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        // 设置抗锯齿
        mProgressPaint.setAntiAlias(true);
        // 设置抖动
        mProgressPaint.setDither(true);
        mProgressPaint.setStrokeWidth(typedArray.getDimension(R.styleable.SimpleRingProgressComponent_progressWidth, 10));
        mProgressPaint.setColor(typedArray.getColor(R.styleable.SimpleRingProgressComponent_progressColor, Color.BLUE));

        // 初始化进度圆环渐变色
        int startColor = typedArray.getColor(R.styleable.SimpleRingProgressComponent_progressStartColor, -1);
        int firstColor = typedArray.getColor(R.styleable.SimpleRingProgressComponent_progressFirstColor, -1);
        if (startColor != -1 && firstColor != -1) {
            mColorArray = new int[]{startColor, firstColor};
        } else {
            mColorArray = null;
        }

        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.SimpleRingProgressComponent_progressRate, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int mRectLength = calculateRectLength(viewWide, viewHigh);
        int mRectL = getPaddingLeft() + (viewWide - mRectLength) / 2;
        int mRectT = getPaddingTop() + (viewHigh - mRectLength) / 2;
        mRectF = new RectF(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength);

        // 设置进度圆环渐变色
        if (mColorArray != null && mColorArray.length > 1) {
            mProgressPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        }
    }

    private int calculateRectLength(int viewWide, int viewHigh) {
        return (int) ((viewWide > viewHigh ? viewHigh : viewWide) - (mBackPaint.getStrokeWidth() > mProgressPaint.getStrokeWidth() ? mBackPaint.getStrokeWidth() : mProgressPaint.getStrokeWidth()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, 0, 360, false, mBackPaint);
        canvas.drawArc(mRectF, 275, 360 * mProgress / 100, false, mProgressPaint);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 获取当前进度
     *
     * @return 当前进度（0-100）
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置当前进度
     *
     * @param progress 当前进度（0-100）
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
     *
     * @param progress 当前进度（0-100）
     * @param animTime 动画时间（毫秒）
     */
    public void setProgress(int progress, long animTime) {
        if (animTime <= 0) {
            setProgress(progress);
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
            animator.addUpdateListener(animation -> {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(animTime);
            animator.start();
        }
    }

    /**
     * 设置背景圆环宽度
     *
     * @param width 背景圆环宽度
     */
    public void setBackWidth(int width) {
        mBackPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置背景圆环颜色
     *
     * @param color 背景圆环颜色
     */
    public void setBackColor(@ColorRes int color) {
        mBackPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    /**
     * 设置进度圆环宽度
     *
     * @param width 进度圆环宽度
     */
    public void setProgressWidth(int width) {
        mProgressPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置进度圆环颜色
     *
     * @param color 景圆环颜色
     */
    public void setProgressColor(@ColorRes int color) {
        mProgressPaint.setColor(ContextCompat.getColor(getContext(), color));
        mProgressPaint.setShader(null);
        invalidate();
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param startColor 进度圆环开始颜色
     * @param firstColor 进度圆环结束颜色
     */
    public void setProgressColor(@ColorRes int startColor, @ColorRes int firstColor) {
        mColorArray = new int[]{ContextCompat.getColor(getContext(), startColor), ContextCompat.getColor(getContext(), firstColor)};
        mProgressPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param colorArray 渐变色集合
     */
    public void setProgressColor(@ColorRes int[] colorArray) {
        if (colorArray == null || colorArray.length < 2) {
            return;
        }
        mColorArray = new int[colorArray.length];
        for (int index = 0; index < colorArray.length; index++) {
            mColorArray[index] = ContextCompat.getColor(getContext(), colorArray[index]);
        }
        mProgressPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }
}
