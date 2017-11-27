/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.util.Date;

/**
 *
 * @author trainee3
 */
public class AdvisorNotificationsVO {

    private String notificationMessage;
    private InvestorDetailsVO investorDetailsVO;
    private String buttonText;
    private Date notificationDate;
    private Integer PortfolioId;
    private String elapsedTimeMsg;
    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public InvestorDetailsVO getInvestorDetailsVO() {
        return investorDetailsVO;
    }

    public void setInvestorDetailsVO(InvestorDetailsVO investorDetailsVO) {
        this.investorDetailsVO = investorDetailsVO;
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

    public Integer getPortfolioId() {
        return PortfolioId;
    }

    public void setPortfolioId(Integer PortfolioId) {
        this.PortfolioId = PortfolioId;
    }

	public String getElapsedTimeMsg() {
		return elapsedTimeMsg;
	}

	public void setElapsedTimeMsg(String elapsedTimeMsg) {
		this.elapsedTimeMsg = elapsedTimeMsg;
	}  
}
