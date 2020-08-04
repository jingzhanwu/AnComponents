# VideoPlayerComponent 视频播放
## 如何使用
#### 1.功能特点
* 滑动调节播放进度，声音，亮度
* 调整显示比例
* 倍速播放
* 列表小窗悬浮播放
* 多路播放器同时播放
* Android8.0 画中画
* 预加载，播放缓存
* 视频截图

#### 1.布局使用
注意：一定要指定宽高
```
 <com.dueeeke.videoplayer.player.VideoView
            android:id="@+id/videoView"
            android:layout_width="match_parent"
            android:layout_height="260dp"
            android:layout_marginLeft="@dimen/size_10"
            android:layout_marginTop="@dimen/size_10" />
```
#### 2.代码调用
##### 第一步，初始化播放器，设置播放controller，标题等
```
private fun initVideoView() {
        videoView.setUrl("https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4")
        var controller = StandardVideoController<VideoView<IjkPlayer>>(this)
        controller.setTitle("VideoRecordComponent")
        videoView.setVideoController(controller)
    }
```

##### 第二步，播放指定url的视频，本地与网络都支持
```
   /**
     * videoView 播放视频
     */
    private fun playVideoView(url: String) {
        videoView.setUrl(url)
        videoView.start()
    }
```