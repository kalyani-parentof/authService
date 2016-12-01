package com.parentoff.rest.application.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ankit on 10/5/16.
 */
public class Role {

    public static Logger LOGGER = LoggerFactory
            .getLogger(Role.class);

    String app_id;
    String user_name;
    String user_grp;
    String perm_name;
    String perm_grp_name;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_grp() {
        return user_grp;
    }

    public void setUser_grp(String user_grp) {
        this.user_grp = user_grp;
    }

    public String getPerm_name() {
        return perm_name;
    }

    public void setPerm_name(String perm_name) {
        this.perm_name = perm_name;
    }

    public String getPerm_grp_name() {
        return perm_grp_name;
    }

    public void setPerm_grp_name(String perm_grp_name) {
        this.perm_grp_name = perm_grp_name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


