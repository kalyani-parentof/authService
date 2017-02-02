package com.parentoff.rest.otp.resources;

import com.parentoff.client.rest.RestClient;
import com.parentoff.rest.common.GenericResponse;
import com.parentoff.rest.config.ConfigHelper;
import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.db.dao.OtpDao;
import com.parentoff.rest.db.dao.UserDao;
import com.parentoff.rest.otp.model.Otp;
import com.parentoff.rest.otp.model.OtpConfirm;
import com.parentoff.rest.user.model.User;
import com.parentoff.rest.utils.PhoneNumberUtil;
import com.parentoff.rest.utils.ResponseCode;
import com.parentoff.rest.utils.ResponseDesc;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by ankit on 10/5/16.
 */
@Path("/otp")
public class OtpResource {
    public static MyBatisDAO<Otp, String> otpDao = new OtpDao(Otp.class);
    public static MyBatisDAO<User, String> usrDao = new UserDao(
            User.class);
    public static Logger LOGGER = LoggerFactory
            .getLogger(OtpResource.class);

    private String smsUrl = ConfigHelper.getSpecificValue("smsUrl");
    private String smsUser = ConfigHelper.getSpecificValue("smsUser");
    private String smsPassword = ConfigHelper.getSpecificValue("smsPassword");
    private String smsSender = ConfigHelper.getSpecificValue("smsSender");
    private String priority = ConfigHelper.getSpecificValue("priority");
    private String msgType = ConfigHelper.getSpecificValue("msgType");
    private String smsTemplate = ConfigHelper.getSpecificValue("smsTemplate");

    @GET
    @Path("{mobile}/{app_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse sendOtp(@PathParam("app_id") String app_id, @PathParam("mobile") String mobile) {
        LOGGER.info("Sending Otp");
        GenericResponse genericResponse = new GenericResponse();

        try {
            Map<String, String> context = new HashMap<>();
            context.put("app_id", app_id);
            context.put("mobile", mobile);
            User user = usrDao.getOneByQueryId("getUserByMobile", context);
            if(null == user){
                user = new User();
                user.setMobile(mobile);
                usrDao.insert(user);

                user = usrDao.getOneByQueryId("getUserByMobile", context);
                context.put("user_id", String.valueOf(user.getId()));

            }else{
                context.put("user_id", String.valueOf(user.getId()));
            }
            genericResponse.setData(user);

            Otp otp = otpDao.getOneByQueryId("getOtpById", context);
            String token;

            if (PhoneNumberUtil.isMobileNumber(mobile)) {
                token = generateOtp();
                Otp newOtp = new Otp(app_id, user.getId(), token);
                LOGGER.info("New Otp : " + newOtp + otp);

                if (null == otp) {
                    otpDao.insert(newOtp);
                } else {
                    otpDao.update(newOtp);
                }

                sentOtpToUser(mobile, token);
                genericResponse.setResponseCode(ResponseCode.SUCCESS);
                genericResponse.setResponseDesc(ResponseDesc.SUCCESS);


            } else {
                genericResponse.setResponseCode(ResponseCode.FAIL);
                genericResponse.setResponseDesc(ResponseDesc.INVALID_MOBILE);
            }


        } catch (Exception e) {
            LOGGER.error("Exception occured in sending Otp ", e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

    @GET
    @Path("/email/{email}/{app_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse sendOtpToMail(@PathParam("app_id") String app_id, @PathParam("email") String email) {
        LOGGER.info("Sending Otp" + email);
        GenericResponse genericResponse = new GenericResponse();

        try {
            Map<String, String> context = new HashMap<>();
            context.put("app_id", app_id);
            context.put("email", email);
            User user = usrDao.getOneByQueryId("getUserByEmail", context);
            LOGGER.info("Sending Otp for user" + user + context);

            genericResponse.setData(user);

            if(null == user){
                genericResponse.setResponseCode(ResponseCode.FAIL);
                genericResponse.setResponseDesc(ResponseDesc.USER_NOT_FOUND);

            }else{
                context.put("user_id", String.valueOf(user.getId()));
                String token = generateOtp();

                Otp otp = otpDao.getOneByQueryId("getOtpById", context);
                Otp newOtp = new Otp(app_id, user.getId(), token);

                if (null == otp) {
                    otpDao.insert(newOtp);
                } else {
                    otpDao.update(newOtp);
                }
                sentMailOtpToUser(email, token);
                genericResponse.setResponseCode(ResponseCode.SUCCESS);
                genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

                LOGGER.info("New Otp : " + newOtp );
            }

        } catch (Exception e) {
            LOGGER.error("Exception occured in sending Otp ", e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.EXCEPTION);
            genericResponse.setData(e.getMessage());
        }

        return genericResponse;
    }

    @POST
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse validateOtp(OtpConfirm otpConfirm) {
        LOGGER.info("validating otp: " + otpConfirm);
        GenericResponse genericResponse = new GenericResponse();
        try {
            Map<String, String> context = new HashMap<>();
            context.put("app_id", otpConfirm.getApp_id());
            context.put("mobile", otpConfirm.getMobile());
            context.put("token", otpConfirm.getToken());
            context.put("email", otpConfirm.getEmail());
            User user = getValidUser(context);

            genericResponse.setData(user);

            if(null == user){
                genericResponse.setResponseCode(ResponseCode.FAIL);
                genericResponse.setResponseDesc(ResponseDesc.USER_NOT_FOUND);

            }else{
                context.put("user_id", String.valueOf(user.getId()));
                LOGGER.info("validating otp context: " + context);
                Otp dbOtp = otpDao.getOneByQueryId("getOtpById", context);
                LOGGER.info("validating otp existing otp: " + dbOtp);
                if ( null != dbOtp) {

                    if (dbOtp.getToken().equals(otpConfirm.getToken())) {
                        genericResponse.setResponseCode(ResponseCode.SUCCESS);
                        genericResponse.setResponseDesc(ResponseDesc.SUCCESS);

                    }else{
                        genericResponse.setResponseCode(ResponseCode.FAIL);
                        genericResponse.setResponseDesc(ResponseDesc.INVALID_OTP);
                    }
                } else {
                    genericResponse.setResponseCode(ResponseCode.FAIL);
                    genericResponse.setResponseDesc(ResponseDesc.OTP_NOT_FOUND);
                }
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            genericResponse.setResponseCode(ResponseCode.FAIL);
            genericResponse.setResponseDesc(ResponseDesc.FAIL);
        }
        LOGGER.info("validating otp response : " + genericResponse);

        return genericResponse;
    }


    private String generateOtp() {
        Random rnd = new Random();
        int n = 1000 + rnd.nextInt(9000);
        return Integer.toString(n);
    }

    private String trimNumber(String mobile) {
        String trimmedNumber = mobile;
        if (mobile != null) {
            int length = mobile.length();
            if (length != 10) {
                if (mobile.startsWith("91")) {
                    trimmedNumber = mobile.substring(2);
                } else if (mobile.startsWith("+91")) {
                    trimmedNumber = mobile.substring(3);
                }
            }
        }

        return trimmedNumber;
    }

    private User getValidUser(Map<String, String> context){
        User user = usrDao.getOneByQueryId("getUserByMobile", context);
        if(null == user){
            user = usrDao.getOneByQueryId("getUserByEmail", context);
        }
        return user;
    }

    private void sentOtpToUser(String mobile, String token) {
        String message = StringUtils.replace(smsTemplate, "#", token);
        LOGGER.info("sms to user: " + mobile + ", message: " + message);

        String trimmedNumber = trimNumber(mobile);

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("user", smsUser);
        queryMap.put("pass", smsPassword);
        queryMap.put("sender", smsSender);
        queryMap.put("phone", trimmedNumber);
        queryMap.put("text", message);
        queryMap.put("priority", priority);
        queryMap.put("stype", msgType);
        String response = RestClient.get(smsUrl, queryMap);
        LOGGER.info("sms to user: " + mobile + ", response: " + response);
    }

    private void sentMailOtpToUser(String emailId, String token){
        String message = StringUtils.replace(smsTemplate, "#", token);
        EmailManager.send(emailId, message);
    }

}