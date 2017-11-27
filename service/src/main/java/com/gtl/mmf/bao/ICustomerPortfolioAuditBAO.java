/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */
package com.gtl.mmf.bao;

public interface ICustomerPortfolioAuditBAO {

    public Integer auditBeforeUpdatePortfolio(Integer customerPortfolioId, String activityType);

}
