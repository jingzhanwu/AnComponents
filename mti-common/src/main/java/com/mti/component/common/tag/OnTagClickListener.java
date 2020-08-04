package com.mti.component.common.tag;

import com.mti.component.common.entry.TagEntry;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/28
 * @change
 * @describe describe
 **/
public interface OnTagClickListener {
    void onItemClick(int position, TagEntry tagEntry);
    default void onItemChanged(int position, TagEntry tagEntry){}
    default void onItemDelete(int position, TagEntry tagEntry){}
}
