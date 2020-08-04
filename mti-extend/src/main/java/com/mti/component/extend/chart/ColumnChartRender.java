package com.mti.component.extend.chart;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * @anthor created by jzw
 * @date 2020/6/4
 * @change
 * @describe 柱状图配置与数据渲染
 **/
public class ColumnChartRender {
    //图表view
    private ColumnChartView chartView;
    /**
     * 底部x轴标题列表--对应数据columnData
     */
    private List<String> xTitles;
    //图表数据，外层list为主列，map为主列对应的子列数据
    private List<Map<Float, Integer>> columnData;
    //x，y轴的轴名称，在hasAxes=true时有效
    private String xAxisName;
    private String yAxisName;
    //轴线的颜色
    private int axisLineColor;
    //轴字体颜色
    private int axisTextColor;
    //轴字体大小
    private int axisTextSize;
    //是否开启子列堆叠模式
    private boolean stacked;
    //是否显示x，y轴线
    private boolean hasAxes;
    //是否显示x，y轴名称
    private boolean hasAxesNames;
    //是否显示数据label标识,与hasLabelForSelected互斥
    private boolean hasLabels;
    //点击选中的时候是否显示对应的label，和hasLabels是互斥的
    private boolean hasLabelForSelected;
    //如果为true，则渲染器将为此轴绘制线条（网格）
    private boolean hasLines;

    public ColumnChartRender(Builder builder) {
        this.chartView = builder.chartView;
        this.columnData = builder.columnData;
        this.xTitles = builder.xTitles;
        this.xAxisName = builder.xAxisName;
        this.yAxisName = builder.yAxisName;
        this.axisLineColor = builder.axisLineColor;
        this.axisTextColor = builder.axisTextColor;
        this.axisTextSize = builder.axisTextSize;
        this.stacked = builder.stacked;
        this.hasAxes = builder.hasAxes;
        this.hasAxesNames = builder.hasAxesNames;
        this.hasLabels = builder.hasLabels;
        this.hasLabelForSelected = builder.hasLabelForSelected;
        this.hasLines = builder.hasLines;
    }

    /**
     * 开始配置渲染
     */
    public void render() {
        if (this.columnData == null) return;
        int numColumns = this.columnData.size();

        //主列数据
        List<Column> columns = new ArrayList<>();
        //子列数据
        List<SubcolumnValue> subValues;
        for (Map<Float, Integer> col : this.columnData) {
            subValues = new ArrayList<>();
            for (Map.Entry<Float, Integer> subCol : col.entrySet()) {
                subValues.add(new SubcolumnValue(subCol.getKey().floatValue(), subCol.getValue().intValue()));
            }

            Column column = new Column(subValues);
            column.setHasLabels(this.hasLabels);
            column.setHasLabelsOnlyForSelected(this.hasLabelForSelected);

            columns.add(column);
        }

        ColumnChartData chartData = new ColumnChartData(columns);
        //是否子列堆叠
        chartData.setStacked(this.stacked);
        if (hasAxes) {
            Axis axisX = new Axis();
            if (xTitles != null && xTitles.size() != 0) {
                List<AxisValue> axisValues = new ArrayList<>();
                for (int i = 0; i < xTitles.size(); i++) {
                    //设置X轴的柱子所对应的属性名称(底部文字)
                    axisValues.add(new AxisValue(i).setLabel(xTitles.get(i)));
                }
                axisX.setValues(axisValues);
            }

            axisX.setLineColor(this.axisLineColor);
            axisX.setTextColor(this.axisTextColor);
            axisX.setTextSize(this.axisTextSize);

            Axis axisY = new Axis().setHasLines(this.hasLines);
            axisY.setLineColor(this.axisLineColor);
            axisY.setTextColor(this.axisTextColor);
            axisY.setTextSize(this.axisTextSize);

            if (this.hasAxesNames) {
                axisX.setName(this.xAxisName == null ? "" : this.xAxisName);
                axisY.setName(this.yAxisName == null ? "" : this.yAxisName);
            }
            chartData.setAxisXBottom(axisX);
            chartData.setAxisYLeft(axisY);
        } else {
            chartData.setAxisXBottom(null);
            chartData.setAxisYLeft(null);
        }
        //开启选中模式
        this.chartView.setValueSelectionEnabled(true);
        this.chartView.setColumnChartData(chartData);
        this.chartView.setVisibility(View.VISIBLE);
        prepareDataAnimation(chartData);
        this.chartView.startDataAnimation();
    }

    /**
     * 准备图表动画
     *
     * @param data
     */
    private void prepareDataAnimation(ColumnChartData data) {
        for (Column column : data.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget(value.getValue());
            }
        }
    }

    public static class Builder {
        //图表view
        private ColumnChartView chartView;
        //底部x轴显示标题数据
        private List<String> xTitles;
        //图表数据，外层list为主列，map为主列对应的子列数据
        private List<Map<Float, Integer>> columnData;
        //x，y轴的轴名称，在hasAxes=true时有效
        private String xAxisName;
        private String yAxisName;
        //轴线的颜色
        private int axisLineColor = Color.parseColor("#EEEEEE");
        //轴字体颜色
        private int axisTextColor = Color.parseColor("#C1EDFF");
        //轴字体大小
        private int axisTextSize = 12;
        //是否开启子列堆叠模式
        private boolean stacked = false;
        //是否显示x，y轴线
        private boolean hasAxes = true;
        //是否显示x，y轴名称
        private boolean hasAxesNames = true;
        //是否显示数据label标识,与hasLabelForSelected互斥
        private boolean hasLabels = false;
        //点击选中的时候是否显示对应的label，和hasLabels是互斥的
        private boolean hasLabelForSelected = false;
        //如果为true，则渲染器将为此轴绘制线条（网格）,开启时hasAxes必须为true
        private boolean hasLines = false;

        /**
         * 图表控件
         *
         * @param chartView
         * @return
         */
        public Builder chartView(ColumnChartView chartView) {
            this.chartView = chartView;
            return this;
        }


        /**
         * x轴显示数据列表
         *
         * @param xTitles
         * @return
         */
        public Builder xTitles(List<String> xTitles) {
            this.xTitles = xTitles;
            return this;
        }


        /**
         * 图表数据，外层list为主列，map为主列对应的子列数据，
         * 如果子列只有一项，则为默认模式，如果子列有多项则显示为子列模式
         *
         * @param data
         * @return
         */
        public Builder columnData(List<Map<Float, Integer>> data) {
            this.columnData = data;
            return this;
        }

        /**
         * x轴名称,在hasAxes=true时有效
         *
         * @param axisName
         * @return
         */
        public Builder xAxisName(String axisName) {
            this.xAxisName = axisName;
            return this;
        }

        /**
         * y轴名称,在hasAxes=true时有效
         *
         * @param axisName
         * @return
         */
        public Builder yAxisName(String axisName) {
            this.yAxisName = axisName;
            return this;
        }

        /**
         * x，y轴线的颜色
         *
         * @param color
         * @return
         */
        public Builder axisLineColor(int color) {
            this.axisLineColor = color;
            return this;
        }

        /**
         * x，y轴线的文本颜色
         *
         * @param color
         * @return
         */
        public Builder axisTextColor(int color) {
            this.axisTextColor = color;
            return this;
        }

        /**
         * x，y轴线的文本字体大小
         *
         * @param textSizeSP
         * @return
         */
        public Builder axisTextSize(int textSizeSP) {
            this.axisTextSize = textSizeSP;
            return this;
        }

        /**
         * 如果为true，则渲染器将为此轴绘制线条（网格）
         * 开启时hasAxes必须为true
         *
         * @param hasLines
         * @return
         */
        public Builder hasLines(boolean hasLines) {
            this.hasLines = hasLines;
            if (hasLines) {
                this.hasAxes = true;
            }
            return this;
        }

        /**
         * 是否开启子列堆叠模式
         *
         * @param stacked
         * @return
         */
        public Builder stacked(boolean stacked) {
            this.stacked = stacked;
            return this;
        }

        /**
         * 是否显示x，y轴线
         *
         * @param hasAxes
         * @return
         */
        public Builder hasAxes(boolean hasAxes) {
            this.hasAxes = hasAxes;
            return this;
        }

        /**
         * 是否显示x，y轴名称
         *
         * @param hasAxesNames
         * @return
         */
        public Builder hasAxesNames(boolean hasAxesNames) {
            this.hasAxesNames = hasAxesNames;
            return this;
        }

        /**
         * 是否显示数据label标识，与hasLabelForSelected互斥
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
         * 点击选中的时候是否显示对应的label，和hasLabels是互斥的
         *
         * @param hasLabelForSelected
         * @return
         */
        public Builder hasLabelForSelected(boolean hasLabelForSelected) {
            this.hasLabelForSelected = hasLabelForSelected;
            this.hasLabels = !hasLabelForSelected;
            return this;
        }

        public ColumnChartRender build() {
            return new ColumnChartRender(this);
        }
    }
}
