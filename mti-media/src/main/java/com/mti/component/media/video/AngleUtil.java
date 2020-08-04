package com.mti.component.media.video;

/**
 *
 */
public class AngleUtil {
    /**
     * 获取手机传感器角度
     *
     * @param x
     * @param y
     * @return
     */
    public static int getSensorAngle(float x, float y) {
        if (Math.abs(x) > Math.abs(y)) {
            /**
             * 横屏倾斜角度比较大
             */
            if (x > 4) {
                /**
                 * 左边倾斜
                 */
                return 270;
            } else if (x < -4) {
                /**
                 * 右边倾斜
                 */
                return 90;
            } else {
                /**
                 * 倾斜角度不够大
                 */
                return 0;
            }
        } else {
            if (y > 7) {
                /**
                 * 左边倾斜
                 */
                return 0;
            } else if (y < -7) {
                /**
                 * 右边倾斜
                 */
                return 180;
            } else {
                /**
                 * 倾斜角度不够大
                 */
                return 0;
            }
        }
    }
}
