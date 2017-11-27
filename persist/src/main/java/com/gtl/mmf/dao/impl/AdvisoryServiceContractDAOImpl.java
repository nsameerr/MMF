/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IAdvisoryServiceContractDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerQuestionResponseSetTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class AdvisoryServiceContractDAOImpl implements IAdvisoryServiceContractDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static final int BATCH_SIZE = 20;

    public CustomerAdvisorMappingTb getContractDetails(Integer relationId) {
        String hql = "From CustomerAdvisorMappingTb WHERE relationId =:RelationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationId", relationId);
        return (CustomerAdvisorMappingTb) query.uniqueResult();
    }

    public void submitInvestorQuestionnair(List<CustomerQuestionResponseSetTb> customerQuestionResponseSetTbList) {
        int count_mode = 0;
        for (CustomerQuestionResponseSetTb customerQuestionResponseSetTb : customerQuestionResponseSetTbList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(customerQuestionResponseSetTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        sessionFactory.getCurrentSession().flush();
    }

    public int getTotalScoreOfQuestinnaire(int relationId) {
        String sql = "SELECT SUM(option_value) From customer_question_response_set_tb WHERE  relation_id=:RelationId";
        SQLQuery sQLQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sQLQuery.setInteger("RelationId", relationId);
        return Integer.parseInt(sQLQuery.list().get(0).toString());
    }

    public void saveRiskProfile(CustomerRiskProfileTb customerRiskProfileTb, CustomerAdvisorMappingTb customerAdvisorMappingTb) {
        sessionFactory.getCurrentSession().save(customerRiskProfileTb);  
        
        //Updated status_date is added while submitting questinnaire
               String sql = "UPDATE customer_advisor_mapping_tb "
                + "SET relation_status =" + customerAdvisorMappingTb.getRelationStatus() + ",status_date =:StatusDate,"
                       + "investor_viewed = :investorViwed,advisor_viewed = :advisorViwed"
                + " WHERE  relation_id=" + customerAdvisorMappingTb.getRelationId();
        SQLQuery sQLQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sQLQuery.setTimestamp("StatusDate", customerAdvisorMappingTb.getStatusDate());
        sQLQuery.setBoolean("advisorViwed", false);
        sQLQuery.setBoolean("investorViwed", true);
        sQLQuery.executeUpdate();
    }

    public MasterPortfolioTypeTb getMasterPortfolioType(Integer id) {
        String hql = "From MasterPortfolioTypeTb WHERE id =:portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", id);
        return (MasterPortfolioTypeTb) query.uniqueResult();
    }

    public MasterCustomerTb getInvestorFundDetails(Integer cusId) {
        String hql = "From MasterCustomerTb WHERE customerId =:cusId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("cusId", cusId);
        return (MasterCustomerTb) query.uniqueResult();
    }
}
