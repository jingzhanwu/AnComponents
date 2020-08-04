package com.mti.component.media.audio.internal;

import android.content.Context;
import android.content.res.Resources;

public class ErrorCode {
    public final static int SUCCESS = 1000;
    public final static int E_NOSDCARD = 1001;
    public final static int E_STATE_RECODING = 1002;
    public final static int E_UNKOWN = 1003;


    public static String getErrorInfo(Context vContext, int vType) throws Resources.NotFoundException {
        switch (vType) {
            case SUCCESS:
                return "录制成功";
            case E_NOSDCARD:
                return "没有SD卡";
            case E_STATE_RECODING:
                return "正在录制";
            case E_UNKOWN:
            default:
                return "未知错误";

        }
    }

}