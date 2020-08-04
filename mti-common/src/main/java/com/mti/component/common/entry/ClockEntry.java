package com.mti.component.common.entry;


import java.io.Serializable;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2018/7/10
 * @change
 * @describe describe
 *  打卡信息
 **/
public class ClockEntry implements Serializable {

    /**
     *
     {
     "id": 9633,
     "personId": 1,
     "name": null,
     "userCode": null,
     "startTime": 1531129539000,
     "startX": 35.235,
     "startY": 52.2114,
     "endTime": 1531129597000,
     "endX": 35.235,
     "endY": 52.2114,
     "zbx": "baidu",
     "longTime": 57789,
     "createTime": 1531129539000,
     "updateTime": 1531129597000,
     "entryTime": null,
     "valid": null,
     "deleted": 0,
     "status": "2"     当前状态 0未上班  1已结上班打卡  2已经下班打卡
     }
     */


    private String id;
    private String personId;
    private String name;
    private String userCode;
    private String startTime;
    private String startX;
    private String startY;
    private String endTime;
    private String endX;
    private String endY;
    private String zbx;
    private String longTime;
    private String createTime;
    private String updateTime;
    private String entryTime;
    /**
     * 当前状态 0未上班  1已结上班打卡  2已经下班打卡
     */
    private String status;
    /**
     * 上班地址
     */
    private String startAddress;
    /**
     * 下班地址
     */
    private String endAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartX() {
        return startX;
    }

    public void setStartX(String startX) {
        this.startX = startX;
    }

    public String getStartY() {
        return startY;
    }

    public void setStartY(String startY) {
        this.startY = startY;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndX() {
        return endX;
    }

    public void setEndX(String endX) {
        this.endX = endX;
    }

    public String getEndY() {
        return endY;
    }

    public void setEndY(String endY) {
        this.endY = endY;
    }

    public String getZbx() {
        return zbx;
    }

    public void setZbx(String zbx) {
        this.zbx = zbx;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    @Override
    public String toString() {
        return "ClockInfo{" +
                "id='" + id + '\'' +
                "personId='" + personId + '\'' +
                ", name='" + name + '\'' +
                ", userCode='" + userCode + '\'' +
                ", startTime='" + startTime + '\'' +
                ", startX='" + startX + '\'' +
                ", startY='" + startY + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endX='" + endX + '\'' +
                ", endY='" + endY + '\'' +
                ", zbx='" + zbx + '\'' +
                ", longTime='" + longTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", status='" + status + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                "} " + super.toString();
    }
}
