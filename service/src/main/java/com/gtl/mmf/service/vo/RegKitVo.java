/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.KitAllocationTb;
import java.util.Date;

/**
 *
 * @author 09860
 */
public class RegKitVo {

    private Integer from;
    private Integer to;
    private String allocationDate;
    private String status;
    private Integer lastKitAllotted;
    private Integer id;
    private String styleClass;
    private Integer balance;

    public KitAllocationTb toKitAllocationTb() {
        KitAllocationTb kitAllocationTb = new KitAllocationTb();
        kitAllocationTb.setFromValue(from);
        kitAllocationTb.setToValue(to);
        kitAllocationTb.setAllocationDate(new Date());
        kitAllocationTb.setKitStatus(status);
        return kitAllocationTb;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(String allocationDate) {
        this.allocationDate = allocationDate;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLastKitAllotted() {
        return lastKitAllotted;
    }

    public void setLastKitAllotted(Integer lastKitAllotted) {
        this.lastKitAllotted = lastKitAllotted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    
}
