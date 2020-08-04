package com.mti.component.master.view.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videoplayer.ijk.IjkPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.media.video.VideoRecordComponent
import kotlinx.android.synthetic.main.act_video_record.*

/**
 * @anthor created by jzw
 * @date 2020/5/27
 * @change
 * @describe 视频录制组件demo
 **/
class VideoRecordComponentActivity : BaseActivity() {

    private val videoRecordCode: Int = 100
    override fun getLayoutId(): Int {
        return R.layout.act_video_record
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("视频录制")
        showToolbarBackView()
        btnVideoRecord.setOnClickListener(View.OnClickListener { startRecordPage() })
        initVideoView()
        setMarkdownData("VideoRecordComponent.html", webView)
        playVideoView("https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4")
    }

    private fun initVideoView() {
        videoView.setUrl("https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4")
        var controller = StandardVideoController<VideoView<IjkPlayer>>(this)
        controller.setTitle("VideoRecordComponent")
        videoView.setVideoController(controller)
    }

    /**
     * videoView 播放视频
     */
    private fun playVideoView(url: String) {
        videoView.setUrl(url)
        videoView.start()
    }

    private fun startRecordPage() {
        var intent = Intent(this, VideoRecordComponent::class.java)
        //设置最大录制时长，单位为秒，这里设置15秒
        intent.putExtra("maxDuration", 15)
        //启动录制
        startActivityForResult(intent, videoRecordCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == videoRecordCode && resultCode == Activity.RESULT_OK) {
            //结果类型,1=图片，2=视频
            var type = data?.getIntExtra("type", VideoRecordComponent.TYPE_VIDEO);
            //录制完成视频的本地存储路径
            var videoPath = data?.getStringExtra("path");
            //视频录制时为缩略图地址，图片时为图片本地路径
            var imagePath = data?.getStringExtra("imagePath");

            print("视频地址：$videoPath,\n图片地址：$imagePath,\n类型：$type")

            if (type == VideoRecordComponent.TYPE_VIDEO) {
                playVideoView(videoPath!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.release()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!videoView.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
