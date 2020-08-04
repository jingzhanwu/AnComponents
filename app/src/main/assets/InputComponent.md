
## 如何使用
### InputComponent 组件
#### 1.属性说明
* defaultText：输入框默认填充文本
* inputBackground：输入框区域背景
* inputHintText：输入框hint文本
* inputTextColor：输入框字体颜色
* inputTextSize：输入框字体大小
* labelText：左边label文本
* labelTextSize：左边label文本大小
* labelTextColor：左边label文本颜色
* inputMinHeight：右边输入区域的最小高度，默认42dp
* openEdit：是否开启编辑模式，默认是TextView模式
* showLabel：是否显示左边label，默认显示
* showLeftImage：是否显示左边ImageView，默认不显示
* showRightImage：是否显示右边ImageView，默认不显示
* leftImage：左边ImageView的图标
* showMarker：是否显示左边的标记view
* disableInput：是否禁止输入，默认不禁止

#### 2.布局使用
```
 <com.mti.component.alarm.InputComponent
                 android:id="@+id/inputName"
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content"
                 android:layout_marginLeft="@dimen/size_5"
                 app:inputHintText="请输入姓名"
                 app:inputTextColor="@color/colorPrimary"
                 app:labelText="报警人:"
                 app:leftImage="@drawable/ic_saoyisao"
                 app:openEdit="true"
                 app:rightImage="@mipmap/ic_arrow_right"
                 app:showLeftImage="true"
                 app:showMarker="true"
                 app:showRightImage="true" />
```
#### 3.点击事件
提供左右ImageView、右边输入区域的点击事件
```
 //左右ImageView的点击事件
 inputAlarmPerson.setOnRightImageClickListener(v -> {
            //右边view的点击
            Toast.makeText(InputComponentActivity.this, "扫描身份证", Toast.LENGTH_SHORT).show();
        });
 //整个输入框区域点击事件
 inputAlarmMode.setOnInputAreaClickListener(v -> {
            Toast.makeText(InputComponentActivity.this, "选择报警方式", Toast.LENGTH_SHORT).show();
        });    
```