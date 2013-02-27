package com.yaoxingyu.hotel.service.book;

import java.util.Date;
import java.util.List;

import com.yaoxingyu.hotel.model.Book;
import com.yaoxingyu.hotel.model.HistoryBookOrder;

public interface BookService {

	List<Book> getBookByUserId(String userId);

	List<Book> getBookByLimit(int start, int limit);

	List<Book> getBookByLimit(int start, int limit, String status);

	int countBookNum();

	int countBookNum(String status);

	Boolean createBookOrder(Book book);

	List<Long> getBookableRoom(Date checkIn, Date checkOut, String roomTypeName);

	Boolean checkBook(long roomId, long orderId);

	Boolean cancelBook(long orderId);

	Boolean deleteOrder(long orderId);

	void createHistroyBookOrder(HistoryBookOrder historyBookOrder);

}
