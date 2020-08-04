# 通用组件以及工具类module
---
主要包含以下组件
* 页面底部文字水印组件
* 图片九宫格组件
* 轮播组件
* 拨号盘组件

# 一、WaterMarkComponent组件
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

# 二、ImageNice9Component组件
## 如何使用
#### 1.属性说明
* nice9_itemMargin：item之间的间距
* nice9_candrag：是否支持拖拽，默认为false
* nice9_tipText：提示文本
* nice9_tipColor：提示文本颜色
* nice9_tipBgColor：提示文本背景色
* nice9_tipBgDrawable：提示文本背景drawable
* nice9_openEditModel：打开编辑模式，默认关闭
* nice9_openNormalModel：打开通用显示模式，默认为动态显示

#### 1.属性方法说明
* bindData(List<String> pictures)：绑定要显示的数据
* setCanDrag(boolean canDrag)：设置是否可以拖拽
* setItemDelegate(ItemDelegate itemDelegate)：设置item的代理，这里为点击回调
* List<String> getAfterPicList()：获取更改后的图片列表
* setTipBgDrawable(Drawable tipBgDrawable)：设置提示文字背景
* setTipColor(int tipBgColor)：设置提示文字颜色
* setTipBgColor(int tipBgColor)：设置提示背景颜色
* setTipText(String string)：设置提示文字
* openNormalDisplay(boolean normal)：打开通用显示模式
* openEditModel(boolean canEdit)：打开编辑模式

#### 2.布局使用
```
 <com.mti.component.common.nice9.ImageNice9Component 
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/ivNice9"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:nice9_openEditModel="true"
        app:nice9_openNormalModel="false"
        app:nice9_candrag="true"
        app:nice9_itemMargin="@dimen/size_4"
        app:nice9_tipColor="@color/black_hint"
        app:nice9_tipText="长按拖拽调整位置" />
```

# 三、轮播图BannerComponent组件
## 如何使用
这里提供广告轮播组件，与其他组件不同，依赖第三方库。

#### 1.布局使用
首先引入第三方日历库
implementation 'com.github.bumptech.glide:glide:4.5.0'
```
 <com.youth.banner.Banner
                android:id="@+id/banner"
                banner:image_scale_type="fit_xy"
                banner:banner_default_image="@drawable/bg_light_blue_rect"
                android:layout_margin="@dimen/size_12"
                android:background="@drawable/bg_light_blue_rect"
                android:layout_width="match_parent"
                android:layout_height="@dimen/size_130" />
```

#### 2.构造加载数据

```
    override fun initViews(savedInstanceState: Bundle?) {
        initData()
        initBanner()
        initBanner1()
    }
    
    
    //构造数据
    private fun initData() {
        banners.add(BannerEntry(url = "https://goss.veer.com/creative/vcg/veer/800water/veer-144021662.jpg"))
        banners.add(BannerEntry(url = "https://goss.veer.com/creative/vcg/veer/800water/veer-144201181.jpg"))
        banners.add(BannerEntry(url = "https://goss.veer.com/creative/vcg/veer/800water/veer-149517314.jpg"))

        banners1.add(BannerEntry(url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3793857646,451898184&fm=26&gp=0.jpg"))
        banners1.add(BannerEntry(url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590412163895&di=186f66618d41cfcf1d5faa3b39aa0b06&imgtype=0&src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20190807%2F14%2F1565158609-VjxRlnSHMg.jpeg"))
        banners1.add(BannerEntry(url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590412248233&di=6786d5deb5983ab0bf97972ea24cfd6e&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D3947860992%2C1668231400%26fm%3D214%26gp%3D0.jpg"))

        titles.add("美好一天")
        titles.add("健康 温暖")
        titles.add("祈祷 怀念")
    }
```

#### 3.配置轮播广告
配置轮播广告属性及监听
```
    private fun initBanner() {

        //设置图片加载器
        banner.setImageLoader(BannerLoader())
        //设置图片集合
        banner.setImages(banners)
        //设置延时
        banner.setDelayTime(3000)
        //设置加载动画
        banner.setBannerAnimation(Transformer.ZoomOutSlide)
        //添加点击事件
        banner.setOnBannerListener { position: Int ->
            toast("position-$position")
        }
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }
```

#### 4.设置生命周期启动与停止
```
    override fun onStart() {
        super.onStart()
        //自动启动
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        //关闭自启动
        banner.stopAutoPlay()
    }
```

# 四、拨号盘DialComponent组件
## 如何使用
这里提供拨号盘组件，与其他组件使用方式不同。非直接提供view ,而是直接提供弹出创建面板方法

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