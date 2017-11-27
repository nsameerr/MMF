/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 * Master Investor RegistrationVo created for 5min auto save.
 * TempInvVo for investor registration page1.
 * TempInvNextPageVo for investor registration page2.
 * 
 * @author 09860
 */
public class InvestorTempRegistrationVo {
    
    private TempInvNextPageVo investorRegPage2;
    private TempInvVo  investorRegPage1;
    private CompleteTempInvVo completeRegPage;

    public TempInvNextPageVo getInvestorRegPage2() {
        return investorRegPage2;
    }

    public void setInvestorRegPage2(TempInvNextPageVo investorRegPage2) {
        this.investorRegPage2 = investorRegPage2;
    }

    public TempInvVo getInvestorRegPage1() {
        return investorRegPage1;
    }

    public void setInvestorRegPage1(TempInvVo investorRegPage1) {
        this.investorRegPage1 = investorRegPage1;
    }

	public CompleteTempInvVo getCompleteRegPage() {
		return completeRegPage;
	}

	public void setCompleteRegPage(CompleteTempInvVo completeRegPage) {
		this.completeRegPage = completeRegPage;
	}
    
    
}
