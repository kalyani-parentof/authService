package com.parentoff.rest.user.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ankit singh on 1/2/17.
 */
public class UserInfo {

    public static Logger LOGGER = LoggerFactory
            .getLogger(User.class);

    private int user_id;
    private String firstname;
    private String lastname;
    private String address;
    private String area;
    private String city;
    private String state;
    private String country;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static UserInfo setFromJoinUser(JoinUsers joinUser){
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(joinUser.getId());
        userInfo.setFirstname(joinUser.getFirstname());
        userInfo.setLastname(joinUser.getLastname());
        userInfo.setAddress(joinUser.getAddress());
        userInfo.setArea(joinUser.getArea());
        userInfo.setCity(joinUser.getCity());
        userInfo.setState(joinUser.getState());
        userInfo.setCountry(joinUser.getCountry());

        if( 0 != joinUser.getId()){
            userInfo.setUser_id(joinUser.getId());
        }
        return userInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
