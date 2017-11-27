/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.PortfolioTb;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAdvisorDashBoardDAO {

    public List<Object[]> getClientStatusDetails(int advisorId);

    public List<Object[]> getRebalanceNotification(int advisorId);
    
    public List<PortfolioTb> getAdvisorsPortfolios(int advisorId);

    public List<BigDecimal> getRecmdPFSubPeriodReturns(int portfolioId);

    public List<BigDecimal> getClientPFSubPeriodReturns(int customerPortfolioId);
    
    public Object[] getBenchmarkPerformance(Date startDate, Date currentDate);
}
