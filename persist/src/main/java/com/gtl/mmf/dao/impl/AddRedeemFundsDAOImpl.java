/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import java.util.Date;

import com.gtl.mmf.dao.IAddRedeemFundsDAO;
import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class AddRedeemFundsDAOImpl implements IAddRedeemFundsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public CustomerPortfolioTb getCustomerPortfolio(int customerId) {
        String hql = "FROM CustomerPortfolioTb WHERE masterCustomerTb = :customerId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", customerId);
        return (CustomerPortfolioTb) query.uniqueResult();
    }

    public int updateCustomerPortfolioAllcation(float newCashAmount, int customerPortfolioId,Double amount) {
        String hql = "UPDATE CustomerPortfolioTb SET cashAmount = :newCashAmount,"
                + " rebalanceRequired =:RebalanceRequired,"
                + " rebalanceReqdDate =:RebalanceReqDate,"
                + " lastUpdated =:LastExecutionUpdate,"
                + " allocatedFund =:allocatedFunds"
                + " WHERE customerPortfolioId = :customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("RebalanceRequired", true);
        query.setDate("LastExecutionUpdate", new Date());
        query.setDate("RebalanceReqDate", new Date());
        query.setFloat("newCashAmount", newCashAmount)
                .setInteger("customerPortfolioId", customerPortfolioId);
        query.setDouble("allocatedFunds", amount);
        return query.executeUpdate();
    }

    public int updateCustomerMappingAllcation(double allocatedFunds, int relationId) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET allocatedFunds = :allocatedFunds WHERE relationId = :relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble("allocatedFunds", allocatedFunds)
                .setInteger("relationId", relationId);
        return query.executeUpdate();
    }

    public int updateAvailableFund(double availableFund,double additionalAmount, int customerId) {
        String hql = "UPDATE MasterCustomerTb SET totalAvailableFunds = :availableFund,totalFunds =:additionalAmount,"
                + " totalAllocatedFunds =:additionalAmount WHERE customerId = :customerId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble("availableFund",0.0)
                .setInteger("customerId", customerId)
                .setDouble("additionalAmount", additionalAmount);
        return query.executeUpdate();
    }

    public Integer logAddRedeemFundDetails(AddRedeemLogTb addRedeemLogTb) {
        return (Integer) sessionFactory.getCurrentSession().save(addRedeemLogTb);
    }

    public List<Object[]> getlogAddRedeemFundDetails(int customerPortfolioId) {
       String sql = "Select addfund,for_amount From add_redeem_log_tb WHERE customer_portfolio_id =:portfolioId AND DATE_FORMAT(date_of_entry,'%Y-%m-%d')= DATE_FORMAT(:edate,'%Y-%m-%d')";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("portfolioId", customerPortfolioId);
        sqlQuery.setDate("edate",  new Date());
        return sqlQuery.list();
        
    }

    public void updateCashFlowTb(CashFlowTb cashFlowTb) {
        sessionFactory.getCurrentSession().save(cashFlowTb);
        sessionFactory.getCurrentSession().flush();
    }
}
