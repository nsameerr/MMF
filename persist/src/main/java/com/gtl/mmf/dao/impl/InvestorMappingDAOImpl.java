/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import java.util.Date;

import com.gtl.mmf.dao.IInvestorMappingDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class InvestorMappingDAOImpl implements IInvestorMappingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Integer inviteNewAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping) {
        return (Integer) sessionFactory.getCurrentSession().save(customerAdvisorMapping);
    }

    public int inviteOldAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping) {

        //Status date updated
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,"
                + " status_date = :StatusDate, advisorRequest =:AdvisorRequest,"
                + " allocatedFunds =  :AllocatedFund,"
                + " advisorViewed = :advisorViwed "
                + " WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId());
        query.setBoolean("AdvisorRequest", customerAdvisorMapping.getAdvisorRequest());
        query.setBoolean("advisorViwed", false);
        query.setParameter("AllocatedFund", customerAdvisorMapping.getAllocatedFunds());
        return query.executeUpdate();
    }

    public int withdrawAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping) {

        //Status date updated
        String hql = "UPDATE CustomerAdvisorMappingTb "
                + " SET relationStatus = :RelationStatus, status_date = :StatusDate,"
                + " allocatedFunds =  :AllocatedFund"
                + " WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId());
        query.setParameter("AllocatedFund", null);
        return query.executeUpdate();
    }

    public int rejectAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping) {

        //Status date updated
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus, status_date = :StatusDate WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId());
        return query.executeUpdate();
    }

    public int acceptAdvisor(Integer relationId, short relationStatus, Double allocatedFunds) {

        //Status date updated
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,status_date= :StatusDate,"
                + " allocatedFunds = :AllocatedFunds, advisorViewed = :advisorViewed WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("RelationStatus", relationStatus);
        query.setParameter("RelationId", relationId);
        query.setTimestamp("StatusDate", new Date());
        query.setBoolean("advisorViewed", false);
        query.setParameter("AllocatedFunds", allocatedFunds);
        return query.executeUpdate();
    }

    public int contractReview(Integer relationId, Integer relationStatus, String review, Double allocatedFund, Short reviewFrequency) {

        //Status date updated
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,"
                + "status_date = :StatusDate, customerReview =:CustomerReview,"
                + " allocatedFunds =:AllocatedFunds, reviewFreq =:ReviewFrequency,investorViewed = :investorViwed,advisorViewed =:advisorViewed "
                + "WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationId", relationId)
                .setInteger("RelationStatus", relationStatus)
                .setString("CustomerReview", review)
                .setDouble("AllocatedFunds", allocatedFund)
                .setLong("ReviewFrequency", reviewFrequency);
        query.setTimestamp("StatusDate", new Date());
        query.setBoolean("advisorViewed", false);
        query.setBoolean("investorViwed", false);
        return query.executeUpdate();
    }

    public int contractAccept(Integer relationId, Integer relationStatus) {

        //Status date updated
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,status_date = :StatusDate WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationId", relationId).setInteger("RelationStatus", relationStatus);
        query.setTimestamp("StatusDate", new Date());
        return query.executeUpdate();
    }

    public Double getInvestorTotalFunds(int customerId) {
        String hql = " SELECT totalFunds FROM MasterCustomerTb"
                + " WHERE customerId = :customerId AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", customerId);
        query.setBoolean("Active", Boolean.TRUE);
        return (Double) query.uniqueResult();
    }

    public int updateInvestorFundDetails(int customerId, Double allocatedFund, Double totalAvaialable) {
        String hql = "UPDATE MasterCustomerTb SET totalAllocatedFunds = :allocatedFund, totalAvailableFunds = :totalAvaialable,"
                + " initLogin =:loginStaus"
                + " WHERE customerId = :customerId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", customerId)
                .setDouble("allocatedFund", allocatedFund)
                .setDouble("totalAvaialable", totalAvaialable)
                .setInteger("loginStaus", 2);
        return query.executeUpdate();
    }

    public void updateNotificationStatus(Integer relationId) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET investorViewed = :investorViewed WHERE relationId = :relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("investorViewed", true);
        query.setInteger("relationId", relationId);
        query.executeUpdate();

    }

    public void updateNotificationStatusRebalance(Integer relationId, Integer advisorid) {
        String hql = "UPDATE customer_portfolio_tb SET rebalanceViewed = " + true
                + ",portfolioChangeViewed =" + true + " WHERE relation_id = " + relationId + " AND"
                + " advisor_id = " + advisorid + " AND (portfolioChangeViewed =" + false + " OR rebalanceViewed =" + false + ")";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.executeUpdate();
    }

    public void updateNotificationStatusRateAdvisor(Integer relationId) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET rateAdvisorViewed = :rateAdvisorViewed WHERE relationId = :relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("rateAdvisorViewed", true);
        query.setInteger("relationId", relationId);
        query.executeUpdate();
    }

    public Integer updateContratDetails(int relationId) {
        String sql = "UPDATE `customer_advisor_mapping_tb` T1,`master_customer_tb` T2,`customer_portfolio_tb` T3 "
                + "SET T1.`relation_status` =:contractteminateStatus,T1.`actual_contract_end_date`=:currentDate "
                + "WHERE T1.`customer_id`=T2.`customer_id` AND T1.`relation_id`=:relationId";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("contractteminateStatus", 403);
        query.setInteger("relationId", relationId);
        query.setTimestamp("currentDate", new Date());
        return query.executeUpdate();
    }
    
    public List<Object[]> advisorDetails(int relationId){
        String sql = "SELECT `first_name`,`email` FROM `master_advisor_tb` "
                + "WHERE `advisor_id`=(SELECT `advisor_id` FROM `customer_advisor_mapping_tb` "
                + "WHERE `relation_id`=:relationId)";
         Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
         query.setInteger("relationId", relationId);
         return query.list();
    }
}
