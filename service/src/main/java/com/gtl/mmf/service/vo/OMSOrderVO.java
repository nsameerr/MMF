/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.vo;

import com.git.oms.api.frontend.service.OMSServices;
import java.io.Serializable;

public class OMSOrderVO implements Serializable {

    private String omsUserid;
    private String omsUserPassword;
    private String omsSessionKey;
    private String omsRoleId;
    private String dpId;
    private String dpClientId;
    private String dpcode;
    private String moh;
    private String omsversion;
    private OMSServices omsServices;

    public String getOmsUserid() {
        return omsUserid;
    }

    public void setOmsUserid(String omsUserid) {
        this.omsUserid = omsUserid;
    }

    public String getOmsUserPassword() {
        return omsUserPassword;
    }

    public void setOmsUserPassword(String omsUserPassword) {
        this.omsUserPassword = omsUserPassword;
    }

    public String getOmsSessionKey() {
        return omsSessionKey;
    }

    public void setOmsSessionKey(String omsSessionKey) {
        this.omsSessionKey = omsSessionKey;
    }

    public String getOmsRoleId() {
        return omsRoleId;
    }

    public void setOmsRoleId(String omsRoleId) {
        this.omsRoleId = omsRoleId;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDpClientId() {
        return dpClientId;
    }

    public void setDpClientId(String dpClientId) {
        this.dpClientId = dpClientId;
    }

    public String getDpcode() {
        return dpcode;
    }

    public void setDpcode(String dpcode) {
        this.dpcode = dpcode;
    }

    public String getMoh() {
        return moh;
    }

    public void setMoh(String moh) {
        this.moh = moh;
    }

    public String getOmsversion() {
        return omsversion;
    }

    public void setOmsversion(String omsversion) {
        this.omsversion = omsversion;
    }

    public OMSServices getOmsServices() {
        return omsServices;
    }

    public void setOmsServices(OMSServices omsServices) {
        this.omsServices = omsServices;
    }

}
