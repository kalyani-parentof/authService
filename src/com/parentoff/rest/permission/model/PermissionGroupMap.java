package com.parentoff.rest.permission.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ankit on 10/5/16.
 */
public class PermissionGroupMap {

    public static Logger LOGGER = LoggerFactory
            .getLogger(PermissionGroupMap.class);

    String perm_grp_name;
    String perm_name;

    public String getPerm_grp_name() {
        return perm_grp_name;
    }

    public void setPerm_grp_name(String perm_grp_name) {
        this.perm_grp_name = perm_grp_name;
    }

    public String getPerm_name() {
        return perm_name;
    }

    public void setPerm_name(String perm_name) {
        this.perm_name = perm_name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


