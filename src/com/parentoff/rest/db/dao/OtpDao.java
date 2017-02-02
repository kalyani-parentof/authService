package com.parentoff.rest.db.dao;

import com.parentoff.rest.db.MyBatisDAO;
import com.parentoff.rest.otp.model.Otp;

/**
 * Created by ankit singh on 2/2/17.
 */
public class OtpDao extends MyBatisDAO<Otp, String> {

    public OtpDao(Class<Otp> type) {
        super(type);
    }
}
