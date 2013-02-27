package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelBookDao;
import com.yaoxingyu.hotel.dao.HotelRoomManageDao;
import com.yaoxingyu.hotel.dao.HotelRoomTypeDao;
import com.yaoxingyu.hotel.obj.HotelBook;
import com.yaoxingyu.hotel.obj.HotelRoomType;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class OrderJsonData implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(RoomJsonData.class);

	private HotelBookDao hotelBookDao;
	
	private HotelRoomTypeDao hotelRoomTypeDao;
	
	private HotelRoomManageDao hotelRoomManageDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp){
		
		if(hotelBookDao == null)
			hotelBookDao = (HotelBookDao)SpringApplicationContextHolderForHotel.getBean("hotelBookDao");
		
		if(hotelRoomTypeDao == null)
			hotelRoomTypeDao = (HotelRoomTypeDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomTypeDao");
		
		if(hotelRoomManageDao == null)
			hotelRoomManageDao = (HotelRoomManageDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomManageDao");
		
		String type = req.getParameter("type");
		
		StringBuilder str = new StringBuilder();
		String jsonData = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		try{
			
			if(StringUtils.isNotBlank(type)){
				if(type.equals("bookInfoForGuest")){

					str.append("[");
					
					Cookie[] cookies = req.getCookies();
					String loginUserId = null;
					
					if(cookies != null && cookies.length != 0){
						for(Cookie cookie : cookies){
							if(cookie.getName().equals("userinfo")){
								loginUserId = cookie.getValue().split("-")[1];
							}
						}
					}
					
					List<HotelBook> hotelBookList = null;
					HotelRoomType hrt = null;
					
					if(StringUtils.isNotBlank(loginUserId)){
						hotelBookList = hotelBookDao.getByUserId(loginUserId);
					
						String bookedRoomTypeName = null;
						
						if(hotelBookList != null && hotelBookList.size() > 0){
							for(HotelBook hb : hotelBookList){
								bookedRoomTypeName = hb.getBookRoomName();
								hrt = hotelRoomTypeDao.getByTypeName(bookedRoomTypeName);
								
								if(hrt != null){
									str.append("{orderId:'" + hb.getId() + "',bookDate:'" + sdf.format(hb.getGmtCreate()) + "',roomTypeName:'" 
											+ hrt.getRoomTypeName() + "',bookPrice:'" + hb.getCurrPrice() + "',checkInTime:'"
											+ sdf.format(hb.getCheckIn()) + "',checkOutTime:'" + sdf.format(hb.getCheckOut()) + "',orderStatus:'"
											+ hb.getStatus() + "',guestName:'" + hb.getGuestName() + "',arrivalTime:'" + hb.getArrivalTime() + "',linkman:'"
											+ hb.getLinkman() + "',phoneNumber:'" + hb.getPhoneNumber() + "',guestNumber:'" + hb.getGuestNumber() + "'},");
								}
							}
							
							jsonData = str.toString();
							
							if(jsonData.endsWith(",")){
								jsonData = jsonData.substring(0,jsonData.length()-1) + "]";
							}
						}
					}
				}else if(type.equals("bookInfoForManager")){
					
					String start = req.getParameter("start");
					String limit = req.getParameter("limit");
					
					if(StringUtils.isNotBlank(start) && StringUtils.isNotBlank(limit)){
						List<HotelBook> hotelBookList = hotelBookDao.getAllByLimit(start,limit);
						int totalCount = hotelBookDao.getAll().size();
						if(hotelBookList != null && hotelBookList.size() > 0){
							str.append("{totalCount:" + totalCount + ",root:[");
							for(HotelBook hb : hotelBookList){
								str.append("{bookOrderId:'" + hb.getId() + "',roomTypeName:'" + hb.getBookRoomName() 
										+ "',bookedRoomPrice:'" + hb.getCurrPrice()
										+ "',bookRoomNo:'" + ( hb.getBookRoomNo() == null?  "" : hb.getBookRoomNo())   
										+ "',checkInTime:'" + sdf.format(hb.getCheckIn()) + "',checkOutTime:'" + sdf.format(hb.getCheckOut()) 
										+ "',guestName:'" + hb.getGuestName() + "',phoneNumber:'" + hb.getPhoneNumber() 
										+ "',arrivalTime:'" + hb.getArrivalTime() + "',status:'" + hb.getStatus()
										+ "'},");
							}
							
							jsonData = str.toString();
							
							if(jsonData.endsWith(",")){
								jsonData = jsonData.substring(0,jsonData.length()-1) + "]}";
							}
						}else{
							jsonData = "{[{}]}";
						}
					}else{
						jsonData = "{[{}]}";
					}
				}else if(type.equals("queryBookOrder")){
					
					String start = req.getParameter("start");
					String limit = req.getParameter("limit");
					String status = "已确认";
					if(StringUtils.isNotBlank(start) && StringUtils.isNotBlank(limit)){
						List<HotelBook> hotelBookList = hotelBookDao.getBookedOrderByLimit(start,limit,status);
						int totalCount = hotelBookDao.countBookedOrder(status);
						if(hotelBookList != null && hotelBookList.size() > 0){
							str.append("{totalCount:" + totalCount + ",root:[");
							for(HotelBook hb : hotelBookList){
								str.append("{bookOrderId:'" + hb.getId() + "',roomTypeName:'" + hb.getBookRoomName() 
										+ "',roomPrice:'" + hb.getCurrPrice() 
										+ "',roomNumber:'" + hb.getBookRoomNo()+ "',checkInTime:'" + sdf.format(hb.getCheckIn()) 
										+ "',checkOutTime:'" + sdf.format(hb.getCheckOut()) + "',guestName:'" + hb.getGuestName() 
										+ "',phoneNumber:'" + hb.getPhoneNumber() + "',arrivalTime:'" + hb.getArrivalTime() 
										+ "',status:'" + hb.getStatus()
										+ "'},");
							}
							
							jsonData = str.toString();
							
							if(jsonData.endsWith(",")){
								jsonData = jsonData.substring(0,jsonData.length()-1) + "]}";
							}
						}else{
							jsonData = "{[{}]}";
						}
					}else{
						jsonData = "{[{}]}";
					}
				}
			}
			
			resp.setContentType("text/xml; charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = resp.getWriter();
			if(StringUtils.isNotBlank(jsonData)){
				pw.write(jsonData);
			}
	    	pw.flush();
	    	pw.close();
		}catch(Exception e){
			LOG.error(e);
		}
		
	}

}
