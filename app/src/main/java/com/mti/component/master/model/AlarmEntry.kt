package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @anthor created by jzw
 * @date 2020/6/18
 * @change
 * @describe 警情实体
 **/
@Parcelize
class AlarmEntry(val alarmLevel: Int,
                 val alarmContent: String,
                 val alarmType: String,
                 val feedback: String,
                 val alarmTime: String) : Parcelable