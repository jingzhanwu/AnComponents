package com.mti.component.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/12
 * @change
 * @describe 背景加载效果
 **/
public class BackgroundLoadingEffectComponent extends View {

    /**
     * 背景框画笔
     */
    private Paint backgroundOutlinePaint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);
    /**
     * 内部两端箭头画笔
     */
    private Paint innerArrowPaint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);
    /**
     * 外部两端箭头画笔
     */
    private Paint outerArrowPaint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);
    /**
     * 进度条效果画笔
     */
    private Paint progressPaint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);

    /**
     * 背景框画笔颜色
     */
    private int backgroundOutlineColor= Color.parseColor("#00CAFC");
    /**
     * 内部两端箭头画笔颜色
     */
    private int innerArrowColor= Color.parseColor("#0081CB");
    /**
     * 外部两端箭头画笔颜色
     */
    private int outerArrowColor= Color.parseColor("#00FFFF");

     /**
     * 进度条效果画笔颜色
     */
    private int progressColor=outerArrowColor;

    /**
     * 外部间距
     */
    private int spacing=10;
    /**
     * 内部箭头距离左边距
     */
    private int arrowSpacing=10;
    /**
     * 小箭头宽度
     */
    private int smallArrowWidth=0;
    /**
     * 大箭头宽度
     */
    private int bigArrowWidth=0;
    /**
     * 大箭头尾巴宽度
     */
    private int bigArrowTailWidth=32;
    /**
     * 画笔宽度
     */
    private int paintWidth=2;

    /**********************大箭头左边*************************/

    /**
     * 左边-大箭头中部点
     */
    private PointF bigArrowLeftCenterPoint=new PointF();
    /**
     * 左边-大箭头上部第一个点
     */
    private PointF bigArrowLeftTopFirstPoint=new PointF();
    /**
     * 左边-大箭头上部第二个点
     */
    private PointF bigArrowLeftTopSecondPoint=new PointF();

    /**
     * 左边-大箭头下部第一个点
     */
    private PointF bigArrowLeftBottomFirstPoint=new PointF();


    /**
     * 左边-大箭头下部第二个点
     */
    private PointF bigArrowLeftBottomSecondPoint=new PointF();


    /**********************大箭头右边*************************/
    /**
     * 右边-大箭头中部点
     */
    private PointF bigArrowRightCenterPoint=new PointF();
    /**
     * 右边-大箭头上部第一个点
     */
    private PointF bigArrowRightTopFirstPoint=new PointF();
    /**
     * 右边-大箭头上部第二个点
     */
    private PointF bigArrowRightTopSecondPoint=new PointF();

    /**
     * 右边-大箭头下部第一个点
     */
    private PointF bigArrowRightBottomFirstPoint=new PointF();


    /**
     * 右边-大箭头下部第二个点
     */
    private PointF bigArrowRightBottomSecondPoint=new PointF();




    /**********************小箭头左边*************************/

    /**
     * 左边-小箭头中部点
     */
    private PointF smallArrowLeftCenterPoint=new PointF();
    /**
     * 左边-小箭头上部第一个点
     */
    private PointF smallArrowLeftTopFirstPoint=new PointF();


    /**
     * 左边-小箭头下部第一个点
     */
    private PointF smallArrowLeftBottomFirstPoint=new PointF();

    /**********************小箭头右边*************************/
    /**
     * 右边-小箭头中部点
     */
    private PointF smallArrowRightCenterPoint=new PointF();
    /**
     * 右边-小箭头上部第一个点
     */
    private PointF smallArrowRightTopFirstPoint=new PointF();


    /**
     * 右边-小箭头下部第一个点
     */
    private PointF smallArrowRightBottomFirstPoint=new PointF();


    /**
     * 背景框背景
     */
    private Path backgroundPath=new Path();

    /**
     * 绘制内容区域
     */
    private RectF contentRect=new RectF();

    /**
     * 左边大箭头路径
     */
    private Path bigArrowLeftPath=new Path();
    /**
     * 右边大箭头路径
     */
    private Path bigArrowRightPath=new Path();

    /**
     * 左边小箭头路径
     */
    private Path smallArrowLeftPath=new Path();
    /**
     * 右边小箭头路径
     */
    private Path smallArrowRightPath=new Path();

    private ValueAnimator animator;
    private PathMeasure pathMeasure;
    private float fraction;

    /**
     * 路径坐标
     */
    private float[] mPos = new float[2];
    /**
     * 路径角度
     */
    private float[] mTan = new float[2];
    /**
     * 光标高度
     */
    private int cursorHeight=4;
    /**
     * 光标宽度
     */
    private int cursorWidth=10;
    /**
     * 是否显示光标
     */
    private boolean isShowingCursor;
    public BackgroundLoadingEffectComponent(Context context) {
        this(context,null);
    }

    public BackgroundLoadingEffectComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        spacing= dip2px(getContext(),spacing);
        arrowSpacing= dip2px(getContext(),arrowSpacing);
        smallArrowWidth=dip2px(getContext(),smallArrowWidth);
        bigArrowWidth=dip2px(getContext(),bigArrowWidth);
        bigArrowTailWidth=dip2px(getContext(),bigArrowTailWidth);
        paintWidth= dip2px(getContext(),paintWidth);
        cursorHeight= dip2px(getContext(),cursorHeight);
        cursorWidth= dip2px(getContext(),cursorWidth);

        backgroundOutlinePaint.setStrokeWidth(dip2px(getContext(),0.5f));
        backgroundOutlinePaint.setStyle(Paint.Style.STROKE);
        backgroundOutlinePaint.setColor(backgroundOutlineColor);
        backgroundOutlinePaint.setMaskFilter(new BlurMaskFilter(4, BlurMaskFilter.Blur.SOLID));

        innerArrowPaint.setStrokeWidth(paintWidth);
        innerArrowPaint.setStyle(Paint.Style.STROKE);
        innerArrowPaint.setColor(innerArrowColor);
        innerArrowPaint.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.SOLID));

        outerArrowPaint.setStrokeWidth(paintWidth);
        outerArrowPaint.setStyle(Paint.Style.STROKE);
        outerArrowPaint.setColor(outerArrowColor);

        progressPaint.setStrokeWidth(paintWidth);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setColor(progressColor);
        progressPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL));

        pathMeasure=new PathMeasure();
        animator=new ValueAnimator();
    }


    public void startProgress(){
       startProgress(9000);
    }


    public void startProgress(long duration){
        isShowingCursor=true;
        animator.setFloatValues(0f,1f);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(va -> {
            fraction= (float) va.getAnimatedValue();
            postInvalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isShowingCursor=false;
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator!=null){
            animator.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1.计算绘制区域
        calculateContentZone();
        //计算关键坐标
        calculateKeyPoints();
    }

    /**
     * 计算绘制区域
     */
    private void calculateContentZone() {
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();

        contentRect.left=spacing;
        contentRect.top=spacing;
        contentRect.right=width-spacing;
        contentRect.bottom=height-spacing;

        bigArrowWidth=dip2px(getContext(),24);
        smallArrowWidth=bigArrowWidth/2;
    }

    /**
     * 计算关键坐标
     */
    private void calculateKeyPoints() {
        calculateBigArrowKeyPoints();
        calculateSmallArrowKeyPoints();
    }

    /**
     * 计算大箭头关键坐标点
     */
    private void calculateBigArrowKeyPoints() {

        //左边箭头关键点
        bigArrowLeftCenterPoint.x=contentRect.left + arrowSpacing;
        bigArrowLeftCenterPoint.y=contentRect.centerY();

        bigArrowLeftTopFirstPoint.x=bigArrowLeftCenterPoint.x+bigArrowWidth;
        bigArrowLeftTopFirstPoint.y=contentRect.top;

        bigArrowLeftTopSecondPoint.x=bigArrowLeftTopFirstPoint.x+bigArrowTailWidth;
        bigArrowLeftTopSecondPoint.y=contentRect.top;

        bigArrowLeftBottomFirstPoint.x=bigArrowLeftTopFirstPoint.x;
        bigArrowLeftBottomFirstPoint.y=contentRect.bottom;

        bigArrowLeftBottomSecondPoint.x=bigArrowLeftTopSecondPoint.x;
        bigArrowLeftBottomSecondPoint.y=contentRect.bottom;


        //右边箭头关键点
        bigArrowRightCenterPoint.x=contentRect.right - arrowSpacing;
        bigArrowRightCenterPoint.y=contentRect.centerY();

        bigArrowRightTopFirstPoint.x=bigArrowRightCenterPoint.x-bigArrowWidth;
        bigArrowRightTopFirstPoint.y=contentRect.top;

        bigArrowRightTopSecondPoint.x=bigArrowRightTopFirstPoint.x-bigArrowTailWidth;
        bigArrowRightTopSecondPoint.y=contentRect.top;

        bigArrowRightBottomFirstPoint.x=bigArrowRightTopFirstPoint.x;
        bigArrowRightBottomFirstPoint.y=contentRect.bottom;

        bigArrowRightBottomSecondPoint.x=bigArrowRightTopSecondPoint.x;
        bigArrowRightBottomSecondPoint.y=contentRect.bottom;

        //背景框路径

        backgroundPath.moveTo(bigArrowRightTopFirstPoint.x,bigArrowRightTopFirstPoint.y);
        backgroundPath.lineTo(bigArrowRightCenterPoint.x,bigArrowRightCenterPoint.y);
        backgroundPath.lineTo(bigArrowRightBottomFirstPoint.x,bigArrowRightBottomFirstPoint.y);
        backgroundPath.lineTo(bigArrowLeftBottomFirstPoint.x,bigArrowLeftBottomFirstPoint.y);
        backgroundPath.lineTo(bigArrowLeftCenterPoint.x,bigArrowLeftCenterPoint.y);
        backgroundPath.lineTo(bigArrowLeftTopFirstPoint.x,bigArrowLeftTopFirstPoint.y);
        backgroundPath.close();

        //左边大箭头路径
        bigArrowLeftPath.moveTo(bigArrowLeftTopSecondPoint.x,bigArrowLeftTopSecondPoint.y);
        bigArrowLeftPath.lineTo(bigArrowLeftTopFirstPoint.x,bigArrowLeftTopFirstPoint.y);
        bigArrowLeftPath.lineTo(bigArrowLeftCenterPoint.x,bigArrowLeftCenterPoint.y);
        bigArrowLeftPath.lineTo(bigArrowLeftBottomFirstPoint.x,bigArrowLeftBottomFirstPoint.y);
        bigArrowLeftPath.lineTo(bigArrowLeftBottomSecondPoint.x,bigArrowLeftBottomSecondPoint.y);


        //左边大箭头路径
        bigArrowRightPath.moveTo(bigArrowRightTopSecondPoint.x,bigArrowRightTopSecondPoint.y);
        bigArrowRightPath.lineTo(bigArrowRightTopFirstPoint.x,bigArrowRightTopFirstPoint.y);
        bigArrowRightPath.lineTo(bigArrowRightCenterPoint.x,bigArrowRightCenterPoint.y);
        bigArrowRightPath.lineTo(bigArrowRightBottomFirstPoint.x,bigArrowRightBottomFirstPoint.y);
        bigArrowRightPath.lineTo(bigArrowRightBottomSecondPoint.x,bigArrowRightBottomSecondPoint.y);

    }


    /**
     * 计算小箭头关键坐标点
     */
    private void calculateSmallArrowKeyPoints() {
        //左边箭头关键点
        smallArrowLeftCenterPoint.x=contentRect.left;
        smallArrowLeftCenterPoint.y=contentRect.centerY();

        smallArrowLeftTopFirstPoint.x=smallArrowLeftCenterPoint.x+smallArrowWidth;
        smallArrowLeftTopFirstPoint.y=contentRect.top+contentRect.height()/4;

        smallArrowLeftBottomFirstPoint.x=smallArrowLeftTopFirstPoint.x;
        smallArrowLeftBottomFirstPoint.y=contentRect.bottom-contentRect.height()/4;

        //左边小箭头路径
        smallArrowLeftPath.moveTo(smallArrowLeftTopFirstPoint.x,smallArrowLeftTopFirstPoint.y);
        smallArrowLeftPath.lineTo(smallArrowLeftCenterPoint.x,smallArrowLeftCenterPoint.y);
        smallArrowLeftPath.lineTo(smallArrowLeftBottomFirstPoint.x,smallArrowLeftBottomFirstPoint.y);

        //右边箭头关键点
        smallArrowRightCenterPoint.x=contentRect.right;
        smallArrowRightCenterPoint.y=contentRect.centerY();

        smallArrowRightTopFirstPoint.x=smallArrowRightCenterPoint.x-smallArrowWidth;
        smallArrowRightTopFirstPoint.y=contentRect.top+contentRect.height()/4;

        smallArrowRightBottomFirstPoint.x=smallArrowRightTopFirstPoint.x;
        smallArrowRightBottomFirstPoint.y=contentRect.bottom-contentRect.height()/4;


        //左边小箭头路径
        smallArrowRightPath.moveTo(smallArrowRightTopFirstPoint.x,smallArrowRightTopFirstPoint.y);
        smallArrowRightPath.lineTo(smallArrowRightCenterPoint.x,smallArrowRightCenterPoint.y);
        smallArrowRightPath.lineTo(smallArrowRightBottomFirstPoint.x,smallArrowRightBottomFirstPoint.y);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //2.绘制背景框
        drawBackgroundOutline(canvas);
        //3.绘制内部箭头
        drawInnerArrows(canvas);
        //4.绘制外部箭头
        drawOuterArrows(canvas);
        //5.绘制进度
        if (isShowingCursor){
            drawProgressLines(canvas);
        }
    }



    /**
     * 绘制背景框
     * @param canvas
     */
    private void drawBackgroundOutline(Canvas canvas) {
        canvas.drawPath(backgroundPath,backgroundOutlinePaint);
    }


    /**
     * 绘制内部箭头
     * @param canvas
     */
    private void drawInnerArrows(Canvas canvas) {
        canvas.drawPath(bigArrowLeftPath,innerArrowPaint);
        canvas.drawPath(bigArrowRightPath,innerArrowPaint);
    }


    /**
     * 绘制外部箭头
     * @param canvas
     */
    private void drawOuterArrows(Canvas canvas) {
        canvas.drawPath(smallArrowLeftPath,outerArrowPaint);
        canvas.drawPath(smallArrowRightPath,outerArrowPaint);
    }


    /**
     * 绘制进度线
     * @param canvas
     */
    private void drawProgressLines(Canvas canvas) {
        pathMeasure.setPath(backgroundPath,true);
        float stopD=fraction*pathMeasure.getLength();
        pathMeasure.getPosTan(stopD,mPos,mTan);

        //计算角度
        float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
        RectF rectF=new RectF();
        rectF.left=mPos[0];
        rectF.top=mPos[1]-cursorHeight/2;
        rectF.right=cursorWidth+rectF.left;
        rectF.bottom=mPos[1]+cursorHeight/2;

        canvas.save();
        canvas.rotate(degree,mPos[0],mPos[1]);
        canvas.drawRect(rectF,progressPaint);
        canvas.restore();
    }



    private   int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
