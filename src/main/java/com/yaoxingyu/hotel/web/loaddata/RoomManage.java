package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelBookDao;
import com.yaoxingyu.hotel.dao.HotelCheckInDao;
import com.yaoxingyu.hotel.dao.HotelHistoryBookOrderDao;
import com.yaoxingyu.hotel.dao.HotelHistoryCheckInOrderDao;
import com.yaoxingyu.hotel.dao.HotelRoomManageDao;
import com.yaoxingyu.hotel.obj.HotelBook;
import com.yaoxingyu.hotel.obj.HotelCheckIn;
import com.yaoxingyu.hotel.obj.HotelHistoryBookOrder;
import com.yaoxingyu.hotel.obj.HotelHistoryCheckInOrder;
import com.yaoxingyu.hotel.obj.HotelRoomManage;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class RoomManage implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(RoomManage.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private HotelHistoryCheckInOrderDao hotelHistoryCheckInOrderDao;

	private HotelHistoryBookOrderDao hotelHistoryBookOrderDao;
		
	private HotelRoomManageDao hotelRoomManageDao;

	private HotelCheckInDao hotelCheckInDao;
	
	private HotelBookDao hotelBookDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp) {
		
		if(hotelHistoryCheckInOrderDao == null)
			hotelHistoryCheckInOrderDao = (HotelHistoryCheckInOrderDao)SpringApplicationContextHolderForHotel.getBean("hotelHistoryCheckInOrderDao");
		
		if(hotelHistoryBookOrderDao == null)
			hotelHistoryBookOrderDao = (HotelHistoryBookOrderDao)SpringApplicationContextHolderForHotel.getBean("hotelHistoryBookOrderDao");
		
		if(hotelRoomManageDao == null)
			hotelRoomManageDao = (HotelRoomManageDao)SpringApplicationContextHolderForHotel.getBean("hotelRoomManageDao");
		
		if(hotelCheckInDao == null)
			hotelCheckInDao = (HotelCheckInDao)SpringApplicationContextHolderForHotel.getBean("hotelCheckInDao");
		
		if(hotelBookDao == null)
			hotelBookDao = (HotelBookDao)SpringApplicationContextHolderForHotel.getBean("hotelBookDao");
		
		String resultJson = null;

		String type = req.getParameter("type");
		try{
			
			if(StringUtils.isBlank(type)){
				
				String roomManageId = req.getParameter("roomManageId");
				String roomNumber = req.getParameter("roomNumber");
				String roomTypeName = req.getParameter("roomTypeName");
				String floor = req.getParameter("floor");
				String problem = req.getParameter("problem");
				
				if(StringUtils.isBlank(roomManageId)){
					if(StringUtils.isNotBlank(roomNumber) && StringUtils.isNotBlank(roomTypeName) && StringUtils.isNotBlank(floor) 
							){
						HotelRoomManage hotelRoomManage = new HotelRoomManage();
						hotelRoomManage.setRoomNumber(Long.parseLong(roomNumber))	;
						hotelRoomManage.setRoomTypeName(roomTypeName);
						hotelRoomManage.setFloor(floor);
						
						if(StringUtils.isBlank(problem))
							hotelRoomManage.setProblem("");
						else
							hotelRoomManage.setProblem(problem);
						
						hotelRoomManage.setGmtCreate(new Date());
						hotelRoomManage.setGmtModify(new Date());
						hotelRoomManageDao.insertRoomInfo(hotelRoomManage);
						
						resultJson = "[{Msg : '新建房间成功'}]";
					}else{
						resultJson = "[{Msg : '所填信息不完整'}]";
					}
				}else if(StringUtils.isNotBlank(roomManageId)){
					HotelRoomManage hotelRoomManage = hotelRoomManageDao.getById(roomManageId);
					if(hotelRoomManage != null && StringUtils.isNotBlank(roomNumber) && StringUtils.isNotBlank(roomTypeName) 
							&& StringUtils.isNotBlank(floor)){
						hotelRoomManage.setRoomNumber(Long.parseLong(roomNumber))	;
						hotelRoomManage.setRoomTypeName(roomTypeName);
						hotelRoomManage.setFloor(floor);
						hotelRoomManage.setProblem(problem);
						hotelRoomManage.setGmtModify(new Date());
						hotelRoomManageDao.updatetRoomInfo(hotelRoomManage);
						resultJson = "[{Msg : '修改房间成功'}]";
					}
				}else{
					resultJson = "[{Msg : '操作失败'}]";
				}
			}else if(StringUtils.isNotBlank(type)){
				if(type.equals("batch")){
				
					String startRoomNo = req.getParameter("startRoomNo");
					String endRoomNo = req.getParameter("endRoomNo");
					String batchRoomType = req.getParameter("batchRoomType");
					String batchFloor = req.getParameter("batchFloor");
					
					if(StringUtils.isNotBlank(startRoomNo) && StringUtils.isNotBlank(endRoomNo) && StringUtils.isNotBlank(batchRoomType)
							&& StringUtils.isNotBlank(batchFloor)){
						HotelRoomManage hotelRoomManage = hotelRoomManageDao.getById(startRoomNo);
		
						if(hotelRoomManage == null){
							int start = Integer.parseInt(startRoomNo);
							int end = Integer.parseInt(endRoomNo);
							hotelRoomManage = new HotelRoomManage();
							
							for(int i = start;i <= end;i++){
								hotelRoomManage.setRoomNumber(Long.parseLong(Integer.toString(i)));
								hotelRoomManage.setRoomTypeName(batchRoomType);
								hotelRoomManage.setFloor(batchFloor);
								hotelRoomManage.setProblem("");
								hotelRoomManage.setGmtCreate(new Date());
								hotelRoomManage.setGmtModify(new Date());
								hotelRoomManageDao.insertRoomInfo(hotelRoomManage);
							}
							resultJson = "[{Msg : '批量新建房间成功'}]";
						}
					}else{
						resultJson = "[{Msg : '所填信息不完整'}]";
					}
				}else if(type.equals("checkIn")){
				
					String idCardNo = req.getParameter("idCardNo");
					String guestName = req.getParameter("guestName");
					String checkInDate = req.getParameter("checkInDate");
					String checkOutDate = req.getParameter("checkOutDate");
					String roomPrice = req.getParameter("roomPrice");
					String roomNumber = req.getParameter("roomNumber");
					String roomTypeName = req.getParameter("roomTypeName");
					
					String bookOrderId = req.getParameter("bookOrderId");
					
					if(StringUtils.isNotBlank(idCardNo) && StringUtils.isNotBlank(guestName) && StringUtils.isNotBlank(roomNumber) 
							&& StringUtils.isNotBlank(roomTypeName) && StringUtils.isNotBlank(checkOutDate) && StringUtils.isNotBlank(checkInDate)
							&& StringUtils.isNotBlank(roomPrice)){
						
						List<HotelBook> hotelBookList = hotelBookDao.getByRoomNumber(roomNumber);
						
						if(hotelBookList != null && hotelBookList.size() > 0){
							Date checkOut = sdf.parse(checkOutDate);
							for(HotelBook hb : hotelBookList){
								if((checkOut.compareTo(hb.getCheckIn())) > 0 && (checkOut.compareTo(hb.getCheckOut())) <= 0){
									if(StringUtils.isBlank(bookOrderId)){
										resultJson = "[{success : 'false',Msg : '该房间在" + sdf.format(hb.getCheckIn()) 
												+ " 到 " + sdf.format(hb.getCheckOut()) + "已经被预订了'}]";
									}else{
										if(Long.parseLong(bookOrderId) != hb.getId()){
											resultJson = "[{success : 'false',Msg : '该房间在" + sdf.format(hb.getCheckIn()) 
													+ " 到 " + sdf.format(hb.getCheckOut()) + "已经被预订了'}]";
										}else{
											
											HotelCheckIn hotelCheckIn = new HotelCheckIn();
											hotelCheckIn.setIdCardNo(idCardNo);
											hotelCheckIn.setGuestName(guestName);
											hotelCheckIn.setRoomTypeName(roomTypeName);
											hotelCheckIn.setRoomNumber(Long.parseLong(roomNumber));
											hotelCheckIn.setCheckInTime(sdf.parse(checkInDate));
											hotelCheckIn.setCheckOutTime(sdf.parse(checkOutDate));
											hotelCheckIn.setGmtCreate(new Date());
											hotelCheckIn.setGmtModify(new Date());
											
											hotelCheckInDao.insertCheckInInfo(hotelCheckIn);
											
											resultJson = "[{success : 'true',Msg : '入住登记成功 房间号为" + roomNumber + "'}]";
											
											HotelBook hotelBook = hotelBookDao.getByOrderId(bookOrderId);
											
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
												hotelHistoryBookOrder.setAttribute(1);
												hotelHistoryBookOrder.setGmtCreate(new Date());
												
												hotelHistoryBookOrderDao.insertHistroyBookOrder(hotelHistoryBookOrder);
								
												hotelBookDao.deleteById(bookOrderId);
											}
										}
									}
								}else
									continue;
							}
						}else{
							HotelCheckIn hotelCheckIn = new HotelCheckIn();
							hotelCheckIn.setIdCardNo(idCardNo);
							hotelCheckIn.setGuestName(guestName);
							hotelCheckIn.setRoomTypeName(roomTypeName);
							hotelCheckIn.setRoomNumber(Long.parseLong(roomNumber));
							hotelCheckIn.setCheckInTime(sdf.parse(checkInDate));
							hotelCheckIn.setCheckOutTime(sdf.parse(checkOutDate));
							hotelCheckIn.setGmtCreate(new Date());
							hotelCheckIn.setGmtModify(new Date());
							
							hotelCheckInDao.insertCheckInInfo(hotelCheckIn);
							
							resultJson = "[{success : 'true',Msg : '入住登记成功 房间号为" + roomNumber + "'}]";
						}
					}
				}else if(type.equals("checkOutRoomCheck")){
				
					String checkOutRoomNo = req.getParameter("checkOutRoomNo");
					
					if(StringUtils.isNotBlank(checkOutRoomNo)){
						HotelCheckIn hotelCheckIn = hotelCheckInDao.getByRoomNo(checkOutRoomNo);
						if(hotelCheckIn != null){
							resultJson = "[{success : 'true'}]";
						}else{
							resultJson = "[{success : 'false'}]";
						}
					}
				}else if(type.equals("checkOut")){
					String checkOutRoomNo = req.getParameter("checkOutRoomNo");
					if(StringUtils.isNotBlank(checkOutRoomNo)){
						HotelCheckIn hotelCheckIn = hotelCheckInDao.getByRoomNo(checkOutRoomNo);
						if(hotelCheckIn != null){
							HotelHistoryCheckInOrder hotelHistoryCheckInOrder = new HotelHistoryCheckInOrder();
							hotelHistoryCheckInOrder.setOrderId(hotelCheckIn.getId());
							hotelHistoryCheckInOrder.setIdCardNo(hotelCheckIn.getIdCardNo());
							hotelHistoryCheckInOrder.setGuestName(hotelCheckIn.getGuestName());
							hotelHistoryCheckInOrder.setRoomTypeName(hotelCheckIn.getRoomTypeName());
							hotelHistoryCheckInOrder.setRoomNumber(hotelCheckIn.getRoomNumber());
							hotelHistoryCheckInOrder.setCheckInTime(hotelCheckIn.getCheckInTime());
							hotelHistoryCheckInOrder.setCheckOutTime(hotelCheckIn.getCheckOutTime());
							hotelHistoryCheckInOrder.setGmtCreate(new Date());
							
							hotelHistoryCheckInOrderDao.insertHistroyCheckInOrder(hotelHistoryCheckInOrder);
							
							hotelCheckInDao.deleteByRoomNo(checkOutRoomNo);
							resultJson = "[{Msg : '退房成功'}]";
						}else{
							resultJson = "[{Msg : '退房失败,该房间没有入住'}]";
						}
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
}

