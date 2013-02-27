package com.yaoxingyu.hotel.model;

import java.util.Date;

public class HistoryBookOrder {

    private Long    id;

    private Long    orderId;

    private Date    checkIn;

    private Date    checkOut;

    private String  userId;

    private String  currPrice;

    private String  bookRoomName;

    private Integer guestNumber;

    private String  guestName;

    private String  linkman;

    private String  phoneNumber;

    private Long    bookRoomNo;

    private Integer attribute;

    private Date    gmtCreate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkman() {
        return linkman;
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

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }
}
