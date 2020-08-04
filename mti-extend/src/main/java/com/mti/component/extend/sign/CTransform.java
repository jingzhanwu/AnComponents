package com.mti.component.extend.sign;

import android.graphics.Matrix;

/**
 * @anthor created by jignzhanwu
 * @date 2020/6/1
 * @change
 * @describe 可变化的绘制对象基类
 **/
public abstract class CTransform extends CDrawable {

    private CDrawable mDrawable;

    /**
     * @return The drawable object that this transform affects.
     */
    public CDrawable getDrawable() {
        return mDrawable;
    }

    /**
     * Setter for the drawable.
     *
     * @param drawable The new drawable object that this transform affects.
     */
    public void setDrawable(CDrawable drawable) {
        this.mDrawable = drawable;
    }

    /**
     * This method will use the provided matrix to transform the drawable.
     *
     * @param matrix The matrix to use for the transform.
     */
    public abstract void applyTransform(Matrix matrix);
}
