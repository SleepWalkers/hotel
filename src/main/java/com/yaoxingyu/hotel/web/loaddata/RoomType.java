package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelRoomTypeDao;
import com.yaoxingyu.hotel.obj.HotelRoomType;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class RoomType implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(RoomType.class);

	private HotelRoomTypeDao hotelRoomTypeDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp) {
		
		if(hotelRoomTypeDao == null)
			hotelRoomTypeDao = (HotelRoomTypeDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomTypeDao");
		
		String roomTypeId = req.getParameter("roomTypeId");
		String roomTypeName = req.getParameter("roomTypeName");
		String roomPrice = req.getParameter("roomPrice");
		String guanWangPrice = req.getParameter("guanWangPrice");
		String memberPrice = req.getParameter("memberPrice");
		String roomArea = req.getParameter("roomArea");
		String bedType = req.getParameter("bedType");
		String bedWidth = req.getParameter("bedWidth");
		String breakfast = req.getParameter("breakfast");
		String isAddBed = req.getParameter("isAddBed");
		String numberIn = req.getParameter("numberIn");
		String broadband = req.getParameter("broadband");
		
		String resultJson = null;
		try{
			if(StringUtils.isBlank(roomTypeId)){
				HotelRoomType hotelRoomType = new HotelRoomType();
				hotelRoomType.setRoomTypeName(roomTypeName);
				hotelRoomType.setRoomPrice(roomPrice);
				hotelRoomType.setGuanWangPrice(guanWangPrice);
				hotelRoomType.setMemberPrice(memberPrice);
				hotelRoomType.setRoomArea(Integer.parseInt(roomArea));
				hotelRoomType.setBedType(bedType);
				hotelRoomType.setBedWidth(bedWidth);
				if(breakfast.equals("有")){
					hotelRoomType.setBreakfast(1);
				}else if(breakfast.equals("没有")){
					hotelRoomType.setBreakfast(0);
				}
				hotelRoomType.setIsAddBed(isAddBed);
				hotelRoomType.setNumberIn(Integer.parseInt(numberIn));
				if(broadband.equals("有")){
					hotelRoomType.setBroadband(1);
				}else if(broadband.equals("没有")){
					hotelRoomType.setBroadband(0);
				}
				hotelRoomType.setGmtCreate(new Date());
				hotelRoomType.setGmtModify(new Date());
				hotelRoomTypeDao.insertRoomTypeInfo(hotelRoomType);
				
				resultJson = "[{Msg : '新建房间类型成功'}]";
				
			}else if(StringUtils.isNotBlank(roomTypeId)){
				HotelRoomType hotelRoomType = hotelRoomTypeDao.getById(roomTypeId);
				if(hotelRoomType != null){
					hotelRoomType.setRoomTypeName(roomTypeName);
					hotelRoomType.setRoomPrice(roomPrice);
					hotelRoomType.setGuanWangPrice(guanWangPrice);
					hotelRoomType.setMemberPrice(memberPrice);
					hotelRoomType.setRoomArea(Integer.parseInt(roomArea));
					hotelRoomType.setBedType(bedType);
					hotelRoomType.setBedWidth(bedWidth);
					if(breakfast.equals("有")){
						hotelRoomType.setBreakfast(1);
					}else if(breakfast.equals("没有")){
						hotelRoomType.setBreakfast(0);
					}
					hotelRoomType.setIsAddBed(isAddBed);
					hotelRoomType.setNumberIn(Integer.parseInt(numberIn));
					if(broadband.equals("有")){
						hotelRoomType.setBroadband(1);
					}else if(broadband.equals("没有")){
						hotelRoomType.setBroadband(0);
					}
					hotelRoomType.setGmtModify(new Date());
					
					hotelRoomTypeDao.updatetRoomTypeInfo(hotelRoomType);
					
					resultJson = "[{Msg : '修改房间类型成功'}]";
				}
			}else{
				resultJson = "[{Msg : '操作失败'}]";
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
}
