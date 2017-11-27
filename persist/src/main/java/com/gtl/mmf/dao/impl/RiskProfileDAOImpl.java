package com.gtl.mmf.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gtl.mmf.dao.IRiskProfileDAO;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.QuestionoptionsmasterTb;
import com.gtl.mmf.entity.RiskProfileGroupDescriptionMasterTb;
import com.gtl.mmf.entity.RiskProfileRecommendedPortfolioMasterTb;
import com.gtl.mmf.entity.CustomerRiskProfileResultTb;
import com.gtl.mmf.entity.CustomerRiskProfileResponseSetTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MasterPortfolioSizeTb;
import com.gtl.mmf.entity.RiskProfileRecommendedPortfolioMasterTb;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.gtl.mmf.entity.CustomerRiskScoreTb;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RiskProfileDAOImpl implements IRiskProfileDAO{
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.rpdaoimpl");
	
	@Autowired
	private SessionFactory sessionFactory;
	HibernateTemplate template;
	/**   
       Returns the weights of answers from the database corresponding to the passed 'option_id' 
    **/

	
	public String fetchRegistrationIdFromTb(String emailId)
	{
		String hql = "SELECT registrationId FROM MasterCustomerTb WHERE email =:emailId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("emailId", emailId);
		String registrationId = (String)query.list().get(0);
		return registrationId;
	}
	
	
	public int getOptionValueFromTb(int optionId)
	{
		String hql = "SELECT QOptionvalue FROM QuestionoptionsmasterTb WHERE id =:optionId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("optionId", optionId);
		int optionValue = (Integer)query.list().get(0);
		return optionValue;
		
	}
	
	
	/**
       Saves the response from the risk profile in the database
	   Items to be saved:
       1. response_set_id    2. question_id     3. answer_id     4. date_time    	   
    **/	
	
	public void saveRiskProfileResponse(int response_set_id, int question_id, int option_id)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		String hql = "INSERT INTO customer_RiskProfileResponse_tb items(response_set_id, question_id, answer_id, date_time) VALUES(:response_set_id, :question_id, :option_id, , :dateFormat.format(date))" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();		
		
	}
	
	
	/**   
       Saves the risk profile score in the database
	   Items to be saved:
       1. response_id   2. customer id    3. date_time     5. risk_score 
    **/
	
	public void saveRiskScore(String customerRegistrationId, int riskScore, int rpGroupId, int recommendedPortfolioId)
	{
		try
		{
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		CustomerRiskProfileResultTb custRpResult = new CustomerRiskProfileResultTb();
		//Session session = this.sessionFactory.openSession();
		//session.beginTransaction();
		custRpResult.setCustomerRegistrationId(customerRegistrationId);
		custRpResult.setDateTime(date);
		custRpResult.setRiskScore(riskScore);
		custRpResult.setRiskProfileGroupId(rpGroupId);
		custRpResult.setRiskProfileRecommemdedPortfolioId(recommendedPortfolioId);
		//session.save(custRpResult);
		//session.getTransaction().commit();
		sessionFactory.getCurrentSession().save(custRpResult);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveRiskScore", e);
		}
		
		
	}


	public CustomerRiskProfileResultTb getCustomerRpResultFromTb(String customerRegistrationId) {
		
		String hql = "FROM CustomerRiskProfileResultTb Q WHERE Q.customerRegistrationId =:customerRegistrationId ORDER BY Q.dateTime DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegistrationId", customerRegistrationId);
		CustomerRiskProfileResultTb Q = (CustomerRiskProfileResultTb)query.list().get(0);
		return Q;
		
	}
	
	public List<RiskProfileGroupDescriptionMasterTb> getRiskProfileGroupDetailsFromTb()
	{
		String hql = "FROM RiskProfileGroupDescriptionMasterTb";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	public List<RiskProfileRecommendedPortfolioMasterTb> getRecommendedPortfolioFromTb(int recommendedPortfolioId)
	{
		String hql = "FROM RiskProfileRecommendedPortfolioMasterTb WHERE riskProfileRecommendedPortfolioId =:recommendedPortfolioId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("recommendedPortfolioId", recommendedPortfolioId);
		return query.list();
	}
	
	public List<MasterAssetTb> getMasterAssetTb()
	{
		String hql = "FROM MasterAssetTb" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	public int getPortfolioSizeId(int funds)
	{
		String hql = "SELECT portfolioSizeId FROM MasterPortfolioSizeTb WHERE minAum <=:funds AND maxAum >:funds " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("funds", funds);
		int portfolioSizeId = (Integer)query.list().get(0);
		return portfolioSizeId;
	}
	
	public double getTotalAllocatedFunds(String customerRegistrationId)
	{
		String hql = "SELECT totalAllocatedFunds FROM MasterCustomerTb WHERE registrationId =:customerRegistrationId" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegistrationId", customerRegistrationId);
		double allocatedFunds = (Double)query.list().get(0);
		return allocatedFunds;
	}


	public int getOptionValueFromTb(int qoptionId, int aoptionId) {
		String hql = "SELECT weight FROM RiskprofileOptionWeightsTb WHERE QId =:qoptionId AND AId =:aoptionId" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("qoptionId", qoptionId);
		query.setInteger("aoptionId", aoptionId);
		int optionValue = (Integer)query.list().get(0);
		return optionValue;
	}


	
	/**
       Saves the response from the risk profile in the database
	   Items to be saved:
       1. response_set_id    2. question_id     3. answer_id     4. date_time    	   
    **/	
	
	/*public void saveRiskProfileResponse(Integer customerId, Integer responseSetId, Integer questionId, Integer answerId)
	{

		Date date = new Date();
		CustomerRiskProfileResponseSetTb custRpResponse = new CustomerRiskProfileResponseSetTb();
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		
		custRpResponse.setResponseSetId(responseSetId);
		custRpResponse.setQuestionId(questionId);
		custRpResponse.setAnswerId(answerId);
		custRpResponse.setCustomerId(customerId);
		custRpResponse.setDateTime(date);
		
		session.save(custRpResponse);
		session.getTransaction().commit();  
		
	}
	
	
	public Integer getLastResponseSetId()
	{
		Integer responseSetId = 0;
		String hql = "SELECT responseSetId FROM CustomerRiskProfileResponseSetTb ORDER BY responseSetId DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List list = query.list();
		String responseSetIdtemp = (String) list.get(0);
		
		if(responseSetIdtemp == null)
			responseSetId = 1;
		
		else if(responseSetIdtemp != null)
			responseSetId = (Integer)list.get(0) + 1;
		
		return responseSetId;	
	}*/
	
	
	/**   
       Saves the risk profile score in the database
	   Items to be saved:
       1. response_id   2. customer id    3. date_time     5. risk_score 
    **/
	
	/*public Integer saveRiskProfileResult(Integer responsetSetId, Integer customerId, Integer finalRiskScore, Integer riskProfileGroupId, Integer riskProfileRecommendedPortfolioId)
	{
	
		Integer rpResultId = 0;
		String hql = "SELECT MAX(riskProfileResultId) FROM CustomerRiskProfileResultTb";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List list = query.list();
		String rpResultIdtemp = (String) list.get(0);
		
		if(rpResultIdtemp == null)
			rpResultId = 1;
		
		else if(rpResultIdtemp != null)
			rpResultId = (Integer)list.get(0) + 1;

		
		Date date = new Date();
		CustomerRiskProfileResultTb custRpResult = new CustomerRiskProfileResultTb();
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		
		custRpResult.setResponseSetId(responsetSetId);
		custRpResult.setCustomerId(customerId);
		custRpResult.setDateTime(date);
		custRpResult.setRiskScore(finalRiskScore);
		custRpResult.setRiskProfileRecommemdedPortfolioId(riskProfileRecommendedPortfolioId);
		custRpResult.setRiskProfileGroupId(riskProfileGroupId);
		
		session.save(custRpResult);
		session.getTransaction().commit();		
		
		return rpResultId;
	}


	public List<CustomerRiskProfileResultTb> getCustomerRiskProfileResult(Integer customerId, Integer riskProfileResultId) 
	{
		// TODO Auto-generated method stub
		String hql = "FROM CustomerRiskProfileResultTb WHERE customerId =:customerId AND riskProfileResultId =:riskProfileResultId" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("customerId", customerId);
		query.setParameter("riskProfileResultId", riskProfileResultId);
		List<CustomerRiskProfileResultTb> custRpResult = query.list();
		return custRpResult;
		
	}
	

*/
	
}


