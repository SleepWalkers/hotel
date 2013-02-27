package com.yaoxingyu.hotel.model;

import java.util.Date;

public class Book {

    private Long    id;

    private Date    checkIn;

    private Date    checkOut;

    private String  userId;

    private String  currPrice;

    private String  bookRoomName;

    private Integer guestNumber;

    private String  guestName;

    private String  arrivalTime;

    private String  linkman;

    private String  checkType;

    private String  phoneNumber;

    private Long    bookRoomNo;

    private String  status;

    private Date    gmtCreate;

    private Date    gmtModify;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(String currPrice) {
        this.currPrice = currPrice;
    }

    public String getBookRoomName() {
        return bookRoomName;
    }

    public void setBookRoomName(String bookRoomName) {
        this.bookRoomName = bookRoomName;
    }

    public void setGuestNumber(Integer guestNumber) {
        this.guestNumber = guestNumber;
    }

    public Integer getGuestNumber() {
        return guestNumber;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getBookRoomNo() {
        return bookRoomNo;
    }

    public void setBookRoomNo(Long bookRoomNo) {
        this.bookRoomNo = bookRoomNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
