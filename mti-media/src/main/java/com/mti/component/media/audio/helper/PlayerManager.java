package com.mti.component.media.audio.helper;

import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/28
 * @change
 * @describe describe
 **/
public class PlayerManager {

    /**
     * 录制 和播放的文件地址
     */
    private String filePath;
    private MediaPlayer mediaPlayer;
    private OnPlayerCallback mCallback;

    private static PlayerManager mInstance;

    private PlayerManager() {
    }

    public static PlayerManager get() {
        if (mInstance == null) {
            mInstance = new PlayerManager();
        }
        return mInstance;
    }

    public static PlayerManager with() {
        return get();
    }

    public void setOnPlayerCallback(OnPlayerCallback callback) {
        mCallback = callback;
    }

    public PlayerManager playAudio(String audioPath, OnPlayerCallback callback) {
        if (TextUtils.isEmpty(audioPath)) {
            return mInstance;
        }
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (isPlaying()) {
            stop();
            return mInstance;
        }
        mCallback = callback;
        filePath = audioPath;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(filePath);
            if (mCallback != null) {
                mCallback.onPrepare(mediaPlayer);
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    if (mCallback != null) {
                        mCallback.onStart(mp, filePath);
                    }
                }
            });

            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if (mCallback != null) {
                        mCallback.onPlaying(mp);
                    }
                    return false;
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mCallback != null) {
                        mCallback.onStop();
                    }
                    releasPlayer();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (mCallback != null) {
                        mCallback.onFailed(mp);
                    }
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mInstance;
    }


    /**
     * 停止播放
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        if (mCallback != null) {
            mCallback.onStop();
        }
    }

    /**
     * 释放播放器资源
     */
    public void releasPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isPlaying() {
        if (mediaPlayer != null && (mediaPlayer.isPlaying() || mediaPlayer.isLooping())) {
            return true;
        }
        return false;
    }

    public interface OnPlayerCallback {
        void onPrepare(MediaPlayer mp);

        void onStart(MediaPlayer mp, String url);

        void onPlaying(MediaPlayer mp);

        void onStop();

        void onFailed(MediaPlayer mp);
    }

}
