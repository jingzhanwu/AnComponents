
## 如何使用
这里提供广告轮播组件，与其他组件不同，依赖第三方库。
### 广告轮播组件

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

