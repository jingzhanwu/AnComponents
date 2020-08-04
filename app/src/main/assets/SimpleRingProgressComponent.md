
## 如何使用

### 环形进度组件

#### 1.布局使用

```
                <com.mti.component.common.SimpleRingProgressComponent
                    android:id="@+id/simpleRingProgressComponent"
                    android:layout_width="match_parent"
                    android:layout_height="@dimen/size_230"
                    android:padding="@dimen/size_8"
                    ring:backColor="#eeeeee"
                    ring:backWidth="@dimen/size_14"
                    ring:progressRate="65"
                    ring:progressColor="@color/colorAccent"
                    ring:progressWidth="@dimen/size_18" />
```

#### 2.主要方法说明
初始化并添加监听，监听提供两个回调方法，分别是onItemDelete删除监听和onItemClick点击监听。




