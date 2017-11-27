/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.AdminuserTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import java.util.Date;

/**
 *
 * @author 07960
 */
public class LoginVO {

    private String username;
    private String password;
    private String usertype;
    private String redirectPage;
    private Date dob;
    private MasterAdvisorTb masterAdvisor;
    private MasterCustomerTb masterCustomer;
    private AdminuserTb adminuser;
    private Boolean investorWithAdvisor;
    private String accessToken;
    private String linkedInId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer relationId;
    private Integer relationStatus;
    private boolean linkedInConnected;
    private Integer initStatus;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getInvestorWithAdvisor() {
        return investorWithAdvisor;
    }

    public void setInvestorWithAdvisor(Boolean investorWithAdvisor) {
        this.investorWithAdvisor = investorWithAdvisor;
    }

    public String getRedirectPage() {
        return redirectPage;
    }

    public void setRedirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    public MasterAdvisorTb getMasterAdvisor() {
        return masterAdvisor;
    }

    public void setMasterAdvisor(MasterAdvisorTb masterAdvisor) {
        this.masterAdvisor = masterAdvisor;
    }

    public MasterCustomerTb getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomerTb masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public AdminuserTb getAdminuser() {
        return adminuser;
    }

    public void setAdminuser(AdminuserTb adminuser) {
        this.adminuser = adminuser;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId) {
        this.linkedInId = linkedInId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public Integer getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(Integer initStatus) {
        this.initStatus = initStatus;
    }

}
