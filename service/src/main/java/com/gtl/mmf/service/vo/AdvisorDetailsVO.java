/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class AdvisorDetailsVO {

    private Integer relationId;
    private int advisorId;
    private String firstName;
    private String workOrganization;
    private String jobTitle;
    private String qualification;
    private String connectionLevel;
    private String connectionShared;
    private String dominantStyle;
    private Double maxReturnsNintyDays;
    private Double maxReturnsOneYear;
    private Double outPerformance;
    private Double avgClientRating;
    private String sebiVerificationNo;
    private int status;
    private String statusValue;
    private String pictureURL;
    private String linkedInMemberid;
    private String regId;
    private String email;
    private String portfolioType;
    Boolean canDisableInvite;
    boolean linkedInConnected;
    private String linkedInProfile;

    public AdvisorDetailsVO() {
        relationId = 0;
        canDisableInvite = false;
    }

    public CustomerAdvisorMappingTb toCustomerAdvisorMappingTb() {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        MasterAdvisorTb masterAdvisorTb = new MasterAdvisorTb();
        masterAdvisorTb.setAdvisorId(advisorId);
        customerAdvisorMappingTb.setMasterAdvisorTb(masterAdvisorTb);
        if (relationId != null) {
            customerAdvisorMappingTb.setRelationId(relationId);
        }
        customerAdvisorMappingTb.setStatusDate(new Date());
        return customerAdvisorMappingTb;
    }

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDominantStyle() {
        return dominantStyle;
    }

    public void setDominantStyle(String dominantStyle) {
        this.dominantStyle = dominantStyle;
    }

    public Double getMaxReturnsNintyDays() {
        return maxReturnsNintyDays;
    }

    public void setMaxReturnsNintyDays(Double maxReturnsNintyDays) {
        this.maxReturnsNintyDays = maxReturnsNintyDays;
    }

    public Double getMaxReturnsOneYear() {
        return maxReturnsOneYear;
    }

    public void setMaxReturnsOneYear(Double maxReturnsOneYear) {
        this.maxReturnsOneYear = maxReturnsOneYear;
    }

    public Double getOutPerformance() {
        return outPerformance;
    }

    public void setOutPerformance(Double outPerformance) {
        this.outPerformance = outPerformance;
    }

    public Double getAvgClientRating() {
        return avgClientRating;
    }

    public void setAvgClientRating(Double avgClientRating) {
        this.avgClientRating = avgClientRating;
    }

    public String getWorkOrganization() {
        return workOrganization;
    }

    public void setWorkOrganization(String workOrganization) {
        this.workOrganization = workOrganization;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getSebiVerificationNo() {
        return sebiVerificationNo;
    }

    public void setSebiVerificationNo(String sebiVerificationNo) {
        this.sebiVerificationNo = sebiVerificationNo;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getConnectionLevel() {
        return connectionLevel;
    }

    public void setConnectionLevel(String connectionLevel) {
        this.connectionLevel = connectionLevel;
    }

    public String getConnectionShared() {
        return connectionShared;
    }

    public void setConnectionShared(String connectionShared) {
        this.connectionShared = connectionShared;
    }

    public String getLinkedInMemberid() {
        return linkedInMemberid;
    }

    public void setLinkedInMemberid(String linkedInMemberid) {
        this.linkedInMemberid = linkedInMemberid;
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

    public String getPortfolioType() {
        return portfolioType;
    }

    public void setPortfolioType(String portfolioType) {
        this.portfolioType = portfolioType;
    }

    public Boolean getCanDisableInvite() {
        return canDisableInvite;
    }

    public void setCanDisableInvite(Boolean canDisableInvite) {
        this.canDisableInvite = canDisableInvite;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(String linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }
}
