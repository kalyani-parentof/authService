package com.parentoff.rest.user.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ankit on 10/5/16.
 */
public class Group {

    public static Logger LOGGER = LoggerFactory
            .getLogger(Group.class);

    String name;

    private String newName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getNewName() {
        return newName;
    }

    @JsonIgnore
    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void update(Group otherGroup){
        if(null != otherGroup.getName()){
            this.setNewName(otherGroup.getName());
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

