package com.parentoff.rest.db.dao;

import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.user.model.UserInfo;

/**
 * Created by ankit singh on 1/2/17.
 */
public class UserInfoDao extends MyBatisDAO<UserInfo, String> {

    public UserInfoDao(Class<UserInfo> type) {
        super(type);
    }
}
