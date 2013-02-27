package com.yaoxingyu.hotel.dao;

import com.yaoxingyu.hotel.model.HistoryBookOrder;

public interface HistoryBookOrderDao {

    public void insertHistroyBookOrder(HistoryBookOrder historyBookOrder);

    public void deleteById(String id);
}
