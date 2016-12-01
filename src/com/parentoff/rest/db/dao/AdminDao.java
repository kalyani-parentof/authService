package com.parentoff.rest.db.dao;

import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.login.model.Admin;

public class AdminDao extends MyBatisDAO<Admin, String> {

	public AdminDao(Class<Admin> type) {
		super(type);
	}

}
