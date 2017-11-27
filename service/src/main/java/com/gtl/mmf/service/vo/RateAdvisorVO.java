/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author trainee3
 */
public class RateAdvisorVO {

    private Integer relationId;
    private Short recommendationQuality;
    private Short recommendationFrequency;
    private Short responsivenessQuality;
    private Short totalRate;
    private Integer advisorId;
    private String advisorName;
    private String organization;
    private String jobTitle;
    private String benchMark;
    private Float portfolioReturn;
    private Float benchMarkReturn;
    private Integer outperformance;
    private boolean portfolioAssigned;
    private String evaluationPeriod;
    private String outPerformanceText;

    public String getOutPerformanceText() {
        return outPerformanceText;
    }

    public void setOutPerformanceText(String outPerformanceText) {
        this.outPerformanceText = outPerformanceText;
    }

    public RateAdvisorVO() {
        portfolioAssigned = false;
    }

    public Short getRecommendationQuality() {
        return recommendationQuality;
    }

    public void setRecommendationQuality(Short recommendationQuality) {
        this.recommendationQuality = recommendationQuality;
    }

    public Short getRecommendationFrequency() {
        return recommendationFrequency;
    }

    public void setRecommendationFrequency(Short recommendationFrequency) {
        this.recommendationFrequency = recommendationFrequency;
    }

    public Short getResponsivenessQuality() {
        return responsivenessQuality;
    }

    public void setResponsivenessQuality(Short responsivenessQuality) {
        this.responsivenessQuality = responsivenessQuality;
    }

    public Short getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Short totalRate) {
        this.totalRate = totalRate;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }

    public String getBenchMark() {
        return benchMark;
    }

    public void setBenchMark(String benchMark) {
        this.benchMark = benchMark;
    }

    public Integer getOutperformance() {
        return outperformance;
    }

    public void setOutperformance(Integer outperformance) {
        this.outperformance = outperformance;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public boolean isPortfolioAssigned() {
        return portfolioAssigned;
    }

    public void setPortfolioAssigned(boolean portfolioAssigned) {
        this.portfolioAssigned = portfolioAssigned;
    }

    public Float getPortfolioReturn() {
        return portfolioReturn;
    }

    public void setPortfolioReturn(Float portfolioReturn) {
        this.portfolioReturn = portfolioReturn;
    }

    public Float getBenchMarkReturn() {
        return benchMarkReturn;
    }

    public void setBenchMarkReturn(Float benchMarkReturn) {
        this.benchMarkReturn = benchMarkReturn;
    }

    public String getEvaluationPeriod() {
        return evaluationPeriod;
    }

    public void setEvaluationPeriod(String evaluationPeriod) {
        this.evaluationPeriod = evaluationPeriod;
    }
}
