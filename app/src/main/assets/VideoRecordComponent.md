# VideoRecordComponent 短视频录制
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