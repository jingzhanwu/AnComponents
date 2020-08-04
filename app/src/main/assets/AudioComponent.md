
## 如何使用

### 音频录制与播放组件

#### 1.布局使用

```
<com.mti.component.media.audio.AudioComponent
                    android:id="@+id/audioComponent"
                    style="@style/match_width" />
```

#### 2.添加监听事件
初始化并添加监听，监听提供两个回调方法，分别是onItemDelete删除监听和onItemClick点击监听。
```
    override fun initViews(savedInstanceState: Bundle?) {
        val list = listOf<String>("测试录音标签一", "测试录音标签二", "测试录音标签三", "测试录音标签四")

        val data = list.map { AudioLabelEntry("$it") }.toList()
        //初始化数据及添加监听事件
        audioComponent.init(this, data, this)
    }
    
    //删除监听回调
    override fun onItemDelete(position: Int) {
    }

    //点击监听回调
    override fun onItemClick(position: Int) {
    }    
    
```

#### 3.音频录制与播放
提供录制方法audioComponent.recordAudio(audioName, 100)和播放方法audioComponent.playAudio(item.tagValue)。
```
    override fun initViews(savedInstanceState: Bundle?) {
        val list = listOf<String>("红色", "黑色", "花边色", "深蓝色",
                "白色", "玫瑰红色", "紫黑紫兰色", "葡萄红色", "橘红色",
                "绿色", "彩虹色", "牡丹色")

        val data = list.map { AudioLabelEntry("$it") }.toList()
        //初始化数据及添加监听事件
        audioComponent.init(this, data, this)
        
        //点击录制
        btnAudioRecord.setOnClickListener {
            val ext = "mti_" + System.currentTimeMillis() + ".wav"
            val audioName: String = AudioFileFunc.getAudioDir(this).toString() + File.separator + ext
            audioComponent.recordAudio(audioName, 100)

            val tagEntry = AudioLabelEntry(ext)
            tagEntry.tagValue = audioName
            audioComponent.addData(tagEntry)
        }
    }
    
    //点击监听回调
    override fun onItemClick(position: Int) {
        toast("点击了第${position}个位置")
        val item = audioComponent.getItemEntry(position)
        Log.d("AudioComponent--page-", "item.tagValue${item.tagValue}")
        //播放
        audioComponent.playAudio(item.tagValue)
    }    
    
```

