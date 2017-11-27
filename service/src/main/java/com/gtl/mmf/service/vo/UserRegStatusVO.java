/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author trainee3
 */
public class UserRegStatusVO {

    private boolean emailExist;
    private Integer regStatus;

    public boolean isEmailExist() {
        return emailExist;
    }

    public void setEmailExist(boolean emailExist) {
        this.emailExist = emailExist;
    }

    public Integer getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(Integer regStatus) {
        this.regStatus = regStatus;
    }

}
