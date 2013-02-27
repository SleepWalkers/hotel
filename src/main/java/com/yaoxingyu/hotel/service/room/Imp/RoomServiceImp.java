package com.yaoxingyu.hotel.service.room.Imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaoxingyu.hotel.dao.BookDao;
import com.yaoxingyu.hotel.dao.CheckInDao;
import com.yaoxingyu.hotel.dao.HistoryBookOrderDao;
import com.yaoxingyu.hotel.dao.HistoryCheckInOrderDao;
import com.yaoxingyu.hotel.dao.RoomManageDao;
import com.yaoxingyu.hotel.dao.RoomTypeDao;
import com.yaoxingyu.hotel.model.Book;
import com.yaoxingyu.hotel.model.CheckIn;
import com.yaoxingyu.hotel.model.HistoryBookOrder;
import com.yaoxingyu.hotel.model.HistoryCheckInOrder;
import com.yaoxingyu.hotel.model.RoomManage;
import com.yaoxingyu.hotel.model.RoomType;
import com.yaoxingyu.hotel.service.room.RoomService;

public class RoomServiceImp implements RoomService {

	@Resource
	private BookDao bookDao;

	@Resource
	private CheckInDao checkInDao;

	@Resource
	private RoomTypeDao roomTypeDao;

	@Resource
	private RoomManageDao roomManageDao;

	@Resource
	private HistoryBookOrderDao historyBookOrderDao;

	@Resource
	private HistoryCheckInOrderDao historyCheckInOrderDao;

	@Override
	public List<RoomType> getAllRoomType() {
		List<RoomType> roomTypes = roomTypeDao
				.getAll(new HashMap<String, Integer>());

		return roomTypes;
	}

	@Override
	public List<RoomType> getAllRoomType(int start, int limit) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("limit", limit);
		List<RoomType> roomTypes = roomTypeDao.getAll(map);
		return roomTypes;
	}

	@Override
	public List<RoomManage> getRoomList(int start, int limit) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("limit", limit);
		List<RoomManage> roomManages = roomManageDao.getAll(map);
		return roomManages;
	}

	@Override
	public Boolean createRoom(RoomManage roomManage) {
		boolean isSuccess = false;
		if (roomManage != null) {
			if (roomManage.getId() != null)
				roomManageDao.updatetRoom(roomManage);
			else
				roomManageDao.insertRoom(roomManage);

			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public Boolean checkIn(long roomNumber, CheckIn checkIn, long bookOrderId) {
		boolean isSuccess = false;
		if (checkIn != null && checkIn.getCheckOutTime() != null) {
			List<Book> bookList = bookDao.getByRoomNumber(roomNumber);

			if (bookList != null && bookList.size() > 0) {
				Date checkOut = checkIn.getCheckOutTime();
				for (Book book : bookList) {
					if ((checkOut.compareTo(book.getCheckIn())) > 0
							&& (checkOut.compareTo(book.getCheckOut())) <= 0) {
						if (bookOrderId > 0) {
							if (bookOrderId == book.getId()) {
								checkInDao.insertCheckIn(checkIn);

								Book oldbook = bookDao
										.getByOrderId(bookOrderId);

								if (oldbook != null) {
									HistoryBookOrder historyBookOrder = new HistoryBookOrder();
									historyBookOrder
											.setOrderId(oldbook.getId());
									historyBookOrder.setCheckIn(oldbook
											.getCheckIn());
									historyBookOrder.setCheckOut(oldbook
											.getCheckOut());
									historyBookOrder.setUserId(oldbook
											.getUserId());
									historyBookOrder.setCurrPrice(oldbook
											.getCurrPrice());
									historyBookOrder.setBookRoomName(oldbook
											.getBookRoomName());
									historyBookOrder.setGuestNumber(oldbook
											.getGuestNumber());
									historyBookOrder.setGuestName(oldbook
											.getGuestName());
									historyBookOrder.setLinkman(oldbook
											.getLinkman());
									historyBookOrder.setPhoneNumber(oldbook
											.getPhoneNumber());
									historyBookOrder.setBookRoomNo(oldbook
											.getBookRoomNo());
									historyBookOrder.setAttribute(1);
									historyBookOrder.setGmtCreate(new Date());

									historyBookOrderDao
											.insertHistroyBookOrder(historyBookOrder);

									bookDao.deleteById(bookOrderId);

									isSuccess = true;
								}
							} else
								continue;
						} else
							continue;
					} else
						continue;
				}
			} else {
				checkInDao.insertCheckIn(checkIn);
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean checkOut(long roomNumber) {
		CheckIn checkIn = checkInDao.getByRoomNo(roomNumber);
		boolean isSuccess = false;

		if (checkIn != null) {
			HistoryCheckInOrder historyCheckInOrder = new HistoryCheckInOrder();
			historyCheckInOrder.setOrderId(checkIn.getId());
			historyCheckInOrder.setIdCardNo(checkIn.getIdCardNo());
			historyCheckInOrder.setGuestName(checkIn.getGuestName());
			historyCheckInOrder.setRoomTypeName(checkIn.getRoomTypeName());
			historyCheckInOrder.setRoomNumber(checkIn.getRoomNumber());
			historyCheckInOrder.setCheckInTime(checkIn.getCheckInTime());
			historyCheckInOrder.setCheckOutTime(checkIn.getCheckOutTime());
			historyCheckInOrder.setGmtCreate(new Date());

			historyCheckInOrderDao
					.insertHistroyCheckInOrder(historyCheckInOrder);

			checkInDao.deleteByRoomNo(roomNumber);

			isSuccess = true;
		} else {
			isSuccess = false;
		}
		return isSuccess;
	}

	@Override
	public Boolean createRoomType(RoomType roomType) {
		boolean isSuccess = false;
		if (roomType != null) {
			if (roomType.getId() == null)
				roomTypeDao.insertRoomType(roomType);
			else
				roomTypeDao.updatetRoomType(roomType);

			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public Integer countRoomWithStatus(String roomTypeName, Integer status) {
		int num = -1;
		if (StringUtils.isNotBlank(roomTypeName)) {
			RoomManage manage = new RoomManage();
			manage.setRoomTypeName(roomTypeName);
			if (status != null && (status == 0 || status == 1 || status == 2)) {
				manage.setStatus(status);
			}
			num = roomManageDao.countRoomByRoom(manage);
		}
		return num;
	}

	@Override
	public RoomType getRoomType(String roomTypeName) {
		return null;
	}
}
