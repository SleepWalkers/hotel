package com.yaoxingyu.hotel.dao;

import com.yaoxingyu.hotel.model.UserManage;

public interface UserManageDao {

    public void insertUserInfo(UserManage userManage);

    public void updatetUserInfo(UserManage userManage);

    public UserManage getByUserId(String userId);

    public void deleteById(String id);

}
