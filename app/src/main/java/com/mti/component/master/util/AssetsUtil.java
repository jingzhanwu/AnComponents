package com.mti.component.master.util;

import android.content.Context;

import java.io.InputStream;

/**
 * @anthor created by jzw
 * @date 2020/5/21
 * @change
 * @describe Assets 目录文件操作工具类
 **/
public class AssetsUtil {
    /**
     * 获取assets目录下 指定文件的文本内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getAssetsFileContent(Context context, String fileName) {
        String result = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            result = new String(buffer, "utf8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
