/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ROBO_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.ADVISORY_SERVICE_CONTRACT_PAGE;
import static com.gtl.mmf.service.util.IConstants.SUCCESS;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_DASHBOARD;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gtl.mmf.bao.IAdvisorMappingBAO;
import com.gtl.mmf.bao.IAdvisoryServiceContractBAO;
import com.gtl.mmf.bao.IInvestmentAdvProfileBAO;
import com.gtl.mmf.bao.IInvestorMappingBAO;
import com.gtl.mmf.bao.IRoboAdvisorBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import com.gtl.mmf.service.vo.InvestmentRelationStatusVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;

/**
 * 
 * @author bhagyashree.chavan
 *
 */
@Component("roboAdvisorController")
@Scope("session")
public class RoboAdvisorController implements Serializable{

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.RoboAdvisorController");
    private HttpServletRequest request;
    private String roboAdvisorMail="roboadvisor@managemyfortune.com";
    
    @Autowired
	private IRoboAdvisorBAO roboAdvisorBAO;
	@Autowired
    private IInvestmentAdvProfileBAO investmentAdvProfileBAO;
	@Autowired
    private IInvestorMappingBAO investorMappingBAO;
    @Autowired
    private IAdvisoryServiceContractBAO advisoryServiceContractBAO;
    @Autowired
    private IAdvisorMappingBAO advisorMappingBAO;
   
	public String selectRoboAdvisor(){
		HttpSession session=request.getSession(false);
    	if(session!=null)
		{
			try {
    			LOGGER.log(Level.INFO, "Inside RoboAdvisorController : selectRoboAdvisor()");
				Map<String, Object> storedValues=(Map<String, Object>) session.getAttribute(STORED_VALUES);
				UserSessionBean userSessionBean=(UserSessionBean) session.getAttribute(USER_SESSION);
				userSessionBean.setFromURL(ROBO_ADVISOR);
				int customerId = userSessionBean.getUserId();
				String investorName=userSessionBean.getFirstName() + " " + userSessionBean.getLastName();
				
				AdvisorDetailsVO advisorDetails;
				advisorDetails = roboAdvisorBAO.getAdvisorDetails(roboAdvisorMail);
				advisorDetails = roboAdvisorBAO.getAdvisorLinkedInDetails(advisorDetails);
				InvestorDetailsVO investorDetails= new InvestorDetailsVO();
				investorDetails.setEmail(userSessionBean.getEmail());
				investorDetails.setFirstName(userSessionBean.getFirstName()+SPACE+
						userSessionBean.getMiddleName()+SPACE+userSessionBean.getLastName());
				investorDetails.setCustomerId(customerId);
				
				//Check whether Investor Already Related to any advisor
				int relationId = roboAdvisorBAO.getRelationId(customerId);
				if(relationId == 0){
					InvestmentRelationStatusVO advisorRelationStatus;
					advisorRelationStatus = investmentAdvProfileBAO.getAdvisorRelationStatus(
							advisorDetails.getRelationId() == null ? ZERO : advisorDetails.getRelationId(), customerId);
					advisorRelationStatus.setAllocatedFund(advisorRelationStatus.getTotalAvailableFund());
				
					//Invite Sent to RoboAdvisor
					investorMappingBAO.inviteAdvisor(advisorDetails, customerId,
							advisorRelationStatus.getAllocatedFund(), investorName);
					
					//Update RelationID
					relationId = roboAdvisorBAO.getRelationId(customerId, advisorDetails.getAdvisorId());
					advisorDetails.setRelationId(relationId);
					userSessionBean.setRelationId(relationId);
					investorDetails.setRelationId(relationId);
					//Accept Investor
					advisorMappingBAO.acceptInvestor(investorDetails, advisorDetails.getAdvisorId(), 
							roboAdvisorBAO.getAdvisorFullName(advisorDetails.getEmail()));
					
					//Update RelationStatus
					int relationStatus = roboAdvisorBAO.getRelationStatus(customerId, advisorDetails.getAdvisorId());
					advisorDetails.setStatus(EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue());
					userSessionBean.setRelationStatus(EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue());
					investorDetails.setStatus(relationStatus);
					LOGGER.log(Level.INFO, "RelationStatus : IniviteAccepted : "+relationStatus+
							" advisorDetails : "+EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue()+
							" userSessionBean : "+EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue());
	
					//Submit Contract
					ContractDetailsVO contractDetailsVO=advisoryServiceContractBAO.getContractDetails(
							investorDetails.getRelationId(), investorDetails.getStatus());
					contractDetailsVO.setStartDate(new Date());
					contractDetailsVO.setVariableManagementFee(new BigDecimal(0.5));
					int durationCount=(contractDetailsVO.getDurationCount() == null)?1 : contractDetailsVO.getDurationCount();
					contractDetailsVO.setDurationCount(durationCount);
					short reviewFrequency=(short) ((contractDetailsVO.getReviewFrequency() == null) ? 1 : contractDetailsVO.getReviewFrequency());
					contractDetailsVO.setReviewFrequency(reviewFrequency);
					
					String message=advisorMappingBAO.submitContract(contractDetailsVO, reviewFrequency, investorDetails, roboAdvisorBAO.getAdvisorFullName(advisorDetails.getEmail()));
					advisorMappingBAO.updateNotificationStatus(relationId);
					LOGGER.log(Level.INFO, "submitContract : "+message);
					
					if(message.equals(SUCCESS))
					{
						relationStatus = roboAdvisorBAO.getRelationStatus(customerId, advisorDetails.getAdvisorId());
						advisorDetails.setStatus(EnumAdvisorMappingStatus.CONTRACT_SENT.getValue());
						userSessionBean.setRelationStatus(EnumCustomerMappingStatus.CONTRACT_RECIEVED.getValue());				
						LOGGER.log(Level.INFO, "RelationStatus : ContractSent :"+relationStatus);
						
						storedValues.put(SELECTED_ADVISOR, advisorDetails);
						session.setAttribute(STORED_VALUES, storedValues);
						session.setAttribute(USER_SESSION, userSessionBean);
						
						return ADVISORY_SERVICE_CONTRACT_PAGE;
					}							
				}else{
					return INVESTOR_DASHBOARD;
				}
					
				
    		}catch (ClassNotFoundException e) {
				LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(e));
			}catch (Exception e) {
				LOGGER.log(Level.SEVERE, "ERROR :" , e);
			}
		}
    	return EMPTY_STRING;
    }
	
	public HttpServletRequest getRequest() {
			return request;
	}
	public void setRequest(HttpServletRequest request) {
			this.request = request;
	}
}
