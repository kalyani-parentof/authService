package com.parentoff.rest.user.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * Created by ankit on 10/5/16.
 */
public class User {

    public static Logger LOGGER = LoggerFactory
            .getLogger(User.class);

    String username;
    String firstname;
    String lastname;
    String email;


    private String newUserName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getNewUserName() {
        return newUserName;
    }

    @JsonIgnore
    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public void updateUser(User otherUser) {

        if(null != otherUser.getUsername()){
            this.setNewUserName(otherUser.getUsername());
        }

        if(null != otherUser.getFirstname()){
            this.setFirstname(otherUser.getFirstname());
        }
        if(null != otherUser.getLastname()){
            this.setLastname(otherUser.getLastname());
        }
        if(null != otherUser.getEmail()){
            this.setEmail(otherUser.getEmail());
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

