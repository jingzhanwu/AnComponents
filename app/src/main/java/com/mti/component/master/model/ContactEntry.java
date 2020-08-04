package com.mti.component.master.model;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2020/5/9
 * @change
 * @describe 联系人
 **/
public class ContactEntry extends BaseIndexPinyinBean {
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobilenum;
    /**
     * 警号
     */
    private String jobnumber;

    public String getName() {
        return name;
    }

    public ContactEntry setName(String name) {
        this.name = name;
        return this;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public ContactEntry setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
        return this;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public ContactEntry setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
        return this;
    }


    public boolean isTop() {
        return isTop;
    }

    public ContactEntry setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return name;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }

    @Override
    public String toString() {
        return "ContactEntry{" +
                "isTop=" + isTop +
                ", name='" + name + '\'' +
                ", mobilenum='" + mobilenum + '\'' +
                ", jobnumber='" + jobnumber + '\'' +
                "} " + super.toString();
    }
}
