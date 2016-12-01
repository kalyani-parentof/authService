package com.parentoff.rest.db.dao;

import com.parentoff.rest.application.model.Role;
import com.parentoff.rest.db.MyBatisDAO;

public class RoleDao extends MyBatisDAO<Role, String> {

	public RoleDao(Class<Role> type) {
		super(type);
	}

}
