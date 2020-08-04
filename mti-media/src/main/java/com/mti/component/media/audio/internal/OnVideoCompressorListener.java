package com.mti.component.media.audio.internal;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/4/21
 * @change
 * @describe describe
 **/
public interface OnVideoCompressorListener {

     void onTranscodeProgress(double progress);

     void onTranscodeCompleted(String compressFilePath);

     void onTranscodeCanceled();

     void onTranscodeFailed(Exception exception);
}
