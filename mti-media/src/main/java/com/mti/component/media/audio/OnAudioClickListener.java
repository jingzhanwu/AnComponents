package com.mti.component.media.audio;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/28
 * @change
 * @describe describe
 **/
public interface OnAudioClickListener {
    void onItemClick(int position);

    default void onItemDelete(int position){};
}
