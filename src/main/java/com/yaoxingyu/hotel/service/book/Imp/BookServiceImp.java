package com.yaoxingyu.hotel.service.book.Imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaoxingyu.hotel.dao.BookDao;
import com.yaoxingyu.hotel.dao.CheckInDao;
import com.yaoxingyu.hotel.dao.HistoryBookOrderDao;
import com.yaoxingyu.hotel.dao.RoomManageDao;
import com.yaoxingyu.hotel.model.Book;
import com.yaoxingyu.hotel.model.CheckIn;
import com.yaoxingyu.hotel.model.HistoryBookOrder;
import com.yaoxingyu.hotel.model.RoomManage;
import com.yaoxingyu.hotel.service.book.BookService;

public class BookServiceImp implements BookService {

	@Resource
	private BookDao bookDao;

	@Resource
	private CheckInDao checkInDao;

	@Resource
	private RoomManageDao roomManageDao;

	@Resource
	private HistoryBookOrderDao historyBookOrderDao;

	@Override
	public List<Book> getBookByUserId(String userId) {
		List<Book> books = null;
		if (StringUtils.isNotBlank(userId))
			books = bookDao.getByUserId(userId);
		return books;
	}

	@Override
	public Boolean createBookOrder(Book book) {
		boolean isSuccess = false;
		if (book != null) {
			bookDao.insertBook(book);
			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public List<Long> getBookableRoom(Date checkIn, Date checkOut,
			String roomTypeName) {

		List<Long> unbookableOfBook = new ArrayList<Long>();
		List<Long> bookable = new ArrayList<Long>();

		List<Book> hbList = bookDao.getByRoomTypeAndStatus(roomTypeName, "已确认");
		List<CheckIn> hciList = checkInDao.getByRoomTypeName(roomTypeName);
		List<RoomManage> hrmList = roomManageDao
				.getByRoomTypeName(roomTypeName);

		for (Book hb : hbList) {
			// 如果预订的入住日期 晚于或等于该订单的预订入住日期 并且 早于该订单的预订离店日期 则该房间无法被预订
			if ((checkIn.compareTo(hb.getCheckIn())) >= 0
					&& (checkIn.compareTo(hb.getCheckOut())) < 0)
				unbookableOfBook.add(hb.getBookRoomNo());
			// 如果预订的离店日期 晚于该订单的预订入住日期 则该房间无法被预订
			else if ((checkOut.compareTo(hb.getCheckIn())) > 0
					&& (checkOut.compareTo(hb.getCheckOut())) <= 0)
				unbookableOfBook.add(hb.getBookRoomNo());
			else {
				continue;
			}
		}

		for (CheckIn hci : hciList) {
			// 如果预订的入住日期 晚于或等于该订单的入住日期 并且 早于该订单的离店日期 则该房间无法被预订
			if (checkIn.compareTo(hci.getCheckInTime()) >= 0
					&& checkIn.compareTo(hci.getCheckOutTime()) < 0)
				unbookableOfBook.add(hci.getRoomNumber());
			// 如果预订的离店日期 晚于该订单的入住日期 则该房间无法被预订
			else if (checkOut.compareTo(hci.getCheckInTime()) > 0)
				unbookableOfBook.add(hci.getRoomNumber());
			else {
				continue;
			}
		}

		for (RoomManage hrm : hrmList) {
			bookable.add(hrm.getRoomNumber());
		}

		bookable.removeAll(unbookableOfBook);

		return bookable;
	}

	@Override
	public Boolean checkBook(long roomId, long orderId) {
		boolean isSuccess = false;
		if (roomId > 0 && orderId > 0) {
			Book book = bookDao.getByOrderId(orderId);
			if (book != null) {
				book.setBookRoomNo(roomId);
				book.setStatus("已确认");
				book.setGmtModify(new Date());

				bookDao.updatetBook(book);
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean cancelBook(long orderId) {
		boolean isSuccess = false;
		Book book = bookDao.getByOrderId(orderId);
		if (book != null) {
			book.setStatus("已取消");
			book.setBookRoomNo(null);
			book.setGmtModify(new Date());

			bookDao.updatetBook(book);

			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public Boolean deleteOrder(long orderId) {
		boolean isSuccess = false;
		if (orderId > 0) {
			Book book = bookDao.getByOrderId(orderId);
			if (book != null) {
				HistoryBookOrder historyBookOrder = new HistoryBookOrder();
				historyBookOrder.setOrderId(book.getId());
				historyBookOrder.setCheckIn(book.getCheckIn());
				historyBookOrder.setCheckOut(book.getCheckOut());
				historyBookOrder.setUserId(book.getUserId());
				historyBookOrder.setCurrPrice(book.getCurrPrice());
				historyBookOrder.setBookRoomName(book.getBookRoomName());
				historyBookOrder.setGuestNumber(book.getGuestNumber());
				historyBookOrder.setGuestName(book.getGuestName());
				historyBookOrder.setLinkman(book.getLinkman());
				historyBookOrder.setPhoneNumber(book.getPhoneNumber());
				historyBookOrder.setBookRoomNo(book.getBookRoomNo());
				historyBookOrder.setAttribute(0);
				historyBookOrder.setGmtCreate(new Date());

				createHistroyBookOrder(historyBookOrder);

				bookDao.deleteById(orderId);

				isSuccess = true;
			}
		}
		return isSuccess;
	}

	@Override
	public void createHistroyBookOrder(HistoryBookOrder historyBookOrder) {
		historyBookOrderDao.insertHistroyBookOrder(historyBookOrder);
	}

	@Override
	public List<Book> getBookByLimit(int start, int limit) {
		return getBookByLimit(start, limit, null);
	}

	@Override
	public int countBookNum() {
		return countBookNum(null);
	}

	@Override
	public List<Book> getBookByLimit(int start, int limit, String status) {
		return null;
	}

	@Override
	public int countBookNum(String status) {
		return 0;
	}
}
