package com.yaoxingyu.hotel.dao;

import java.util.List;
import java.util.Map;

import com.yaoxingyu.hotel.model.RoomManage;

public interface RoomManageDao {

	public void insertRoom(RoomManage roomManage);

	public void updatetRoom(RoomManage roomManage);

	public RoomManage getById(String id);

	public List<RoomManage> getByRoomTypeName(String roomTypeName);

	public RoomManage getByRoomNumber(String roomNumber);

	public Integer countRoomByRoom(RoomManage roomManage);

	public List<RoomManage> getAll(Map<String, Integer> map);

	public void deleteById(String id);

}
