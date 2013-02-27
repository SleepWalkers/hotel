package com.yaoxingyu.hotel.dao;

import com.yaoxingyu.hotel.model.HistoryCheckInOrder;

public interface HistoryCheckInOrderDao {

    public void insertHistroyCheckInOrder(HistoryCheckInOrder historyCheckInOrder);

    public void deleteById(String id);
}
