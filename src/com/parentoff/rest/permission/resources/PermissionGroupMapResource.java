package com.parentoff.rest.permission.resources;

import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.db.ParentDAO;
import com.parentoff.rest.db.dao.PermissionDao;
import com.parentoff.rest.db.dao.PermissionGroupDao;
import com.parentoff.rest.db.dao.PermissionGroupMapDao;
import com.parentoff.rest.permission.model.Permission;
import com.parentoff.rest.permission.model.PermissionGroup;
import com.parentoff.rest.permission.model.PermissionGroupMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/perm_grp_map")
public class PermissionGroupMapResource {

	public static ParentDAO<PermissionGroupMap, String> dao = new PermissionGroupMapDao(
			PermissionGroupMap.class);

	public static ParentDAO<PermissionGroup, String> permgrp_dao = new PermissionGroupDao(
			PermissionGroup.class);

	public static ParentDAO<Permission, String> permission_dao = new PermissionDao(
			Permission.class);
	public static Logger LOGGER = LoggerFactory
			.getLogger(PermissionGroupMapResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PermissionGroupMap> getAllPermissionGroupMap() {
		LOGGER.info("Entering getAllPermissions..");
		List<PermissionGroupMap> permissionGroupMaps = null;
		try {
			permissionGroupMaps = dao.getAll();
			LOGGER.info("Exiting getAllPermissions.." + permissionGroupMaps);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllPermissions ", e);
		}

		return permissionGroupMaps;
	}

	@GET
	@Path("/roles/{perm_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PermissionGroup> getAllRolesOfPermission(@PathParam("perm_name") String perm_name) {
		LOGGER.info("Entering getAllRolesOfPermission..");
		List<PermissionGroup> permissionGroups = null;
		try {
			Map<String, String> query_map = new HashMap<String, String>();
			query_map.put("perm_name", perm_name);
			permissionGroups = permgrp_dao.getByqueryId("getAllRolesOfPermission", query_map);
			LOGGER.info("Exiting getAllGroupsOfUser.." + permissionGroups);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllRolesOfPermission ", e);
		}

		return permissionGroups;
	}

	@GET
	@Path("/permissions/{perm_grp_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Permission> getAllPermmissionOfRole(@PathParam("perm_grp_name") String perm_grp_name) {
		LOGGER.info("Entering getAllPermmissionOfRole..");
		List<Permission> permissions = null;
		try {
			Map<String, String> query_map = new HashMap<String, String>();
			query_map.put("perm_grp_name", perm_grp_name);
			permissions = permission_dao.getByqueryId("getAllPermmissionOfRole", query_map);
			LOGGER.info("Exiting getAllPermmissionOfRole.." + permissions);
		} catch (Exception e) {
			LOGGER.error("Exception occured in getAllPermmissionOfRole ", e);
		}

		return permissions;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse addPermissionToGroup(PermissionGroupMap permissionGroupMap) {
		LOGGER.info("adding new Role");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info(permissionGroupMap.toString());
			dao.insert(permissionGroupMap);
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
	@Path("{perm_grp_name}/{perm_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericResponse deletePermissionGroupMap(@PathParam("perm_grp_name") String perm_grp_name,
													@PathParam("perm_name") String perm_name) {
		LOGGER.info("deleting perm_name");
		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.info("deleting " + perm_name);
			Map<String, String> query_map = new HashMap<String, String>();
			query_map.put("perm_grp_name", perm_grp_name);
			query_map.put("perm_name", perm_name);
			dao.getByqueryId("deletePermissionFromRole", query_map);
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
