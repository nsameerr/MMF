package com.gtl.mmf.riskprofile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
//import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gtl.mmf.bao.IAdvisoryServiceContractBAO;
import com.gtl.mmf.bao.IRiskProfileBAO;
import com.gtl.mmf.controller.UserSessionBean;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import com.gtl.mmf.service.vo.riskprofile.RPResponseSet;
import com.gtl.mmf.service.vo.riskprofile.RiskProfileResponse;
import com.gtl.mmf.service.vo.riskprofile.RiskProfileResult;

import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;

@RestController
public class RiskProfileController {
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.rpController");

	//private static final String STORED_VALUES = "storedValues";
	
	private final AtomicLong responseId = new AtomicLong();
	//private final AtomicLong customer_id = new AtomicLong();
	private String customerRegistrationId;
    
	@Autowired
	private IRiskProfileBAO riskprofilebao;
    @Autowired
    private IAdvisoryServiceContractBAO advisoryServiceContractBAO;
    
    private AdvisorDetailsVO advisorDetailsVO;
    private Double allocatedFund;
    private Map<Integer, QuestionnaireVO> questionnnaireMap;
	@RequestMapping(value="/RiskProfile", method=RequestMethod.POST)
  	public void getResponses(@RequestBody List<RiskProfileResponse> response, HttpSession session) {
		// fetch the current user session
		UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute(USER_SESSION);
		if (userSessionBean != null) {
           // this.customerId = userSessionBean.getUserId();
			LOGGER.log(Level.INFO," usersession bean code - emailId "+userSessionBean.getEmail());
			customerRegistrationId = userSessionBean.getRegId();
        } else {
        	LOGGER.log(Level.INFO,"usersession bean is null");
        }
		// fetch the stored values from session
		Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
		
		//customerRegistrationId = riskprofilebao.fetchCustomerRegistrationIdUsingEmail(email); 
		
		//customerRegistrationId = "11";
		//setting values to RPResponseSet
		RPResponseSet responseSet = new RPResponseSet();
		//responseSet.setResponseId((int)responseId.incrementAndGet());
		responseSet.setCustomerRegistrationId(customerRegistrationId);
		responseSet.setRPResponse(response);
		
		/*//Printing logs
		for(int i=0; i<responseSet.getRPResponse().size(); i++)
		{
			int qid = responseSet.getRPResponse().get(i).getQuestionId();
			int aid = responseSet.getRPResponse().get(i).getAnswerId();
			
			LOGGER.log(Level.INFO,"Rp Response ->  Qid " + qid + "  AnsId  " + aid);
			
		}*/
		// save the risk score in new tables
		int finalRiskScore = riskprofilebao.computeAndSaveRiskScore(responseSet);
		
		//get backward compatible code
		questionnnaireMap = riskprofilebao.getRiskProfileBackwordFormat(responseSet);
		
		// code for backward compatiblity of existing questionnarie
		// get allocated fund of respective client
		allocatedFund = advisoryServiceContractBAO.getInvestorFundDetail(userSessionBean.getUserId());  
		//Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        advisorDetailsVO = ((AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR));
		// save the risk score in old tables
        if(advisorDetailsVO!=null)
        	advisoryServiceContractBAO.submitQuestionnaireNew(questionnnaireMap,finalRiskScore, advisorDetailsVO, LookupDataLoader.getPortfolioTypes(), userSessionBean.getFirstName() + SPACE + userSessionBean.getLastName(), allocatedFund);
        else
        	LOGGER.log(Level.INFO,"advisorDetailsVo is null");	
	}	
	
	@RequestMapping(value="/RiskProfileOutput", method=RequestMethod.GET)
  	public RiskProfileResult getRiskProfileOutput(HttpSession session) {
		UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute(USER_SESSION);
		if (userSessionBean != null) {
           // this.customerId = userSessionBean.getUserId();
			LOGGER.log(Level.INFO," usersession bean code - emailId "+userSessionBean.getEmail());
			customerRegistrationId = userSessionBean.getRegId();
        } else {
        	LOGGER.log(Level.INFO,"usersession bean is null");
        }
		
		//Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
		//String email = (String) storedValues.get("emailId");
	  
		//customerRegistrationId = riskprofilebao.fetchCustomerRegistrationIdUsingEmail(email); 
		
		//customerRegistrationId = "11";
		LOGGER.log(Level.INFO,"get Suman code - Rp Controller Data Send " + customerRegistrationId);
		RiskProfileResult rpOutput = riskprofilebao.getRiskProfileOutput(customerRegistrationId);
		
		return rpOutput;
	
	}
	
	@RequestMapping(value="/RiskProfileOutput", method=RequestMethod.POST)
  	public void getRiskProfileOutputTemp() {
		customerRegistrationId = "11";
		LOGGER.log(Level.INFO,"Suman code - Rp Controller Data Send " + customerRegistrationId);
		RiskProfileResult rpOutput = riskprofilebao.getRiskProfileOutput(customerRegistrationId);
		
		//return rpOutput;
	
	}
	
	@RequestMapping(value="/RiskProfiles", method=RequestMethod.POST)
  	public void sumeetTestingController() {
		LOGGER.log(Level.INFO,"suman controller testing by dev");
	}
}
