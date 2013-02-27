package com.yaoxingyu.hotel.dao;

import java.util.List;
import java.util.Map;

import com.yaoxingyu.hotel.model.RoomType;

public interface RoomTypeDao {

	public void insertRoomType(RoomType roomType);

	public void updatetRoomType(RoomType RoomType);

	public void deleteById(String id);

	public RoomType getById(String id);

	public RoomType getByTypeName(String roomTypeName);

	public List<RoomType> getAll(Map<String, Integer> map);

}
