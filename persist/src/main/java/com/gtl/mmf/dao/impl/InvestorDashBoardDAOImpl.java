/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IInvestorDashBoardDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.ArrayList;
import java.util.Calendar;
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
public class InvestorDashBoardDAOImpl implements IInvestorDashBoardDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<CustomerAdvisorMappingTb> getInvestorNotifications(int clientId, Integer Status) {
        String hql = "FROM CustomerAdvisorMappingTb WHERE masterCustomerTb.customerId =:CustomerId "
                + "ORDER BY statusDate DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("CustomerId", clientId);
        return query.list();
    }

    public List<CustomerAdvisorMappingTb> getAdvisorNotifications(int advisorId) {
        String hql = "FROM CustomerAdvisorMappingTb WHERE masterAdvisorTb.advisorId =:AdvisorId ORDER BY statusDate DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("AdvisorId", advisorId);
        return query.list();
    }

    public CustomerPortfolioTb getPortfolioDetails(int customerId) {
        String hql = "FROM CustomerPortfolioTb WHERE masterCustomerTb =:customerId "
                + "AND customerAdvisorMappingTb IN (SELECT relationId FROM CustomerAdvisorMappingTb "
                + "WHERE relationStatus !=:blockedStatus AND masterCustomerTb =:customerId)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", customerId);
        query.setInteger("blockedStatus", 403);
        return (CustomerPortfolioTb) query.uniqueResult();
    }

    public List<Object> getBenchMarkPerfomance(Date currentDate, Date backDate, Integer customerId, String groupCondition) {
        List<Object> responseList = new ArrayList<Object>();
        Calendar cal = Calendar.getInstance();

        String sql = "SELECT T1.`benchmark_unit_value`,T1.`datetime`,T2.portfolio_assigned FROM customer_portfolio_performance_tb T1"
                + " INNER JOIN customer_portfolio_tb T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " WHERE  T1.`datetime` BETWEEN"
                + " T2.portfolio_assigned "
                + " AND :CureentDate"
                + " AND T2.`customer_id` = :CustomerId"
                + " GROUP BY T1.`datetime` ORDER BY T1.`datetime`;";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setDate("CureentDate", currentDate);
        query.setInteger("CustomerId", customerId);
        query.getQueryString();
        responseList.add(query.list());

        sql = "SELECT T1.`market_value_after_cash_flow`,T1.`datetime`,T3.portfolio_assigned  FROM recomended_customer_portfolio_performance_tb T1"
                + " INNER JOIN  customer_portfolio_tb T3  ON(T3.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " WHERE  T1.`datetime` BETWEEN"
                + " T3.portfolio_assigned "
                + " AND :CureentDate"
                + " AND T3.`customer_id` = :CustomerId"
                + " GROUP BY T1.`datetime` ORDER BY T1.`datetime`;";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setDate("CureentDate", currentDate);
        query.setInteger("CustomerId", customerId);
        responseList.add(query.list());

        sql = "SELECT T1.`market_value_after_cash_flow`,T1.`datetime`,T2.portfolio_assigned FROM customer_portfolio_performance_tb T1"
                + " INNER JOIN customer_portfolio_tb T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " WHERE  T1.`datetime` BETWEEN"
                + " T2.portfolio_assigned "
                + " AND :CureentDate"
                + " AND T2.`customer_id` = :CustomerId"
                + " GROUP BY T1.`datetime` ORDER BY T1.`datetime`;";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setDate("CureentDate", currentDate);
        query.setInteger("CustomerId", customerId);
        responseList.add(query.list());

        return responseList;
    }
}
