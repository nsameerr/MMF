/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author trainee3
 */
public interface IRateAdvisorDAO {

    public CustomerAdvisorMappingTb getAdvisordetails(Integer relationId);

    public Integer updateAdvisorRating(Short ratingOverall, Short ratingSubFreq, Short ratingSubQuality, Short ratingSubResponse, Integer outperformance, Integer relationId);

    public BigDecimal getCustomerPortfolioReturns(Date startDate, Date endDate, Integer customerPortfolioId);
    
    public List<BigDecimal> getCustomerPortfolioSubReturns(Date startDate, Date endDate, Integer customerPortfolioId);

    public Object[] getBenchmarkReturns(Date startDate, Date endDate, Integer benchmark);
}
