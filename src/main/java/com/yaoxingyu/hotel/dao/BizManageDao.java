package com.yaoxingyu.hotel.dao;

import com.yaoxingyu.hotel.model.BizManage;

public interface BizManageDao {

	public void insertBiz(BizManage bizManage);

	public void updateBiz(BizManage bizManage);

	public void deleteById(String id);
}
