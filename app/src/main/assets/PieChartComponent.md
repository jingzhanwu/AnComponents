# 饼图图表使用
## 如何使用
由于需要配置的属性比较多，所以这里使用Builder设计模式让调用者更加便捷

#### 1.属性与方法说明（这里只列举比较常用的，属性对应同名方法）

PieChartView pieChartView： //图表view
       
Map<Float, Integer> sliceValues： //数据，key=值，value=对应颜色值
      
String centerText1：  //圆环中间文本1
       
String centerText2： //圆环中间文本2
       
int centerText1Color = Color.BLACK： //text1的颜色

int centerText2Color = Color.BLACK： //text2的颜色
       
int centerText1FontSize = 18： //text1的字体大小，sp

int centerText2FontSize = 12： //text2的字体大小，sp

boolean hasLabels： //是否显示数据label文本，此属性和hasLabelForSelected是互斥的
       
boolean hasLabelsOutside： //外圈是否显示label文本
       
boolean hasCenterCircle： //是否中间是空圆形
       
boolean hasCenterText1： //空心圆中间是否显示一个文本
       
boolean hasCenterText2： //空心圆中间是否显示两行文本
        
boolean hasLabelForSelected：//点击选中的时候是否显示对应的label，和hasLabels是互斥的
       
int slicesSpacing： //项与项之间的间距
       
boolean useDefaultFontTypeface： //是否使用默认的字体库
        
#### 2.布局使用
```
 <lecho.lib.hellocharts.view.PieChartView
            android:id="@+id/pieChartView"
            android:layout_width="240dp"
            android:layout_height="240dp"
            android:layout_alignParentRight="true"
            android:visibility="visible" />
```
#### 3.代码调用
##### 3.1 饼状图
```
    /**
     * 饼状图表
     */
    private fun pieChartViewRender() {
        var data = mutableMapOf<Float, Int>(
                pieData[0] to Color.parseColor("#ffac41"),
                pieData[1] to Color.parseColor("#2a6dbf"),
                pieData[2] to Color.parseColor("#29cece"),
                pieData[3] to Color.parseColor("#379cf8"),
                pieData[4] to Color.parseColor("#01a968")
        )
        PieChartRender.Builder()
                .chartView(pieChartView)
                .sliceValues(data)
                .hasLabels(false)
                .hasCenterCircle(false)
                .hasLabelForSelected(true)
                .build()
                .render()
    }
```
##### 3.2 环形图
```
    /**
     * 环形图表
     */
    private fun ringChartViewRender() {
        var data = mutableMapOf<Float, Int>(
                ringData[0] to Color.parseColor("#FF0033"),
                ringData[1] to Color.parseColor("#ff6633"),
                ringData[2] to Color.parseColor("#33ff99"),
                ringData[3] to Color.parseColor("#cc00ff")
        )
        PieChartRender.Builder()
                .chartView(ringChartView)
                .sliceValues(data)
                .hasLabels(true)
                .hasCenterCircle(true)
                .hasCenterText1(true)
                .hasCenterText2(true)
                .centerText1("MTI")
                .centerText2("警情统计")
                .hasLabelForSelected(false)
                .build()
                .render()
    }
```

#### 4.点击事件
```
  pieChartView.onValueTouchListener = object : PieChartOnValueSelectListener {
            override fun onValueSelected(arcIndex: Int, value: SliceValue?) {
                
            }

            override fun onValueDeselected() {
            }
        }
```