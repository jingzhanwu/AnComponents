# 柱状图图表使用
## 如何使用
由于需要配置的属性比较多，所以这里使用Builder设计模式让调用者更加便捷

#### 1.属性与方法说明（这里只列举比较常用的，属性对应同名方法）

ColumnChartView chartView： //图表view
        
List<Map<Float, Integer>> columnData：//图表数据，外层list为主列，map为主列对应的子列数据
       
String xAxisName： //x轴的轴名称，在hasAxes=true时有效

String yAxisName： //y轴的轴名称，在hasAxes=true时有效
     
int axisLineColor：   //轴线的颜色
        
int axisTextColor：//轴字体颜色
       
int axisTextSize： //轴字体大小，单位sp
      
boolean stacked：  //是否开启子列堆叠模式
       
boolean hasAxes： //是否显示x，y轴线
        
boolean hasAxesNames：//是否显示x，y轴名称
        
boolean hasLabels：//是否显示数据label标识,与hasLabelForSelected互斥
        
boolean hasLabelForSelected：//点击选中的时候是否显示对应的label，和hasLabels是互斥的
        
boolean hasLines：//如果为true，则渲染器将为此轴绘制线条（网格）,开启时hasAxes必须为true
        
#### 2.布局使用
```
  <lecho.lib.hellocharts.view.ColumnChartView
        android:id="@+id/columnChartView"
        style="@style/match_width"
        android:layout_height="300dp"
        android:visibility="visible" />
```
#### 3.代码调用
##### 3.1 饼状图
```
 /**
     * 柱状图
     */
    private fun render() {
        var data = mutableListOf<Map<Float, Int>>()
        data.add(mapOf(50F to ChartUtils.pickColor()))
        data.add(mapOf(23F to ChartUtils.pickColor()))
        data.add(mapOf(10F to ChartUtils.pickColor()))
        data.add(mapOf(78F to ChartUtils.pickColor()))
        data.add(mapOf(45F to ChartUtils.pickColor()))
        data.add(mapOf(39F to ChartUtils.pickColor()))
        data.add(mapOf(21F to ChartUtils.pickColor()))
        data.add(mapOf(66F to ChartUtils.pickColor()))
        data.add(mapOf(38F to ChartUtils.pickColor()))
        data.add(mapOf(29F to ChartUtils.pickColor()))

        ColumnChartRender.Builder()
                .chartView(columnChartView)
                .columnData(data)
                .hasAxes(true)
                .hasLines(true)
                .hasLabels(true)
                .hasLabelForSelected(true)
                .build()
                .render()
    }  
```

#### 4.点击事件
```
  columnChartView.onValueTouchListener = object : PieChartOnValueSelectListener {
            override fun onValueSelected(arcIndex: Int, value: SliceValue?) {
                
            }

            override fun onValueDeselected() {
            }
        }
```