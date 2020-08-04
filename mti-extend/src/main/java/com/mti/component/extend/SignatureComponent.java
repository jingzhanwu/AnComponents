package com.mti.component.extend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.mti.component.extend.detector.ScaleRotationGestureDetector;
import com.mti.component.extend.sign.CBitmap;
import com.mti.component.extend.sign.CDrawable;
import com.mti.component.extend.sign.CPath;
import com.mti.component.extend.sign.CRotation;
import com.mti.component.extend.sign.CScale;
import com.mti.component.extend.sign.CText;
import com.mti.component.extend.sign.CTransform;
import com.mti.component.extend.sign.CTranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * @anthor created by jzw
 * @date 2020/6/1
 * @change
 * @describe 自定义电子签名组件
 **/
public class SignatureComponent extends View {

    //绘制对象和属性
    private ArrayList<CDrawable> mDrawableList = new ArrayList<>();
    //撤销的绘制对象
    private ArrayList<CDrawable> mUndoList = new ArrayList<>();
    private CDrawable selected = null;
    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private CDrawable hovering = null;
    private CTranslation hoveringTranslation = null;

    //默认画笔颜色
    private int mColor = Color.BLACK;
    private int savePoint = -1;
    private Bitmap deleteIcon;
    private RectF deleteIconPosition = new RectF(-1, -1, -1, -1);
    private DeletionListener deletionListener = null;
    private DeletionConfirmationListener deletionConfirmationListener = null;

    // Canvas interaction modes
    private int mInteractionMode = DRAW_MODE;

    //Mode prior to rotation.
    private Integer mOldInteractionMode = null;

    // background color of the library
    private int mBackgroundColor = Color.WHITE;
    // default style for the library
    private Paint.Style mStyle = Paint.Style.STROKE;

    // default stroke size for the library
    private float mSize = 5f;

    // flag indicating whether or not the background needs to be redrawn
    private boolean mRedrawBackground;

    // background mode for the library, default to blank
    private int mBackgroundMode = BACKGROUND_STYLE_BLANK;

    /**
     * Default Notebook left line color. Value = Color.Red.
     */
    public static final int NOTEBOOK_LEFT_LINE_COLOR = Color.RED;

    // Flag indicating that we are waiting for a location for the text
    private boolean mTextExpectTouch;

    //This handles gestures for the PinchGestureListener and ROTATE_MODE.
    private ScaleRotationGestureDetector mScaleDetector;

    //This is a listener for pinching gestures.
    private ScaleRotateListener mScaleRotateListener;

    //During a rotation gesture, this is the rotation of the selected object.
    private CRotation mCurrentRotation;

    //During a rotation gesture, this is the scale of the selected object.
    private CScale mCurrentScale;

    // Vars to decrease dirty area and increase performance
    private float lastTouchX, lastTouchY;
    private final RectF dirtyRect = new RectF();

    // 跟踪路径和正在使用的路径
    CPath currentPath;
    Paint currentPaint;
    Paint selectionPaint;

    private int selectionColor = Color.DKGRAY;
    private static final int MAX_CLICK_DURATION = 1000;
    private static final int MAX_CLICK_DISTANCE = 15;


    /*********************************************************************************************/
    /************************************     一些常量   *******************************************/
    /*********************************************************************************************/

    //背景绘制模式
    /**
     * 背景模式(白色默认)：在setBackgroundMode（）中使用。在背景上不会画线。这是默认值。
     */
    public static final int BACKGROUND_STYLE_BLANK = 0;
    /**
     * 背景模式(笔记本背景)： 在setBackgroundMode（）中使用。将水平绘制蓝线，垂直绘制红线。
     */
    public static final int BACKGROUND_STYLE_NOTEBOOK_PAPER = 1;
    /**
     * 背景模式(方格纸背景)：在setBackgroundMode（）中使用。将水平和垂直绘制蓝线。
     */
    public static final int BACKGROUND_STYLE_GRAPH_PAPER = 2;

    //交互模式
    /**
     * 交互模式: 将让用户绘制。这是默认值。
     */
    public static final int DRAW_MODE = 0;
    /**
     * 交互模式: 将让用户选择对象。
     */
    public static final int SELECT_MODE = 1;
    /**
     * 交互模式: 将让用户旋转和缩放对象。
     */
    public static final int ROTATE_MODE = 2;
    /**
     * 交互模式：已锁定，将删除所有装饰，并且用户将无法修改任何内容。
     * 这是使用getCroppedCanvasBitmap（）或getCanvasBitmap（）检索位图时使用的模式。
     */
    public static final int LOCKED_MODE = 3;

    public static final int NOTEBOOK_LEFT_LINE_PADDING = 120;
    private static final int SELECTION_LINE_WIDTH = 2;

    private float mZoomLevel = 1.0f; //TODO Support Zoom
    private float mHorizontalOffset = 1, mVerticalOffset = 1; // TODO Support Offset and Viewport
    /**
     * Unused at this time.
     */
    public int mAutoscrollDistance = 100; // TODO Support Autoscroll
    private Rect cropBounds = null;


    public SignatureComponent(Context context) {
        this(context, null);
    }

    public SignatureComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.setBackgroundColor(mBackgroundColor);
        mTextExpectTouch = false;
        //初始化画笔
        selectionPaint = new Paint();
        selectionPaint.setAntiAlias(true);
        selectionPaint.setColor(selectionColor);
        selectionPaint.setStyle(Paint.Style.STROKE);
        selectionPaint.setStrokeJoin(Paint.Join.ROUND);
        selectionPaint.setStrokeWidth(SELECTION_LINE_WIDTH);
        selectionPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));

        deleteIcon = BitmapFactory.decodeResource(context.getResources(),
                android.R.drawable.ic_menu_delete);
        mScaleDetector = new ScaleRotationGestureDetector(context, new ScaleRotationGestureDetector.OnScaleRotationGestureListener() {

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                handleScaleEnd();
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return handleScaleBegin((ScaleRotationGestureDetector) detector);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return handleScale((ScaleRotationGestureDetector) detector);
            }

            @Override
            public boolean onRotate(ScaleRotationGestureDetector detector) {
                return handleScale((ScaleRotationGestureDetector) detector);
            }

        });
    }

    private boolean handleScaleBegin(ScaleRotationGestureDetector detector) {
        boolean consumed = false;
        if (mScaleRotateListener != null && selected != null) {
            try {
                consumed = mScaleRotateListener.startRotate();
                if (consumed) {
                    mOldInteractionMode = mInteractionMode;
                    setInteractionMode(ROTATE_MODE);
                    mCurrentRotation = new CRotation(selected, selected.getLastBounds().centerX(), selected.getLastBounds().centerY());
                    mCurrentScale = new CScale(selected, selected.getLastBounds().centerX(), selected.getLastBounds().centerY());
                    selected.addTransform(mCurrentRotation);
                    mDrawableList.add(mCurrentRotation);
                    selected.addTransform(mCurrentScale);
                    mDrawableList.add(mCurrentScale);

                    handleScale(detector);
                }
            } catch (Exception e) {
                //Do nothing.
            }
        }
        return consumed;
    }

    private void handleScaleEnd() {
        if (mScaleRotateListener != null) {
            try {
                mScaleRotateListener.endRotate();
                if (mOldInteractionMode != null) {
                    setInteractionMode(mOldInteractionMode);
                    mOldInteractionMode = null;
                }
                mCurrentScale = null;
                mCurrentRotation = null;
            } catch (Exception e) {
                //Do nothing.
            }
        }
    }

    public void setScaleRotateListener(ScaleRotateListener listener) {
        mScaleRotateListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 1);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 检查是否需要重绘背景
        drawBackground(canvas, mBackgroundMode);
        Rect totalBounds = new Rect(canvas.getWidth(), canvas.getHeight(), 0, 0);

        //循环绘制列表中的每项
        for (int i = 0; i < mDrawableList.size(); i++) {
            try {
                CDrawable d = mDrawableList.get(i);
                if (d instanceof CTransform) {
                    continue;
                }

                Rect bounds = d.computeBounds();
                totalBounds.union(bounds);
                d.draw(canvas);
                if (mInteractionMode == SELECT_MODE && d.equals(selected)) {
                    growRect(bounds, SELECTION_LINE_WIDTH);
                    canvas.drawRect(new RectF(bounds), selectionPaint);
                    deleteIconPosition = new RectF();
                    deleteIconPosition.left = bounds.right - (deleteIcon.getWidth() / 2);
                    deleteIconPosition.top = bounds.top - (deleteIcon.getHeight() / 2);
                    deleteIconPosition.right = deleteIconPosition.left + deleteIcon.getWidth();
                    deleteIconPosition.bottom = deleteIconPosition.top + deleteIcon.getHeight();
                    canvas.drawBitmap(deleteIcon, deleteIconPosition.left, deleteIconPosition.top, d.getPaint());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (totalBounds.width() <= 0) {
            //No bounds
            cropBounds = null;
        } else {
            cropBounds = totalBounds;
        }
    }

    private void growRect(Rect rect, int amount) {
        rect.left -= amount;
        rect.top -= amount;
        rect.bottom += amount;
        rect.right += amount;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //ROTATE_MODE is processed inside this:
        mScaleDetector.onTouchEvent(event);
        if (mScaleDetector.isInProgress()) {
            return true;
        }
        // delegate action to the correct method
        if (getInteractionMode() == DRAW_MODE)
            return onTouchDrawMode(event);
        if (getInteractionMode() == SELECT_MODE)
            return onTouchSelectMode(event);
        // if none of the above are selected, delegate to locked mode
        return onTouchLockedMode(event);
    }

    /**
     * 如果模式设置为锁定，则处理触摸事件
     */
    private boolean onTouchLockedMode(MotionEvent event) {
        return false;
    }

    /**
     * 负责缩放和旋转
     *
     * @return 如果使用缩放手势，则为true。
     */
    private boolean handleScale(ScaleRotationGestureDetector detector) {
        if (mInteractionMode != ROTATE_MODE) {
            return false;
        }
        mCurrentScale.setFactor(detector.getScaleFactor(), Math.min(getWidth(), getHeight()));

        mCurrentRotation.setRotation((int) detector.getRotation());

        invalidate();
        return true;
    }

    private static final float TOUCH_TOLERANCE = 4;

    /**
     * 如果模式设置为绘制，则处理触摸输入
     *
     * @param event the touch event
     * @return the result of the action
     */
    public boolean onTouchDrawMode(MotionEvent event) {
        // get location of touch
        float eventX = event.getX();
        float eventY = event.getY();
        if (eventX < 0) {
            eventX = 0;
        }
        if (eventY < 0) {
            eventY = 0;
        }
        if (eventX > getWidth()) {
            eventX = getWidth();
        }
        if (eventY > getHeight()) {
            eventY = getHeight();
        }

        // based on the users action, start drawing
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // create new path and paint
                currentPath = new CPath();
                currentPaint = new Paint();
                currentPaint.setAntiAlias(true);
                currentPaint.setColor(mColor);
                currentPaint.setStyle(mStyle);
                currentPaint.setStrokeJoin(Paint.Join.ROUND);
                currentPaint.setStrokeWidth(mSize);
                currentPath.setPaint(currentPaint);
                currentPath.moveTo(eventX, eventY);
                // capture touched locations
                lastTouchX = eventX;
                lastTouchY = eventY;
                mDrawableList.add(currentPath);
                mUndoList.clear();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(eventX - lastTouchX);
                float dy = Math.abs(eventY - lastTouchY);

                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {

                    currentPath.quadTo(lastTouchX, lastTouchY, (eventX + lastTouchX) / 2, (eventY + lastTouchY) / 2);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                }
//                int historySize = event.getHistorySize();
//                for (int i = 0; i < historySize; i++) {
//                    float historicalX = event.getHistoricalX(i);
//                    float historicalY = event.getHistoricalY(i);
//                    currentPath.lineTo(historicalX, historicalY);
//                }

                // After replaying history, connect the line to the touch point.
                //  currentPath.lineTo(eventX, eventY);

                dirtyRect.left = Math.min(currentPath.getXcoords(), dirtyRect.left);
                dirtyRect.right = Math.max(currentPath.getXcoords() + currentPath.getWidth(), dirtyRect.right);
                dirtyRect.top = Math.min(currentPath.getYcoords(), dirtyRect.top);
                dirtyRect.bottom = Math.max(currentPath.getYcoords() + currentPath.getHeight(), dirtyRect.bottom);

                // After replaying history, connect the line to the touch point.
                cleanDirtyRegion(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                currentPath.lineTo(eventX, eventY);
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                return false;
        }

        // Include some padding to ensure nothing is clipped
        invalidate();
//                (int) (dirtyRect.left - 20),
//                (int) (dirtyRect.top - 20),
//                (int) (dirtyRect.right + 20),
//                (int) (dirtyRect.bottom + 20));

        // register most recent touch locations
        lastTouchX = eventX;
        lastTouchY = eventY;
        return true;
    }

    /**
     * 如果将模式设置为选择，则处理触摸输入
     *
     * @param event the touch event
     */
    private boolean onTouchSelectMode(MotionEvent event) {
        ListIterator<CDrawable> li = mDrawableList.listIterator(mDrawableList.size());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hovering = null;
                pressStartTime = SystemClock.uptimeMillis();
                pressedX = event.getX();
                pressedY = event.getY();

                while (li.hasPrevious()) {
                    CDrawable d = li.previous();
                    if (d instanceof CTransform) {
                        continue;
                    }
                    Rect rect = d.computeBounds();
                    if (rect.contains((int) pressedX, (int) pressedY)) {
                        hovering = d;
                        break;
                    }
                }
                if (hovering != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (hovering == null) {
                    break; //Nothing is being dragged.
                }
                updateHoveringPosition(event);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (hovering != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                long pressDuration = SystemClock.uptimeMillis() - pressStartTime;
                double distance = Math.sqrt(Math.pow((event.getX() - pressedX), 2) + Math.pow((event.getY() - pressedY), 2));
                if (pressDuration < MAX_CLICK_DURATION && distance < MAX_CLICK_DISTANCE) {
                    //It was a click not a drag.
                    if (hovering == null && deleteIconPosition.contains(event.getX(), event.getY())) {
                        deleteSelection();
                        return true;
                    }
                    selected = hovering;
                    if (hovering != null) {
                        hovering.removeTransform(hoveringTranslation);
                        mDrawableList.remove(hoveringTranslation);
                    }
                } else if (distance > MAX_CLICK_DISTANCE) {
                    //It was a drag. Move the object there.
                    if (hovering != null) {
                        updateHoveringPosition(event);
                    }
                }
                invalidate();
                hovering = null;
                hoveringTranslation = null;
                return true;
            case MotionEvent.ACTION_CANCEL:
                if (hovering != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    hovering.removeTransform(hoveringTranslation);
                    mDrawableList.remove(hoveringTranslation);
                    hovering = null;
                    hoveringTranslation = null;
                }
                return true;

        }
        return false;
    }

    private void updateHoveringPosition(MotionEvent event) {

        double distance = Math.sqrt(Math.pow((event.getX() - pressedX), 2) + Math.pow((event.getY() - pressedY), 2));
        if (distance < MAX_CLICK_DISTANCE) {
            return; //Movement too small
        }

        if (hoveringTranslation == null) {
            hoveringTranslation = new CTranslation(hovering);
            Vector<Integer> v = new Vector<>(2);
            v.add((int) (event.getX() - pressedX));
            v.add((int) (event.getY() - pressedY));
            hoveringTranslation.setDirection(v);
            hovering.addTransform(hoveringTranslation);
            mDrawableList.add(hoveringTranslation);
            mUndoList.clear();
        } else {
            //Last transform was a translation. Replace translation with new coordinates.
            Vector<Integer> v = new Vector<>(2);
            v.add((int) (event.getX() - pressedX));
            v.add((int) (event.getY() - pressedY));
            hoveringTranslation.setDirection(v);
        }
    }

    /**
     * 在画布上绘制背景
     *
     * @param canvas         the canvas to draw on
     * @param backgroundMode one of BACKGROUND_STYLE_GRAPH_PAPER, BACKGROUND_STYLE_NOTEBOOK_PAPER, BACKGROUND_STYLE_BLANK
     */
    private void drawBackground(Canvas canvas, int backgroundMode) {
        if (mBackgroundColor != Color.TRANSPARENT) {
            canvas.drawColor(mBackgroundColor);
        }
        if (backgroundMode != BACKGROUND_STYLE_BLANK) {
            Paint linePaint = new Paint();
            linePaint.setColor(Color.argb(50, 0, 0, 0));
            linePaint.setStyle(mStyle);
            linePaint.setStrokeJoin(Paint.Join.ROUND);
            linePaint.setStrokeWidth(mSize - 2f);
            switch (backgroundMode) {
                case BACKGROUND_STYLE_GRAPH_PAPER:
                    drawGraphPaperBackground(canvas, linePaint);
                    break;
                case BACKGROUND_STYLE_NOTEBOOK_PAPER:
                    drawNotebookPaperBackground(canvas, linePaint);
                default:
                    break;
            }
        }
        mRedrawBackground = false;
    }

    /**
     * 在视图上绘制方格纸背景
     *
     * @param canvas the canvas to draw on
     * @param paint  the paint to use
     */
    private void drawGraphPaperBackground(Canvas canvas, Paint paint) {
        int i = 0;
        boolean doneH = false, doneV = false;

        // while we still need to draw either H or V
        while (!(doneH && doneV)) {

            // check if there is more H lines to draw
            if (i < canvas.getHeight())
                canvas.drawLine(0, i, canvas.getWidth(), i, paint);
            else
                doneH = true;

            // check if there is more V lines to draw
            if (i < canvas.getWidth())
                canvas.drawLine(i, 0, i, canvas.getHeight(), paint);
            else
                doneV = true;

            // declare as done
            i += 75;
        }
    }

    /**
     * 在视图上绘制笔记本纸背景
     *
     * @param canvas the canvas to draw on
     * @param paint  the paint to use
     */
    private void drawNotebookPaperBackground(Canvas canvas, Paint paint) {
        int i = 0;
        boolean doneV = false;
        // draw horizental lines
        while (!(doneV)) {
            if (i < canvas.getHeight())
                canvas.drawLine(0, i, canvas.getWidth(), i, paint);
            else
                doneV = true;
            i += 75;
        }
        // change line color
        paint.setColor(NOTEBOOK_LEFT_LINE_COLOR);
        // draw side line
        canvas.drawLine(NOTEBOOK_LEFT_LINE_PADDING, 0,
                NOTEBOOK_LEFT_LINE_PADDING, canvas.getHeight(), paint);


    }

    /**
     * 在屏幕上绘制文字
     *
     * @param text the text to draw
     * @param x    the x location of the text
     * @param y    the y location of the text
     * @param p    使用的paint。这用于文本大小，颜色。如果为null，则默认值为20sp大小的黑色。
     */
    public void drawText(String text, int x, int y, Paint p) {
        if (p == null) {
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getContext().getResources().getDisplayMetrics());
            p = new Paint();
            p.setTextSize(px);
            p.setColor(Color.BLACK);
        }
        mDrawableList.add(new CText(text, x, y, p));
        mUndoList.clear();
        invalidate();
    }

    /**
     * 从键盘捕获文本并将其绘制在屏幕上
     * //TODO Implement the method
     */
    private void drawTextFromKeyboard() {
        Toast.makeText(getContext(), "Touch where you want the text to be", Toast.LENGTH_LONG).show();
        //TODO
        mTextExpectTouch = true;
    }

    /**
     * 检索需要重绘的区域
     *
     * @param eventX The current x location of the touch
     * @param eventY the current y location of the touch
     */
    private void cleanDirtyRegion(float eventX, float eventY) {
        // figure out the sides of the dirty region
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }

    /**
     * 取消上一个操作。适用于CDrawable和CTransform。
     */
    public void undo() {
        if (mDrawableList.size() > 0) {
            CDrawable toUndo = mDrawableList.get(mDrawableList.size() - 1);
            mUndoList.add(toUndo);
            mDrawableList.remove(mDrawableList.size() - 1);
            if (toUndo instanceof CTransform) {
                CTransform t = (CTransform) toUndo;
                t.getDrawable().removeTransform(t);
            }

            invalidate();
        }
    }

    /**
     * 恢复上一次撤消的操作。
     */
    public void redo() {
        if (mUndoList.size() > 0) {
            CDrawable toRedo = mUndoList.get(mUndoList.size() - 1);
            mDrawableList.add(toRedo);
            mDrawableList.addAll(toRedo.getTransforms());
            mUndoList.remove(toRedo);
            if (toRedo instanceof CTransform) {
                CTransform t = (CTransform) toRedo;
                t.getDrawable().addTransform(t);
            }

            invalidate();
        }
    }

    /**
     * 清除画布，删除在画布上绘制的所有内容。
     * 警告：调用此方法之前，请用户确认，因为此操作无法撤消
     */
    public void cleanPage() {
        // remove everything from the list
        mDrawableList.clear();
        currentPath = null;
        mUndoList.clear();
        savePoint = -1;
        // request to redraw the canvas
        invalidate();
    }

    /**
     * 在画布上绘制图像
     *
     * @param x      location of the image
     * @param y      location of the image
     * @param width  the width of the image
     * @param height the height of the image
     * @param pic    the image itself
     */
    public void drawImage(int x, int y, int width, int height, Bitmap pic) {
        CBitmap bitmap = new CBitmap(pic, x, y);
        bitmap.setWidth(width);
        bitmap.setHeight(height);
        mDrawableList.add(bitmap);
        mUndoList.clear();
        invalidate();
    }

    /**
     * 获取到目前为止在画布上绘制的图像
     *
     * @return Bitmap of the canvas.
     */
    public Bitmap getCanvasBitmap() {
        // build drawing cache of the canvas, use it to create a new bitmap, then destroy it.
        buildDrawingCache();
        Bitmap mCanvasBitmap = Bitmap.createBitmap(getDrawingCache());
        destroyDrawingCache();

        // return the created bitmap.
        return mCanvasBitmap;
    }

    /**
     * 获取到目前为止在画布上绘制的bitmap图像，删除绘制对象周围的所有边距。
     *
     * @return Bitmap of the canvas, cropped.
     */
    public Bitmap getCroppedCanvasBitmap() {
        if (cropBounds == null) {
            //No pixels at all
            return null;
        }
        Bitmap mCanvasBitmap = getCanvasBitmap();

        Rect size = new Rect(cropBounds);
        if (size.left < 0) {
            size.left = 0;
        }
        if (size.top < 0) {
            size.top = 0;
        }
        if (size.right > mCanvasBitmap.getWidth()) {
            size.right = mCanvasBitmap.getWidth();
        }
        if (size.bottom > mCanvasBitmap.getHeight()) {
            size.bottom = mCanvasBitmap.getHeight();
        }

        Bitmap cropped = Bitmap.createBitmap(mCanvasBitmap, size.left, size.top, size.width(), size.height());
        return cropped;
    }

    /**
     * @return 返回绘制paint的颜色。默认值为Color.BLACK。
     */
    public int getColor() {
        return mColor;
    }

    /**
     * 设置画笔颜色
     *
     * @param mColor The new color.
     */
    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    /**
     * @return 返回背景颜色。默认值为Color.WHITE。
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * 设置背景颜色
     *
     * @param mBackgroundColor The new background color.
     */
    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    /**
     * @return 背景色模式. 默认为 BACKGROUND_STYLE_BLANK.
     */
    public int getBackgroundMode() {
        return mBackgroundMode;
    }

    /**
     * 设置背景显示模式
     * BACKGROUND_STYLE_BLANK,
     * BACKGROUND_STYLE_NOTEBOOK_PAPER,
     * BACKGROUND_STYLE_GRAPH_PAPER.
     *
     * @param mBackgroundMode
     */
    public void setBackgroundMode(int mBackgroundMode) {
        this.mBackgroundMode = mBackgroundMode;
        invalidate();
    }

    /**
     * 画笔样式。可以是Paint.Style.FILL，
     * Paint.Style.STROKE或Paint.Style.FILL_AND_STROKE。默认值为Paint.Style.STROKE。
     *
     * @return
     */
    public Paint.Style getStyle() {
        return mStyle;
    }

    /**
     * 设置画笔样式
     *
     * @param mStyle The new drawing style. Can be Can be FILL, STROKE, or FILL_AND_STROKE.
     */
    public void setStyle(Paint.Style mStyle) {
        this.mStyle = mStyle;
    }

    /**
     * @return 返回绘制线的宽度
     */
    public float getSize() {
        return mSize;
    }

    /**
     * 设置线段宽度，默认5
     *
     * @param mSize The new width for the line.
     */
    public void setSize(float mSize) {
        this.mSize = mSize;
    }

    /**
     * @return 交互方式。默认值为DRAW_MODE。
     */
    public int getInteractionMode() {
        return mInteractionMode;
    }

    /**
     * 交互模式的设置。可以是DRAW_MODE，SELECT_MODE，ROTATE_MODE或LOCKED_MODE。
     *
     * @param interactionMode
     */
    public void setInteractionMode(int interactionMode) {

        // if the value passed is not any of the flags, set the library to locked mode
        if (interactionMode > LOCKED_MODE)
            interactionMode = LOCKED_MODE;
        else if (interactionMode < DRAW_MODE)
            interactionMode = LOCKED_MODE;

        this.mInteractionMode = interactionMode;
        invalidate();
    }

    /**
     * @return 按插入顺序列出所有CDrawables的列表。
     */
    public List<CDrawable> getDrawablesList() {
        return mDrawableList;
    }

    /**
     * 表示列表中的所有CDrawables已保存。
     */
    public void markSaved() {
        savePoint = mDrawableList.size() - 1;
    }

    /**
     * @return 如果在最后一次调用markSaved（）之后没有任何新操作，则返回true。
     */
    public boolean isSaved() {
        return savePoint < mDrawableList.size();
    }

    /**
     * @return 在最后一次调用markSaved（）之后添加的所有CDrawables的列表。
     */
    public List<CDrawable> getUnsavedDrawablesList() {
        if (savePoint >= mDrawableList.size()) {
            //Some things were deleted.
            return new ArrayList<>();
        }
        return mDrawableList.subList(savePoint + 1, mDrawableList.size());
    }

    /**
     * 删除在最后一次调用markSaved（）之后添加的所有CDrawable。
     * 不触发DeletionConfirmationListener。
     */
    public void revertUnsaved() {
        List<CDrawable> unsaved = new ArrayList<>(getUnsavedDrawablesList());
        for (CDrawable d :
                unsaved) {
            deletionConfirmed(d);
        }
    }

    /**
     * 将最后插入的CDrawable标记为所选对象。
     */
    public void selectLastDrawn() {
        if (mDrawableList.isEmpty()) {
            return;
        }

        ListIterator<CDrawable> li = mDrawableList.listIterator(mDrawableList.size());
        while (li.hasPrevious()) {
            CDrawable d = li.previous();
            if (d instanceof CTransform) {
                continue;
            }
            selected = d;
            break;
        }
        invalidate();
    }

    /**
     * @return 当前选择的CDrawable。
     */
    public CDrawable getSelection() {
        return selected;
    }

    /**
     * 取消所有选择。不会选择任何对象。
     */
    public void deSelect() {
        selected = null;
        invalidate();
    }

    /**
     * 删除当前选择的对象。此后将不会选择任何对象。
     */
    public void deleteSelection() {
        if (selected == null) {
            return;
        }
        deleteDrawable(selected);
        selected = null;
    }

    /**
     * 删除特定的CDrawable，并在需要时进行确认。
     *
     * @param drawable The object to remove.
     */
    public void deleteDrawable(CDrawable drawable) {
        if (drawable == null) {
            return;
        }
        if (deletionConfirmationListener != null) {
            try {
                deletionConfirmationListener.confirmDeletion(drawable);
            } catch (Exception e) {
                //Do nothing
            }
            return;
        }
        deletionConfirmed(drawable);
    }

    /**
     * 删除特定的CDrawable，而不进行确认。
     * 必须由DeletionConfirmationListener.confirmDeletion（）调用才能完成删除。
     *
     * @param drawable The object to remove.
     */
    public void deletionConfirmed(CDrawable drawable) {
        if (drawable == null) {
            return;
        }
        ArrayList<CDrawable> toDelete = new ArrayList<>();
        toDelete.add(drawable);
        toDelete.addAll(drawable.getTransforms());
        for (CDrawable d :
                toDelete) {
            if (mDrawableList.indexOf(d) <= savePoint) {
                savePoint--;
            }
            mDrawableList.remove(d);
        }
        mUndoList.add(drawable);
        if (deletionListener != null) {
            try {
                deletionListener.deleted(drawable);
            } catch (Exception e) {
                //Do nothing
            }
        }
        invalidate();
    }

    /**
     * 设置程序的“删除”图标。默认值为android.R.drawable.ic_menu_delete。
     *
     * @param newIcon The new delete icon.
     */
    public void setDeleteIcon(Bitmap newIcon) {
        deleteIcon = newIcon;
    }

    /**
     * 删除事件侦听器的设置。请参阅观察者模式。
     *
     * @param newListener The listener for any deletion event.
     */
    public void setDeletionListener(DeletionListener newListener) {
        deletionListener = newListener;
    }

    /**
     * 删除确认监听器设置。
     *
     * @param newListener The listener for any deletion confirmation request.
     */
    public void setDeletionConfirmationListener(DeletionConfirmationListener newListener) {
        deletionConfirmationListener = newListener;
    }

    /**
     * @return 返回选择器的颜色，
     * 默认是Color.DKGRAY.
     */
    public int getSelectionColor() {
        return selectionColor;
    }

    /**
     * 设置选择器颜色
     *
     * @param selectionColor The new selection rectangle color.
     */
    public void setSelectionColor(int selectionColor) {
        this.selectionColor = selectionColor;
    }

    public interface ScaleRotateListener {

        /**
         * 如果要让SignatureComponent 组件处理旋转和调整大小，则返回true。
         *
         * @return 如果旋转将由SignatureComponent 组件处理，则为true。为false即可忽略手势
         */
        boolean startRotate();

        /**
         * 完成旋转手势时调用。
         */
        void endRotate();
    }

    /**
     * 此接口必须由您的删除事件监听器实现。
     */
    public interface DeletionListener {
        /**
         * 删除CDrawable之后将调用此方法。
         */
        void deleted(CDrawable drawable);
    }

    /**
     * 此接口必须由侦听器实现以确认删除。如果得到确认，
     * 则侦听器必须调用confirmDeletion（CDrawable）。
     */
    public interface DeletionConfirmationListener {
        /**
         * 在删除CDrawable之前将调用此方法，以确认删除。
         * 如果允许删除，则调用FabricView.deletionConfirmed（CDrawable）。
         */
        void confirmDeletion(CDrawable drawable);
    }
}
