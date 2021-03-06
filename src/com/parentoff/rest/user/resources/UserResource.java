package com.parentoff.rest.user.resources;

import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.db.dao.JoinUsersDao;
import com.parentoff.rest.db.dao.UserDao;
import com.parentoff.rest.db.dao.UserInfoDao;
import com.parentoff.rest.user.model.JoinUsers;
import com.parentoff.rest.user.model.User;
import com.parentoff.rest.user.model.UserInfo;
import com.parentoff.rest.utils.ResponseCode;
import com.parentoff.rest.utils.ResponseDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user")
public class UserResource {

    public static MyBatisDAO<User, String> dao = new UserDao(
            User.class);
    public static MyBatisDAO<UserInfo, String> infoDao = new UserInfoDao(
            UserInfo.class);
    public static MyBatisDAO<JoinUsers, String> allUsrDao = new JoinUsersDao(
            JoinUsers.class);
    public static Logger LOGGER = LoggerFactory
            .getLogger(UserResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse getAllUsers() {
        LOGGER.info("Entering getAllUsers..");
        GenericResponse genericResponse = new GenericResponse();
        List<JoinUsers> users = null;
        try {
            Map<String, String> context = new HashMap<>();
            users = dao.getByqueryId("getAllUsersByJoin", context);
            genericResponse.setResponseCode(ResponseCode.SUCCESS);
            genericResponse.setResponseDesc(ResponseDesc.SUCCESS);
            genericResponse.setData(users);
            LOGGER.info("Exiting getAllUsers.." + users);
        } catch (Exception e) {
            LOGGER.error("Exception occured in getAllUsers ", e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse getUser(@PathParam("id") String id) {
        LOGGER.info("Entering getUser..");
        GenericResponse genericResponse = new GenericResponse();
        JoinUsers user = null;
        try {
            user = allUsrDao.get(id);
            LOGGER.info("Exiting getUser.." + user);
            genericResponse.setResponseCode(ResponseCode.SUCCESS);
            genericResponse.setResponseDesc(ResponseDesc.SUCCESS);
            genericResponse.setData(user);
        } catch (Exception e) {
            LOGGER.error("Exception occured in getUser ", e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.USER_NOT_FOUND);
            genericResponse.setData(user);
        }

        if (null == user) {
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(user);
        }


        return genericResponse;
    }

    @GET
    @Path("/mobile/{mobile}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse getUserByMobile(@PathParam("mobile") String mobile) {
        LOGGER.info("Entering getUser..");
        GenericResponse genericResponse = new GenericResponse();
        JoinUsers user = null;
        try {
            Map<String, String> context = new HashMap<>();
            context.put("mobile", mobile);
            user = allUsrDao.getOneByQueryId("getJoinUserByMobile", context);
            if(null == user){
                genericResponse.setResponseCode(ResponseCode.FAIL);
                genericResponse.setResponseDesc(ResponseDesc.USER_NOT_FOUND);
                genericResponse.setData(null);
            }else {
                genericResponse.setResponseCode(ResponseCode.SUCCESS);
                genericResponse.setResponseDesc(ResponseDesc.SUCCESS);
            }

            genericResponse.setData(user);
            LOGGER.info("Exiting getUser.." + user);
        } catch (Exception e) {
            LOGGER.error("Exception occured in getUser ", e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse addUser(JoinUsers joinUsers) {
        LOGGER.info("adding new user");
        GenericResponse genericResponse = new GenericResponse();
        try {
            LOGGER.info(joinUsers.toString());
            Map<String, String> context = new HashMap<>();
            context.put("mobile", joinUsers.getMobile());
            JoinUsers dbUser = allUsrDao.getOneByQueryId("getJoinUserByMobile", context);

            if (null == dbUser ) {
                User user = User.setFromJoinUser(joinUsers);
                dao.insert(user);
                User newDbUsr = dao.getOneByQueryId("getUserByMobile", context);
                joinUsers.setId(newDbUsr.getId());

                UserInfo userInfo = UserInfo.setFromJoinUser(joinUsers);
                infoDao.insert(userInfo);

                genericResponse.setResponseCode(ResponseCode.SUCCESS);
                genericResponse.setResponseDesc(ResponseDesc.SUCCESS);
                genericResponse.setData(joinUsers);
            } else {
                genericResponse.setResponseCode(ResponseCode.SUCCESS);
                genericResponse.setResponseDesc(ResponseDesc.USER_ALREADY_EXIST);
                genericResponse.setData(dbUser);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse updateUser(@PathParam("id") String id, JoinUsers joinUsers) {
        LOGGER.info("updating new user");
        GenericResponse genericResponse = new GenericResponse();
        try {
            LOGGER.info(joinUsers.toString());
            JoinUsers dbUser = allUsrDao.get(id);
            if (null != dbUser) {
                JoinUsers updatedUsr = dbUser.update(joinUsers);

                User user = User.setFromJoinUser(updatedUsr);
                dao.update(user);

                UserInfo userInfo = UserInfo.setFromJoinUser(updatedUsr);
                infoDao.update(userInfo);
                genericResponse.setResponseCode(ResponseCode.SUCCESS);
                genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

            } else {
                genericResponse.setResponseCode(ResponseCode.FAIL);
                genericResponse.setResponseDesc(ResponseDesc.USER_NOT_FOUND);
            }
            genericResponse.setData(dbUser);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse deleteUser(@PathParam("id") String id) {
        LOGGER.info("adding new user");
        GenericResponse genericResponse = new GenericResponse();
        try {
            LOGGER.info("deleting " + id);
            dao.delete(id);
            genericResponse.setResponseCode(ResponseCode.SUCCESS);
            genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

}
