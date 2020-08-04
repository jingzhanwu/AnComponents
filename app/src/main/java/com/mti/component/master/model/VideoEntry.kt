package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @anthor created by jzw
 * @date 2020/5/29
 * @change
 * @describe 本地视频数据实体类
 **/
@Parcelize
data class VideoEntry(val name: String, val url: String, val size: Long, val duration: Long) : Parcelable