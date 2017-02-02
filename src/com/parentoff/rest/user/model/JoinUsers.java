package com.parentoff.rest.user.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by ankit singh on 1/2/17.
 */
public class JoinUsers {

    private int id;
    private String mobile;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String area;
    private String city;
    private String state;
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public JoinUsers update(JoinUsers other){
        if(other.getEmail() != null){
            this.setEmail(other.getEmail());
        }
        if(other.getMobile() != null){
            this.setMobile(other.getMobile());
        }
        if(other.getAddress() != null){
            this.setAddress(other.getAddress());
        }
        if(other.getArea() != null){
            this.setArea(other.getArea());
        }
        if(other.getCity() != null){
            this.setCountry(other.getCity());
        }
        if(other.getState() != null){
            this.setState(other.getState());
        }
        if(other.getCountry() != null){
            this.setCountry(other.getCountry());
        }
        if(other.getFirstname() != null){
            this.setFirstname(other.getFirstname());
        }
        if(other.getLastname() != null){
            this.setLastname(other.getLastname());
        }

        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
