
## 如何使用
这里提供WorkPunchLayoutComponent上下班打卡组件，WorkPunchLayoutComponent组件是有两个PunchLayoutComponent组件构成，
其中处理了上下班打卡逻辑，方便使用。更精细化控制，可使用PunchLayoutComponent组件，这里主要以WorkPunchLayoutComponent组件为例说明。
### WorkPunchLayoutComponent 打卡组件

#### 1.布局使用
```
<com.mti.component.alarm.punch.WorkPunchLayout
    android:id="@+id/workPunchLayout"
    style="@style/match_width"/>
```
#### 2.添加监听事件
提供上下班打卡点击监听
```
   override fun initViews(savedInstanceState: Bundle?) {
        workPunchLayout.addOnWorkPunchClickListener(this)
    }   
    
    //监听说明
    public interface OnWorkPunchClickListener {
        /**
         * 上班打卡
         */
        void onGoWork();

        /**
         * 下班打卡
         */
        void onGoOffWork();
    }    
    
```
#### 3.开启时间
在设置监听之后或者时间接口请求数据后开启startTimer
```
   override fun initViews(savedInstanceState: Bundle?) {
        workPunchLayout.addOnWorkPunchClickListener(this)
        workPunchLayout.startTimer()
    }   
    
```

#### 4.执行打卡渲染数据
在打卡监听中渲染数据，同时结束加载动画
```
    //这里非实际使用，实际情况会在这里向后台发送打卡请求，请求成功后渲染打卡状态
    override fun onGoWork() {
        toast("上班")
        var clockEntry = ClockEntry()
                .apply {
                    status = "1"
                    startAddress = "太和时代广场"
                    startTime = formatCurrentTime(dateFormat = standardDateFormat)
                }
        //渲染数据状态
        renderView(clockEntry)
    }
    
   //渲染打卡状态 
   private fun renderView(clockEntry: ClockEntry) {
        this.workPunchLayout?.rendView(clockEntry)
        this.workPunchLayout?.cancelWorkPunchPerformance()
    } 
    
```

#### 5.关闭时间
在onDestroy()方法中调用，即关闭时间计数
```
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "时间结束");
        workPunchLayout.stopTimer();
    }
```

#### 6.主要方法说明
* setIndicatorColor：设置加载和背景颜色
* addOnWorkPunchClickListener：添加上下班打卡监听
* rendView：渲染接口数据
* cancelWorkPunchPerformance：结束打卡操作
* startTimer：开启时间计数stopTimer
* stopTimer：关闭时间计数

其他方法详情WorkPunchLayoutComponent