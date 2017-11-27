/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.ICustomerPortfolioAuditBAO;
import com.gtl.mmf.dao.ICustomerPortfolioAuditDAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CustomerPortfolioAuditBAOImpl implements ICustomerPortfolioAuditBAO {

    // private static final String ACTIVITYTYPE = "DELETE";
    @Autowired
    ICustomerPortfolioAuditDAO customerPortfolioAuditDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer auditBeforeUpdatePortfolio(Integer portfolioId, String activityType) {
        int returnCount = 0;
        List<CustomerPortfolioTb> customerPortfolioTbs = customerPortfolioAuditDAO.getCustomerPortfoliosAssigned(portfolioId);
        for (CustomerPortfolioTb customerPortfolioTb : customerPortfolioTbs) {
            Date lastUpdateOn = new Date();
            int count = customerPortfolioAuditDAO.auditBeforeAssignPortfolio(customerPortfolioTb, lastUpdateOn, activityType);
            returnCount = returnCount + count;
        }
        return returnCount;
    }
}
