/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;

public interface ICustomerPortfolioAuditDAO {

    public Integer auditBeforeAssignPortfolio(CustomerPortfolioTb customerPortfolioTb,
            Date lastUpdateOn, String activityType);

    public List<CustomerPortfolioTb> getCustomerPortfoliosAssigned(int portfolioId);
}
