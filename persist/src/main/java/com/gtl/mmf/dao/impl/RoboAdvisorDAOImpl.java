package com.gtl.mmf.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gtl.mmf.dao.IRoboAdvisorDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
/**
 * 
 * @author bhagyashree.chavan
 *
 */
@Repository
public class RoboAdvisorDAOImpl implements IRoboAdvisorDAO{
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.RoboAdvisorDAOImpl");
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public List<Object> getAdvisorDetails(String advisorEmail) {
		LOGGER.log(Level.INFO, "DAO - Fetching Robo Advisor from master_advisor_tb"); 
		String hql = "FROM MasterAdvisorTb WHERE email = :advisorEmail";
	    Query query = sessionFactory.getCurrentSession().createQuery(hql);
	    query.setString("advisorEmail", advisorEmail);
	    return query.list();		
	}
	
	public int getRelationId(int investorId, int advisorId) {
		int relationId=0;
		LOGGER.log(Level.INFO, "DAO - Fetching RelationId from customer_advisor_mapping_tb"); 
		String hql="FROM CustomerAdvisorMappingTb where masterCustomerTb.customerId = :investorId AND masterAdvisorTb.advisorId = :advisorId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("investorId", investorId);
		query.setInteger("advisorId", advisorId);
		List<Object> list=query.list();
		if(!list.isEmpty()){
			CustomerAdvisorMappingTb customerAdvisorMappingEntity=(CustomerAdvisorMappingTb) list.get(0);
			relationId = customerAdvisorMappingEntity.getRelationId();
		}
		return relationId;
	}
	
	public int getRelationId(int investorId) {
		int relationId=0;
		LOGGER.log(Level.INFO, "DAO - Fetching RelationId from customer_advisor_mapping_tb"); 
		String hql="FROM CustomerAdvisorMappingTb where masterCustomerTb.customerId = :investorId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("investorId", investorId);
		
		List<Object> list=query.list();
		if(!list.isEmpty())
		{
			CustomerAdvisorMappingTb customerAdvisorMappingEntity=(CustomerAdvisorMappingTb) list.get(0);
			relationId = customerAdvisorMappingEntity.getRelationId();
		}
		return relationId;
	}
	
	public int getRelationStatus(int investorId, int advisorId) {
		int relationStatus=0;
		LOGGER.log(Level.INFO, "DAO - Fetching RelationStatus from customer_advisor_mapping_tb"); 
		String hql="FROM CustomerAdvisorMappingTb where masterCustomerTb.customerId = :investorId AND masterAdvisorTb.advisorId = :advisorId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("investorId", investorId);
		query.setInteger("advisorId", advisorId);
		List<Object> list=query.list();
		if(!list.isEmpty()){
			CustomerAdvisorMappingTb customerAdvisorMappingEntity=(CustomerAdvisorMappingTb) list.get(0);
			relationStatus = customerAdvisorMappingEntity.getRelationStatus();
		}
		return relationStatus;
	}

	public List<Object> getAdvisorLinkedInDetails(String advisorEmail) {
		LOGGER.log(Level.INFO, "DAO - Fetching Robo Advisor from master_applicant_tb"); 
		String hql = "FROM MasterApplicantTb WHERE email = :advisorEmail";
	    Query query = sessionFactory.getCurrentSession().createQuery(hql);
	    query.setString("advisorEmail", advisorEmail);
	    return query.list();	
	}
	
}