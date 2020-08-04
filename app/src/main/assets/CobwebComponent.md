
## 如何使用
这里提供具有科技感的蛛网背景效果
### 蛛网背景组件

#### 1.布局使用

```
<com.mti.component.common.CobwebComponent
                android:id="@+id/cobWebComponent"
                android:layout_height="@dimen/size_300"
                style="@style/match_width" />
                
    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("CobwebComponent")
        showToolbarBackView()
        cobWebComponent.lineWidth = 5
        setMarkdownData("CobwebComponent.html", webView)
    }                
```


#### 2.主要属性

```             
    override fun initViews(savedInstanceState: Bundle?) {
        cobWebComponent.lineWidth = 5
    }                
```


* lineWidth：线的数量
* pointNum：默认小球数量

其他属性参考源码
