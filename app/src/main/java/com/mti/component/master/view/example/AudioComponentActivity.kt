package com.mti.component.master.view.example

import android.os.Bundle
import android.util.Log
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.util.toast
import com.mti.component.media.audio.OnAudioClickListener
import com.mti.component.media.audio.internal.AudioFileFunc
import com.mti.component.media.entry.AudioLabelEntry
import kotlinx.android.synthetic.main.activity_audio_component.*
import java.io.File

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 音频组件 适用于音频录制 播放
 **/
class AudioComponentActivity : BaseActivity(), OnAudioClickListener {

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("AudioComponent")
        showToolbarBackView()
        val list = listOf<String>("红色", "黑色", "花边色", "深蓝色",
                "白色", "玫瑰红色", "紫黑紫兰色", "葡萄红色", "橘红色",
                "绿色", "彩虹色", "牡丹色")

        val data = list.map { AudioLabelEntry("$it") }.toList()

        audioComponent.init(this, data, this)
        btnAudioRecord.setOnClickListener {
            val ext = "mti_" + System.currentTimeMillis() + ".wav"
            val audioName: String = AudioFileFunc.getAudioDir(this).toString() + File.separator + ext
            audioComponent.recordAudio(audioName, 100)

            val tagEntry = AudioLabelEntry(ext)
            tagEntry.tagValue = audioName
            audioComponent.addData(tagEntry)
        }
        setMarkdownData("AudioComponent.html", webView)
    }


    override fun getLayoutId(): Int = R.layout.activity_audio_component
    override fun onItemDelete(position: Int) {
        toast("删除第${position}个位置")
        audioComponent.deleteData(position)
    }

    override fun onItemClick(position: Int) {
        toast("点击了第${position}个位置")
        val item = audioComponent.getItemEntry(position)
        Log.d("AudioComponent--page-", "item.tagValue${item.tagValue}")


        //播放
        audioComponent.playAudio(item.tagValue)

    }


}