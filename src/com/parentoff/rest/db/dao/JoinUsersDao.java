package com.parentoff.rest.db.dao;


import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.user.model.JoinUsers;

/**
 * Created by ankit singh on 1/2/17.
 */
public class JoinUsersDao extends MyBatisDAO<JoinUsers, String> {

    public JoinUsersDao(Class<JoinUsers> type) {
        super(type);
    }
}
