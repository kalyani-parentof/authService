package com.parentoff.rest.application.resources;

import com.parentoff.rest.application.model.Application;
import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.ApplicationDao;
import com.parentoff.rest.utils.ResponseCode;
import com.parentoff.rest.utils.ResponseDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/application")
public class ApplicationResource {

	public static MyBatisDAO<Application, String> dao = new ApplicationDao(
			Application.class);
	public static Logger LOGGER = LoggerFactory
			.getLogger(ApplicationResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse getAllApplications() {
		LOGGER.info("Entering getAllRoles..");
		GenericResponse genericResponse = new GenericResponse();

		List<Application> applications = null;
		try {
			applications = dao.getAll();
			genericResponse.setResponseCode(ResponseCode.SUCCESS);
			genericResponse.setResponseDesc(ResponseDesc.SUCCESS);
			genericResponse.setData(applications);
			LOGGER.info("Exiting getAllRoles.." + applications);
		} catch (Exception e) {
			genericResponse.setResponseCode(ResponseCode.FAIL);
			genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
			genericResponse.setData(e.getMessage());
			LOGGER.error("Exception occured in getAllRoles ", e);
		}

		return genericResponse;
	}

	@GET
	@Path("{appId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse getApplication(@PathParam("appId") String appId) {
		LOGGER.info("Entering getApplication..");
		GenericResponse genericResponse = new GenericResponse();
		Application application = null;
		try {
			application = dao.get(appId);
			genericResponse.setResponseCode(ResponseCode.SUCCESS);
			genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

			if(null == application){
				genericResponse.setResponseDesc(ResponseDesc.APPLICATION_NOT_FOUND);
			}

			genericResponse.setData(application);

			LOGGER.info("Exiting getApplication.." + application);
		} catch (Exception e) {
			genericResponse.setResponseCode(ResponseCode.FAIL);
			genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
			genericResponse.setData(e.getMessage());

			LOGGER.error("Exception occured in getApplication ", e);
		}

		return genericResponse;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse addApplication(Application application) {
		LOGGER.info("adding new application");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info(application.toString());
			Application dbApp = dao.get(application.getId());
			genericResponse.setResponseCode(ResponseCode.SUCCESS);
			genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

			if(null == dbApp){
				LOGGER.info("app insert");
				dao.insert(application);
				genericResponse.setData(application);
			}else{
				LOGGER.info("db app exist");
				genericResponse.setResponseDesc(ResponseDesc.APPLICATION_ALREADY_EXIST);
				genericResponse.setData(dbApp);
			}




		}catch (Exception e){
			LOGGER.error(e.getMessage());
			genericResponse.setResponseCode(ResponseCode.FAIL);
			genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
			genericResponse.setData(e.getMessage());
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
			genericResponse.setResponseCode(ResponseCode.SUCCESS);
			genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

			if(dbApp == null){
				genericResponse.setResponseDesc(ResponseDesc.APPLICATION_NOT_FOUND);
			}else{
				Application updatedApp = dbApp.updateApp(app);
				LOGGER.info("updated application" + dbApp.toString());
				dao.update(updatedApp);
				genericResponse.setData(updatedApp);
			}


		}catch (Exception e){
			LOGGER.error(e.getMessage());
			genericResponse.setResponseCode(ResponseCode.FAIL);
			genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
			genericResponse.setData(e.getMessage());
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
			genericResponse.setResponseCode(ResponseCode.SUCCESS);
			genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

		}catch (Exception e){
			LOGGER.error(e.getMessage());
			genericResponse.setResponseCode(ResponseCode.FAIL);
			genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
			genericResponse.setData(e.getMessage());
		}

		return genericResponse;
	}

}
