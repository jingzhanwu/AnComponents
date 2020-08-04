# 多媒体组件module
---
主要包含以下组件
* 短视频录制组件
* 视频播放组件
* 音频录制组件
* 音频播放组件

# 一、VideoRecordComponent 短视频录制
## 如何使用
#### 1.功能特点
* 一建启动录制
* 自定义录制时长
* 视频预览

#### 2.代码调用
##### 第一步，启动视频录制，启动参数为最大录制时长，单位为秒
```
    private fun startRecordPage() {
        var intent = Intent(this, VideoRecordComponent::class.java)
        //设置最大录制时长，单位为秒，这里设置15秒
        intent.putExtra("maxDuration", 15)
        //启动录制
        startActivityForResult(intent, 100)
    }
```

##### 第二步，接收处理录制结果
```
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            //结果类型,1=图片，2=视频
            var type = data?.getIntExtra("type", VideoRecordComponent.TYPE_VIDEO);
            //录制完成视频的本地存储路径
            var videoPath = data?.getStringExtra("path");
            //视频录制时为缩略图地址，图片时为图片本地路径
            var imagePath = data?.getStringExtra("imagePath");

            print("视频地址：$videoPath,\n图片地址：$imagePath,\n类型：$type")
        }
    }
```

# 二、VideoPlayerComponent 视频播放
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