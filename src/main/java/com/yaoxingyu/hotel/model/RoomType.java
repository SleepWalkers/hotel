package com.yaoxingyu.hotel.model;

import java.util.Date;

public class RoomType {

    private Long    id;

    private String  roomTypeName;

    private String  roomPrice;

    private String  guanWangPrice;

    private String  memberPrice;

    private Integer roomArea;

    private String  bedType;

    private String  bedWidth;

    private Integer breakfast;

    private String  isAddBed;

    private Integer numberIn;

    private Integer broadband;

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

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public String getGuanWangPrice() {
        return guanWangPrice;
    }

    public void setGuanWangPrice(String guanWangPrice) {
        this.guanWangPrice = guanWangPrice;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public void setRoomArea(Integer roomArea) {
        this.roomArea = roomArea;
    }

    public Integer getRoomArea() {
        return roomArea;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedWidth(String bedWidth) {
        this.bedWidth = bedWidth;
    }

    public String getBedWidth() {
        return bedWidth;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setIsAddBed(String isAddBed) {
        this.isAddBed = isAddBed;
    }

    public String getIsAddBed() {
        return isAddBed;
    }

    public void setNumberIn(Integer numberIn) {
        this.numberIn = numberIn;
    }

    public Integer getNumberIn() {
        return numberIn;
    }

    public void setBroadband(Integer broadband) {
        this.broadband = broadband;
    }

    public Integer getBroadband() {
        return broadband;
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
