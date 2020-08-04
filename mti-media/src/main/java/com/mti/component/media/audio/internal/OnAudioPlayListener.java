package com.mti.component.media.audio.internal;

import android.media.MediaPlayer;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/4/21
 * @change
 * @describe describe
 **/
public interface OnAudioPlayListener {
     void onPrepare(MediaPlayer mp, int clickIndex);

     void onStart(MediaPlayer mp, String url, int clickIndex);

     void onStop(int clickIndex);

     void onFailed(MediaPlayer mp, int clickIndex);
}
