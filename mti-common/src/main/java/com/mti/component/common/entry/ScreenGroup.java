package com.mti.component.common.entry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2019/7/17
 * @change
 * @describe 筛选条件分组实体
 **/
public class ScreenGroup implements Serializable {
    private String key;
    private String title;
    /**
     * 是否显示删除
     */
    private boolean showingDelete;

    /**
     * 是否可选择
     */
    private boolean optional;
    /**
     * 是否多选
     */
    private boolean isMultiple;

    private List<TagEntry> tags;

    public String getKey() {
        return key;
    }

    public ScreenGroup key(String key) {
        this.key = key;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ScreenGroup title(String title) {
        this.title = title;
        return this;
    }

    public List<TagEntry> getTags() {
        return tags;
    }

    public ScreenGroup tags(List<TagEntry> tags) {
        this.tags = tags;
        return this;
    }


    public boolean isShowingDelete() {
        return showingDelete;
    }

    public ScreenGroup showingDelete(boolean showingDelete) {
        this.showingDelete = showingDelete;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }

    public ScreenGroup optional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public ScreenGroup multiple(boolean multiple) {
        isMultiple = multiple;
        return this;
    }

    @Override
    public String toString() {
        return "ScreenGroup{" +
                "key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", showingDelete=" + showingDelete +
                ", optional=" + optional +
                ", isMultiple=" + isMultiple +
                ", tags=" + tags +
                '}';
    }

    public Object deepClone() throws IOException, OptionalDataException,
            ClassNotFoundException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);


        oo.close();
        bo.close();

        Object newObj = (oi.readObject());

        oi.close();
        bi.close();
        return newObj;
    }
}
