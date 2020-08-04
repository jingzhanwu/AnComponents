package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @anthor created by jzw
 * @date 2020/6/22
 * @change
 * @describe 首页组件楼层实体
 **/
@Parcelize
class ComponentSection(var title: String, val component: List<ComponentEntry>) : Parcelable