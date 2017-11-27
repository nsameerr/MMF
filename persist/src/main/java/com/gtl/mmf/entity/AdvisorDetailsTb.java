package com.gtl.mmf.entity;
// Generated Mar 27, 2015 12:31:09 PM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;

/**
 * AdvisorDetailsTb generated by hbm2java
 */
public class AdvisorDetailsTb  implements java.io.Serializable {


     private int advisorId;
     private MasterAdvisorTb masterAdvisorTb;
     private String registrationId;
     private String email;
     private Integer dominantStyle;
     private BigDecimal maxReturns90Days;
     private BigDecimal maxReturns1Year;
     private BigDecimal outperformance;
     private Integer outperformanceCountCompleted;
     private Integer outperformanceCountInprogress;
     private Integer outperformanceCount;
     private Integer totalPortfoliosManaged;
     private Integer customerRating;
     private BigDecimal avgClientRating;

    public AdvisorDetailsTb() {
    }

	
    public AdvisorDetailsTb(int advisorId, MasterAdvisorTb masterAdvisorTb, String registrationId, String email) {
        this.advisorId = advisorId;
        this.masterAdvisorTb = masterAdvisorTb;
        this.registrationId = registrationId;
        this.email = email;
    }
    public AdvisorDetailsTb(int advisorId, MasterAdvisorTb masterAdvisorTb, String registrationId, String email, Integer dominantStyle, BigDecimal maxReturns90Days, BigDecimal maxReturns1Year, BigDecimal outperformance, Integer outperformanceCountCompleted, Integer outperformanceCountInprogress, Integer outperformanceCount, Integer totalPortfoliosManaged, Integer customerRating, BigDecimal avgClientRating) {
       this.advisorId = advisorId;
       this.masterAdvisorTb = masterAdvisorTb;
       this.registrationId = registrationId;
       this.email = email;
       this.dominantStyle = dominantStyle;
       this.maxReturns90Days = maxReturns90Days;
       this.maxReturns1Year = maxReturns1Year;
       this.outperformance = outperformance;
       this.outperformanceCountCompleted = outperformanceCountCompleted;
       this.outperformanceCountInprogress = outperformanceCountInprogress;
       this.outperformanceCount = outperformanceCount;
       this.totalPortfoliosManaged = totalPortfoliosManaged;
       this.customerRating = customerRating;
       this.avgClientRating = avgClientRating;
    }
   
    public int getAdvisorId() {
        return this.advisorId;
    }
    
    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }
    public MasterAdvisorTb getMasterAdvisorTb() {
        return this.masterAdvisorTb;
    }
    
    public void setMasterAdvisorTb(MasterAdvisorTb masterAdvisorTb) {
        this.masterAdvisorTb = masterAdvisorTb;
    }
    public String getRegistrationId() {
        return this.registrationId;
    }
    
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getDominantStyle() {
        return this.dominantStyle;
    }
    
    public void setDominantStyle(Integer dominantStyle) {
        this.dominantStyle = dominantStyle;
    }
    public BigDecimal getMaxReturns90Days() {
        return this.maxReturns90Days;
    }
    
    public void setMaxReturns90Days(BigDecimal maxReturns90Days) {
        this.maxReturns90Days = maxReturns90Days;
    }
    public BigDecimal getMaxReturns1Year() {
        return this.maxReturns1Year;
    }
    
    public void setMaxReturns1Year(BigDecimal maxReturns1Year) {
        this.maxReturns1Year = maxReturns1Year;
    }
    public BigDecimal getOutperformance() {
        return this.outperformance;
    }
    
    public void setOutperformance(BigDecimal outperformance) {
        this.outperformance = outperformance;
    }
    public Integer getOutperformanceCountCompleted() {
        return this.outperformanceCountCompleted;
    }
    
    public void setOutperformanceCountCompleted(Integer outperformanceCountCompleted) {
        this.outperformanceCountCompleted = outperformanceCountCompleted;
    }
    public Integer getOutperformanceCountInprogress() {
        return this.outperformanceCountInprogress;
    }
    
    public void setOutperformanceCountInprogress(Integer outperformanceCountInprogress) {
        this.outperformanceCountInprogress = outperformanceCountInprogress;
    }
    public Integer getOutperformanceCount() {
        return this.outperformanceCount;
    }
    
    public void setOutperformanceCount(Integer outperformanceCount) {
        this.outperformanceCount = outperformanceCount;
    }
    public Integer getTotalPortfoliosManaged() {
        return this.totalPortfoliosManaged;
    }
    
    public void setTotalPortfoliosManaged(Integer totalPortfoliosManaged) {
        this.totalPortfoliosManaged = totalPortfoliosManaged;
    }
    public Integer getCustomerRating() {
        return this.customerRating;
    }
    
    public void setCustomerRating(Integer customerRating) {
        this.customerRating = customerRating;
    }
    public BigDecimal getAvgClientRating() {
        return this.avgClientRating;
    }
    
    public void setAvgClientRating(BigDecimal avgClientRating) {
        this.avgClientRating = avgClientRating;
    }




}

