/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.common.EnumCustomerNotification;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class InvestorNotificationsVO {

    private String notificationMessage;
    private AdvisorDetailsVO advisorDetailsVO;
    private String buttonText;
    private boolean viewButtonDispStatus;
    private Date notificationDate;
    private EnumCustomerNotification enumCustomerNotification;
    private String elapsedTimeMsg;
    public EnumCustomerNotification getEnumCustomerNotification() {
        return enumCustomerNotification;
    }

    public void setEnumCustomerNotification(EnumCustomerNotification enumCustomerNotification) {
        this.enumCustomerNotification = enumCustomerNotification;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public AdvisorDetailsVO getAdvisorDetailsVO() {
        return advisorDetailsVO;
    }

    public void setAdvisorDetailsVO(AdvisorDetailsVO advisorDetailsVO) {
        this.advisorDetailsVO = advisorDetailsVO;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public boolean isViewButtonDispStatus() {
        return viewButtonDispStatus;
    }

    public void setViewButtonDispStatus(boolean viewButtonDispStatus) {
        this.viewButtonDispStatus = viewButtonDispStatus;
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
    
    
}
