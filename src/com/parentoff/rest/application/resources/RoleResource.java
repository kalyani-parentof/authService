package com.parentoff.rest.application.resources;

import com.parentoff.rest.application.model.Role;
import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.RoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by pooja on 9/11/16.
 */
public class RoleResource {

    public static ParentDAO<Role, String> dao = new RoleDao(
            Role.class);
    public static Logger LOGGER = LoggerFactory
            .getLogger(ApplicationResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Role> getAllRoles() {
        LOGGER.info("Entering getAllRoles..");
        List<Role> roles = null;
        try {
            roles = dao.getAll();
            LOGGER.info("Exiting getAllRoles.." + roles);
        } catch (Exception e) {
            LOGGER.error("Exception occured in getAllRoles ", e);
        }

        return roles;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse addRoles(Role role) {
        LOGGER.info("adding new role");
        GenericResponse genericResponse = new GenericResponse();
        try {
            LOGGER.info(role.toString());
            dao.insert(role);
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
    @Path("{app_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse deleteRoles(@PathParam("app_id") String app_id) {
        LOGGER.info("deleting application");
        GenericResponse genericResponse = new GenericResponse();
        try {
            LOGGER.info("deleting " + app_id);
            dao.delete(app_id);
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
