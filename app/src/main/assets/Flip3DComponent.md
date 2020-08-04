
## 如何使用
这里提供3d翻转效果组件，相当于一个3d容器，使用时直接添加相应布局，事件需要自己处理。这里主要用做展示类页面使用。
### 3d翻转组件

#### 1.布局使用

使用时，像其他容器一样使用，若有滑动事件，需要自行处理
```
 <com.mti.component.common.Flip3DComponent
                android:layout_gravity="center_horizontal"
                android:layout_width="250dp"
                android:layout_height="250dp"
                android:layout_centerInParent="true"
                android:layout_marginLeft="5dp"
                android:layout_marginRight="5dp">

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:background="@drawable/picture_carousel" />

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:background="@drawable/picture_cobweb" />

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:background="@drawable/picture_punch" />

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:background="@drawable/picture_video" />
            </com.mti.component.common.Flip3DComponent>
```

