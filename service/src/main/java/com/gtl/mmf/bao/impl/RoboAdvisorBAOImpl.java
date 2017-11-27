package com.gtl.mmf.bao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IRoboAdvisorBAO;
import com.gtl.mmf.dao.IRoboAdvisorDAO;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;


/**
 * 
 * @author bhagyashree.chavan
 *
 */
@Service
public class RoboAdvisorBAOImpl implements IRoboAdvisorBAO{
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.RoboAdvisorBAOImpl");
	@Autowired
	private IRoboAdvisorDAO roboAdvisorDAO;
	
	/**
	 * Fetching RoboAdvisor details from DB
	 * @param email - RoboAdvisor email ID
	 * @return AdvisorDetails of RoboAdvisor  
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public AdvisorDetailsVO getAdvisorDetails(String email) {
		LOGGER.log(Level.INFO, "BAO - Fetching Robo Advisor based on email");
		AdvisorDetailsVO roboAdvisorProfile=new AdvisorDetailsVO();
		List<Object> advisorDetailsList= roboAdvisorDAO.getAdvisorDetails(email);
		MasterAdvisorTb masterAdvisorTb= (MasterAdvisorTb)advisorDetailsList.get(0);
		
		roboAdvisorProfile.setAdvisorId(masterAdvisorTb.getAdvisorId());
		roboAdvisorProfile.setEmail(masterAdvisorTb.getEmail());
		roboAdvisorProfile.setRegId(masterAdvisorTb.getRegistrationId());
		roboAdvisorProfile.setFirstName(masterAdvisorTb.getFirstName());
		
		roboAdvisorProfile.setJobTitle(masterAdvisorTb.getJobTitle());
		roboAdvisorProfile.setWorkOrganization(masterAdvisorTb.getWorkOrganization());
		
		return roboAdvisorProfile;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public int getRelationId(int investorId, int advisorId) {
		LOGGER.log(Level.INFO, "BAO - Fetching RelationId based on customerId and advisorId");
		return roboAdvisorDAO.getRelationId(investorId, advisorId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public int getRelationId(int investorId) {
		LOGGER.log(Level.INFO, "BAO - Fetching RelationId based on customerId and advisorId");
		return roboAdvisorDAO.getRelationId(investorId);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public int getRelationStatus(int investorId, int advisorId) {
		LOGGER.log(Level.INFO, "BAO - Fetching RelationStatus based on customerId and advisorId");
		return roboAdvisorDAO.getRelationStatus(investorId, advisorId);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getAdvisorFullName(String email) {
		LOGGER.log(Level.INFO, "BAO - Fetching Robo Advisor FullName");
		String advisorName;
		List<Object> advisorDetailsList= roboAdvisorDAO.getAdvisorDetails(email);
		MasterAdvisorTb masterAdvisorTb= (MasterAdvisorTb)advisorDetailsList.get(0);		
		advisorName=masterAdvisorTb.getFirstName()+" "+masterAdvisorTb.getMiddleName()+" "+masterAdvisorTb.getLastName();
		if(advisorName!=null)
			advisorName.trim();
		return advisorName;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public AdvisorDetailsVO getAdvisorLinkedInDetails(AdvisorDetailsVO roboAdvisorProfile){
		LOGGER.log(Level.INFO, "BAO - Fetching Robo Advisor LinkedIn Details");
		if(roboAdvisorProfile!=null && roboAdvisorProfile.getEmail()!=null)
		{ 
			List<Object> advisorDetailsList= roboAdvisorDAO.getAdvisorLinkedInDetails(roboAdvisorProfile.getEmail());
			MasterApplicantTb masterApplicantTb= (MasterApplicantTb) advisorDetailsList.get(0);
			
			roboAdvisorProfile.setLinkedInConnected(masterApplicantTb.getLinkedInConnected());
			roboAdvisorProfile.setLinkedInProfile(masterApplicantTb.getLinkedinProfileUrl());
			roboAdvisorProfile.setLinkedInMemberid(masterApplicantTb.getLinkedinMemberId());
			
		}
		return roboAdvisorProfile;
	}
}
