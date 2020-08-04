package com.mti.component.common.entry;

import java.io.Serializable;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/27
 * @change
 * @describe 标签
 **/
public class TagEntry implements Serializable {
    private String id;
    private String groupId;
    /**
     * 标签名称
     */
    private String tagName;
    private String tagValue;
    /**
     * 是否选中
     */
    private boolean checked;

    public TagEntry() {
    }

    public TagEntry(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public TagEntry setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public String getTagValue() {
        return tagValue;
    }

    public TagEntry setTagValue(String tagValue) {
        this.tagValue = tagValue;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public TagEntry setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public TagEntry setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getId() {
        return id;
    }

    public TagEntry setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "TagEntry{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", checked=" + checked +
                '}';
    }

}
