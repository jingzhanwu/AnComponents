package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author created by jzw
 * @date 2020/5/22
 * @change
 * @describe 组件列表描述实体类
 **/
@Parcelize
class ComponentEntry(var name: String, var imageRes: Int, var desc: String, var tag:String) : Parcelable