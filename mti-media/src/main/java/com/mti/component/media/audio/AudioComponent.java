package com.mti.component.media.audio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mti.component.media.R;
import com.mti.component.media.audio.helper.PlayerManager;
import com.mti.component.media.audio.internal.record.AndroidAudioRecorder;
import com.mti.component.media.audio.internal.record.model.AudioChannel;
import com.mti.component.media.audio.internal.record.model.AudioSampleRate;
import com.mti.component.media.audio.internal.record.model.AudioSource;
import com.mti.component.media.entry.AudioLabelEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/27
 * @change
 * @describe 音频标签显示、编辑、录音播放、录制
 **/
public class AudioComponent extends LinearLayout {
    private AudioAdapter audioAdapter;
    private RecyclerView recyclerView;

    /**
     * 背景选择器
     */
    private int backgroundSelectorRes = R.drawable.audio_label_selector;
    /**
     * 删除图片
     */
    private @DrawableRes
    int deleteResId = R.drawable.ic_audio_delete;
    /**
     * 文本颜色
     */
    private int fontColor;
    /**
     * 文本大小
     */
    private float fontSize;
    /**
     * 是否显示删除
     */
    private boolean showingDelete = true;

    /**
     * 是否可选择
     */
    private boolean optional;
    /**
     * 是否多选
     */
    private boolean isMultiple;
    private Activity mActivity;

    public AudioComponent(Context context) {
        this(context, null);
    }

    public AudioComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_audio_flex_layout, this);
        recyclerView = findViewById(R.id.recyclerView);
    }

    public int getBackgroundSelectorRes() {
        return backgroundSelectorRes;
    }

    public void setBackgroundSelectorRes(int backgroundSelectorRes) {
        this.backgroundSelectorRes = backgroundSelectorRes;
    }

    public int getDeleteResId() {
        return deleteResId;
    }

    public void setDeleteResId(int deleteResId) {
        this.deleteResId = deleteResId;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }


    public boolean isShowingDelete() {
        return showingDelete;
    }

    public void setShowingDelete(boolean showingDelete) {
        this.showingDelete = showingDelete;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public void init(Activity activity, OnAudioClickListener listener) {
        init(activity, new ArrayList<>(), listener);
    }


    public void init(Activity activity, List<AudioLabelEntry> data, OnAudioClickListener listener) {
        this.mActivity = activity;

        audioAdapter = new AudioAdapter(getContext(), data);
        audioAdapter.setBackgroundSelectorRes(backgroundSelectorRes);
        audioAdapter.setDeleteResId(deleteResId);
        audioAdapter.setFontColor(fontColor);
        audioAdapter.setFontSize(fontSize);
        audioAdapter.setShowingDelete(showingDelete);

        //初始化recycler
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        //主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        //按正常方向换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        //交叉轴的起点对齐。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerView.setLayoutManager(flexboxLayoutManager);
        recyclerView.setAdapter(audioAdapter);

        audioAdapter.setOnAudioClickListener(new OnAudioClickListener() {
            @Override
            public void onItemClick(int position) {
                if (listener == null) {
                    return;

                }
                listener.onItemClick(position);

                if (!optional) {
                    return;
                }

                if (isMultiple) {
                    audioAdapter.selectByMultipleChoice(position);
                } else {
                    audioAdapter.selectBySingleChoice(position);
                }

            }

            @Override
            public void onItemDelete(int position) {
                if (listener == null) {
                    return;

                }
                listener.onItemDelete(position);
            }
        });

    }


    /**
     * 音频录制
     *
     * @param audioPath
     * @param requestCode
     */
    public void recordAudio(String audioPath, int requestCode) {
        AndroidAudioRecorder.with(mActivity)
                // Required
                .setFilePath(audioPath)
                .setColor(Color.parseColor("#651FFF"))
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)
                // Start recording
                .record();
//        model.recordAudio(audioPath, requestCode);
    }

    /**
     * 播放录音
     *
     * @param audio
     */
    public void playAudio(String audio) {
        PlayerManager.get().playAudio(audio, new PlayerManager.OnPlayerCallback() {
            @Override
            public void onPrepare(MediaPlayer mp) {

            }

            @Override
            public void onStart(MediaPlayer mp, String url) {

            }

            @Override
            public void onPlaying(MediaPlayer mp) {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onFailed(MediaPlayer mp) {

            }
        });
    }

    /**
     * 停止
     */
    public void stop() {
        if (PlayerManager.get().isPlaying()) {
            PlayerManager.get().stop();
        }
    }

    public List<AudioLabelEntry> getAudioData() {
        return audioAdapter.getData();
    }

    public AudioLabelEntry getItemEntry(int position) {
        return audioAdapter.getItemEntry(position);
    }

    public void setNewData(List<AudioLabelEntry> list) {
        audioAdapter.setNewData(list);
    }

    /**
     * 添加数据
     *
     * @param tagEntry
     */
    public void addData(AudioLabelEntry tagEntry) {
        audioAdapter.addTag(tagEntry);
    }

    /**
     * 删除
     *
     * @param pos
     */
    public void deleteData(int pos) {
        audioAdapter.deleteTag(pos);
    }


    public void release() {
        PlayerManager.get().releasPlayer();
    }

}
