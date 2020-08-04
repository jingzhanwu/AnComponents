
## 如何使用
### TimeLineComponent 组件
#### 1.属性说明
* marker：时间轴的节点marker颜色
* markerSize：marker大小
* markerPaddingLeft：marker左边的边距
* markerPaddingTop：marker上边的边距
* markerPaddingRight：marker右边的边距
* markerPaddingBottom：marker下边的边距
* markerInCenter：设置marker在线的中间
* startLineColor：开始线颜色
* endLineColor：结束线颜色
* lineWidth：线宽度
* lineStyleDashGap：虚线间隙大小
* lineStyleDashLength：虚线长度
* lineOrientation：时间轴方向
* linePadding：线边距

#### 2.常用方法说明
* setMarker()：设置节点marker的drawable
* setMarkerColor()：节点marker颜色
* setStartLineColor()：设置开始线的颜色
* setEndLineColor()：设置结束线的颜色
* setMarkerSize()：设置marker的大小
* setMarkerPaddingLeft()：设置marker的左边padding
* setMarkerPaddingTop()：设置marker的上边padding
* setMarkerPaddingRight()：设置marker的右边padding
* setMarkerPaddingBottom()：设置marker的下边padding
* setMarkerInCenter()：设置marker在线的中间显示
* setLineWidth()：设置线的宽度
* setLineOrientation()：设置时间轴方向，支持水平和垂直
* setLineStyle()：设置线段的样式，支持实线和虚线
* showStartLine()：显示开始线
* showEndLine()：显示结束线
* getTimeLineComponentType()：获得时间轴当前设置的类型

#### 3.布局使用
```
<com.mti.component.alarm.TimeLineComponent
        android:id="@+id/timeline"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:endLineColor="@color/colorAccent"
        app:lineStyle="dash"
        app:lineWidth="3dp"
        app:markerSize="20dp"
        app:lineOrientation="vertical"
        app:lineStyleDashGap="2dp"
        app:lineStyleDashLength="5dp"
        app:startLineColor="@color/colorAccent" />
```