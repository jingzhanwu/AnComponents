
## 如何使用
这里主要价绍两种进度加载组件，背景加载组件和直线加载组件。
### 进度加载组件

#### 1.布局使用
背景加载组件
```
<com.mti.component.common.BackgroundLoadingEffectComponent
                        android:id="@+id/backgroundLoadingEffectComponent"
                        android:layout_width="match_parent"
                        android:layout_height="@dimen/size_80" />
```
直线加载组件
```
<com.mti.component.common.PathEffectDividerComponent
                    android:id="@+id/pathDivider"
                    android:layout_width="match_parent"
                    android:layout_height="@dimen/size_4"
                    android:layout_marginStart="@dimen/size_20"
                    android:layout_marginTop="@dimen/size_12"
                    android:layout_marginEnd="@dimen/size_20" />

```
#### 2.启动加载

```
    override fun initViews(savedInstanceState: Bundle?) {
        backgroundLoadingEffectComponent.startProgress()
        pathDivider.startLoading(9000)
    }
    
```




