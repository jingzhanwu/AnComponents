package com.mti.component.master.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mti.component.common.util.DisplayTool.dip2px
import com.mti.component.master.model.BannerEntry
import com.youth.banner.loader.ImageLoader

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/25
 * @change
 * @describe describe
 **/
class BannerLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val entry: BannerEntry = path as BannerEntry
        val roundRadius: Int = dip2px(context, 10f)
        val requestManager: RequestManager = Glide.with(context!!)


        var builder = if (entry.url.isNullOrBlank())
            requestManager.load(entry.url)
        else requestManager.load(entry.url)


        builder.apply(RequestOptions.bitmapTransform(RoundedCorners(roundRadius)))
                .into(imageView!!)

    }


}