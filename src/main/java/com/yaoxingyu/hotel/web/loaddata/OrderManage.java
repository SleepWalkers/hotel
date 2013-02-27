package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelBookDao;
import com.yaoxingyu.hotel.dao.HotelCheckInDao;
import com.yaoxingyu.hotel.dao.HotelHistoryBookOrderDao;
import com.yaoxingyu.hotel.dao.HotelRoomManageDao;
import com.yaoxingyu.hotel.dao.HotelRoomTypeDao;
import com.yaoxingyu.hotel.obj.HotelBook;
import com.yaoxingyu.hotel.obj.HotelCheckIn;
import com.yaoxingyu.hotel.obj.HotelHistoryBookOrder;
import com.yaoxingyu.hotel.obj.HotelRoomManage;
import com.yaoxingyu.hotel.obj.HotelRoomType;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class OrderManage implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(OrderManage.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Calendar cale = Calendar.getInstance();
	
	private HotelHistoryBookOrderDao hotelHistoryBookOrderDao;
	
	private HotelRoomManageDao hotelRoomManageDao;
	
	private HotelRoomTypeDao hotelRoomTypeDao;

	private HotelCheckInDao hotelCheckInDao;
	
	private HotelBookDao hotelBookDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp) {
		
		if(hotelHistoryBookOrderDao == null)
			hotelHistoryBookOrderDao = (HotelHistoryBookOrderDao)SpringApplicationContextHolderForHotel.getBean("hotelHistoryBookOrderDao");
	
		if(hotelRoomManageDao == null)
			hotelRoomManageDao = (HotelRoomManageDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomManageDao");
		
		if(hotelRoomTypeDao == null)
			hotelRoomTypeDao = (HotelRoomTypeDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomTypeDao");
		
		if(hotelCheckInDao == null)
			hotelCheckInDao = (HotelCheckInDao)SpringApplicationContextHolderForHotel.getBean("hotelCheckInDao");
		
		if(hotelBookDao == null)
			hotelBookDao = (HotelBookDao)SpringApplicationContextHolderForHotel.getBean("hotelBookDao");
		


		String resultJson = null;

		String type = req.getParameter("type");
		try{
			if(StringUtils.isNotBlank(type)){
				if(type.equals("bookInfoByGuest")){
					
					Cookie[] cookies = req.getCookies();
					String loginUserId = null;
					
					if(cookies != null && cookies.length != 0){
						for(Cookie cookie : cookies){
							if(cookie.getName().equals("userinfo")){
								loginUserId = cookie.getValue().split("-")[1];
							}
						}
					}
					
					String checkInDate = req.getParameter("checkInDate");
					String checkOutDate = req.getParameter("checkOutDate");
					String roomName = req.getParameter("roomName");
					String guestNumber =req.getParameter("guestNumber");
					String guestName = req.getParameter("guestName");
					String phoneNumber = req.getParameter("phoneNumber");
					
					if(StringUtils.isNotBlank(checkInDate) && StringUtils.isNotBlank(checkOutDate) && StringUtils.isNotBlank(roomName)
							&& StringUtils.isNotBlank(guestNumber) && StringUtils.isNotBlank(guestName) && StringUtils.isNotBlank(phoneNumber)){
						
						HotelRoomType hotelRoomType = hotelRoomTypeDao.getByTypeName(roomName);
						String currPrice = null;
						
						HotelBook hotelBook = new HotelBook();
						hotelBook.setCheckIn(sdf.parse(checkInDate));
						hotelBook.setCheckOut(sdf.parse(checkOutDate));
						hotelBook.setBookRoomName(roomName);
						
						if(loginUserId == null){
							currPrice = hotelRoomType.getGuanWangPrice();
						}else if(loginUserId != null){
							currPrice = hotelRoomType.getMemberPrice();
						}
							
						hotelBook.setCurrPrice(currPrice);
						hotelBook.setUserId(loginUserId);
						hotelBook.setGuestName(guestName);
						hotelBook.setGuestNumber(Integer.parseInt(guestNumber));
						hotelBook.setLinkman(guestName);
						hotelBook.setPhoneNumber(phoneNumber);
						hotelBook.setStatus("未确认");
						hotelBook.setGmtCreate(new Date());
						hotelBook.setGmtModify(new Date());
						hotelBookDao.insertBookInfo(hotelBook);
						
						resultJson = "[{success : 'true',Msg : '预订成功'}]";
					}else{
						resultJson = "[{success : 'false',Msg : '所填信息不完整 预订失败'}]";
					}
				}else if(type.equals("checkIfBookable")){
					String roomTypeName = req.getParameter("roomTypeName");
					String checkInDate = req.getParameter("checkInDate");
					String checkOutDate = req.getParameter("checkOutDate");
					Date checkIn = sdf.parse(checkInDate);
					Date checkOut = sdf.parse(checkOutDate);

					List<Long> bookableRoomNoList = null;
					
					bookableRoomNoList = getBookableRoomNo(checkIn,checkOut,roomTypeName);	
					
					if(bookableRoomNoList.size() > 0){
						resultJson = "[{success : 'true'}]";
					}else
						resultJson = "[{success : 'false'}]";
				}else if(type.equals("checkBook")){
					
					String roomId = req.getParameter("roomId");
					String bookOrderId =req.getParameter("bookOrderId");
					HotelBook hotelBook = null;
					
					if(StringUtils.isNotBlank(roomId) && StringUtils.isNotBlank(bookOrderId)){
						hotelBook = hotelBookDao.getByOrderId(bookOrderId);
						if(hotelBook != null){
							hotelBook.setBookRoomNo(Long.parseLong(roomId));
							hotelBook.setStatus("已确认");
							hotelBook.setGmtModify(new Date());
									
							hotelBookDao.updatetBookInfo(hotelBook);
							resultJson = "[{success : 'true',Msg : '确认成功'}]";
						}else{
							resultJson = "[{success : 'false',Msg : '确认失败  该订单不存在'}]";
						}
					}
				}else if(type.equals("cancelBookedRoom")){
					String cancelBookedRoomNo = req.getParameter("cancelBookedRoomNo");
					String cancelOrderNo = req.getParameter("cancelOrderNo");
					
					if(StringUtils.isNotBlank(cancelBookedRoomNo) && StringUtils.isNotBlank(cancelOrderNo)){
						
						HotelBook hotelBook = hotelBookDao.getByOrderId(cancelOrderNo);
						if(hotelBook != null){
							hotelBook.setStatus("已取消");
							hotelBook.setBookRoomNo(null);
							hotelBook.setGmtModify(new Date());
							
							
							hotelBookDao.updatetBookInfo(hotelBook);
							
							resultJson = "[{Msg : '取消预订成功'}]";
						}


					}
				}else if(type.equals("deleteOverdueBookOrder")){
					Date deleteDate = cale.getTime();
					String a = sdf.format(deleteDate);
					deleteDate = sdf.parse(a);
					
					List<HotelBook> hotelBookList = hotelBookDao.getByCheckInDate(a);
					
					if(hotelBookList != null && hotelBookList.size() > 0){
						HotelHistoryBookOrder hotelHistoryBookOrder = new HotelHistoryBookOrder();
						for(HotelBook hotelBook : hotelBookList){
							hotelHistoryBookOrder.setOrderId(hotelBook.getId());
							hotelHistoryBookOrder.setCheckIn(hotelBook.getCheckIn());
							hotelHistoryBookOrder.setCheckOut(hotelBook.getCheckOut());
							hotelHistoryBookOrder.setUserId(hotelBook.getUserId());
							hotelHistoryBookOrder.setCurrPrice(hotelBook.getCurrPrice());
							hotelHistoryBookOrder.setBookRoomName(hotelBook.getBookRoomName());
							hotelHistoryBookOrder.setGuestNumber(hotelBook.getGuestNumber());
							hotelHistoryBookOrder.setGuestName(hotelBook.getGuestName());
							hotelHistoryBookOrder.setLinkman(hotelBook.getLinkman());
							hotelHistoryBookOrder.setPhoneNumber(hotelBook.getPhoneNumber());
							hotelHistoryBookOrder.setBookRoomNo(hotelBook.getBookRoomNo());
							hotelHistoryBookOrder.setAttribute(0);
							hotelHistoryBookOrder.setGmtCreate(new Date());
							
							hotelHistoryBookOrderDao.insertHistroyBookOrder(hotelHistoryBookOrder);
						}
						
						hotelBookDao.deleteByCheckIn(deleteDate);
						
						resultJson = "[{success : 'true',Msg : '清除成功'}]";
					}else{
						resultJson = "[{success : 'false',Msg : '无过期预订订单'}]";
					}
				}else if(type.equals("deleteBookOrder")){
					String orderIdForDelete = req.getParameter("orderIdForDelete");
					
					if(StringUtils.isNotBlank(orderIdForDelete)){
						HotelBook hotelBook = hotelBookDao.getByOrderId(orderIdForDelete);
						if(hotelBook != null){
							HotelHistoryBookOrder hotelHistoryBookOrder = new HotelHistoryBookOrder();
							hotelHistoryBookOrder.setOrderId(hotelBook.getId());
							hotelHistoryBookOrder.setCheckIn(hotelBook.getCheckIn());
							hotelHistoryBookOrder.setCheckOut(hotelBook.getCheckOut());
							hotelHistoryBookOrder.setUserId(hotelBook.getUserId());
							hotelHistoryBookOrder.setCurrPrice(hotelBook.getCurrPrice());
							hotelHistoryBookOrder.setBookRoomName(hotelBook.getBookRoomName());
							hotelHistoryBookOrder.setGuestNumber(hotelBook.getGuestNumber());
							hotelHistoryBookOrder.setGuestName(hotelBook.getGuestName());
							hotelHistoryBookOrder.setLinkman(hotelBook.getLinkman());
							hotelHistoryBookOrder.setPhoneNumber(hotelBook.getPhoneNumber());
							hotelHistoryBookOrder.setBookRoomNo(hotelBook.getBookRoomNo());
							hotelHistoryBookOrder.setAttribute(0);
							hotelHistoryBookOrder.setGmtCreate(new Date());
							
							hotelHistoryBookOrderDao.insertHistroyBookOrder(hotelHistoryBookOrder);
							
							hotelBookDao.deleteById(orderIdForDelete);
							
							resultJson = "[{success : 'true',Msg : '删除成功'}]";
						}else{
							resultJson = "[{success : 'false',Msg : '该订单不存在 删除失败'}]";
						}
					}else{
						resultJson = "[{success : 'false',Msg : '请先选择要删除的订单'}]";
					}
				}
			}
			
			resp.setContentType("text/xml; charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = resp.getWriter();
			if(StringUtils.isNotBlank(resultJson)){
				pw.write(resultJson);
			}
	    	pw.flush();
	    	pw.close();
		}catch(Exception e){
			LOG.error(e);
		}
	}
	
	private List<Long> getBookableRoomNo(Date checkIn,Date checkOut,String roomTypeName){
		List<Long> unbookableOfBook = new ArrayList<Long>();
		List<Long> bookable = new ArrayList<Long>();
		
		List<HotelBook> hbList = hotelBookDao.getByRoomTypeAndStatus(roomTypeName,"已确认");
		List<HotelCheckIn> hciList = hotelCheckInDao.getByRoomTypeName(roomTypeName);
		List<HotelRoomManage> hrmList = hotelRoomManageDao.getByRoomTypeName(roomTypeName);
		
		for(HotelBook hb : hbList){
			//如果预订的入住日期 晚于或等于该订单的预订入住日期 并且 早于该订单的预订离店日期 则该房间无法被预订
			if((checkIn.compareTo(hb.getCheckIn())) >= 0 && (checkIn.compareTo(hb.getCheckOut())) < 0)
				unbookableOfBook.add(hb.getBookRoomNo());
			//如果预订的离店日期 晚于该订单的预订入住日期 则该房间无法被预订
			else if((checkOut.compareTo(hb.getCheckIn())) > 0 && (checkOut.compareTo(hb.getCheckOut())) <= 0)
				unbookableOfBook.add(hb.getBookRoomNo());
			else{
				continue;
			}
		}
		
		for(HotelCheckIn hci : hciList){
			//如果预订的入住日期 晚于或等于该订单的入住日期 并且 早于该订单的离店日期 则该房间无法被预订
			if(checkIn.compareTo(hci.getCheckInTime()) >= 0 && checkIn.compareTo(hci.getCheckOutTime()) < 0)
				unbookableOfBook.add(hci.getRoomNumber());
			//如果预订的离店日期 晚于该订单的入住日期 则该房间无法被预订
			else if(checkOut.compareTo(hci.getCheckInTime()) > 0)
				unbookableOfBook.add(hci.getRoomNumber());
			else{
				continue;
			}
		}
		
		for(HotelRoomManage hrm : hrmList){
			bookable.add(hrm.getRoomNumber());
		}
		
		bookable.removeAll(unbookableOfBook);
			
		return bookable;
	}
}
