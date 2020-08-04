package com.mti.component.master.view.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mti.component.common.HorizontalProgressComponent;
import com.mti.component.master.R;
import com.mti.component.master.base.BaseFragment;

import java.util.Random;

/**
 * @anthor created by jzw
 * @date 2020/5/21
 * @change
 * @describe 组件开发管理
 **/
@SuppressLint("NewApi")
public class DevelopmentFragment extends BaseFragment {

    private LinearLayout commonContainer;
    private LinearLayout alarmContainer;
    private LinearLayout mediaContainer;
    private LinearLayout comprehensiveContainer;
    private LinearLayout extendsContainer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_development;
    }

    @Override
    public void initViews(View view, @Nullable Bundle savedInstanceState) {
        commonContainer = rootView.findViewById(R.id.commonContainer);
        alarmContainer = rootView.findViewById(R.id.alarmContainer);
        mediaContainer = rootView.findViewById(R.id.mediaContainer);
        comprehensiveContainer = rootView.findViewById(R.id.comprehensiveContainer);
        extendsContainer = rootView.findViewById(R.id.extendsContainer);

        initCommonComponentProgress();
        initAlarmComponentProgress();
        initMediaComponentProgress();
        initComprehensiveComponentProgress();
        initExtendsComponentProgress();
    }

    @Override
    public void loadData() {

    }


    private void initCommonComponentProgress() {
        commonContainer.removeAllViews();
        commonContainer.addView(createComponentProgressItemView("水印组件", 100));
        commonContainer.addView(createComponentProgressItemView("图片展示组件", 100));
        commonContainer.addView(createComponentProgressItemView("轮播组件", 100));
        commonContainer.addView(createComponentProgressItemView("标签组件", 100));
        commonContainer.addView(createComponentProgressItemView("水平进度条组件", 100));
        commonContainer.addView(createComponentProgressItemView("圆形进度组件", 100));
        commonContainer.addView(createComponentProgressItemView("进度加载组件", 100));
        commonContainer.addView(createComponentProgressItemView("3D翻转组件", 100));
        commonContainer.addView(createComponentProgressItemView("蜘蛛网背景组件", 100));
        commonContainer.addView(createComponentProgressItemView("筛选组件", 100));
        commonContainer.addView(createComponentProgressItemView("拨号盘组件", 100));
    }

    private void initAlarmComponentProgress() {
        alarmContainer.removeAllViews();
        alarmContainer.addView(createComponentProgressItemView("信息录入组件", 100));
        alarmContainer.addView(createComponentProgressItemView("勤务打卡组件", 100));
        alarmContainer.addView(createComponentProgressItemView("时间轴组件", 100));
        alarmContainer.addView(createComponentProgressItemView("日历组件", 100));
    }

    private void initComprehensiveComponentProgress() {
        comprehensiveContainer.removeAllViews();
        comprehensiveContainer.addView(createComponentProgressItemView("人员信息采集组件", 0));
        comprehensiveContainer.addView(createComponentProgressItemView("房屋信息采集组件", 0));
        comprehensiveContainer.addView(createComponentProgressItemView("组织场所采集组件", 0));
    }

    private void initMediaComponentProgress() {
        mediaContainer.removeAllViews();
        mediaContainer.addView(createComponentProgressItemView("短视频录制组件", 100));
        mediaContainer.addView(createComponentProgressItemView("视频播放组件", 100));
        mediaContainer.addView(createComponentProgressItemView("音频录制组件", 90));
        mediaContainer.addView(createComponentProgressItemView("推流组件", 0));
        mediaContainer.addView(createComponentProgressItemView("拉流组件", 0));
    }


    private void initExtendsComponentProgress() {
        extendsContainer.removeAllViews();
        extendsContainer.addView(createComponentProgressItemView("统计图表组件", 80));
        extendsContainer.addView(createComponentProgressItemView("电子签名组件", 100));
        extendsContainer.addView(createComponentProgressItemView("原生TTS组件", 0));
        extendsContainer.addView(createComponentProgressItemView("OCR识别组件", 0));
    }

    /**
     * 创建一个组件的进度view
     *
     * @param componentName 组件名称
     * @param progress      当前开发进度
     * @return
     */
    private View createComponentProgressItemView(String componentName, int progress) {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.dev_progress_layout, null, false);
        TextView tvName = itemView.findViewById(R.id.tvComponentName);
        TextView tvValue = itemView.findViewById(R.id.tvComponentProgressValue);
        if (progress >= 100) {
            tvValue.setTextColor(Color.GREEN);
        }

        HorizontalProgressComponent progressComponent = itemView.findViewById(R.id.componentProgress);
        progressComponent.setMax(100);
        progressComponent.setProgress(progress);
        progressComponent.setBgColor(Color.parseColor("#F3F3F3"));
        progressComponent.setProgressColor(randomColor());

        tvName.setText(componentName);
        tvValue.setText(progress + "%");
        return itemView;
    }

    /**
     * 随机获取一个颜色值
     *
     * @return
     */
    private int randomColor() {
        return Color.parseColor(getRandColor());
    }

    /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    private String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(255)).toUpperCase();
        G = Integer.toHexString(random.nextInt(255)).toUpperCase();
        B = Integer.toHexString(random.nextInt(255)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}
