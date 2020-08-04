package com.mti.component.common;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * @author xuebing
 * @company 上海道枢信息科技-->
 * @date 2020/5/29
 * @change
 * @describe 3d翻转效果组件
 **/
public class Flip3DComponent extends ViewGroup {
    private Camera mCamera;
    private Matrix mMatrix;
    /**
     * 开始时的item位置
     */
    private int mStartScreen = 1;
    private float mDownY = 0;
    private static final int standerSpeed = 2000;
    /**
     * 当前item的位置
     */
    private int mCurScreen = 1;
    /**
     * 控件的高
     */
    private  int mHeight = 0;
    private VelocityTracker mTracker;
    private Scroller mScroller;
    /**
     * 旋转的角度，可以进行修改来观察效果
     */
    private float angle = 90;
    //三种状态
    private static final int STATE_PRE = 0;
    private static final int STATE_NEXT = 1;
    private static final int STATE_NORMAL = 2;
    private int STATE = -1;
    /**
     * 滑动阻力
     */
    private float resistance = 1.6f;
    public Flip3DComponent(Context context) {
        this(context,null);
    }

    public Flip3DComponent(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Flip3DComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCamera = new Camera();
        mMatrix = new Matrix();

        if (mScroller == null){
            mScroller = new Scroller(getContext(),new LinearInterpolator());
        }
    }
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth,measureHeight);

        MarginLayoutParams params = (MarginLayoutParams) this.getLayoutParams();
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                measureWidth- (params.leftMargin + params.rightMargin), MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                measureHeight - (params.topMargin + params.bottomMargin), MeasureSpec.EXACTLY);

        measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);

        mHeight = getMeasuredHeight();

        scrollTo(0,mStartScreen * mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        MarginLayoutParams params = (MarginLayoutParams) this.getLayoutParams();
        int childTop = 0;
        for (int i = 0; i <getChildCount() ; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE){
                if (i==0){
                    childTop+=params.topMargin;
                }
                child.layout(params.leftMargin, childTop,
                        child.getMeasuredWidth() + params.leftMargin, childTop + child.getMeasuredHeight());
                childTop = childTop + child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0;i<getChildCount();i++){
            drawScreen(canvas,i,getDrawingTime());
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                return false;
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null){
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                if (!mScroller.isFinished()){
                    mScroller.setFinalY(mScroller.getCurrY());
                    mScroller.abortAnimation();
                    scrollTo(0,getScrollY());
                }
                mDownY = y;
                break;
            case  MotionEvent.ACTION_MOVE:

                int disY  = (int) (mDownY - y);
                mDownY = y;
                if (mScroller.isFinished()){
                    recycleMove(disY);
                }
                break;

            case MotionEvent.ACTION_UP:
                mTracker.computeCurrentVelocity(1000);

                float velocitY = mTracker.getYVelocity();
                //滑动的速度大于规定的速度，或者向上滑动时，上一页页面展现出的高度超过1/2。则设定状态为STATE_PRE
                if(velocitY > standerSpeed || ((getScrollY() + mHeight / 2) / mHeight < mStartScreen)){
                    STATE =  STATE_PRE;
                }else if(velocitY < -standerSpeed  || ((getScrollY() + mHeight / 2) / mHeight > mStartScreen)){
                    //滑动的速度大于规定的速度，或者向下滑动时，下一页页面展现出的高度超过1/2。则设定状态为STATE_NEXT
                    STATE =  STATE_NEXT;
                }else{
                    STATE =  STATE_NORMAL;
                }
                //根据STATE进行相应的变化
                changeByState();
                if (mTracker != null){
                    mTracker.recycle();
                    mTracker = null;
                }
                break;
        }
        //返回true,消耗点击事件
        return true;
    }
    private void changeByState() {
        switch (STATE) {
            case STATE_NORMAL:
                toNormalAction();
                break;
            case STATE_PRE:
                toPrePager();
                break;
            case STATE_NEXT:
                toNextPager();
                break;
        }
        invalidate();
    }
    /**
     * 当前页
     */
    private void toNormalAction() {
        int startY;
        int delta;
        int duration;
        STATE = STATE_NORMAL;
        startY = getScrollY();
        delta = mHeight * mStartScreen - getScrollY();
        duration = (Math.abs(delta)) * 4;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }
    /**
     * 上一页
     */
    private void toPrePager() {
        int startY;
        int delta;
        int duration;
        STATE = STATE_PRE;
        //增加新的页面
        setPre();
        //mScroller开始的坐标
        startY = getScrollY() + mHeight;
        setScrollY(startY);
        //mScroller移动的距离
        delta = -(startY - mStartScreen * mHeight);
        duration = (Math.abs(delta)) * 2;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }
    /**
     * 下一页
     */
    private void toNextPager() {
        int startY;
        int delta;
        int duration;
        STATE = STATE_NEXT;
        setNext();
        startY = getScrollY() - mHeight;
        setScrollY(startY);
        delta = mHeight * mStartScreen - startY;
        duration = (Math.abs(delta)) * 2;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }
    private void setNext(){
        mCurScreen = (mCurScreen + 1) % getChildCount();

        int childCount = getChildCount();
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, childCount - 1);
    }

    private void setPre(){
        mCurScreen = ((mCurScreen - 1) + getChildCount()) % getChildCount();

        int childCount = getChildCount();
        View view = getChildAt(childCount - 1);
        removeViewAt(childCount - 1);
        addView(view, 0);
    }
    private void recycleMove(int delta) {
        delta = delta % mHeight;
        delta = (int) (delta / resistance);
        if (Math.abs(delta) > mHeight / 4) {
            return;
        }
        if (getScrollY() <= 0 && mCurScreen <= 0  && delta<=0){
            return;
        }
        if (mHeight*mCurScreen <= getScrollY()  && mCurScreen == getChildCount()-1 && delta>= 0){
            return;
        }
        scrollBy(0, delta);

        if (getScrollY() < 8 && mCurScreen != 0) {
            setPre();
            scrollBy(0, mHeight);
        } else if (getScrollY() > (getChildCount() - 1) * mHeight - 8) {
            setNext();
            scrollBy(0, -mHeight);
        }

    }
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
    /**
     * 画单个页面
     * @param canvas
     * @param screen
     * @param drawingTime
     */
    private void drawScreen(Canvas canvas, int screen, long drawingTime) {
        // 得到当前子View的高度
        final int height = getHeight();
        final int scrollHeight = screen * height;
        final int scrollY = this.getScrollY();
        // 偏移量不足的时
        if (scrollHeight > scrollY + height || scrollHeight + height < scrollY) {
            return;
        }
        final View child = getChildAt(screen);
        final int faceIndex = screen;
        final float currentDegree = getScrollY() * (angle / getMeasuredHeight());
        final float faceDegree = currentDegree - faceIndex * angle;
        if (faceDegree > 90 || faceDegree < -90) {
            return;
        }
        final float centerY = (scrollHeight < scrollY) ? scrollHeight + height
                : scrollHeight;
        final float centerX = getWidth() / 2;
        final Camera camera = mCamera;
        final Matrix matrix = mMatrix;
        canvas.save();
        camera.save();
        camera.rotateX(faceDegree);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.concat(matrix);
        drawChild(canvas, child, drawingTime);
        canvas.restore();
    }
}
