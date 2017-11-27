/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IAdvisorMappingDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class AdvisorMappingDAOImpl implements IAdvisorMappingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Integer inviteNewInvestor(CustomerAdvisorMappingTb customerAdvisorMapping) {
        return (Integer) sessionFactory.getCurrentSession().save(customerAdvisorMapping);
    }

    public int inviteOldInvestor(CustomerAdvisorMappingTb customerAdvisorMapping) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,"
                + " statusDate = :StatusDate, advisorRequest =:AdvisorRequest,"
                + " investorViewed = :investorViwed WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId());
        query.setBoolean("AdvisorRequest", customerAdvisorMapping.getAdvisorRequest());
        query.setBoolean("investorViwed", false);
        return query.executeUpdate();
    }

    
    public int withdrawInvestor(CustomerAdvisorMappingTb customerAdvisorMapping) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus, "
                + " allocatedFunds =  :AllocatedFund,"
                + "statusDate = :StatusDate, investorViewed = :investerViewed WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId());
        query.setBoolean("investerViewed",false);
        query.setParameter("AllocatedFund", null);
        return query.executeUpdate();
    }

    public int acceptInvestor(Integer relationId, short relationStatus) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus, "
                + "statusDate = :StatusDate, investorViewed = :investerViewed WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("RelationStatus", relationStatus);
        query.setParameter("RelationId", relationId);
        query.setParameter("StatusDate", new Date());
        query.setBoolean("investerViewed",false);
        return query.executeUpdate();
    }

    public int declineInvestorInvite(CustomerAdvisorMappingTb customerAdvisorMapping) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,"
                + " allocatedFunds =  :AllocatedFund,"
                + " statusDate = :StatusDate,investorViewed = :investorViwed "
                + " WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId()); 
        query.setParameter("AllocatedFund", null);
        query.setBoolean("investorViwed", false);
        
        return query.executeUpdate();
    }

    public int submitContract(CustomerAdvisorMappingTb customerAdvisorMappingTb, Integer reviewFrequency) {
        StringBuilder hql = new StringBuilder("UPDATE CustomerAdvisorMappingTb SET statusDate =:StatusDate ,contractStart =:ContractStart"
                + ",contractEnd =:ContractEnd, mgmtFeeTypeVariable =:MgmtFeeTypeVariable,mgmtFeeFreq =:MgmtFeeFreq"
                + ", perfFeeThreshold =:PerfFeeThreshold, perfFeePercent =:PerfFeePercent"
                + ", perfFeeFreq =:PerfFeeFreq, durationCount =:DurationCount"
                + ", durationFrequencyMonth =:DurationFrequencyMonth, relationStatus = :RelationStatus"
                + ", customerReview = :CustomerReview,investorViewed = :investerViewed "
                + ", reviewFreq = :ReviewFreq , rateAdvisorFlag = :RateAdvisorFlag");
        Query query;
        if (customerAdvisorMappingTb.getMgmtFeeTypeVariable()) {
            hql.append(", managementFeeVariable =:MgmtFeeVariable WHERE relationId = :RelationId");
            query = sessionFactory.getCurrentSession().createQuery(hql.toString());
            query.setBigDecimal("MgmtFeeVariable", customerAdvisorMappingTb.getManagementFeeVariable());
        } else {
            hql.append(", managementFeeFixed =:MgmtFeeFixed WHERE relationId = :RelationId");
            query = sessionFactory.getCurrentSession().createQuery(hql.toString());
            query.setInteger("MgmtFeeFixed", customerAdvisorMappingTb.getManagementFeeFixed());
        }
        query.setTimestamp("StatusDate", customerAdvisorMappingTb.getStatusDate());
        query.setTimestamp("ContractStart", customerAdvisorMappingTb.getContractStart());
        query.setTimestamp("ContractEnd", customerAdvisorMappingTb.getContractEnd());
        query.setBoolean("MgmtFeeTypeVariable", customerAdvisorMappingTb.getMgmtFeeTypeVariable());
        query.setInteger("RelationStatus", customerAdvisorMappingTb.getRelationStatus());
        query.setInteger("MgmtFeeFreq", customerAdvisorMappingTb.getMgmtFeeFreq());
        query.setInteger("DurationCount", customerAdvisorMappingTb.getDurationCount());
        query.setInteger("DurationFrequencyMonth", customerAdvisorMappingTb.getDurationFrequencyMonth());
        query.setInteger("PerfFeeFreq", customerAdvisorMappingTb.getPerfFeeFreq());
        query.setBigDecimal("PerfFeePercent", customerAdvisorMappingTb.getPerfFeePercent());
        query.setBigDecimal("PerfFeeThreshold", customerAdvisorMappingTb.getPerfFeeThreshold());
        query.setInteger("RelationId", customerAdvisorMappingTb.getRelationId());
        query.setString("CustomerReview", customerAdvisorMappingTb.getCustomerReview());
        query.setInteger("ReviewFreq", reviewFrequency);
        query.setBoolean("investerViewed",customerAdvisorMappingTb.getInvestorViewed());
        query.setBoolean("RateAdvisorFlag",false);
        return query.executeUpdate();
    }

    public Integer getInvestorAvailableStatus(Integer customerId, List<Integer> unavailableStatus) {
        String sql = "SELECT a.relation_id FROM customer_advisor_mapping_tb a WHERE a.relation_status NOT IN (:status)"
                + " AND customer_id=:customerId";
        Query sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameterList("status", unavailableStatus)
                .setInteger("customerId", customerId);
        return (Integer) sqlQuery.uniqueResult();
    }

    public int sentQuestionnaire(CustomerAdvisorMappingTb customerAdvisorMapping) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :RelationStatus,"
                + " statusDate = :StatusDate,investorViewed = :investorViewed WHERE relationId = :RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMapping.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMapping.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMapping.getRelationId());
        query.setBoolean("investorViewed", customerAdvisorMapping.getInvestorViewed());
        return query.executeUpdate();
    }
    
    public void updateNotificationStatus(Integer relationId) {
        String hql = "UPDATE CustomerAdvisorMappingTb SET advisorViewed = :advisorViewed WHERE relationId = :relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("advisorViewed", true);
        query.setInteger("relationId", relationId);
        query.executeUpdate();

    }

    public void updateNotificationStatusForRebalance(Integer portfolioId) {
        String hql = "UPDATE PortfolioTb SET rebalanceViewed = :rebalanceViewed WHERE portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("rebalanceViewed", true);
        query.setInteger("portfolioId", portfolioId);
        query.executeUpdate();
    }
}
