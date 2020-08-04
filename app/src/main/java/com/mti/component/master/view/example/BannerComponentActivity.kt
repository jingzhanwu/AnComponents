package com.mti.component.master.view.example

import android.os.Bundle
import com.mti.component.master.R
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.model.BannerEntry
import com.mti.component.master.util.BannerLoader
import com.mti.component.master.util.toast
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_banner_component.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 广告轮播
 **/
class BannerComponentActivity : BaseActivity() {
    private var banners = mutableListOf<BannerEntry>()
    private var banners1 = mutableListOf<BannerEntry>()
    private var titles = mutableListOf<String>()

    override fun initViews(savedInstanceState: Bundle?){
        setToolbarTitle("BannerComponent")
        showToolbarBackView()
        initData()
        initBanner()
        initBanner1()
        setMarkdownData("BannerComponent.html", webView)

    }

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


    override fun getLayoutId(): Int = R.layout.activity_banner_component


    private fun initBanner() {

        //设置图片加载器
        banner.setImageLoader(BannerLoader())
        //设置图片集合
        banner.setImages(banners)
        banner.setDelayTime(3000)
        banner.setBannerAnimation(Transformer.ZoomOutSlide)
        banner.setOnBannerListener { position: Int ->
            toast("position-${position}")
        }
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }


    private fun initBanner1() {

        //设置图片加载器
        banner1.setImageLoader(BannerLoader())
        //设置图片集合
        banner1.setImages(banners1)
        banner1.setBannerTitles(titles)
        banner1.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner1.setBannerAnimation(Transformer.Accordion)
        banner1.setOnBannerListener { position: Int ->
            toast("position-$position")
        }
        banner1.start()
    }


    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }
}