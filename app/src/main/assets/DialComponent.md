
## 如何使用
这里提供拨号盘组件，与其他组件使用方式不同。非直接提供view ,而是直接提供弹出创建面板方法
### 拨号盘组件

#### 1.Java代码中使用
事件创建拨号盘面板前，需先设置监听事件和相关属性

```
     override fun initViews(savedInstanceState: Bundle?) {
        //点击按钮弹出弹窗
        tvEffectShow.setOnClickListener {
            DialPlateHelper.create()?.run {
                addOnVoiceCLickListener { toast("语音") }
                addOnVideoClickListener { toast("视频") }
                //设置字符最大12个
                maxCharacters = 12
                //拨号盘底部左边图标
                leftResId=R.drawable.dropshadow
                //拨号盘底部右边图标
                rightResId=R.drawable.black_background
                //实际创建拨号盘面板前，先设置属性、监听事件
                createDialPlate(supportFragmentManager)
            }
        }
    }

```


