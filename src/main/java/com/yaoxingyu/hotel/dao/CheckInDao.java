package com.yaoxingyu.hotel.dao;

import java.util.List;

import com.yaoxingyu.hotel.model.CheckIn;

public interface CheckInDao {

	public void insertCheckIn(CheckIn checkIn);

	public void updatetCheckIn(CheckIn checkIn);

	public CheckIn getByRoomNo(long roomNumber);

	public List<CheckIn> getByRoomTypeName(String roomTypeName);

	public List<CheckIn> getAll();

	public void deleteByRoomNo(long roomNumber);

	public void deleteById(long id);

}
