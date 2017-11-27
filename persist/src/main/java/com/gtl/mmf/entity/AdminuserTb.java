package com.gtl.mmf.entity;
// Generated Feb 20, 2014 10:41:01 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;

/**
 * AdminuserTb generated by hbm2java
 */
public class AdminuserTb implements java.io.Serializable {

    private Integer id;
    private String username;
    private String password;
    private String userType;
    private String email;
    private String mobile;;

    public AdminuserTb() {
    }

    public AdminuserTb(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AdminuserTb(String username, String password, String userType, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.email = email;
        this.mobile = mobile;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
}