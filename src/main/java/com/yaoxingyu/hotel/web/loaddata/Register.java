package com.yaoxingyu.hotel.web.loaddata;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yaoxingyu.hotel.dao.HotelUserManageDao;
import com.yaoxingyu.hotel.obj.HotelUserManage;
import com.yaoxingyu.hotel.utils.LoadDatas;
import com.yaoxingyu.hotel.utils.SpringApplicationContextHolderForHotel;

public class Register implements LoadDatas{
	
	private static final Logger LOG = Logger.getLogger(Register.class);
	
	private HotelUserManageDao hotelUserManageDao;
	
	public void loadDatas(HttpServletRequest req, HttpServletResponse resp) {
		
		if(hotelUserManageDao == null)
			hotelUserManageDao = (HotelUserManageDao)SpringApplicationContextHolderForHotel.getBean("hotelUserManageDao");
		
		
		String resultJson = null;

		String type = req.getParameter("type");
		
		try{
			if(StringUtils.isNotBlank(type)){
				if(type.equals("user")){
					
					String userId = req.getParameter("userId");
					String password = req.getParameter("password");
					String realName = req.getParameter("realName");
					String phone = req.getParameter("phone");
					String email = req.getParameter("email");
					
					if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(realName) 
							&& StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(email)){
						HotelUserManage hotelUserManage = new HotelUserManage();
						hotelUserManage.setUserId(userId);
						hotelUserManage.setPassword(password);
						hotelUserManage.setName(realName);
						hotelUserManage.setPhoneNumber(Long.parseLong(phone));
						hotelUserManage.setEmail(email);
						hotelUserManage.setGmtCreate(new Date());
						hotelUserManage.setGmtModify(new Date());
						
			//			int a = realName.hashCode();
						
						hotelUserManageDao.insertUserInfo(hotelUserManage);
						
						resultJson = "[{success : 'true',Msg : '注册成功'}]";
						
						StringBuilder cookieStr = new StringBuilder();
						cookieStr.append("userId-" + userId);
						Cookie cookie = new Cookie("userinfo",cookieStr.toString());
						cookie.setMaxAge(7200);
						resp.addCookie(cookie);
						
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
