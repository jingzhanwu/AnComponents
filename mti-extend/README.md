# 扩展组件类module
---
主要包含以下组件
* 原生TTS组件
* ORC识别组件
* 电子签名组件
* FloatingActionMenu组件
* 图标类组件

# 一、SignatureComponent组件
## 如何使用
#### 1.属性方法说明（这里只列举比较常用的）

* setColor(int mColor)：设置画笔的颜色，默认为黑色
* setBackgroundColor(int mBackgroundColor)：设置背景颜色
* setStyle(Paint.Style style)：设置画笔的样式，默认实线
* setSize(float size)：设置画笔线段的宽度，默认5px
* setInteractionMode(int interactionMode)：设置交互模式，默认是DRAW_MODE模式，还有SELECT_MODE，ROTATE_MODE或LOCKED_MODE
* getInteractionMode()：返回当前的交互模式
* setBackgroundMode()：设置画板背景的模式，默认是纯白色模式
* undo()：撤销一步操作
* redo()：恢复上一步撤销的操作
* cleanPage()：清空当前画布，此操作一旦执行将无法恢复
* getCanvasBitmap()：获得当前画布上的所有对象
* markSaved()：标记当前画布上的对象已经保存
* isSaved()：当前画布的对象是否已经保存
* getUnsavedDrawablesList()：返回在调用markSaved方法之后添加的绘制对象集合
* revertUnsaved()：删除在调用markSaved方法之后添加的绘制对象，不触发DeletionConfirmationListener
* getCroppedCanvasBitmap()：获取目前画布上绘制的bitmap图像，删除绘制对象周围的所有边距
* getDrawablesList()：按插入顺序返回所有的绘制对象集合
* deletionConfirmed(CDrawable drawable)：删除经过确认的drawable对象
* setDeletionListener()：设置删除监听器，绘制对象被删除后回调
* setScaleRotateListener()：设置收拾缩放旋转监听器
* setDeletionConfirmationListener()：设置删除确认监听器，如果设置此监听，则必须手动调用deletionConfirmed()删除

#### 2.布局使用
```
 <com.mti.component.extend.SignatureComponent
                android:id="@+id/signatureView"
                android:layout_width="match_parent"
                android:layout_height="400dp"/>
```
#### 3.代码使用
```
  floatUndo.setOnClickListener(View.OnClickListener {
            println("点击了撤销")
            signatureView.undo()
        })
        floatClean.setOnClickListener(View.OnClickListener {
            println("点击了清空")
            signatureView.cleanPage()
        })
        floatSave.setOnClickListener {
            println("点击了保存")
            var result = signatureView.canvasBitmap
            if (result != null) {
                layoutResult.visibility = View.VISIBLE
                signatureBitmap.setImageBitmap(result)
            } else {
                layoutResult.visibility = View.GONE
            }
            signatureView.markSaved()
            signatureView.cleanPage()
        }

        signatureView.setDeletionListener { println("对象被删除$it") }
        signatureView.setScaleRotateListener(object : SignatureComponent.ScaleRotateListener {
            override fun endRotate() {
                println("结束旋转")
            }

            override fun startRotate(): Boolean {
                println("开始旋转")
                return true
            }
        })
```

# 二、FloatingActionMenu组件
## 如何使用
#### 1.属性方法说明（这里只列举比较常用的）
##### FloatingActionsMenu 属性
app:fab_addButtonColorNormal：菜单按钮的背景色
app:fab_addButtonColorPressed：按钮按下时的颜色
app:fab_labelStyle：菜单按钮左边label文本的样式
app:fab_original_icon：菜单按钮的图标

##### FloatingActionButton 属性       
app:fab_colorNormal：子button的背景色
app:fab_colorPressed：子button按下时的背景
app:fab_icon：子button的图标
app:fab_size：子button的尺寸模式，提供两种模式：mini和normal
app:fab_title：子button左边的显示文本

#### 2.布局使用
```
<com.mti.component.extend.floatbutton.FloatingActionsMenu
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/floatingMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:fab_addButtonColorNormal="@color/colorPrimary"
        app:fab_addButtonColorPressed="#F6F7FB"
        app:fab_addButtonPlusIconColor="@color/white"
        app:fab_labelStyle="@style/menu_labels_style"
        app:fab_original_icon="@drawable/fab_menu">

        <com.mti.component.extend.floatbutton.FloatingActionButton
            android:id="@+id/floatUndo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fab_colorNormal="#EE7000"
            app:fab_colorPressed="#33000000"
            app:fab_icon="@drawable/fab_undo"
            app:fab_size="mini"
            app:fab_title="撤销" />

        <com.mti.component.extend.floatbutton.FloatingActionButton
            android:id="@+id/floatClean"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fab_colorNormal="#00D070"
            app:fab_colorPressed="#33000000"
            app:fab_icon="@drawable/fab_redo"
            app:fab_size="mini"
            app:fab_title="清空" />

        <com.mti.component.extend.floatbutton.FloatingActionButton
            android:id="@+id/floatSave"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fab_colorNormal="#00CAAC"
            app:fab_colorPressed="#33000000"
            app:fab_icon="@drawable/fab_feedback"
            app:fab_size="mini"
            app:fab_title="保存" />
    </com.mti.component.extend.floatbutton.FloatingActionsMenu>
```
#### 3.点击事件添加
```
floatUndo.setOnClickListener(View.OnClickListener {
            println("点击了撤销")
        })
        floatClean.setOnClickListener(View.OnClickListener {
            println("点击了清空")
        })
        floatSave.setOnClickListener {
            println("点击了保存")
        }
```

# 三、折线图图表使用
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

# 四、饼图图表使用
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

# 五、柱状图图表使用
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
