package com.yaoxingyu.hotel.dao;

import java.util.Date;
import java.util.List;

import com.yaoxingyu.hotel.model.Book;

public interface BookDao {

	public void insertBook(Book book);

	public void updatetBook(Book book);

	public List<Book> getByUserId(String userId);

	public Book getByOrderId(long orderId);

	public List<Book> getByRoomNumber(long roomId);

	public List<Book> getBookedOrderByLimit(int start, int limit, String status);

	public List<Book> getByStatus(String status);

	public int countBookedOrder(String status);

	public List<Book> getByCheckInDate(String checkInDate);

	public List<Book> getByRoomType(String roomTypeName);

	public List<Book> getByRoomTypeAndStatus(String roomTypeName, String status);

	public List<Book> getAllByLimit(int start, int limit);

	public List<Book> getAll();

	public void deleteByRoomNo(long roomId);

	public void deleteByCheckIn(Date checkIn);

	public void deleteById(long id);
}
