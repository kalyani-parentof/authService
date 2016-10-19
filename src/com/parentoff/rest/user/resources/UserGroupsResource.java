package com.parentoff.rest.user.resources;

import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.GroupDao;
import com.parentoff.rest.db.dao.UserDao;
import com.parentoff.rest.db.dao.UserGroupDao;
import com.parentoff.rest.user.model.Group;
import com.parentoff.rest.user.model.User;
import com.parentoff.rest.user.model.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user_group")
public class UserGroupsResource {

	public static ParentDAO<UserGroup, String> dao = new UserGroupDao(
			UserGroup.class);

	public static ParentDAO<User, String> user_dao = new UserDao(
			User.class);
	public static ParentDAO<Group, String> group_dao = new GroupDao(
			Group.class);
	public static Logger LOGGER = LoggerFactory
			.getLogger(UserGroupsResource.class);

	@GET
	@Path("/user/{group_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUserOfGroup(@PathParam("group_name") String group_name) {
		LOGGER.info("Entering getAllUserOfGroup..");
		List<User> users = null;
		try {
			Map<String, String> query_map = new HashMap<String, String>();
			query_map.put("grp_name", group_name);
			users = user_dao.getByqueryId("getUserOfGroup", query_map);
			LOGGER.info("Exiting getAllUserOfGroup.." + users);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllUserOfGroup ", e);
		}

		return users;
	}

	@GET
	@Path("/group/{user_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Group> getAllGroupsOfUser(@PathParam("user_name") String user_name) {
		LOGGER.info("Entering getAllGroupsOfUser..");
		List<Group> groups = null;
		try {
			Map<String, String> query_map = new HashMap<String, String>();
			query_map.put("user_name", user_name);
			groups = group_dao.getByqueryId("getAllGroupsOfUser", query_map);
			LOGGER.info("Exiting getAllGroupsOfUser.." + groups);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllGroupsOfUser ", e);
		}

		return groups;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserGroup> getAllUserGroup() {
		LOGGER.info("Entering getAllUserGroup..");
		List<UserGroup> userGroups = null;
		try {
			userGroups = dao.getAll();
			LOGGER.info("Exiting getAllUserGroup.." + userGroups);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllUserGroup ", e);
		}

		return userGroups;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse addUserToGroup(UserGroup userGroup) {
		LOGGER.info("adding new User Group");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info(userGroup.toString());
			dao.insert(userGroup);
			genericResponse.setResponseCode(200);
			genericResponse.setResponseDesc("success");

		}catch (Exception e){
			e.printStackTrace();
			genericResponse.setResponseCode(-1);
			genericResponse.setResponseDesc(e.getMessage());
		}

		return genericResponse;
	}

	@DELETE
	@Path("{group_name}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse removeUserFromGroup(@PathParam("group_name") String group_name,
									  @PathParam("username") String username) {
		LOGGER.info("removing user from group");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info("deleting " + username);
			Map<String, String> query_map = new HashMap<String, String>();
			query_map.put("grp_name", group_name);
			query_map.put("usr_name", username);
			dao.getByqueryId("deleteUserFromGroup", query_map);
			genericResponse.setResponseCode(200);
			genericResponse.setResponseDesc("success");

		}catch (Exception e){
			e.printStackTrace();
			genericResponse.setResponseCode(-1);
			genericResponse.setResponseDesc(e.getMessage());
		}

		return genericResponse;
	}

}
