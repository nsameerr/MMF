/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IRateAdvisorDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author trainee3
 */
public class RateAdvisorDAOImpl implements IRateAdvisorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public CustomerAdvisorMappingTb getAdvisordetails(Integer relationId) {
        String hql = "FROM CustomerAdvisorMappingTb WHERE relationId = :relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return (CustomerAdvisorMappingTb) query.setInteger("relationId", relationId).uniqueResult();
    }

    public Integer updateAdvisorRating(Short ratingOverall, Short ratingSubFreq, Short ratingSubQuality, Short ratingSubResponse, Integer outperformance, Integer relationId) {
        String sql = "UPDATE customer_advisor_mapping_tb a, `customer_portfolio_tb` b"
                + " SET a.rating_overall = :ratingOverall, a.rating_sub_freq = :ratingSubFreq, a.rating_sub_quality = :ratingSubQuality,"
                + " a.rating_sub_response = :ratingSubResponse, a.rate_advisor_flag = :rateAdvisorFlag, b.outperformance = :outperformance,"
                + " rateAdvisor_viewed=:rateAdvisorViewed"
                + " WHERE a.relation_id = :relationId AND b.relation_id = a.relation_id ";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setShort("ratingOverall", ratingOverall).setShort("ratingSubFreq", ratingSubFreq).setShort("ratingSubQuality", ratingSubQuality)
                .setShort("ratingSubResponse", ratingSubResponse).setInteger("outperformance", outperformance)
                .setBoolean("rateAdvisorFlag", false).setInteger("relationId", relationId).
                setBoolean("rateAdvisorViewed", true).executeUpdate();
    }

    public BigDecimal getCustomerPortfolioReturns(Date startDate, Date endDate, Integer customerPortfolioId) {
        BigDecimal portfolioReturn = null;
        String sql = "CALL eod_client_portfolio_performance_calculation_sp(:customerPortfolioId, :startDate, :endDate, :portfolioReturn)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("customerPortfolioId", customerPortfolioId).setDate("startDate", startDate)
                .setDate("endDate", endDate).setBigDecimal("portfolioReturn", portfolioReturn)
                .executeUpdate();
        return portfolioReturn;
    }

    public List<BigDecimal> getCustomerPortfolioSubReturns(Date startDate, Date endDate, Integer customerPortfolioId) {
        String hql = "SELECT subPeriodReturn FROM CustomerPortfolioPerformanceTb WHERE customerPortfolioTb = :customerPortfolioId"
                + " AND datetime BETWEEN :startDate AND :endDate";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setInteger("customerPortfolioId", customerPortfolioId).setDate("startDate", startDate)
                .setDate("endDate", endDate).list();
    }

    public Object[] getBenchmarkReturns(Date startDate, Date endDate, Integer benchmark) {

        String sql = "SELECT a.close FROM benchmark_performance_tb a WHERE DATE(a.datetime) = DATE(:startDate) AND a.benchmark = :benchmark";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Object startValue = sqlQuery.setInteger("benchmark", benchmark).setDate("startDate", startDate).uniqueResult();
        Object closeValue = sqlQuery.setInteger("benchmark", benchmark).setDate("startDate", endDate).uniqueResult();
        Object[] returnValue = {startValue, closeValue};
        return returnValue;

    }
}
