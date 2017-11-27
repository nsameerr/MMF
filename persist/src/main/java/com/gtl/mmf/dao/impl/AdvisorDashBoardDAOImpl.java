/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IAdvisorDashBoardDAO;
import com.gtl.mmf.entity.PortfolioTb;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class AdvisorDashBoardDAOImpl implements IAdvisorDashBoardDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<PortfolioTb> getAdvisorsPortfolios(int advisorId) {
        String hql = "FROM PortfolioTb WHERE masterAdvisorTb = :advisorId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setInteger("advisorId", advisorId).list();
    }

    public List<Object[]> getClientStatusDetails(int advisorId) {
        String hql = "SELECT T3,T2,T1 FROM CustomerRiskProfileTb AS T1"
                + " INNER JOIN T1.masterPortfolioTypeTb AS T2"
                + " RIGHT OUTER JOIN T1.customerAdvisorMappingTb AS T3"
                + " WHERE T3.masterAdvisorTb=:AdvisorId "
                + " ORDER BY T3.statusDate DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("AdvisorId", advisorId);
        return query.list();
    }

    public List<Object[]> getRebalanceNotification(int advisorId) {
        String hql = "SELECT rebalanceReqdDate,portfolioId,rebalanceViewed,portfolioName "
                + "FROM PortfolioTb WHERE masterAdvisorTb = :advisorId "
                + "AND rebalanceRequired = :rebalanceRequired";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("advisorId", advisorId);
        query.setBoolean("rebalanceRequired", true);
        return query.list();
    }

    public List<BigDecimal> getClientPFSubPeriodReturns(int customerPortfolioId) {
        String hql = "SELECT subPeriodReturn FROM CustomerPortfolioPerformanceTb WHERE customerPortfolioTb = :customerPortfolioTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setInteger("customerPortfolioTb", customerPortfolioId).list();
    }

    public List<BigDecimal> getRecmdPFSubPeriodReturns(int portfolioId) {
        String hql = "SELECT subPeriodReturn FROM PortfolioPerformanceTb WHERE portfolioTb = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setInteger("portfolioId", portfolioId).list();
    }

    public Object[] getBenchmarkPerformance(Date startDate, Date currentDate) {
        String sql = "SELECT a.close FROM benchmark_performance_tb a WHERE DATE(a.datetime) = DATE(:dateValue) LIMIT 1";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Object startValue = sqlQuery.setParameter("dateValue", startDate).uniqueResult();
        Object closeValue = sqlQuery.setParameter("dateValue", currentDate).uniqueResult();
        Object[] objects = {startValue, closeValue};
        return objects;
    }
}
