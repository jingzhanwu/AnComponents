package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @company 上海道枢信息科技-->
 * @author created by xuebing
 * @date 2020/5/25
 * @change
 * @describe 日程事件
 **/
@Parcelize
class ScheduleEntry(var name: String? = "", //名称
                    var periodTime: String? = "", //时间段
                    var longitude: String? = "", //地址
                    var address: String? = "", //经度
                    var latitude: String? = "", //纬度
                    var serviceType: String? = "", //勤务类型
                    var positionType: String? = "", //岗位类型
                    var leader: String? = "", //值班领导
                    var members: Array<String>? = null,//组员
                    var requirement: String? = ""   //岗位要求
) : Parcelable {
}