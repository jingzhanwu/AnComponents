package com.mti.component.master.view.template

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.mti.component.common.entry.ImageData
import com.mti.component.common.image.ImageAddComponent
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.util.toast
import com.mti.component.media.audio.internal.AudioFileFunc
import com.mti.component.media.entry.AudioLabelEntry
import com.mti.component.media.video.VideoRecordComponent
import kotlinx.android.synthetic.main.act_alarm_feedback_template.*

/**
 * @anthor created by jzw
 * @date 2020/6/11
 * @change
 * @describe 警情反馈模板
 **/
class AlarmFeedbackTemplateActivity : BaseActivity() {

    private val TAG: String = AlarmFeedbackTemplateActivity::class.java.simpleName

    override fun getLayoutId() = R.layout.act_alarm_feedback_template

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("反馈模板")
        showToolbarBackView()
        initImage()
        initVideo()
        initAudio()
        btnFeedback.setOnClickListener {
            submitFeedback()
        }
    }


    private fun initImage() {
        imageComponent.enableEditMode(true)
                .showPlayVideoButton(false)
                .setAddDrawable(getDrawable(R.mipmap.ic_image_add))
                .setNewData(ArrayList())
                .setOnImageComponentItemListener(object : ImageAddComponent.OnImageComponentItemListener {
                    override fun onAddViewClick() {
                        choosePic()
                    }

                    override fun onItemClick(position: Int) {

                    }

                    override fun onItemDeleteClick(position: Int) {
                        imageComponent.deleteItem(position)
                    }
                })
    }

    private fun choosePic() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(true)
                .compress(true)
                .maxSelectNum(8)
                .forResult(100)


    }

    private fun viewPicture(currentIndex: Int) {
        val data = imageComponent.imageData
        val urlList = data?.map {
            it.url
        }?.toMutableList()
    }

    private lateinit var audioExt: String
    private lateinit var audioName: String

    private fun initAudio() {
        audioComponent.init(this, ArrayList()) {
            //点击事件，播放控制
            val item = audioComponent.getItemEntry(it)
            Log.d(TAG, "正在播放Audio=：${item.tagValue}")
            audioComponent.playAudio(item.tagValue)
        }

        //设置录制按钮监听
        ivAudioRecord.setOnClickListener {
            //开始录制
            audioExt = "mti_" + System.currentTimeMillis() + ".wav"
            audioName = AudioFileFunc.getAudioDir(this@AlarmFeedbackTemplateActivity) + "/" + audioExt

            Log.d(TAG, "开始录制：$audioName")
            audioComponent.recordAudio(audioName, 300)
        }
    }

    /**
     * 初始化video 显示view
     */
    private fun initVideo() {
        videoImageComponent.enableEditMode(true)
                .showPlayVideoButton(true)
                .setAddDrawable(getDrawable(R.mipmap.ic_image_add))
                .setNewData(ArrayList())
                .setOnImageComponentItemListener(object : ImageAddComponent.OnImageComponentItemListener {
                    override fun onAddViewClick() {
                        val intent = Intent(this@AlarmFeedbackTemplateActivity, VideoRecordComponent::class.java)
                        intent.putExtra("maxDuration", 15)
                        startActivityForResult(intent, 200)
                    }

                    override fun onItemClick(position: Int) {

                    }

                    override fun onItemDeleteClick(position: Int) {
                        videoImageComponent.deleteItem(position)
                    }

                    override fun onPlayViewClick(position: Int) {
                        val intent = Intent(this@AlarmFeedbackTemplateActivity, VideoPlayerActivity::class.java)
                        intent.putExtra("url", videoImageComponent.getData(position).fileName)
                        startActivity(intent)
                    }
                })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 300) {
                handleAudioData()
            } else {
                data?.let {
                    when (requestCode) {
                        100 -> handleImageData(it)
                        200 -> handleVideoData(it)
                    }
                }
            }
        }
    }

    private fun handleImageData(data: Intent) {
        val selectList = PictureSelector.obtainMultipleResult(data)
        if (!selectList.isNullOrEmpty()) {
            var list = arrayListOf<ImageData>()
            selectList.filter { it.mimeType != 2 }
                    .forEach {
                        val url = if (it.isCompressed) it.compressPath else it.path
                        println("选择的图片：$url")
                        list.add(ImageData(url, url))
                    }
            imageComponent.addData(list)
        }
    }

    /**
     * 处理录制视频的结果返回
     */
    private fun handleVideoData(data: Intent) {
        //结果类型,1=图片，2=视频
        val type = data?.getIntExtra("type", VideoRecordComponent.TYPE_VIDEO);
        //录制完成视频的本地存储路径
        val videoPath = data?.getStringExtra("path");
        //视频录制时为缩略图地址，图片时为图片本地路径
        val imagePath = data?.getStringExtra("imagePath");

        print("录制的视频：$videoPath,\n缩略图地址：$imagePath,\n类型：$type")

        if (type == VideoRecordComponent.TYPE_VIDEO) {
            //视频
            videoImageComponent.addData(ImageData(imagePath, videoPath))
        }
    }

    /**
     * 处理录制音频结果返回
     */
    private fun handleAudioData() {
        //录制完成，将结果添加到tag list上
        if (audioExt != null && audioName != null) {
            val audioLabel = AudioLabelEntry(audioExt)
            audioLabel.tagValue = audioName
            audioComponent.addData(audioLabel)

            println("录音：$audioName")
        }
    }

    /**
     * 提交反馈信息
     */
    private fun submitFeedback() {
        val images = imageComponent.imageData
        val video = videoImageComponent.imageData;
        val audio = audioComponent.audioData
        if (images.isEmpty() && video.isEmpty() && audio.isEmpty()) {
            toast("请至少选择一种反馈内容")
            return
        }
        val content = input.inputText

        toast("反馈成功")
    }
}