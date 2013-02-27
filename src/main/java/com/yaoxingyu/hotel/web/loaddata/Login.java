package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelManagePermissionDao;
import com.yaoxingyu.hotel.dao.HotelUserManageDao;
import com.yaoxingyu.hotel.obj.HotelManagePermission;
import com.yaoxingyu.hotel.obj.HotelUserManage;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class Login implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(Login.class);
	
	private HotelUserManageDao hotelUserManageDao;
	
	private HotelManagePermissionDao hotelManagePermissionDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp) {
		
		if(hotelUserManageDao == null)
			hotelUserManageDao = (HotelUserManageDao)SpringApplicationContextHolderForHotel.getBean("hotelUserManageDao");
		
		if(hotelManagePermissionDao == null)
			hotelManagePermissionDao = (HotelManagePermissionDao)SpringApplicationContextHolderForHotel.getBean("hotelManagePermissionDao");
		
		String resultJson = null;

		String type = req.getParameter("type");
		
		try{
			
			if(StringUtils.isNotBlank(type)){
				
				String userId = req.getParameter("userId");
				String password = req.getParameter("password");
				
				if(type.equals("user")){
					if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password)){
						HotelUserManage hotelUserManage = null;
						hotelUserManage = hotelUserManageDao.getByUserId(userId);
						
						if(hotelUserManage != null && hotelUserManage.getPassword().equals(password)){
							
							StringBuilder cookieStr = new StringBuilder();
							cookieStr.append("userId-" + userId);
							Cookie cookie = new Cookie("userinfo",cookieStr.toString());
							cookie.setMaxAge(7200);
							resp.addCookie(cookie);
							
							resultJson = "[{success : 'true',Msg : '登录成功',memberCardId : '" + hotelUserManage.getMemberCardId() 
									+ "',phoneNumber : '" + hotelUserManage.getPhoneNumber() + "',email : '" + hotelUserManage.getEmail() 
									+ "',name : '" + hotelUserManage.getName() + "'}]";
						}else{
							resultJson = "[{success : 'false',Msg : '密码错误 请重新输入'}]";
						}
					}
				}else if(type.equals("checkLogin")){
					Cookie[] cookies = req.getCookies();
					String loginUserId = null;
					
					if(cookies != null && cookies.length != 0){
						for(Cookie cookie : cookies){
							if(cookie.getName().equals("userinfo")){
								loginUserId = cookie.getValue().split("-")[1];
							}
						}
					}
					
					if(StringUtils.isNotBlank(loginUserId)){
						HotelUserManage hotelUserManage = hotelUserManageDao.getByUserId(loginUserId);
						
						resultJson = "[{success : 'true',memberCardId : '" + hotelUserManage.getMemberCardId() 
								+ "',phoneNumber : '" + hotelUserManage.getPhoneNumber() + "',email : '" + hotelUserManage.getEmail() 
								+ "',name : '" + hotelUserManage.getName() + "'}]";
					}else{
						resultJson = "[{success : 'false'}]";
					}
				}else if(type.equals("manager")){
					if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password)){
						if(userId.equals("admin") && password.equals("admin")){
							resultJson = "[{success : 'true',attribute : 'admin'}]";
						}else if(userId.equals("user") && password.equals("user")){
							resultJson = "[{success : 'true',attribute : 'user'}]";
						}else{
							resultJson = "[{success : 'false']";
						}
					}else{
						resultJson = "[{success : 'false']";
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
