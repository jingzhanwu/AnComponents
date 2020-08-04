# 折线图图表使用
## 如何使用
由于需要配置的属性比较多，所以这里使用Builder设计模式让调用者更加便捷

#### 1.属性与方法说明（这里只列举比较常用的，属性对应同名方法）

LineChartView lineChartView：//图标view

int[] lineColor：//折线颜色

 String xName：//x轴名称

String yName：//y轴名称

List<String> xData：//x轴数据

List<List<Float>> lineData：//y轴数据,可以支持多条线

int axisLineColor：//轴线的颜色

int axisTextColor：//轴字体颜色

boolean hasAxes：//是否显示x，y轴线

boolean cubic：//曲线是否平滑，即是曲线还是折线

boolean hasLabels：//曲线的数据坐标是否加上备注

boolean hasLabelForSelected：//点击选中的时候是否显示对应的label,和hasLabels是互斥的

boolean hasLines：//是否用线显示。如果为false 则没有曲线只有点显示

#### 2.布局使用
```
 <lecho.lib.hellocharts.view.LineChartView
        android:id="@+id/lineChartView"
        style="@style/match_width"
        android:layout_height="300dp"
        android:visibility="visible" />
```
#### 3.代码调用
```
    //折线数据
    private val lineData = listOf(17F, 12F, 25F, 28F, 23F, 30F, 15F)
    /**
     * 折线图
     */
    private fun render() {
        //底部轴线的显示数据
        val xData = listOf<String>("4/21", "4/30", "5/08", "6/01", "6/03", "6/04", "6/06")
        val color = intArrayOf(Color.parseColor("#00EAFF"))
        //渲染图表数据
        LineChartRender.Builder()
                .chartView(lineChartView)
                .lineData(arrayListOf(lineData))
                .lineColor(color)
                .xName("时间/天")
                .yName("预警数量/起")
                .axisTextColor(Color.parseColor("#6699ff"))
                .xData(xdata)
                .hasAxes(true)
                .hasPoints(false)
                .strokeWidth(2)
                .areaTransparency(40)
                .build()
                .render()
    }
```
#### 3.曲线设置

折线与曲线的控制是由 cubic属性控制的，所以在渲染时只需要设置cubic为true即可

---

例：
```
 LineChartRender.Builder()
                .chartView(cubicLineChartView)
                .lineData(arrayListOf(data1, data2))
                .xData(xdata)
                .xName("时间/天")
                .yName("预警数量/起")
                .cubic(true) //设置为曲线
                .axisTextColor(Color.parseColor("#ff66ff"))
                .build()
                .render()
```
