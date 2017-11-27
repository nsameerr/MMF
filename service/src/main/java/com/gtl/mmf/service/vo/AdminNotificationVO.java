/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.service.vo;

import com.gtl.mmf.common.EnumAdminNotificationStatus;
import java.util.Date;

/**
 *
 * @author 09860
 */
public class AdminNotificationVO {
    
    private String notificationMessage;
    private String buttonText;
    private Date notificationDate;
    private String elapsedTimeMsg;
    private String notificationStatus;
    private Integer statusCode;
    private EnumAdminNotificationStatus enumAdminNotificationStatus;
    
    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getElapsedTimeMsg() {
        return elapsedTimeMsg;
    }

    public void setElapsedTimeMsg(String elapsedTimeMsg) {
        this.elapsedTimeMsg = elapsedTimeMsg;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
    
    public EnumAdminNotificationStatus getEnumAdminNotificationStatus() {
        return enumAdminNotificationStatus;
    }

    public void setEnumAdminNotificationStatus(EnumAdminNotificationStatus enumAdminNotificationStatus) {
        this.enumAdminNotificationStatus = enumAdminNotificationStatus;
    }
    
    
}
