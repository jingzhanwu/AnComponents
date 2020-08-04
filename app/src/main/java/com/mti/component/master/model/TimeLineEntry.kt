package com.mti.component.master.model

import android.os.Parcelable
import com.mti.component.master.view.example.timeline.OrderStatus
import kotlinx.android.parcel.Parcelize

/**
 * @author created by jzw
 * @date 2020/5/22
 * @change
 * @describe 时间轴显示的数据实体
 **/
@Parcelize
class TimeLineEntry(var message: String, var date: String, var status: OrderStatus) : Parcelable