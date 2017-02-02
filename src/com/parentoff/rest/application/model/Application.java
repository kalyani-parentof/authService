package com.parentoff.rest.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ankit on 10/5/16.
 */
public class Application {

    public static Logger LOGGER = LoggerFactory
            .getLogger(Application.class);

    @JsonIgnore
    String newId;

    String id;
    String name;
    String domain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public Application updateApp(Application otherApp) {

        if(null != otherApp.getId()){
            this.setNewId(otherApp.getId());
        }

        if(null != otherApp.getName()){
            this.setName(otherApp.getName());
        }
        if(null != otherApp.getDomain()){
            this.setDomain(otherApp.getDomain());
        }
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

