# ImageNice9Component组件
## 如何使用
#### 1.属性说明
* nice9_itemMargin：item之间的间距
* nice9_candrag：是否支持拖拽，默认为false
* nice9_tipText：提示文本
* nice9_tipColor：提示文本颜色
* nice9_tipBgColor：提示文本背景色
* nice9_tipBgDrawable：提示文本背景drawable

#### 1.属性方法说明
* bindData(List<String> pictures)：绑定要显示的数据
* setCanDrag(boolean canDrag)：设置是否可以拖拽
* setItemDelegate(ItemDelegate itemDelegate)：设置item的代理，这里为点击回调
* List<String> getAfterPicList()：获取更改后的图片列表
* setTipBgDrawable(Drawable tipBgDrawable)：设置提示文字背景
* setTipColor(int tipBgColor)：设置提示文字颜色
* setTipBgColor(int tipBgColor)：设置提示背景颜色
* setTipText(String string)：设置提示文字

#### 2.布局使用
```
 <com.mti.component.common.nice9.ImageNice9Component 
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/ivNice9"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:nice9_candrag="true"
        app:nice9_itemMargin="@dimen/size_4"
        app:nice9_tipColor="@color/black_hint"
        app:nice9_tipText="长按拖拽调整位置" />
```