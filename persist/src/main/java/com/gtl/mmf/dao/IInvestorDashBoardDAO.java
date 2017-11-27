/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.BenchmarkPerformanceTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IInvestorDashBoardDAO {

    public List<CustomerAdvisorMappingTb> getInvestorNotifications(int clientId, Integer Status);

    public List<CustomerAdvisorMappingTb> getAdvisorNotifications(int advisorId);

    public CustomerPortfolioTb getPortfolioDetails(int customerId);

    public List<Object> getBenchMarkPerfomance(Date currentDate, Date backDate, Integer customerId, String groupCondition);

}
