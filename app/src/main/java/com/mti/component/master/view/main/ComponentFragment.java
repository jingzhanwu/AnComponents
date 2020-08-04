package com.mti.component.master.view.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mti.component.master.R;
import com.mti.component.master.adapter.ComponentSectionAdapter;
import com.mti.component.master.base.BaseFragment;
import com.mti.component.master.model.ComponentEntry;
import com.mti.component.master.model.ComponentSection;
import com.mti.component.master.view.example.AudioComponentActivity;
import com.mti.component.master.view.example.BannerComponentActivity;
import com.mti.component.master.view.example.CalendarComponentDemoActivity;
import com.mti.component.master.view.example.CloudTag3DComponentActivity;
import com.mti.component.master.view.example.CobwebComponentActivity;
import com.mti.component.master.view.example.ConditionComponentActivity;
import com.mti.component.master.view.example.DialComponentActivity;
import com.mti.component.master.view.example.Flip3DComponentActivity;
import com.mti.component.master.view.example.FlowComponentActivity;
import com.mti.component.master.view.example.HorizontalProgressComponentActivity;
import com.mti.component.master.view.example.ImageNice9ComponentActivity;
import com.mti.component.master.view.example.InputComponentActivity;
import com.mti.component.master.view.example.LoadingComponentActivity;
import com.mti.component.master.view.example.SignatureComponentActivity;
import com.mti.component.master.view.example.SimpleRingProgressComponentActivity;
import com.mti.component.master.view.example.VideoPlayerComponentActivity;
import com.mti.component.master.view.example.VideoRecordComponentActivity;
import com.mti.component.master.view.example.WaterMarkComponentActivity;
import com.mti.component.master.view.example.WorkPunchComponentActivity;
import com.mti.component.master.view.example.chart.ChartComponentActivity;
import com.mti.component.master.view.example.timeline.TimeLineComponentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author created by jzw
 * @date 2020/5/21
 * @change
 * @describe 组件开发进度
 **/
@SuppressLint("NewApi")
public class ComponentFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private ComponentSectionAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_component;
    }

    @Override
    public void initViews(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        initAdapter();
    }

    @Override
    public void loadData() {

    }

    private void initAdapter() {
        mAdapter = new ComponentSectionAdapter(getActivity(), createSectionData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<ComponentSection> createSectionData() {

        //通用
        ComponentEntry waterMark = new ComponentEntry("文字水印", R.drawable.pic_water, "整个APP页面设置文字水印，使用起来so easy！", WaterMarkComponentActivity.class.getName());
        ComponentEntry imageNice9 = new ComponentEntry("九宫格", R.drawable.pic_nice9, "支持自由拖拽的九宫格图片显示器，拥有9种显示风格", ImageNice9ComponentActivity.class.getName());
        ComponentEntry carousel = new ComponentEntry("广告轮播", R.drawable.picture_carousel, "大厂都在用的广告轮播效果！", BannerComponentActivity.class.getName());
        ComponentEntry dial = new ComponentEntry("拨号盘", R.drawable.picture_dial, "自定义的拨号盘！", DialComponentActivity.class.getName());
        ComponentEntry flow = new ComponentEntry("标签组件", R.drawable.picture_flow, "简单易用流式布局", FlowComponentActivity.class.getName());
        ComponentEntry cobweb = new ComponentEntry("蜘蛛网背景", R.drawable.picture_cobweb, "富有科技感的蜘蛛网背景", CobwebComponentActivity.class.getName());
        ComponentEntry flip3d = new ComponentEntry("3d翻转", R.drawable.picture_3d_flip, "让视图具有3d翻转效果", Flip3DComponentActivity.class.getName());
        ComponentEntry tagCloud3d = new ComponentEntry("3d云图标签", R.drawable.picture_3d_cloud_tag, "让标签具有3d效果", CloudTag3DComponentActivity.class.getName());
        ComponentEntry condition = new ComponentEntry("筛选组件", R.drawable.picture_condition, "筛选组件", ConditionComponentActivity.class.getName());
        ComponentEntry loadingComponent = new ComponentEntry("进度加载组件", R.drawable.picture_loading, "进度加载组件", LoadingComponentActivity.class.getName());
        ComponentEntry simpleRingProgressComponent = new ComponentEntry("圆环进度组件", R.drawable.picture_simple_ring_progress, "简易圆环进度组件", SimpleRingProgressComponentActivity.class.getName());
        ComponentEntry horizontalProgressComponent = new ComponentEntry("水平进度组件", R.drawable.picture_simple_ring_progress, "简易圆环进度组件", HorizontalProgressComponentActivity.class.getName());
        //多媒体
        ComponentEntry videoRecord = new ComponentEntry("短视频录制", R.drawable.picture_video, "简易的短视频录制组件", VideoRecordComponentActivity.class.getName());
        ComponentEntry videoPlayer = new ComponentEntry("视频播放组件", R.drawable.picture_video_player, "功能强大的视频播放器，支持mp4，flv，http等主流视频格式", VideoPlayerComponentActivity.class.getName());
        ComponentEntry audioRecord = new ComponentEntry("音频录制播放", R.drawable.picture_audio_record_play, "轻松实现音频录制与播放", AudioComponentActivity.class.getName());
        //警情
        ComponentEntry input = new ComponentEntry("信息录入", R.drawable.pic_input, "适用于任何需要信息录入和显示的场景", InputComponentActivity.class.getName());
        ComponentEntry duty = new ComponentEntry("勤务打卡", R.drawable.picture_punch, "仿钉钉打卡，专为勤务应用设计", WorkPunchComponentActivity.class.getName());
        ComponentEntry timeLine = new ComponentEntry("时间轴", R.drawable.pic_timeline, "针对警情指令设计的时间轴组件，也可用于其他任何类似场景", TimeLineComponentActivity.class.getName());
        ComponentEntry calendar = new ComponentEntry("日历", R.drawable.pic_calendar, "超炫酷的日历组件，可覆盖90%以上的应用场景，计划应用于勤务排班", CalendarComponentDemoActivity.class.getName());
        //扩展
        ComponentEntry signatureDraw = new ComponentEntry("电子签名组件", R.drawable.picture_sign, "自定义电子签名画板", SignatureComponentActivity.class.getName());
        ComponentEntry chart = new ComponentEntry("统计图表组件", R.drawable.picture_chart, "包含常用的各种统计图表", ChartComponentActivity.class.getName());

        //分组列表
        List<ComponentSection> sections = new ArrayList<>();

        sections.add(new ComponentSection("通用组件", batchAddData(
                waterMark, imageNice9,
                carousel, dial,
                flow, horizontalProgressComponent,
                cobweb, flip3d, tagCloud3d,
                condition, loadingComponent, simpleRingProgressComponent
        )));

        sections.add(new ComponentSection("警情组件", batchAddData(
                input, duty,
                timeLine, calendar
        )));

        sections.add(new ComponentSection("多媒体组件", batchAddData(
                videoRecord, videoPlayer,
                audioRecord
        )));

        sections.add(new ComponentSection("扩展组件", batchAddData(
                chart, signatureDraw
        )));

        return sections;
    }

    /**
     * 批量添加model数据
     *
     * @param model ComponentModel
     * @return List<ComponentModel>
     */

    private List<ComponentEntry> batchAddData(ComponentEntry... model) {
        return Stream.of(model).collect(Collectors.toList());
    }
}
