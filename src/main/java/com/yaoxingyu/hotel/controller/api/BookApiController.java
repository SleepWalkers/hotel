package com.yaoxingyu.hotel.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.yaoxingyu.hotel.model.Book;
import com.yaoxingyu.hotel.model.RoomType;
import com.yaoxingyu.hotel.service.book.BookService;
import com.yaoxingyu.hotel.service.room.RoomService;
import com.yaoxingyu.hotel.vo.JsonVO;

public class BookApiController {

	@Resource
	private BookService bookService;

	@Resource
	private RoomService roomService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final Logger logger = Logger
			.getLogger(BookApiController.class);

	@ResponseBody
	@RequestMapping(value = "/bookForGuest/", method = RequestMethod.GET)
	public String getBookForGuest(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		String loginUserId = null;

		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userinfo")) {
					loginUserId = cookie.getValue().split("-")[1];
				}
			}
		}

		List<Book> books = null;
		if (StringUtils.isNotBlank(loginUserId)) {
			books = bookService.getBookByUserId(loginUserId);
		}

		String json = null;

		if (books.size() > 0)
			json = new Gson().toJson(books);

		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/bookForManager", method = RequestMethod.GET)
	public String getBookForManager(@PathVariable int start,
			@PathVariable int limit) {

		int totalCount = bookService.countBookNum();

		List<Book> books = bookService.getBookByLimit(start, limit);

		JsonVO jsonVO = new JsonVO();

		jsonVO.setTotalCount(totalCount);
		jsonVO.setRoot(books);

		return new Gson().toJson(jsonVO);
	}

	@ResponseBody
	@RequestMapping(value = "/getQueryBook", method = RequestMethod.GET)
	public String getQueryBook(@PathVariable int start, @PathVariable int limit) {
		String status = "已确认";
		int totalCount = bookService.countBookNum(status);

		List<Book> books = bookService.getBookByLimit(start, limit, status);

		JsonVO jsonVO = new JsonVO();

		jsonVO.setTotalCount(totalCount);
		jsonVO.setRoot(books);

		return new Gson().toJson(jsonVO);
	}

	@ResponseBody
	@RequestMapping(value = "/createBook", method = RequestMethod.POST)
	public String createBook(@RequestParam String checkInDate,
			@RequestParam String checkOutDate,
			@RequestParam String roomTypeName, @RequestParam String guestName,
			@RequestParam Integer guestNumber, @RequestParam String linkman,
			@RequestParam String phoneNumber) {

		String loginUserId = "";
		String currPrice = null;

		Book book = new Book();
		try {
			book.setCheckIn(sdf.parse(checkInDate));
			book.setCheckOut(sdf.parse(checkOutDate));
		} catch (ParseException e) {
			logger.error("", e);
		}

		book.setBookRoomName(roomTypeName);

		RoomType roomType = roomService.getRoomType(roomTypeName);

		if (loginUserId == null) {
			currPrice = roomType.getGuanWangPrice();
		} else if (loginUserId != null) {
			currPrice = roomType.getMemberPrice();
		}

		book.setCurrPrice(currPrice);
		book.setUserId(loginUserId);
		book.setGuestName(guestName);
		book.setGuestNumber(guestNumber);
		book.setLinkman(linkman);
		book.setPhoneNumber(phoneNumber);
		book.setStatus("未确认");
		book.setGmtCreate(new Date());
		book.setGmtModify(new Date());

		bookService.createBookOrder(book);

		JsonVO jsonVO = new JsonVO();
		jsonVO.setIsSuccess(true);

		return new Gson().toJson(jsonVO);
	}
}
