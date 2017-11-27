/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 07958
 */
public class ClientDetailsVO {

    private Integer relationId;
    private String name;
    private String company;
    private String jobTitle;
    private String connectionLevel;
    private String status;
    private InvestorDetailsVO investorDetailsVO;
    private String styleClass;
    private String sharedConnection;

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConnectionLevel() {
        return connectionLevel;
    }

    public void setConnectionLevel(String connectionLevel) {
        this.connectionLevel = connectionLevel;
    }

    public InvestorDetailsVO getInvestorDetailsVO() {
        return investorDetailsVO;
    }

    public void setInvestorDetailsVO(InvestorDetailsVO investorDetailsVO) {
        this.investorDetailsVO = investorDetailsVO;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getSharedConnection() {
        return sharedConnection;
    }

    public void setSharedConnection(String sharedConnection) {
        this.sharedConnection = sharedConnection;
    }
}
