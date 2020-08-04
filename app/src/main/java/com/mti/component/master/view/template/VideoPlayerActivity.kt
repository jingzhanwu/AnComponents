package com.mti.component.master.view.template

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videoplayer.ijk.IjkPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import kotlinx.android.synthetic.main.act_common_video_player.*

/**
 * @anthor created by jzw
 * @date 2020/5/29
 * @change
 * @describe 视频播放页面
 * 专门进行视频的播放
 **/
class VideoPlayerActivity : BaseActivity() {

    private var videoUrl: String = ""

    override fun getLayoutId(): Int {
        return R.layout.act_common_video_player
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("视频播放")
        showToolbarBackView()
        requestPermission()
        videoUrl = intent.getStringExtra("url")
        initVideoView()
        playVideoView(videoUrl)
    }

    private fun initVideoView() {
        var controller = StandardVideoController<VideoView<IjkPlayer>>(this)
        controller.setTitle("播放视频")
        videoPlayView.setVideoController(controller)
    }

    /**
     * videoView 播放视频
     */
    private fun playVideoView(url: String) {
        videoPlayView.setUrl(url)
        videoPlayView.start()
    }

    /**
     * 权限申请
     */
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(
                    Manifest.permission.SYSTEM_ALERT_WINDOW
            )
            ActivityCompat.requestPermissions(this, permission, 100)
        }
    }

    override fun onResume() {
        super.onResume()
        videoPlayView.resume()
    }

    override fun onPause() {
        super.onPause()
        videoPlayView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayView.release()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!videoPlayView.onBackPressed()) {
            super.onBackPressed()
        }
    }

}