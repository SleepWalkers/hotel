package com.yaoxingyu.hotel.model;

import java.util.Date;

public class BizManage {

    private Long    id;

    private String  roomTypeName;

    private Integer roomCount;

    private Integer arrivedRoomCount;

    private Date    logDate;

    private Date    gmtCreate;

    private Date    gmtModify;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setArrivedRoomCount(Integer arrivedRoomCount) {
        this.arrivedRoomCount = arrivedRoomCount;
    }

    public Integer getArrivedRoomCount() {
        return arrivedRoomCount;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Date getGmtModify() {
        return gmtModify;
    }
}
