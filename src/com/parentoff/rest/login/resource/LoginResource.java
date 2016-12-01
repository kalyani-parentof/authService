package com.parentoff.rest.login.resource;

import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.AdminDao;
import com.parentoff.rest.logger.FileTracker;
import com.parentoff.rest.login.model.Admin;
import com.parentoff.rest.login.model.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Created by ankit on 10/5/16.
 */
@Path("/login")
public class LoginResource {
    public static ParentDAO<Admin, String> adminDao = new AdminDao(Admin.class);
    public static Logger LOGGER = LoggerFactory
            .getLogger(LoginResource.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse validateLogin(Admin admin) {
        LoginResponse response = new LoginResponse();
        HashMap<String, Object> map = new HashMap<String, Object>();

        try{
            LOGGER.info("login admin request " + admin);
            map.put("uiadmin", admin);

            Admin dbadmin = adminDao.get(admin.getUsername());
            map.put("dbadmin", dbadmin);

            LOGGER.info("db admin " + dbadmin);
            if(admin.equals(dbadmin)){
                response.setResponseDesc("success");
            }else {
                response.setResponseDesc("fail");
            }
            LOGGER.info("auth response " + response);

        }catch (Exception e){
            response.setResponseDesc("fail");
            map.put("error", e.getMessage());
        }finally {
            map.put("response", response);
            FileTracker fileTracker = new FileTracker("STARHEALTH_LOGIN");
            fileTracker.createMap(map);
            fileTracker.writeInfile();
        }

        return response;
    }

}
