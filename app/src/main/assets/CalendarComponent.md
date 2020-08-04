# CalendarComponent日历组件
## 如何使用
这里提供日历组件，与其他组件不同，依赖第三方库。其中，日历组件主要使用两个类CalendarLayout和CalendarView，前者主要是控制上下滑动折叠，后者主要是显示日期和交互。
### 日历组件
这里以日历组件和列表结合使用为例，说明日历组件最基本用法，其他参考给出示例代码。所有demo示例在mti-calendar module模块中
#### 1.布局使用
首先引入第三方日历库
```
implementation 'com.haibin:calendarview:3.6.8'
```
```
<com.haibin.calendarview.CalendarLayout
        android:id="@+id/calendarLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#fff"
        android:orientation="vertical"
        app:calendar_content_view_id="@+id/recyclerView">

        <com.haibin.calendarview.CalendarView
            android:id="@+id/calendarView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#fff"
            app:calendar_padding="10dp"
            app:month_view="com.mti.calendar.simple.SimpleMonthView"
            app:calendar_height="46dp"
            app:current_month_lunar_text_color="#CFCFCF"
            app:current_month_text_color="#333333"
            app:min_year="2004"
            app:other_month_text_color="#e1e1e1"
            app:scheme_text="假"
            app:scheme_text_color="#333"
            app:scheme_theme_color="#333"
            app:selected_text_color="#fff"
            app:selected_theme_color="#333"
            app:week_background="#fff"
            app:week_text_color="#111"
            app:week_view="com.mti.calendar.simple.SimpleWeekView"
            app:year_view_day_text_color="#333333"
            app:year_view_day_text_size="9sp"
            app:year_view_month_text_color="#ff0000"
            app:year_view_month_text_size="20sp"
            app:year_view_scheme_color="#f17706"/>

        <com.mti.calendar.group.GroupRecyclerView
            android:id="@+id/recyclerView"
            app:group_background="@color/content_background"
            app:group_center="false"
            app:group_height="42dp"
            app:group_has_header="false"
            app:group_padding_left="16dp"
            app:group_padding_right="16dp"
            app:group_text_size="14sp"
            app:group_text_color="#555555"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/content_background" />
        　
    </com.haibin.calendarview.CalendarLayout>
```

#### 2.添加监听事件
在设置监听之后或者时间接口请求数据后开启startTimer
```
    @Override
    public void initViews(Bundle savedInstanceState) {
        //添加年份变化监听
        mCalendarView.setOnYearChangeListener(this);
        //添加日期选中监听
        mCalendarView.setOnCalendarSelectListener(this);
    }   
    
    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }
    
    //超出范围
   @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    //选中日期
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }
```

#### 3.添加日期数据
在设置监听之后或者时间接口请求数据后开启startTimer
```
    @Override
    protected void initData() {
        //当前年份
        int year = mCalendarView.getCurYear();
        //当前月份
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);

    }
```

#### 4.主要参数说明
CalendarLayout重要属性说明
* calendar_content_view_id：关联的RecyclerView列表的id

CalendarView重要属性说明

* month_view：定义日期view的全类名
* week_view：定期周view的全类名
* week_background：周背景色
* week_text_color：周文本颜色


