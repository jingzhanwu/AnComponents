package com.mti.component.master.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/25
 * @change
 * @describe describe
 **/

/**
 * 扩展Activity toast方法
 */


fun Context.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()
}



fun Context.dip2px(context: Context, dipValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}


fun Context.formatCurrentTime(zoneId: String = "GMT+8", dateFormat: String = "HH:mm:ss"): String {
    try {
        var dateFormat = SimpleDateFormat(dateFormat)
        val timeZone = TimeZone.getTimeZone(zoneId)
        val currentTime = System.currentTimeMillis()
        var calendar = Calendar.getInstance(timeZone)
        calendar.timeInMillis = currentTime
        dateFormat.timeZone = timeZone
        return dateFormat.format(calendar.time)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}