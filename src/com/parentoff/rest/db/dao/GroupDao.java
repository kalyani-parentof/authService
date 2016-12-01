package com.parentoff.rest.db.dao;

import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.user.model.Group;

public class GroupDao extends MyBatisDAO<Group, String> {

	public GroupDao(Class<Group> type) {
		super(type);
	}

}
