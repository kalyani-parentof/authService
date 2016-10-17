package com.parentoff.rest.user.resources;

import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.UserDao;
import com.parentoff.rest.db.dao.UserGroupDao;
import com.parentoff.rest.user.model.User;
import com.parentoff.rest.user.model.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user_group")
public class UserGroupsResource {

	public static ParentDAO<UserGroup, String> dao = new UserGroupDao(
			UserGroup.class);
	public static Logger LOGGER = LoggerFactory
			.getLogger(UserGroupsResource.class);


//	addusertogroup
//	getalluserfromgroup
//	removeuserfromgroup


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUserGroup() {
		LOGGER.info("Entering getAllUserGroup..");
		List<UserGroup> users = null;
		try {
			users = dao.getAll();
			LOGGER.info("Exiting getAllUserGroup.." + users);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllUserGroup ", e);
		}

		return users;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse addUser(User user) {
		LOGGER.info("adding new user");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info(user.toString());
			dao.insert(user);
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
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse deleteUser(@PathParam("username") String username) {
		LOGGER.info("adding new user");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info("deleting " + username);
			dao.delete(username);
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
