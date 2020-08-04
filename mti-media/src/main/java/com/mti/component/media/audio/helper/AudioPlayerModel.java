package com.mti.component.media.audio.helper;


import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;

import com.mti.component.media.audio.internal.record.AndroidAudioRecorder;
import com.mti.component.media.audio.internal.record.model.AudioChannel;
import com.mti.component.media.audio.internal.record.model.AudioSampleRate;
import com.mti.component.media.audio.internal.record.model.AudioSource;

import java.lang.ref.WeakReference;


/**
 * @company 上海道枢信息科技-->
 * @anthor created by jingzhanwu
 * @date 2018/3/14 0014
 * @change
 * @describe describe
 **/
public class AudioPlayerModel {
    /**
     * 音视频播放管理器
     */
    private PlayerManager.OnPlayerCallback mCallback;
    //    private Call<ResultArray<AudioEntry>> call;
    private boolean isRelease = false;
    private WeakReference<Activity> weakReference;

    public AudioPlayerModel(Activity activity) {
        this.weakReference=new WeakReference<>(activity);
    }


    /**
     * 播放录音
     *
     * @param audio
     */
    public void playAudio(String audio, PlayerManager.OnPlayerCallback callback) {

        mCallback = callback;
        if (!isNeedDownloadUrl(audio)) {
            //直接播放
            play(audio);
        } else {
            //先去下载，在播放
            getAudioById(audio);
        }
    }

    /**
     * 音频录制
     *
     * @param requestCode
     */
    public void recordAudio(String audioPath, int requestCode) {

       Activity activity= weakReference.get();
       if (activity==null){
           return;
       }
        AndroidAudioRecorder.with(activity)
                // Required
                .setFilePath(audioPath)
                .setColor(Color.parseColor("#651FFF"))
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)
                // Start recording
                .record();
    }





    /**
     * 根据录音id 获取录音存储地址
     *
     * @param id
     */
    private void getAudioById(String id) {
//        call = HttpManager.Get().getApiService(ApiService.class)
//                .getAudioById(id);
//        HttpManager.Get().request(call, new OnRequestListener<ResultArray<AudioEntry>>() {
//            @Override
//            public void onSuccess(ResultArray<AudioEntry> result) {
//                if (!isRelease) {
//
//                    if (result.getCode() != HttpURLConnection.HTTP_OK) {
//                        if (mCallback != null) {
//                            mCallback.onFaild(null);
//                        }
//                        PlayerManager.get().releasPlayer();
//                        return;
//                    }
//
//                    if (CollectionUtil.isEmpty(result.getData())) {
//                        if (mCallback != null) {
//                            mCallback.onFaild(null);
//                        }
//                        PlayerManager.get().releasPlayer();
//                        return;
//                    }
//                    String url = result.getData().get(0).getFileName();
//                    if (!url.startsWith("http:") && !url.startsWith("https:")) {
//                        url = UrlConfig.FILE_DOWNLOAD_URL + url;
//                    }
//                    //去播放录音
//                    play(url);
//
//                }
//            }
//
//            @Override
//            public void onFaild(int code, String msg) {
//                if (!isRelease) {
//                    if (mCallback != null) {
//                        mCallback.onFaild(null);
//                    }
//                    PlayerManager.get().releasPlayer();
//                }
//            }
//        });
    }

    /**
     * 播放录音
     */
    public void play(String audioUrl) {
        //播放
        PlayerManager.with().playAudio(audioUrl, mCallback);
    }

    /**
     * 判断录音 是否需要获取录音的播放地址，只有是录音id的时候
     * 才去下载获取录音播放地址
     *
     * @param audio
     * @return
     */
    private boolean isNeedDownloadUrl(String audio) {
        String url = (TextUtils.isEmpty(audio) || audio.equals("null")) ? "" : audio;
        boolean isAudioFile = url.toLowerCase().endsWith(".wav") || url.toLowerCase().endsWith(".mp3");
        boolean isServerUrl = isAudioFile && (url.startsWith("http://") || url.startsWith("https://"));
        return isServerUrl || isAudioFile;
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return PlayerManager.get().isPlaying();
    }


    /**
     * 停止
     */
    public void stop(){
        if (isPlaying()){
            PlayerManager.get().stop();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        releaseAudioPlayer();
        weakReference.clear();
    }

    /**
     * 释放音频播放器
     */
    public void releaseAudioPlayer() {
        isRelease = true;
        PlayerManager.get().releasPlayer();
//        if (call != null) {
//            call.cancel();
//            call = null;
//        }
        if (mCallback != null) {
            mCallback = null;
        }
    }

}
