package com.gtl.mmf.entity;
// Generated 5 Dec, 2015 1:20:59 PM by Hibernate Tools 3.6.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * CustomerTransactionExecutionDetailsTb generated by hbm2java
 */
public class CustomerTransactionExecutionDetailsTb implements java.io.Serializable {

    private Integer customerTransactionExecId;
    private MasterCustomerTb masterCustomerTb;
    private PortfolioTb portfolioTb;
    private long transId;
    private String orderId;
    private String securityCode;
    private String venueCode;
    private String venueScriptCode;
    private BigDecimal securityUnits;
    private BigDecimal securityPrice;
    private Date orderDate;
    private String omsUserid;
    private String buySell;
    private Boolean processed;

    public CustomerTransactionExecutionDetailsTb() {
        processed = false;
    }

    public CustomerTransactionExecutionDetailsTb(MasterCustomerTb masterCustomerTb, PortfolioTb portfolioTb, long transId, String orderId, BigDecimal securityUnits, Date orderDate, String omsUserid, String buySell) {
        this.masterCustomerTb = masterCustomerTb;
        this.portfolioTb = portfolioTb;
        this.transId = transId;
        this.orderId = orderId;
        this.securityUnits = securityUnits;
        this.orderDate = orderDate;
        this.omsUserid = omsUserid;
        this.buySell = buySell;
    }

    public CustomerTransactionExecutionDetailsTb(MasterCustomerTb masterCustomerTb, PortfolioTb portfolioTb, long transId, String orderId, String securityCode, String venueCode, String venueScriptCode, BigDecimal securityUnits, BigDecimal securityPrice, Date orderDate, String omsUserid, String buySell, Boolean processed) {
        this.masterCustomerTb = masterCustomerTb;
        this.portfolioTb = portfolioTb;
        this.transId = transId;
        this.orderId = orderId;
        this.securityCode = securityCode;
        this.venueCode = venueCode;
        this.venueScriptCode = venueScriptCode;
        this.securityUnits = securityUnits;
        this.securityPrice = securityPrice;
        this.orderDate = orderDate;
        this.omsUserid = omsUserid;
        this.buySell = buySell;
        this.processed = processed;
    }

    public Integer getCustomerTransactionExecId() {
        return this.customerTransactionExecId;
    }

    public void setCustomerTransactionExecId(Integer customerTransactionExecId) {
        this.customerTransactionExecId = customerTransactionExecId;
    }

    public MasterCustomerTb getMasterCustomerTb() {
        return this.masterCustomerTb;
    }

    public void setMasterCustomerTb(MasterCustomerTb masterCustomerTb) {
        this.masterCustomerTb = masterCustomerTb;
    }

    public PortfolioTb getPortfolioTb() {
        return this.portfolioTb;
    }

    public void setPortfolioTb(PortfolioTb portfolioTb) {
        this.portfolioTb = portfolioTb;
    }

    public long getTransId() {
        return this.transId;
    }

    public void setTransId(long transId) {
        this.transId = transId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getVenueCode() {
        return this.venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public String getVenueScriptCode() {
        return this.venueScriptCode;
    }

    public void setVenueScriptCode(String venueScriptCode) {
        this.venueScriptCode = venueScriptCode;
    }

    public BigDecimal getSecurityUnits() {
        return this.securityUnits;
    }

    public void setSecurityUnits(BigDecimal securityUnits) {
        this.securityUnits = securityUnits;
    }

    public BigDecimal getSecurityPrice() {
        return this.securityPrice;
    }

    public void setSecurityPrice(BigDecimal securityPrice) {
        this.securityPrice = securityPrice;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOmsUserid() {
        return this.omsUserid;
    }

    public void setOmsUserid(String omsUserid) {
        this.omsUserid = omsUserid;
    }

    public String getBuySell() {
        return this.buySell;
    }

    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

}