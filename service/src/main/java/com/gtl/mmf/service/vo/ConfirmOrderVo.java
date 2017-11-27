/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 09860
 */
public class ConfirmOrderVo {

    String exchange;
    String symbol;
    String scripCode;
    String priceCondition;
    String amcCode;
    String category;
    String buyOrSell;
    Double quantity;
    Double price;
    String moh;
    String depositoryMode;
    String purchaseType;
    String bookType;
    String triggerprice;
    String disclosedQty;
    Double totalOrderValue;
    private String  buySellTextColor;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getScripCode() {
        return scripCode;
    }

    public void setScripCode(String scripCode) {
        this.scripCode = scripCode;
    }

    public String getPriceCondition() {
        return priceCondition;
    }

    public void setPriceCondition(String priceCondition) {
        this.priceCondition = priceCondition;
    }

    public String getAmcCode() {
        return amcCode;
    }

    public void setAmcCode(String amcCode) {
        this.amcCode = amcCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMoh() {
        return moh;
    }

    public void setMoh(String moh) {
        this.moh = moh;
    }

    public String getDepositoryMode() {
        return depositoryMode;
    }

    public void setDepositoryMode(String depositoryMode) {
        this.depositoryMode = depositoryMode;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getTriggerprice() {
        return triggerprice;
    }

    public void setTriggerprice(String triggerprice) {
        this.triggerprice = triggerprice;
    }

    public String getDisclosedQty() {
        return disclosedQty;
    }

    public void setDisclosedQty(String disclosedQty) {
        this.disclosedQty = disclosedQty;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(Double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public String getBuySellTextColor() {
        return buySellTextColor;
    }

    public void setBuySellTextColor(String buySellTextColor) {
        this.buySellTextColor = buySellTextColor;
    }

    
    
}
