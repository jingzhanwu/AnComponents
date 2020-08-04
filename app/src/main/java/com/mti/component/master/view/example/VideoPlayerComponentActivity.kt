package com.mti.component.master.view.example

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videoplayer.ijk.IjkPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.mti.component.master.R
import com.mti.component.master.adapter.VideoPlayerAdapter
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.callback.OnAdapterItemClickListener
import com.mti.component.master.model.VideoEntry
import com.mti.component.media.player.PIPManager
import kotlinx.android.synthetic.main.act_video_player.*
import kotlinx.android.synthetic.main.act_video_record.webView

/**
 * @anthor created by jzw
 * @date 2020/5/29
 * @change
 * @describe 视频播放组件demo
 **/
class VideoPlayerComponentActivity : BaseActivity() {

    /**
     * 悬浮窗播放控制器
     */
    private lateinit var mPIPManager: PIPManager
    private lateinit var controller: StandardVideoController<*>

    override fun getLayoutId(): Int {
        return R.layout.act_video_player
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("视频播放")
        showToolbarBackView()
        requestPermission()
        initVideoView()
        btnLocalVideo.setOnClickListener(View.OnClickListener { scanLocalVideo() })
        setMarkdownData("VideoPlayerComponent.html", webView)
    }

    /**
     * 初始化videoView
     * 如果已经有悬浮窗播放，则直接播放，否则添加到容器播放
     */
    private fun initVideoView() {
        mPIPManager = PIPManager.getInstance(this)
        controller = StandardVideoController<VideoView<IjkPlayer>>(this)
        var videoView = mPIPManager.videoView
        videoView.setVideoController(controller)
        if (mPIPManager.isStartFloatWindow) {
            mPIPManager.stopFloatWindow()
            controller.setPlayerState(videoView.currentPlayerState)
            controller.setPlayState(videoView.currentPlayState)
        } else {
            mPIPManager.actClass = VideoPlayerComponentActivity::class.java
            controller.setTitle("悬浮窗播放")
            videoView.setUrl("https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4")
            videoView.start()
            videoViewContainer.addView(videoView)
        }
    }

    /**
     * videoView 悬浮窗播放视频
     */
    private fun playFloatVideo(url: String, title: String) {
        mPIPManager.apply {
            if (isStartFloatWindow) {
                stopFloatWindow()
            }
            videoView.release()
            videoView.setUrl(url)
            videoView.setVideoController(controller)
            videoView.start()
        }
        controller.setTitle(title ?: "悬浮窗播放")
//        mPIPManager.startFloatWindow()
    }

    /**
     * 扫描本地视频
     */
    private fun scanLocalVideo() {
        var list = ArrayList<VideoEntry>()
        val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
        )
        val cursor = this.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val title = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                val size = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                val duration = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))

                val video = VideoEntry(title, path, size, duration)

                println("本地视频：${video.url}")
                list.add(video)
            }
            it.close()
        }

        setVideoData(list)
    }

    /**
     * 设置视频列表数据
     */
    private fun setVideoData(data: List<VideoEntry>) {
        if (data.isEmpty()) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            val adapter = VideoPlayerAdapter(this, data)
            recyclerView.layoutManager = object : LinearLayoutManager(this) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            adapter.setOnItemClickListener(object : OnAdapterItemClickListener<VideoEntry> {
                override fun onItemClick(position: Int, item: VideoEntry) {
                    playFloatVideo(item.url, item.name)
                }
            })
            recyclerView.adapter = adapter
        }
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
        mPIPManager.resume()
    }

    override fun onPause() {
        super.onPause()
        mPIPManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPIPManager.reset()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!mPIPManager.onBackPress()) {
            super.onBackPressed()
        }
    }
}