package com.mti.component.media.entry;

import java.io.Serializable;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/27
 * @change
 * @describe 标签
 **/
public class AudioLabelEntry implements Serializable {
    private String id;
    /**
     * 标签名称
     */
    private String tagName;
    private String tagValue;
    /**
     * 是否选中
     */
    private boolean checked;


    public AudioLabelEntry(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "TagEntry{" +
                "id='" + id + '\'' +
                ", tagName='" + tagName + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", checked=" + checked +
                '}';
    }
}
