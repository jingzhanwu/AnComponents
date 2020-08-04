package com.mti.component.media.player;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.dueeeke.videoplayer.player.VideoView;


/**
 * 悬浮播放
 * Created by jignzhanwu on 2018/3/30.
 */

public class PIPManager {

    private static PIPManager instance;
    private VideoView mVideoView;
    private FloatView floatView;
    private FloatController mFloatController;
    private boolean isShowing;
    //    private KeyReceiver mKeyReceiver;
    private int mPlayingPosition = -1;
    private Class mActClass;
//    private MyVideoListener mMyVideoListener = new MyVideoListener() {
//        @Override
//        public void onComplete() {
//            super.onComplete();
//            reset();
//        }
//    };


    private PIPManager(Context context) {
        mVideoView = new VideoView(context);
//        mVideoView.setVideoListener(mMyVideoListener);
//        mKeyReceiver = new KeyReceiver();
        mFloatController = new FloatController(context);
//        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        MyApplication.getInstance().registerReceiver(mKeyReceiver, homeFilter);
        floatView = new FloatView(context, 0, 0);
    }

    public static PIPManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PIPManager.class) {
                if (instance == null) {
                    instance = new PIPManager(context);
                }
            }
        }
        return instance;
    }

    public VideoView getVideoView() {
        return mVideoView;
    }

    public void startFloatWindow() {
        if (isShowing) return;
        removePlayerFormParent();
        mFloatController.setPlayState(mVideoView.getCurrentPlayState());
        mFloatController.setPlayerState(mVideoView.getCurrentPlayerState());
        mVideoView.setVideoController(mFloatController);
        floatView.addView(mVideoView);
        floatView.addToWindow();
        isShowing = true;
    }

    public void stopFloatWindow() {
        if (!isShowing) return;
        floatView.removeFromWindow();
        removePlayerFormParent();
        isShowing = false;
    }

    private void removePlayerFormParent() {
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mVideoView);
        }
    }

    public void setPlayingPosition(int position) {
        this.mPlayingPosition = position;
    }

    public int getPlayingPosition() {
        return mPlayingPosition;
    }

    public void pause() {
        if (isShowing) return;
        mVideoView.pause();
    }

    public void resume() {
        if (isShowing) return;
        mVideoView.resume();
    }

    public void reset() {
        if (isShowing) return;
        removePlayerFormParent();
        mVideoView.setVideoController(null);
        mVideoView.release();
        mPlayingPosition = -1;
        mActClass = null;
    }

    public boolean onBackPress() {
        return !isShowing && mVideoView.onBackPressed();
    }

    public boolean isStartFloatWindow() {
        return isShowing;
    }

    /**
     * 显示悬浮窗
     */
    public void setFloatViewVisible() {
        if (isShowing) {
            mVideoView.resume();
            floatView.setVisibility(View.VISIBLE);
        }
    }

    public void setActClass(Class cls) {
        this.mActClass = cls;
    }

    public Class getActClass() {
        return mActClass;
    }

}
