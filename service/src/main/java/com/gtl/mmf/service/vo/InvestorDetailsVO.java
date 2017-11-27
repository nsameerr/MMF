/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class InvestorDetailsVO implements Serializable {

    private Integer relationId;
    private int customerId;
    private String firstName;
    private String workOrganization;
    private String jobTitle;
    private String portFolio;
    private Double investedAssets;
    private String benchmark;
    private Double maxReturn90Days;
    private Double maxReturn365Days;
    private String startDate;
    private int status;
    private String statusValue;
    private Double allocatedInvestments;
    private String linkedInId;
    private String connectionLevel;
    private String pictureURL;
    private String portFolioStyle;
    private String regId;
    private String email;
    private boolean linkedInConnected;
    private String connectionShared;
    private String linkedinProfileUrl;
    Boolean canDisableInvite;

    public InvestorDetailsVO() {
        relationId = 0;
        canDisableInvite = false;
    }

    public CustomerAdvisorMappingTb toCustomerAdvisorMappingTb() {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
        masterCustomerTb.setCustomerId(customerId);
        customerAdvisorMappingTb.setMasterCustomerTb(masterCustomerTb);
        customerAdvisorMappingTb.setAllocatedFunds(allocatedInvestments);
        if (relationId != null) {
            customerAdvisorMappingTb.setRelationId(relationId);
        }
        customerAdvisorMappingTb.setStatusDate(new Date());
        return customerAdvisorMappingTb;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getWorkOrganization() {
        return workOrganization;
    }

    public void setWorkOrganization(String workOrganization) {
        this.workOrganization = workOrganization;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPortFolio() {
        return portFolio;
    }

    public void setPortFolio(String portFolio) {
        this.portFolio = portFolio;
    }

    public Double getInvestedAssets() {
        return investedAssets;
    }

    public void setInvestedAssets(Double investedAssets) {
        this.investedAssets = investedAssets;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Double getMaxReturn90Days() {
        return maxReturn90Days;
    }

    public void setMaxReturn90Days(Double maxReturn90Days) {
        this.maxReturn90Days = maxReturn90Days;
    }

    public Double getMaxReturn365Days() {
        return maxReturn365Days;
    }

    public void setMaxReturn365Days(Double maxReturn365Days) {
        this.maxReturn365Days = maxReturn365Days;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Double getAllocatedInvestments() {
        return allocatedInvestments;
    }

    public void setAllocatedInvestments(Double allocatedInvestments) {
        this.allocatedInvestments = allocatedInvestments;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId) {
        this.linkedInId = linkedInId;
    }

    public String getConnectionLevel() {
        return connectionLevel;
    }

    public void setConnectionLevel(String connectionLevel) {
        this.connectionLevel = connectionLevel;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getPortFolioStyle() {
        return portFolioStyle;
    }

    public void setPortFolioStyle(String portFolioStyle) {
        this.portFolioStyle = portFolioStyle;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public String getConnectionShared() {
        return connectionShared;
    }

    public void setConnectionShared(String connectionShared) {
        this.connectionShared = connectionShared;
    }

    public String getLinkedinProfileUrl() {
        return linkedinProfileUrl;
    }

    public void setLinkedinProfileUrl(String linkedinProfileUrl) {
        this.linkedinProfileUrl = linkedinProfileUrl;
    }
     public Boolean getCanDisableInvite() {
        return canDisableInvite;
    }

    public void setCanDisableInvite(Boolean canDisableInvite) {
        this.canDisableInvite = canDisableInvite;
    }
}
