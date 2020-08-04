package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @company 上海道枢信息科技-->
 * @author created by xuebing
 * @date 2020/5/25
 * @change
 * @describe 打卡信息
 **/
@Parcelize
class PunchInfoEntry(var punchCount: Int? = 0, //打卡次数
                     var punchDuration: Int? = 0, //打卡时长
                     var punchOrder: String? = "", //打卡班次
                     var punchTime: String? = "", //打卡时间
                     var punchDes: String? = "", //打卡描述
                     var address: String? = "", //打卡地址
                     var longitude: String? = "", //经度
                     var latitude: String? = "", //纬度
                     var punchStatus: String? = ""//打卡状态
) : Parcelable