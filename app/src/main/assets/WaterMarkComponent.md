# WaterMarkComponent组件
## 如何使用
#### 1.属性方法说明
* showWaterMarkView(activity: Activity, content: String)
只提供一个打水印的方法，参数content 为水印文字

#### 2.代码使用
```
 fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_waterkark)
         //开始打水印，一定要在setContentView方法调用之后调用
        WaterMarkComponent.showWaterMarkView(this, "道枢MTI")
    }
```