
## 如何使用
这里提供标签组件。
### 标签组件

#### 1.布局使用

```
<com.mti.component.media.tag.FlowComponent
                android:id="@+id/interestFlowComponent"
                style="@style/match_width" />
```

#### 2.构造加载数据

```
    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("FlowComponent")
        showToolbarBackView()
        val interests = listOf<String>("唱歌", "看书", "划船", "饲养宠物",
                "保龄球", "登山", "摄影", "下各种棋", "吃美食",
                "书法", "看新闻", "做饭")

        val interestData = interests.map { TagEntry("$it") }.toList()

        interestFlowComponent.isShowingDelete=false
        interestFlowComponent.initAddListener(interestData, object :OnTagClickListener{
            override fun onItemClick(position: Int) {
                toast("点击了${interestData[position].tagName}")
            }

            override fun onItemDelete(position: Int) {

            }
        })

    }
```


