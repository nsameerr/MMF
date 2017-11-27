/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IInvestmentAdvProfileDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class InvestmentAdvProfileDAOImpl implements IInvestmentAdvProfileDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Double getInvestorDetails(Integer customerId) {
        String hql = " SELECT a.totalFunds FROM MasterCustomerTb a "
                + " WHERE a.customerId=:customerId AND a.isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", customerId);
        query.setBoolean("Active", Boolean.TRUE);
        return (Double) query.uniqueResult();
    }

    public Object[] getAdvisorRelationStatus(Integer relationId) {
        String hql = "SELECT a.allocatedFunds, a.pendingFees, a.contractEnd, a.relationStatus FROM CustomerAdvisorMappingTb a "
                + "WHERE a.relationId=:relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("relationId", relationId);
        return (Object[]) query.uniqueResult();
    }

    public Object[] getContractTerminationStatus(Integer relationId) {
        String sql = "SELECT a.`contract_terminate_status`,a.`advisor_id`,a.`relation_status`,"
                + "b.`blocked_cash`,b.`blocked_count`,b.`temp_blocked_cash`,b.`temp_blocked_count` "
                + "FROM `customer_advisor_mapping_tb` a,`customer_portfolio_tb` b "
                + "WHERE a.`relation_id`=:relationId AND a.`relation_id` = b.`relation_id`";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("relationId", relationId);
        return (Object[]) query.uniqueResult();
    }

    public Float getAllocatedFundForContractterminateduser(Integer customerId) {
        String sql = "SELECT `current_value` FROM `customer_portfolio_tb` WHERE `customer_id`=:customerId AND `portfolio_status`=:status";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("customerId", customerId);
        query.setString("status","ACTIVE");
        return (Float) query.uniqueResult();
    }
}
