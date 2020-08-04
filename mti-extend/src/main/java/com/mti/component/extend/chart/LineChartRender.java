package com.mti.component.extend.chart;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * @anthor created by jzw
 * @date 2020/6/3
 * @change
 * @describe 折线图数据生成器
 **/
public class LineChartRender {

    private LineChartView lineChartView;
    //折线颜色与数据对应，一条线一个颜色
    private int[] lineColor;
    //折线端宽度
    private int strokeWidth;
    //x轴名称
    private String xName;
    //y轴名称
    private String yName;
    //x轴数据
    private List<String> xData;
    //线数据，可以支持多条线
    private List<List<Float>> lineData;
    //轴线的颜色
    private int axisLineColor;
    //轴字体颜色
    private int axisTextColor;
    //轴子字体大小
    private int axisTextSize;
    //是否显示x，y轴线
    private boolean hasAxes = true;
    //曲线是否平滑，即是曲线还是折线
    private boolean cubic;
    //是否填充曲线的面积
    private boolean filled;
    //曲线的数据坐标是否加上备注
    private boolean hasLabels;
    //是否用线显示。如果为false 则没有曲线只有点显示
    private boolean hasLines;
    //设置行为属性，支持缩放、滑动以及平移
    private boolean interactive;
    //是否显示峰值的圆点
    private boolean hasPoints;
    //点击选中的时候是否显示对应的label,和hasLabels是互斥的
    private boolean hasLabelForSelected;
    //圆点的大小
    private int pointRadius;
    //填充区域透明度，255位完全透明，默认64
    private int areaTransparency;
    //填充区域是否渐变
    private boolean hasGradientToTransparent;

    public LineChartRender(Builder builder) {
        this.lineChartView = builder.lineChartView;
        this.lineColor = builder.lineColor;
        this.xName = builder.xName;
        this.yName = builder.yName;
        this.xData = builder.xData;
        this.lineData = builder.lineData;
        this.axisLineColor = builder.axisLineColor;
        this.axisTextColor = builder.axisTextColor;
        this.axisTextSize = builder.axisTextSize;
        this.hasAxes = builder.hasAxes;
        this.cubic = builder.cubic;
        this.filled = builder.filled;
        this.hasLabels = builder.hasLabels;
        this.hasLabelForSelected = builder.hasLabelForSelected;
        this.hasLines = builder.hasLines;
        this.interactive = builder.interactive;
        this.hasPoints = builder.hasPoints;
        this.pointRadius = builder.pointRadius;
        this.strokeWidth = builder.strokeWidth;
        this.hasGradientToTransparent = builder.hasGradientToTransparent;
        this.areaTransparency = builder.areaTransparency;
    }

    /**
     * 开始渲染图标属性
     */
    public void render() {
        if (this.xData == null || this.lineData == null) return;
        if (lineColor == null || lineColor.length == 0) {
            lineColor = new int[]{Color.parseColor("#00EAFF")};
        }
        //1.线集合
        List<Line> lines = new ArrayList();
        int lineSize = this.lineData.size();
        for (int l = 0; l < lineSize; l++) {
            //1.1.处理一条线的数据
            List<Float> oneData = this.lineData.get(l);
            List<PointValue> pointValues = new ArrayList<>();
            for (int j = 0; j < oneData.size(); ++j) {
                pointValues.add(new PointValue(j, oneData.get(j)));
            }
            //1.2 添加线
            int color = this.lineColor.length == lineSize ? this.lineColor[l] : this.lineColor[0];
            Line line = createLine(color);
            line.setValues(pointValues);
            lines.add(line);
        }

        LineChartData data = new LineChartData(lines);
        //如果显示x，y轴则进行配置
        if (this.hasAxes) {
            //2.处理x轴数据
            List<AxisValue> axisValues = new ArrayList<>();
            for (int i = 0; i < this.xData.size(); i++) {
                AxisValue value = new AxisValue(i);
                value.setLabel(this.xData.get(i));
                axisValues.add(value);
            }
            //3 添加x轴
            //坐标轴 X轴
            Axis axisX = createAxis(this.xName);
            //填充X轴的坐标名称
            axisX.setValues(axisValues);
            //4. 添加y轴
            Axis axisY = createAxis(this.yName);
            //设置底部轴
            data.setAxisXBottom(axisX);
            //设置左边轴
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        //5.设置图标其他属性
        //设置行为属性，支持缩放、滑动以及平移
        lineChartView.setInteractive(this.interactive);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        //最大方法比例
        lineChartView.setMaxZoom((float) 2);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        lineChartView.setLineChartData(data);
        lineChartView.setVisibility(View.VISIBLE);
        prepareDataAnimation(data);
        lineChartView.startDataAnimation();
    }

    /**
     * 准备图表动画
     *
     * @param data
     */
    private void prepareDataAnimation(LineChartData data) {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                value.setTarget(value.getX(), value.getY());
            }
        }
    }

    /**
     * 创建线
     *
     * @param lineColor
     * @return
     */
    private Line createLine(int lineColor) {
        Line line = new Line();
        //设置线的颜色
        line.setColor(lineColor);
        //折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setShape(ValueShape.CIRCLE);
        //曲线是否平滑，即是曲线还是折线
        line.setCubic(this.cubic);
        //是否填充曲线的面积
        line.setFilled(this.filled);
        //曲线的数据坐标是否加上备注
        line.setHasLabels(this.hasLabels);
        //点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLabelsOnlyForSelected(this.hasLabelForSelected);
        //是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasLines(this.hasLines);
        //是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setHasPoints(this.hasPoints);
        line.setPointRadius(this.pointRadius);
        line.setStrokeWidth(this.strokeWidth);
        //填充区域透明度
        line.setAreaTransparency(this.areaTransparency);
//        line.setHasGradientToTransparent();
        return line;
    }


    /**
     * 创建轴
     *
     * @return
     */
    private Axis createAxis(String axisName) {
        //坐标轴
        Axis axis = new Axis();
        axis.setHasLines(this.hasLines);
        //坐标轴字体是斜的显示还是直的，true是斜的显示
        axis.setHasTiltedLabels(false);
        //设置字体颜色
        axis.setTextColor(this.axisTextColor);
        //设置字体大小
        axis.setTextSize(this.axisTextSize);
        axis.setLineColor(this.axisLineColor);
        //表格名称
        if (!TextUtils.isEmpty(axisName)) {
            axis.setName(axisName);
        }
        return axis;
    }

    public static class Builder {

        //图标view
        private LineChartView lineChartView;
        //折线端宽度
        private int strokeWidth = 1;
        //折线颜色
        private int[] lineColor;
        //x轴名称
        private String xName;
        //y轴名称
        private String yName;
        //x轴数据
        private List<String> xData;
        //y轴数据,可以支持多条线
        private List<List<Float>> lineData;
        //轴线的颜色
        private int axisLineColor = Color.parseColor("#EEEEEE");
        //轴字体颜色
        private int axisTextColor = Color.parseColor("#C1EDFF");
        //轴子字体大小
        private int axisTextSize = 12;
        //是否显示x，y轴线
        private boolean hasAxes = true;
        //曲线是否平滑，即是曲线还是折线
        private boolean cubic = false;
        //是否填充曲线的面积
        private boolean filled = true;
        //曲线的数据坐标是否加上备注
        private boolean hasLabels = false;
        //点击选中的时候是否显示对应的label,和hasLabels是互斥的
        private boolean hasLabelForSelected = true;
        //是否用线显示。如果为false 则没有曲线只有点显示
        private boolean hasLines = true;
        //设置行为属性，支持缩放、滑动以及平移
        private boolean interactive = true;
        //是否显示峰值的圆点
        private boolean hasPoints = true;
        //圆点的大小
        private int pointRadius = 4;
        //填充区域是否渐变
        private boolean hasGradientToTransparent = true;
        //填充区域透明度，255位完全透明，默认64
        private int areaTransparency = 64;


        /**
         * 设置图表view
         *
         * @param chartView
         * @return
         */
        public Builder chartView(LineChartView chartView) {
            this.lineChartView = chartView;
            return this;
        }

        /**
         * 设置线颜色,一条线对应一个颜色值
         *
         * @param lineColor
         * @return
         */
        public Builder lineColor(int[] lineColor) {
            this.lineColor = lineColor;
            return this;
        }

        /**
         * 设置水平轴名称
         *
         * @param name
         * @return
         */
        public Builder xName(String name) {
            this.xName = name;
            return this;
        }

        /**
         * 设置垂直轴名称
         *
         * @param name
         * @return
         */
        public Builder yName(String name) {
            this.yName = name;
            return this;
        }

        /**
         * 设置水平轴，数据节点对应label文本
         *
         * @param data
         * @return
         */
        public Builder xData(List<String> data) {
            this.xData = data;
            return this;
        }

        /**
         * 设置线数据，支持多条线
         *
         * @param data
         * @return
         */
        public Builder lineData(List<List<Float>> data) {
            this.lineData = data;
            return this;
        }

        /**
         * 设置轴线颜色
         *
         * @param color
         * @return
         */
        public Builder axisLineColor(int color) {
            this.axisLineColor = color;
            return this;
        }

        /**
         * 设置轴文本颜色
         *
         * @param color
         * @return
         */
        public Builder axisTextColor(int color) {
            this.axisTextColor = color;
            return this;
        }

        /**
         * 设置轴文本字体大小，单位为sp
         *
         * @param textSizeSP
         * @return
         */
        public Builder axisTextSize(int textSizeSP) {
            this.axisTextSize = textSizeSP;
            return this;
        }

        /**
         * 是否平滑曲线，默认是折线
         *
         * @param cubic
         * @return
         */
        public Builder cubic(boolean cubic) {
            this.cubic = cubic;
            return this;
        }

        /**
         * 是否填充区域
         *
         * @param filled
         * @return
         */
        public Builder filled(boolean filled) {
            this.filled = filled;
            return this;
        }

        /**
         * 是否显示折线label
         *
         * @param hasLabels
         * @return
         */
        public Builder hasLabels(boolean hasLabels) {
            this.hasLabels = hasLabels;
            this.hasLabelForSelected = !hasLabels;
            return this;
        }

        /**
         * 是否在点击选中时显示label
         *
         * @param hasLabelForSelected
         * @return
         */

        public Builder hasLabelForSelected(boolean hasLabelForSelected) {
            this.hasLabelForSelected = hasLabelForSelected;
            this.hasLabels = !hasLabelForSelected;
            return this;
        }

        /**
         * 是否显示线
         *
         * @param hasLines
         * @return
         */
        public Builder hasLines(boolean hasLines) {
            this.hasLines = hasLines;
            return this;
        }

        /**
         * 是否为交互模式
         *
         * @param interactive
         * @return
         */
        public Builder interactive(boolean interactive) {
            this.interactive = interactive;
            return this;
        }

        /**
         * 是否显示峰值圆点
         *
         * @param hasPoints
         * @return
         */

        public Builder hasPoints(boolean hasPoints) {
            this.hasPoints = hasPoints;
            return this;
        }

        /**
         * 圆点大小
         *
         * @param pointRadius
         * @return
         */
        public Builder pointRadius(int pointRadius) {
            this.pointRadius = pointRadius;
            return this;
        }

        /**
         * 折线/曲线 线宽
         *
         * @param strokeWidth
         * @return
         */
        public Builder strokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        /**
         * 填充区域是否渐变
         *
         * @param gradient
         * @return
         */
        @Deprecated
        public Builder hasGradientToTransparent(boolean gradient) {
            this.hasGradientToTransparent = gradient;
            return this;
        }

        /**
         * 是否显示x，y 轴线
         *
         * @param hasAxes
         * @return
         */
        public Builder hasAxes(boolean hasAxes) {
            this.hasAxes = hasAxes;
            return this;
        }

        /**
         * 设置填充区域透明度，默认为64，255为完全不透明
         *
         * @param transparency
         * @return
         */
        public Builder areaTransparency(int transparency) {
            this.areaTransparency = transparency;
            return this;
        }

        public LineChartRender build() {
            //如果没有设置颜色，则给一个默认的颜色
            if (lineColor == null || lineColor.length == 0) {
                if (this.lineData != null) {
                    lineColor = new int[this.lineData.size()];
                    for (int i = 0; i < lineColor.length; i++) {
                        lineColor[i] = Color.parseColor("#00EAFF");
                    }
                } else {
                    lineColor = new int[]{
                            Color.parseColor("#00EAFF"),
                    };
                }
            }
            return new LineChartRender(this);
        }
    }
}
