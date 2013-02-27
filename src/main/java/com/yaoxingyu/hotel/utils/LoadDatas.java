package com.yaoxingyu.hotel.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoadDatas {
	public abstract void loadDatas(HttpServletRequest req, HttpServletResponse resp);
}
