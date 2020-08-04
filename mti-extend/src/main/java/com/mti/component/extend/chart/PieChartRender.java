package com.mti.component.extend.chart;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * @anthor created by jzw
 * @date 2020/6/3
 * @change
 * @describe 饼图，环形图表 渲染工具
 **/
public class PieChartRender {
    //图标控件
    private PieChartView pieChartView;
    //数据，key=值，value=对应颜色值
    private Map<Float, Integer> sliceValues;
    //圆环中间文本1
    private String centerText1;
    //圆环中间文本2
    private String centerText2;
    //text1和text2的颜色
    private int centerText1Color;
    private int centerText2Color;
    //text1和text2的字体大小，sp
    private int centerText1FontSize;
    private int centerText2FontSize;
    //是否显示label文本，此属性和hasLabelForSelected是互斥的
    private boolean hasLabels;
    //外圈是否显示label文本
    private boolean hasLabelsOutside;
    //是否中间是空圆形
    private boolean hasCenterCircle;
    //空心圆中间是否显示一个文本
    private boolean hasCenterText1;
    //空心圆中间是否显示两行文本
    private boolean hasCenterText2;
    //点击选中的时候是否显示对应的label,和hasLabels是互斥的
    private boolean hasLabelForSelected;
    //项与项之间的间距
    private int slicesSpacing;
    //是否使用默认的字体库
    private boolean useDefaultFontTypeface;

    /**
     * 饼图，环形图数据渲染
     *
     * @param builder
     */
    public PieChartRender(Builder builder) {
        this.pieChartView = builder.pieChartView;
        this.sliceValues = builder.sliceValues;
        this.hasLabels = builder.hasLabels;
        this.hasLabelsOutside = builder.hasLabelsOutside;
        this.hasCenterCircle = builder.hasCenterCircle;
        this.hasCenterText1 = builder.hasCenterText1;
        this.hasCenterText2 = builder.hasCenterText2;
        this.hasLabelForSelected = builder.hasLabelForSelected;
        this.slicesSpacing = builder.slicesSpacing;
        this.centerText1 = builder.centerText1;
        this.centerText2 = builder.centerText2;
        this.centerText1Color = builder.centerText1Color;
        this.centerText2Color = builder.centerText2Color;
        this.centerText1FontSize = builder.centerText1FontSize;
        this.centerText2FontSize = builder.centerText2FontSize;
        this.useDefaultFontTypeface = builder.useDefaultFontTypeface;
    }

    public void render() {
        if (this.sliceValues == null) return;
        int humValues = this.sliceValues.size();
        //初始化饼图数据
        List<SliceValue> values = new ArrayList<>();
        for (Map.Entry<Float, Integer> entry : this.sliceValues.entrySet()) {
            SliceValue v = new SliceValue(entry.getKey().floatValue(), entry.getValue().intValue());
            values.add(v);
        }
        //初始化基础配置数据
        PieChartData pieChartData = new PieChartData(values);
        pieChartData.setHasLabels(this.hasLabels);
        pieChartData.setHasLabelsOnlyForSelected(this.hasLabelForSelected);
        pieChartData.setHasLabelsOutside(this.hasLabelsOutside);
        pieChartData.setSlicesSpacing(this.slicesSpacing);

        if (this.hasCenterText2) {
            this.hasCenterCircle = true;
            this.hasCenterText1 = true;
        }
        if (!this.hasCenterText2 && this.hasCenterText1) {
            this.hasCenterCircle = true;
            this.hasCenterText2 = false;
        }
        pieChartData.setHasCenterCircle(this.hasCenterCircle);
        Context context = this.pieChartView.getContext();
        //如果设置有圆环中心的text1 则进行设置
        if (this.hasCenterText1) {
            pieChartData.setCenterText1(this.centerText1 == null ? "" : this.centerText1);
            //如果开启了使用默认字体库，则进行设置,默认是开启的
            if (this.useDefaultFontTypeface) {
                // Get roboto-italic font.
                AssetManager assetManager = context.getAssets();
                Typeface tf = Typeface.createFromAsset(assetManager, "Roboto-Italic.ttf");
                pieChartData.setCenterText1Typeface(tf);
            }
            //文本的颜色和大小
            pieChartData.setCenterText1FontSize(this.centerText1FontSize);
            pieChartData.setCenterText1Color(this.centerText1Color);
        }
        //如果设置有圆环中心的text2 则进行设置
        if (this.hasCenterText2) {
            pieChartData.setCenterText2(this.centerText2 == null ? "" : this.centerText2);
            //如果开启了使用默认字体库，则进行设置
            if (this.useDefaultFontTypeface) {
                AssetManager assetManager = context.getAssets();
                Typeface tf = Typeface.createFromAsset(assetManager, "Roboto-Italic.ttf");
                pieChartData.setCenterText2Typeface(tf);
            }
            //文本的颜色和大小
            pieChartData.setCenterText2Color(this.centerText2Color);
            pieChartData.setCenterText2FontSize(this.centerText2FontSize);
        }

        this.pieChartView.setValueSelectionEnabled(hasLabelForSelected);
        if (hasLabelForSelected) {
            pieChartData.setHasLabels(false);
            pieChartData.setHasLabelsOutside(false);
            if (this.hasLabelsOutside) {
                this.pieChartView.setCircleFillRatio(0.7f);
            } else {
                this.pieChartView.setCircleFillRatio(1.0f);
            }
        }
        if (this.hasLabels) {
            this.hasLabelForSelected = false;
            this.pieChartView.setValueSelectionEnabled(false);
            if (this.hasLabelsOutside) {
                this.pieChartView.setCircleFillRatio(0.7f);
            } else {
                this.pieChartView.setCircleFillRatio(1.0f);
            }
        }
        this.pieChartView.setPieChartData(pieChartData);
        this.pieChartView.setVisibility(View.VISIBLE);
        prepareDataAnimation(pieChartData);
        this.pieChartView.startDataAnimation();
    }

    private void prepareDataAnimation(PieChartData data) {
        for (SliceValue value : data.getValues()) {
            value.setTarget(value.getValue());
        }
    }

    public static class Builder {
        //图表view
        private PieChartView pieChartView;
        //数据，key=值，value=对应颜色值
        private Map<Float, Integer> sliceValues;
        //圆环中间文本1
        private String centerText1;
        //圆环中间文本2
        private String centerText2;
        //text1和text2的颜色
        private int centerText1Color = Color.BLACK;
        private int centerText2Color = Color.BLACK;
        //text1和text2的字体大小，sp
        private int centerText1FontSize = 18;
        private int centerText2FontSize = 12;

        //是否显示数据label文本，此属性和hasLabelForSelected是互斥的
        private boolean hasLabels = false;
        //外圈是否显示label文本
        private boolean hasLabelsOutside = false;
        //是否中间是空圆形
        private boolean hasCenterCircle = false;
        //空心圆中间是否显示一个文本
        private boolean hasCenterText1 = false;
        //空心圆中间是否显示两行文本
        private boolean hasCenterText2 = false;
        //点击选中的时候是否显示对应的label，和hasLabels是互斥的
        private boolean hasLabelForSelected = false;
        //项与项之间的间距
        private int slicesSpacing = 1;
        //是否使用默认的字体库
        private boolean useDefaultFontTypeface = true;

        public Builder chartView(PieChartView chartView) {
            this.pieChartView = chartView;
            return this;
        }

        public Builder sliceValues(Map<Float, Integer> data) {
            this.sliceValues = data;
            return this;
        }

        public Builder hasLabels(boolean hasLabels) {
            this.hasLabels = hasLabels;
            this.hasLabelForSelected = !hasLabels;
            return this;
        }

        public Builder centerText1(String centerText1) {
            this.centerText1 = centerText1;
            return this;
        }

        public Builder centerText2(String centerText2) {
            this.centerText2 = centerText2;
            return this;
        }

        public Builder centerText1Color(int color) {
            this.centerText1Color = color;
            return this;
        }

        public Builder centerText2Color(int color) {
            this.centerText2Color = color;
            return this;
        }

        public Builder centerText1FontSize(int sizeSP) {
            this.centerText1FontSize = sizeSP;
            return this;
        }

        public Builder centerText2FontSize(int sizeSP) {
            this.centerText2FontSize = sizeSP;
            return this;
        }

        public Builder hasLabelsOutside(boolean hasLabelsOutside) {
            this.hasLabelsOutside = hasLabelsOutside;
            return this;
        }

        public Builder hasCenterCircle(boolean hasCenterCircle) {
            this.hasCenterCircle = hasCenterCircle;
            return this;
        }

        public Builder hasCenterText1(boolean hasCenterText1) {
            this.hasCenterText1 = hasCenterText1;
            return this;
        }

        public Builder hasCenterText2(boolean hasCenterText2) {
            this.hasCenterText2 = hasCenterText2;
            return this;
        }

        public Builder hasLabelForSelected(boolean hasLabelForSelected) {
            this.hasLabelForSelected = hasLabelForSelected;
            this.hasLabels = !hasLabelForSelected;
            return this;
        }

        public Builder slicesSpacing(int slicesSpacing) {
            this.slicesSpacing = slicesSpacing;
            return this;
        }

        public Builder useDefaultFontTypeface(boolean useDefaultFontTypeface) {
            this.useDefaultFontTypeface = useDefaultFontTypeface;
            return this;
        }

        public PieChartRender build() {
            return new PieChartRender(this);
        }
    }
}
