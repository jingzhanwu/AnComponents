package com.mti.component.common;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;



/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/8
 * @change
 * @describe 带动效的直线
 **/
public class PathEffectDividerComponent extends View {
    /**
     * 背景颜色
     */
    private int lineBackgroundColor= Color.parseColor("#005986");
    /**
     * 两边端点颜色
     */
    private int pointColor= Color.parseColor("#4ABDFF");
    /**
     * 背景直线画笔
     */
    private Paint backgroundPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
    /**
     * 两端端点画笔
     */
    private Paint pointPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
    /**
     * 进度线画笔
     */
    private Paint linePaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
    /**
     * 背景线高度
     */
    private int backgroundPointHeight=1;
    /**
     * 两边两端高度
     */
    private int pointHeight=1;
    private float radius=pointHeight;
    /**
     * 是否绘制进度线
     */
    private boolean isDrawProgressLine;
    private float fraction;
    private  ValueAnimator valueAnimator;
    public PathEffectDividerComponent(Context context) {
        this(context,null);
    }

    public PathEffectDividerComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        pointHeight= dip2px(getContext(),pointHeight);

        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(lineBackgroundColor);
        backgroundPaint.setStrokeWidth(backgroundPointHeight);

        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(pointColor);
        pointPaint.setStrokeWidth(pointHeight);

        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(pointColor);
        linePaint.setStrokeWidth(backgroundPointHeight);
    }

    public void setLineBackgroundColor(int lineBackgroundColor) {
        this.lineBackgroundColor = lineBackgroundColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    /**
     * 执行绘制进度线
     * @param duration
     */
    public void startLoading(long duration){
        this.isDrawProgressLine=true;
        valueAnimator=new ValueAnimator();
        valueAnimator.setDuration(duration);
        valueAnimator.setFloatValues(0,1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(va -> {
            fraction= (float) va.getAnimatedValue();
            Log.d("divider-path","setDrawProgressLine-fraction--"+fraction);
            postInvalidate();
        });
        valueAnimator.start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator!=null){
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        drawBackground(canvas);

        //绘制两边端点
        drawEndpoint(canvas);

        //绘制进度
        drawProgress(canvas);

    }

    /**
     * 绘制进度
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        if (isDrawProgressLine){
            canvas.drawLine(radius,getHeight()/2,fraction*(getWidth()-radius),getHeight()/2,linePaint);
        }
    }

    /**
     * 绘制端点
     * @param canvas
     */
    private void drawEndpoint(Canvas canvas) {
        int height=getHeight();
        int width=getWidth();
        float radius=pointHeight;
        //绘制左边端点
        float leftFx=radius;
        float leftFy=height/2;

        canvas.drawCircle(leftFx,leftFy,radius,pointPaint);


        //绘制右边端点
        float rightFx=width-radius;
        float rightFy=height/2;
        canvas.drawCircle(rightFx,rightFy,radius,pointPaint);
    }

    /**
     * 绘制背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        int height=getHeight();
        canvas.drawLine(0,height/2,getWidth(),height/2,backgroundPaint);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
