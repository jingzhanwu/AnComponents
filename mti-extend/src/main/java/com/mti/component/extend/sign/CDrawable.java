package com.mti.component.extend.sign;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;


/**
 * @anthor created by jignzhanwu
 * @date 2020/6/1
 * @change
 * @describe 可绘制的顶层 对象，所有绘制在画布上的对象都继承自该类，
 * 它定义了绘制的基础信息和方法，包括绘制与撤销
 **/
public abstract class CDrawable {
    private int id;
    private static int nextId = 0;

    private int x, y, height, width;
    private Paint mPaint;
    private List<CTransform> mTransforms = new ArrayList<>();
    private Rect lastBounds = null;

    /**
     * 如果你调用了该构造函数创建了一个对象, 则必须调用以下方法：
     * setXCoord(), setYCoord(), setHeight(),
     * setWidth()，setPaint().
     */
    public CDrawable() {
        id = generateNextId();
    }

    /**
     * 如果你调用了该构造函数创建了一个对象, 则必须调用以下方法：
     * setHeight() and setWidth().
     *
     * @param x     The X coordinate where the object will be drawn.
     * @param y     The Y coordinate where the object will be drawn.
     * @param paint The style and color to paint this object.
     */
    public CDrawable(int x, int y, Paint paint) {
        id = generateNextId();
        this.x = x;
        this.y = y;
        this.mPaint = paint;
    }

    private static int generateNextId() {
        return nextId++;
    }

    /**
     * @return The incremental ID for this object. Is unique only in the current execution and not
     * globally.
     */
    public int getId() {
        return id;
    }

    public Paint getPaint() {
        return mPaint;
    }

    /**
     * 设置新的paint对象
     */
    public void setPaint(Paint p) {
        mPaint = p;
    }

    /**
     * Setter for the height.
     *
     * @param height The new height of this object.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return The current height of this object.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the width.
     *
     * @param width The new width of this object.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return The current width of this object.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The current "x" position of this object.
     */
    public int getXcoords() {
        return x;
    }

    /**
     * @return The current "y" position of this object.
     */
    public int getYcoords() {
        return y;
    }

    /**
     * Setter for the "x" (horizontal from the left) position.
     *
     * @param x The new "x" position of this object.
     */
    public void setXcoords(int x) {
        this.x = x;
    }

    /**
     * Setter for the "y" (vertical from the top) position.
     *
     * @param y The new "y" position of this object.
     */
    public void setYcoords(int y) {
        this.y = y;
    }

    /**
     * 此功能用于在画布上绘制当前对象。子类必须实现它。
     *
     * @param canvas The canvas to draw on.
     */
    public abstract void draw(Canvas canvas);

    /**
     * 计算边界
     *
     * @return 返回该对象在画布上的位置rect
     */
    public Rect computeBounds() {
        RectF bounds = new RectF(x, y, x + width, y + height);
        Matrix m = new Matrix();
        for (CTransform t :
                mTransforms) {
            t.applyTransform(m);
        }
        m.mapRect(bounds);
        lastBounds = new Rect();
        bounds.round(lastBounds);
        return lastBounds;
    }

    public Rect getLastBounds() {
        if (lastBounds == null) {
            int x = getXcoords();
            int y = getYcoords();
            int r = x + getWidth();
            int b = y + getHeight();
            lastBounds = new Rect(x, y, r, b);
        }
        return lastBounds;
    }

    public boolean hasTransforms() {
        return !mTransforms.isEmpty();
    }

    /**
     * 取消转换。请小心，因为所有其他转换仍在堆栈中。
     * 如果您不是从堆栈的顶部开始，则此*可能会产生奇怪的结果。
     *
     * @param transform The transform to cancel.
     */
    public void removeTransform(CTransform transform) {
        mTransforms.remove(transform);
    }

    /**
     * 将一个转换添加到此对象的顶部。
     *
     * @param transform The transform to add.
     */
    public void addTransform(CTransform transform) {
        mTransforms.add(transform);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof CDrawable)) {
            return false;
        }
        CDrawable other = (CDrawable) obj;
        return other.getId() == this.getId() &&
                other.getXcoords() == this.getXcoords() &&
                other.getXcoords() == this.getXcoords() &&
                other.getYcoords() == this.getYcoords() &&
                other.getHeight() == this.getHeight() &&
                other.getWidth() == this.getWidth() &&
                other.getPaint() == this.getPaint();
    }

    /**
     * @return 返回该对象的所有转换的堆栈。
     */
    public List<CTransform> getTransforms() {
        return mTransforms;
    }
}
