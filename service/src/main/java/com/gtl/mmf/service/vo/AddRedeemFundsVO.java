/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import java.io.Serializable;

/**
 *
 * @author 07958
 */
public class AddRedeemFundsVO extends CustomerPortfolioVO implements Serializable{

    private Integer benchmarkIndexId;
    private String benchmarkIndexName;
    private Double totalAvailableFund;
    private Double balanceFundAvailable;
    private Double finalValue;
    private Double addRedeemFund;
    private Boolean addFund;
    private Boolean zeroBalance;
    private Double cashBalanceAmount;
    private int orderStatus;
    private String message;
    public AddRedeemFundsVO() {
        addRedeemFund = ZERO_POINT_ZERO;
    }

    public Integer getBenchmarkIndexId() {
        return benchmarkIndexId;
    }

    public void setBenchmarkIndexId(Integer benchmarkIndexId) {
        this.benchmarkIndexId = benchmarkIndexId;
    }

    public String getBenchmarkIndexName() {
        return benchmarkIndexName;
    }

    public void setBenchmarkIndexName(String benchmarkIndexName) {
        this.benchmarkIndexName = benchmarkIndexName;
    }

    public Double getBalanceFundAvailable() {
        return balanceFundAvailable;
    }

    public void setBalanceFundAvailable(Double balanceFundAvailable) {
        this.balanceFundAvailable = balanceFundAvailable;
    }

    public Double getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Double finalValue) {
        this.finalValue = finalValue;
    }

    public Double getAddRedeemFund() {
        return addRedeemFund;
    }

    public void setAddRedeemFund(Double addRedeemFund) {
        this.addRedeemFund = addRedeemFund;
    }

    public Boolean getAddFund() {
        return addFund;
    }

    public void setAddFund(Boolean addFund) {
        this.addFund = addFund;
    }

    public Double getTotalAvailableFund() {
        return totalAvailableFund;
    }

    public void setTotalAvailableFund(Double totalAvailableFund) {
        this.totalAvailableFund = totalAvailableFund;
    }

    public Boolean isZeroBalance() {
        return zeroBalance;
    }

    public void setZeroBalance(Boolean zeroBalance) {
        this.zeroBalance = zeroBalance;
    }

    public Double getCashBalanceAmount() {
        return cashBalanceAmount;
    }

    public void setCashBalanceAmount(Double cashBalanceAmount) {
        this.cashBalanceAmount = cashBalanceAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
