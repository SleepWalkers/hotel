package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelBookDao;
import com.yaoxingyu.hotel.dao.HotelCheckInDao;
import com.yaoxingyu.hotel.dao.HotelRoomManageDao;
import com.yaoxingyu.hotel.dao.HotelRoomTypeDao;
import com.yaoxingyu.hotel.obj.HotelBook;
import com.yaoxingyu.hotel.obj.HotelCheckIn;
import com.yaoxingyu.hotel.obj.HotelRoomManage;
import com.yaoxingyu.hotel.obj.HotelRoomType;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class RoomJsonData implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(RoomJsonData.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Calendar cale = Calendar.getInstance();
	
	private HotelRoomTypeDao hotelRoomTypeDao;
	
	private HotelRoomManageDao hotelRoomManageDao;
	
	private HotelCheckInDao hotelCheckInDao;
	
	private HotelBookDao hotelBookDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp){
		
		if(hotelRoomTypeDao == null)
			hotelRoomTypeDao = (HotelRoomTypeDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomTypeDao");
		
		if(hotelRoomManageDao == null)
			hotelRoomManageDao = (HotelRoomManageDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomManageDao");
	
		if(hotelCheckInDao == null)
			hotelCheckInDao = (HotelCheckInDao)SpringApplicationContextHolderForHotel.getBean("hotelCheckInDao");
		
		if(hotelBookDao == null)
			hotelBookDao = (HotelBookDao)SpringApplicationContextHolderForHotel.getBean("hotelBookDao");
		
		String type = req.getParameter("type");
		
		StringBuilder roomStr = new StringBuilder();
		String roomJson = null;
		
		try{
			
			if(type.equals("roomType")){
				List<HotelRoomType> hotelRoomTypeList = new ArrayList<HotelRoomType>();
				roomStr.append("[");

				hotelRoomTypeList = hotelRoomTypeDao.getAll();
				
				if(hotelRoomTypeList != null && hotelRoomTypeList.size() > 0){
					for(HotelRoomType hrt : hotelRoomTypeList){
						
						String breakFast = null;
						String broadband = null;
						
						if(hrt.getBreakfast() == 1)
							breakFast = "有";
						else if(hrt.getBreakfast() == 0)
							breakFast = "无";
		
						if(hrt.getBroadband() == 1)
							broadband = "无";
						else if(hrt.getBroadband() == 0)
							broadband = "有";
						
						roomStr.append("{roomTypeId:'" + hrt.getId() + "',roomTypeName:'" + hrt.getRoomTypeName() 
								+ "',roomPrice:'" + hrt.getRoomPrice() + "',guanWangDiscount:'" + hrt.getGuanWangPrice()
								+ "',memberDiscount:'" + hrt.getMemberPrice() + "',roomArea:'" + hrt.getRoomArea() 
								+ "',bedType:'" + hrt.getBedType() + "',bedWidth:'" + hrt.getBedWidth() 
								+ "',breakfast:'" + breakFast + "',isAddBed:'" + hrt.getIsAddBed() 
								+ "',numberIn:'" + hrt.getNumberIn() + "',broadband:'" + broadband + "'},");
					}
					
					roomJson = roomStr.toString();
					
					if(roomJson.endsWith(",")){
						roomJson = roomJson.substring(0,roomJson.length()-1) + "]";
					}
				}
			}else if(type.equals("roomManage")){
				
				String start = req.getParameter("start");
				String limit = req.getParameter("limit");
				
				List<HotelRoomManage> hotelRoomManageList = new ArrayList<HotelRoomManage>();
				
				List<HotelRoomType> hotelRoomTypeList = hotelRoomTypeDao.getAll();
				Map<String,String> priceOfRoom = new HashMap<String,String>();
				
				for(HotelRoomType hrt : hotelRoomTypeList){
					if(priceOfRoom.containsKey(hrt.getRoomTypeName()))
						continue;
					else
						priceOfRoom.put(hrt.getRoomTypeName(), hrt.getRoomPrice());
				}
				
				int totalCount = hotelRoomManageDao.getAll().size();
				
				hotelRoomManageList = hotelRoomManageDao.getAllByLimit(start,limit);
				
				Date now = cale.getTime();
				String a = sdf.format(now);
				now = sdf.parse(a);
								
				List<HotelBook> hbList = hotelBookDao.getByStatus("已确认");
				List<HotelCheckIn> hciList = hotelCheckInDao.getAll();
				
				List<Long> todayBeingBooked = new ArrayList<Long>();
				List<Long> todayBeingLived = new ArrayList<Long>();
				
				for(HotelBook hb : hbList){
					if(now.compareTo(hb.getCheckIn()) == 0)
						todayBeingBooked.add(hb.getBookRoomNo());
					else if(now.compareTo(hb.getCheckIn()) < 0)
						continue;
				}
				
				for(HotelCheckIn hci : hciList){
					if(now.compareTo(hci.getCheckOutTime()) <= 0)
						todayBeingLived.add(hci.getRoomNumber());
					else if(now.compareTo(hci.getCheckOutTime()) == 0)
						continue;
				}
				

				if(hotelRoomManageList != null && hotelRoomManageList.size() > 0){
					
					String status = null;
					
					HotelCheckIn hotelCheckIn = null;

					roomStr.append("{totalCount:" + totalCount + ",root:[");
					for(HotelRoomManage hrm : hotelRoomManageList){
						
						if(todayBeingBooked.contains(hrm.getRoomNumber()))
							status = "已预订";
						else if(todayBeingLived.contains(hrm.getRoomNumber())){
							hotelCheckIn = hotelCheckInDao.getByRoomNo((hrm.getRoomNumber()).toString());
							status = "已入住";
						}else
							status = "空闲";
						
						roomStr.append("{roomManageId:'" + hrm.getId() + "',roomNumber:'" + hrm.getRoomNumber() + "',roomTypeName:'" 
								+ hrm.getRoomTypeName() + "',roomPrice:'" + priceOfRoom.get(hrm.getRoomTypeName()) + "',floor:'" 
								+ hrm.getFloor() + "',status:'" + status + "',problem:'" + hrm.getProblem() + "'");
						
						if(hotelCheckIn != null){
							roomStr.append(",checkIn:'" + sdf.format(hotelCheckIn.getCheckInTime()) + "',checkOut:'"
									+ sdf.format(hotelCheckIn.getCheckOutTime()) + "'},");
							
							hotelCheckIn = null;
						}else
							roomStr.append("},");
					}
					
					roomJson = roomStr.toString();
					
					if(roomJson.endsWith(",")){
						roomJson = roomJson.substring(0,roomJson.length()-1) + "]}";
					}
				}
			}else if(StringUtils.isNotBlank(type) && type.equals("roomTotalInfo")){
				
				List<String> roomTypeNameList = new ArrayList<String>();
 			
				Date now = cale.getTime();
				String a = sdf.format(now);
				now = sdf.parse(a);
				
				List<HotelRoomType> hrtList = hotelRoomTypeDao.getAll();
				List<HotelCheckIn> hciList = null;
				List<HotelBook> hbList = null;
				
				for(HotelRoomType hrt : hrtList){
					roomTypeNameList.add(hrt.getRoomTypeName());
				}
				
				roomStr.append("[");
				
				
				for(String iter : roomTypeNameList){
					int roomCount = 0;
					int roomLefted = 0;
	 				int roomBooked = 0;
	 				int roomChecked = 0;
	 				
					roomCount = hotelRoomManageDao.countRoomByType(iter);
					hbList = hotelBookDao.getByRoomTypeAndStatus(iter, "已确认");
					hciList = hotelCheckInDao.getByRoomTypeName(iter);
					
					for(HotelBook hb : hbList){
						if((now.compareTo(hb.getCheckIn())) == 0)
							roomBooked++;
					}
					
					for(HotelCheckIn hci : hciList){
						if((now.compareTo(hci.getCheckInTime())) >= 0 && (now.compareTo(hci.getCheckOutTime())) <= 0 )
							roomChecked++;
					}
					
					roomLefted = roomCount - roomBooked - roomChecked;
					
	 				roomStr.append("{roomTypeName:'" + iter + "',roomCount:'" + roomCount + "',roomLeft:'" 
	 						+ roomLefted + "',roomBooked:'" + roomBooked + "',roomChecked:'" + roomChecked + "'},");
	 	
				}
				
				roomJson = roomStr.toString();
				
				if(roomJson.endsWith(",")){
					roomJson = roomJson.substring(0,roomJson.length()-1) + "]";
				}
			}else if(StringUtils.isNotBlank(type) && type.equals("bookStore")){
				
				List<HotelRoomType> hotelRoomTypeList = new ArrayList<HotelRoomType>();
				roomStr.append("[");

				hotelRoomTypeList = hotelRoomTypeDao.getAll();
				
				if(hotelRoomTypeList != null && hotelRoomTypeList.size() > 0){
					for(HotelRoomType hrt : hotelRoomTypeList){
						
						String breakFast = null;
						
						if(hrt.getBreakfast() == 1)
							breakFast = "有";
						else if(hrt.getBreakfast() == 0)
							breakFast = "无";
						
						roomStr.append("{roomTypeName:'" + hrt.getRoomTypeName() + "',retailPrice:'" + hrt.getRoomPrice() 
								+ "',guanWangPrice:'" + hrt.getGuanWangPrice() + "',memberPrice:'" + hrt.getMemberPrice()
								+ "',breakfast:'" + breakFast  + "'},");
					}
					
					roomJson = roomStr.toString();
					
					if(roomJson.endsWith(",")){
						roomJson = roomJson.substring(0,roomJson.length()-1) + "]";
					}
				}
				
			}else if(StringUtils.isNotBlank(type) && type.equals("selectedTypeOfRoomList")){
				String roomTypeName = req.getParameter("roomTypeName");
				String checkIn = req.getParameter("checkInTime");
				String checkOut = req.getParameter("checkOutTime");

				roomTypeName = new String(roomTypeName.getBytes("ISO-8859-1"),"UTF-8");
				checkIn = new String(checkIn.getBytes("ISO-8859-1"),"UTF-8");
				checkOut = new String(checkOut.getBytes("ISO-8859-1"),"UTF-8");

				Date checkInTime = sdf.parse(checkIn);
				Date checkOutTime = sdf.parse(checkOut);
				
				List<Long> bookableRoomNoList = null;
				
				bookableRoomNoList = getBookableRoomNo(checkInTime,checkOutTime,roomTypeName);	
				
				if(bookableRoomNoList != null && bookableRoomNoList.size() > 0){
					
					roomStr.append("[");
							
					for(Long bookable : bookableRoomNoList){
						roomStr.append("{text:'房间号:" + bookable + "',id:'" + bookable 
								+ "',leaf:true,checked:false},");
					}
					
					roomJson = roomStr.toString();
						
					if(roomJson.endsWith(",")){
						roomJson = roomJson.substring(0,roomJson.length()-1) + "]";
					}
				}
			}
			
			resp.setContentType("text/xml; charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = resp.getWriter();
			if(StringUtils.isNotBlank(roomJson)){
				pw.write(roomJson);
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
