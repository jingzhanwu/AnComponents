package com.mti.component.master.callback

/**
 * @anthor created by jzw
 * @date 2020/5/25
 * @change
 * @describe listview item点击事件
 **/
interface OnAdapterItemClickListener<T> {
     fun onItemClick(position: Int, item: T)
}