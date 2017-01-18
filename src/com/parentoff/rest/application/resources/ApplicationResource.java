package com.parentoff.rest.application.resources;

import com.parentoff.rest.application.model.Application;
import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.ApplicationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/application")
public class ApplicationResource {

	public static ParentDAO<Application, String> dao = new ApplicationDao(
			Application.class);
	public static Logger LOGGER = LoggerFactory
			.getLogger(ApplicationResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Application> getAllApplications() {
		LOGGER.info("Entering getAllRoles..");
		List<Application> applications = null;
		try {
			applications = dao.getAll();
			LOGGER.info("Exiting getAllRoles.." + applications);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllRoles ", e);
		}

		return applications;
	}

	@GET
	@Path("{appId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Application getApplication(@PathParam("appId") String appId) {
		LOGGER.info("Entering getApplication..");
		Application application = null;
		try {
			application = dao.get(appId);
			LOGGER.info("Exiting getApplication.." + application);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getApplication ", e);
		}

		return application;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse addApplication(Application application) {
		LOGGER.info("adding new application");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info(application.toString());
			dao.insert(application);
			genericResponse.setResponseCode(200);
			genericResponse.setResponseDesc("success");

		}catch (Exception e){
			e.printStackTrace();
			genericResponse.setResponseCode(-1);
			genericResponse.setResponseDesc(e.getMessage());
		}

		return genericResponse;
	}

	@POST
	@Path("{appId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse updateApplication(@PathParam("appId") String appId, Application app) {
		LOGGER.info("updating new application");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info(app.toString());
			Application dbApp = dao.get(appId);
			dbApp.updateUser(app);
			LOGGER.info("updated application" + dbApp.toString());
			dao.update(dbApp);
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
	public GenericResponse deleteApplication(@PathParam("app_id") String app_id) {
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
