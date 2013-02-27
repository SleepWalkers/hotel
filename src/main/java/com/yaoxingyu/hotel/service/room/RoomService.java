package com.yaoxingyu.hotel.service.room;

import java.util.List;

import com.yaoxingyu.hotel.model.CheckIn;
import com.yaoxingyu.hotel.model.RoomManage;
import com.yaoxingyu.hotel.model.RoomType;

public interface RoomService {

	RoomType getRoomType(String roomTypeName);

	List<RoomType> getAllRoomType();

	List<RoomType> getAllRoomType(int start, int limit);

	List<RoomManage> getRoomList(int start, int limit);

	Integer countRoomWithStatus(String roomTypeName, Integer status);

	Boolean createRoom(RoomManage roomManage);

	Boolean checkIn(long roomNumber, CheckIn checkIn, long bookOrderId);

	Boolean checkOut(long roomNumber);

	Boolean createRoomType(RoomType roomType);
}
