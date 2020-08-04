package com.mti.component.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @anthor created by jzw
 * @date 2020/5/12
 * @change
 * @describe 给整个decorView 加水印文字
 **/
public class WaterMarkComponent {

    /**
     * Activity中显示水印，在onCreate的setContentView方法后调用
     * 多个界面显示水印可在BaseActivity中统一调用
     *
     * @param activity
     * @param content  水印文字内容
     */
    public static void showWaterMarkView(Activity activity, String content) {
        ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        View frameView = LayoutInflater.from(activity).inflate(R.layout.layout_watermark, null);
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //屏幕宽高像素值
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        ImageView imageView = frameView.findViewById(R.id.ivWm);
        imageView.setImageDrawable(getMarkTextBitmapDrawable(activity, content, width, height));
        //可对水印布局进行初始化操作
        rootView.addView(frameView);
    }

    /**
     * 获得文字水印的Drawable图片
     *
     * @param width
     * @param height
     * @return
     */
    private static Drawable getMarkTextBitmapDrawable(Context context, String text, int width, int height) {
        Bitmap bitmap = getMarkTextBitmap(context, text, width, height);
        if (bitmap != null) {
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            drawable.setDither(true);
            return drawable;
        }

        return null;
    }

    private static Bitmap getMarkTextBitmap(Context context, String text, int width, int height) {

        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.getResources().getDisplayMetrics());
        float inter = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22f, context.getResources().getDisplayMetrics());

        int sideLength;
        if (width > height) {
            sideLength = (int) Math.sqrt(2 * (width * width));
        } else {
            sideLength = (int) Math.sqrt(2 * (height * height));
        }
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        Rect rect = new Rect();
        paint.setTextSize(textSize);
        //获取文字长度和宽度
        paint.getTextBounds(text, 0, text.length(), rect);
        //文字长和宽
        int strwid = rect.width();
        int strhei = rect.height();

        Bitmap markBitmap = null;
        try {
            markBitmap = Bitmap.createBitmap(sideLength, sideLength, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(markBitmap);
            //创建透明画布
            canvas.drawColor(Color.TRANSPARENT);

            paint.setColor(Color.BLACK);
            paint.setAlpha((int) (0.2 * 255f));
            // 获取跟清晰的图像采样
            paint.setDither(true);
            paint.setFilterBitmap(true);

            //先平移，再旋转才不会有空白，使整个图片充满
            if (width > height) {
                canvas.translate(width - sideLength - inter, sideLength - width + inter);
            } else {
                canvas.translate(height - sideLength - inter, sideLength - height + inter);
            }

            //将该文字图片逆时针方向倾斜45度
            canvas.rotate(-45);

            //x 和y 坐标 控制水平和垂直的间距，我这里 分别给了1.5倍
            int i = 0;
            while (i <= sideLength) {
                int count = 0;
                int j = 0;
                while (j <= sideLength) {
                    if (count % 2 == 0) {
                        canvas.drawText(text, i * 1.5f, j * 1.5f, paint);
                    } else {
                        //偶数行进行错开
                        canvas.drawText(text, (i + strwid / 2) * 1.5f, j * 1.5f, paint);
                    }
                    j = (int) (j + inter + strhei);
                    count++;
                }
                i = (int) (i + strwid + inter);
            }
            canvas.save();
        } catch (OutOfMemoryError e) {
            if (markBitmap != null && !markBitmap.isRecycled()) {
                markBitmap.recycle();
                markBitmap = null;
            }
        }

        return markBitmap;
    }
}
