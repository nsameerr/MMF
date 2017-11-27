package com.gtl.mmf.financialplanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gtl.mmf.service.vo.financialplanner.FinancialPlannerOutput;
import com.gtl.mmf.service.vo.financialplanner.FPResponseSet;
import com.gtl.mmf.service.vo.financialplanner.LifeGoalsInput;
import com.gtl.mmf.controller.UserSessionBean;
import com.gtl.mmf.dao.IFinancialPlannerDAO;
import com.gtl.mmf.service.vo.financialplanner.assumptions.FpMasterAssumption;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;
import com.gtl.mmf.bao.IFpMainCalculationBAO;
import com.gtl.mmf.bao.IFpSetAssumptionsBAO;
import com.gtl.mmf.bao.IFpSaveAndFetchDataForFpBAO;

import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;


@RestController
public class FinancialPlannerController {
	
	private String customerId;
	private FPResponseSet fpUserResponseSet;
	private FinancialPlannerOutput fpOutput;
	private FpMasterAssumption fpmasterassumption = new FpMasterAssumption();
	
	@Autowired
	private IFpMainCalculationBAO fpmaincalculations;
	@Autowired
	private IFpSetAssumptionsBAO fpsetassumptionsbao;
	@Autowired
	private IFpSaveAndFetchDataForFpBAO fpsaveandfetchdataforfpbao;
	

	private final AtomicLong responseid = new AtomicLong();
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.financialplanner");
	
	public FpMasterAssumption fetchDefaultAssumptionsFromDatabase()
	{
		FpMasterAssumption assumptionObj = new FpMasterAssumption();
		
		// set Assumptions from Database
		if (fpsetassumptionsbao.assignDefaultFpMiscAssumptions()!= null){
			assumptionObj.setFpmiscassumptions(fpsetassumptionsbao.assignDefaultFpMiscAssumptions());
		}
		else
			LOGGER.log(Level.INFO,"Suman code - check assignDefaultFpMiscAssumptions giving null ");
		
		
		if(fpsetassumptionsbao.assignDefaultFpLoanAssumption() != null){
			assumptionObj.setFploanassumptions(fpsetassumptionsbao.assignDefaultFpLoanAssumption());
		}
		else
			LOGGER.log(Level.INFO,"Suman code - check assignDefaultFpLoanAssumption giving null ");
		
		
		if(fpsetassumptionsbao.assignDefaultFpSalaryExpenseIncrementAssumption() != null){
			assumptionObj.setFpsalaryexpenseincrement(fpsetassumptionsbao.assignDefaultFpSalaryExpenseIncrementAssumption());
		}
		else
			LOGGER.log(Level.INFO,"Suman code - check assignDefaultFpSalaryExpenseIncrementAssumption giving null ");
		
		return assumptionObj;
		
	}
	
	
	public FpMasterAssumption fetchSavedAssumptionsFromDatabase()
	{
		FpMasterAssumption assumptionObj = new FpMasterAssumption();
		
		// set Assumptions from Database
		if (fpsetassumptionsbao.assignSavedFpMiscAssumptions(customerId)!= null){
			assumptionObj.setFpmiscassumptions(fpsetassumptionsbao.assignSavedFpMiscAssumptions(customerId));
		}
		else
			LOGGER.log(Level.INFO,"Suman code - check assignDefaultFpMiscAssumptions giving null ");
				

				
		if(fpsetassumptionsbao.assignSavedFpLoanAssumption(customerId) != null){
			assumptionObj.setFploanassumptions(fpsetassumptionsbao.assignSavedFpLoanAssumption(customerId));
		}
		else
		LOGGER.log(Level.INFO,"Suman code - check assignDefaultFpLoanAssumption giving null ");
				
				
		if(fpsetassumptionsbao.assignSavedFpSalaryExpenseIncrementAssumption(customerId) != null){
			assumptionObj.setFpsalaryexpenseincrement(fpsetassumptionsbao.assignSavedFpSalaryExpenseIncrementAssumption(customerId));
		}
		else
			LOGGER.log(Level.INFO,"Suman code - check assignDefaultFpSalaryExpenseIncrementAssumption giving null ");
		
		return assumptionObj;
				
	}
	
	
	@RequestMapping(value="/FinancialPlanner", method=RequestMethod.POST)
	public String getResponses(@RequestBody FPResponseSet fpresponseSet, HttpSession session) {
		
		Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
		String email = (String) storedValues.get("emailId");
		
		customerId = fpsaveandfetchdataforfpbao.getRegIdFromTb(email);
		
		//customerId = "21";
		
		try
		{
		fpUserResponseSet = fpresponseSet; 
		
		fpsaveandfetchdataforfpbao.deleteFpResponseFromTempTb(customerId);
		
		fpsaveandfetchdataforfpbao.saveFpUserBasicInputDetailsToTempTb(fpUserResponseSet, customerId);
		fpsaveandfetchdataforfpbao.saveFpUserOutstandingLoansToTempTb(fpUserResponseSet.getOutstandingLoans(), customerId);
		fpsaveandfetchdataforfpbao.saveFpUserInsuranceDetailsToTempTb(fpUserResponseSet.getHealthInsuranceCover(), fpUserResponseSet.getLifeInsuranceCover(), customerId);
		fpsaveandfetchdataforfpbao.saveFpFinAssetDetailsToTempTb(fpUserResponseSet.getFinancialAssetList(),customerId);
		fpsaveandfetchdataforfpbao.saveFpLifeGoalsToTempTb(fpUserResponseSet.getLifeGoals(),customerId);	
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - error in getResponses in FpController",e);
		}
	
		// returning empty json
		return "{}";
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="riskScore", method=RequestMethod.POST)
	public FinancialPlannerOutput editRiskScore(@RequestParam int riskScore) {
		
		fpUserResponseSet.setRiskGroupId(riskScore);
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(fpmasterassumption);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput;
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="savingsRate", method=RequestMethod.POST)
	public FinancialPlannerOutput editSavingsRate(@RequestParam float savingsRate) {
		
		fpUserResponseSet.setSavingsRate(savingsRate);
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(fpmasterassumption);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput;
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="allocationMMF", method=RequestMethod.POST)
	public FinancialPlannerOutput editAllocationToMMF(@RequestParam float allocationMMF) {
		
		fpmasterassumption.getFpmiscassumptions().setAmountInvestedToMmf(allocationMMF);
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(fpmasterassumption);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput;
		
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="retirementAge", method=RequestMethod.POST)
	public FinancialPlannerOutput editRetirementAge(@RequestParam int retirementAge) {
		
		try
		{
		int lifeExpectancy = fpmasterassumption.getFpmiscassumptions().getLifeExpectancy();
		
		if(retirementAge >= lifeExpectancy)
		{
			fpmasterassumption.getFpmiscassumptions().setRetirementAge(lifeExpectancy);
		}
			
		else
		{
			fpmasterassumption.getFpmiscassumptions().setRetirementAge(retirementAge);
		}
		
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(fpmasterassumption);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput;
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - error in editRetirementAge",e);
		}
		
		return null;
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="lifeGoals", method=RequestMethod.POST)
	public FinancialPlannerOutput editLifeGoals(@RequestParam String lifeGoals) {
		
		//List<LifeGoalsInput> lifeGoalsListTempObj = new ArrayList<LifeGoalsInput>();
		//List<ObjectMapper> mapper = new ArrayList<ObjectMapper>();
		//List<LifeGoalsInput> lifeGoalsObj = ArrayList mapper.readValue(lifeGoals, LifeGoalsInput.class);
		
		//List<LifeGoalsInput> myObjects = mapper.readValue(lifeGoals, new TypeReference<List<LifeGoalsInput>>(){});
		
		List<LifeGoalsInput> lifeGoalsObj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			lifeGoalsObj = mapper.readValue(lifeGoals, mapper.getTypeFactory().constructCollectionType(List.class, LifeGoalsInput.class));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE,"Suman code - error in editLifeGoals",e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE,"Suman code - error in editLifeGoals",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE,"Suman code - error in editLifeGoals",e);
		}
		
		//printing the edited data
		for(int i=0; i<lifeGoalsObj.size(); i++)
		{
			LOGGER.log(Level.INFO,"Suman code - goalID  "+ lifeGoalsObj.get(i).getGoalDescriptionId()+" year  "+lifeGoalsObj.get(i).getYearofRealization());
			LOGGER.log(Level.INFO,"Suman code - frequencyId  "+ lifeGoalsObj.get(i).getFrequency()+" amount  "+lifeGoalsObj.get(i).getEstimatedAmount());
		}

		fpUserResponseSet.setLifeGoals(lifeGoalsObj);
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(fpmasterassumption);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput;
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="fpmasterassumption", method=RequestMethod.POST)
	public FinancialPlannerOutput editAssumptions(@RequestParam String fpmasterassumption) {
		
		FpMasterAssumption assumptionObj = null;
		
		try{
		ObjectMapper mapper = new ObjectMapper();
		assumptionObj = mapper.readValue(fpmasterassumption, FpMasterAssumption.class);
		
		this.fpmasterassumption = assumptionObj;
		
		int retirementAge = assumptionObj.getFpmiscassumptions().getRetirementAge();
		int lifeExpectancy = assumptionObj.getFpmiscassumptions().getLifeExpectancy();
		
		if(lifeExpectancy <= retirementAge)
		{
			assumptionObj.getFpmiscassumptions().setLifeExpectancy(retirementAge);
		}
		   
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(assumptionObj);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput;
		
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE,"Suman code - error in editAssumptions",e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE,"Suman code - error in editAssumptions",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE,"Suman code - error in editAssumptions",e);
		}
		
		return null;
	
	}
	
	@RequestMapping(value="/FinancialPlanner", method=RequestMethod.GET)
	public FPResponseSet onLoadUserProfile(HttpSession session) {
		
		try
		{
		Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
		String email = (String) storedValues.get("emailId");
		
		customerId = fpsaveandfetchdataforfpbao.getRegIdFromTb(email);
		
		//customerId = "21";
		
		boolean existTempTbResult = fpsaveandfetchdataforfpbao.checkUserExistsInFpTempTb(customerId);
		boolean existFinalTbResult = fpsaveandfetchdataforfpbao.checkUserExistsInFpFinalSaveTb(customerId);
		
		//entry does not exist in temp and final save table
		//happens when user is coming to Fp for first time
		if(existTempTbResult == true && existFinalTbResult == true)
		{
			LOGGER.log(Level.INFO,"Suman code - Code enters default if");
			FPResponseSet tempObj = new FPResponseSet();
			tempObj = fpsaveandfetchdataforfpbao.getFpDefaultResponse();
			return tempObj;
		}
		
		//entry exists in temp tb but not in final save tb
		//happens when user comes from edit your response button (after going to FpOutput for first time)
		// and user has not done a "Save and Submit" ever
		else if(existTempTbResult == false && existFinalTbResult == true)
		{
			FPResponseSet tempObj = new FPResponseSet();
			tempObj = fpsaveandfetchdataforfpbao.getFpResponseFromTempTb(customerId);
			//fpsaveandfetchdataforfpbao.deleteFpResponseFromTempTb(customerId);
			return tempObj;
		}
		
		//entry does not exist in temp tb but exists in final save tb
		//happens when user comes from edit your response button (revisiting of FpOutput)
		//happens after Rp and when user decides to update Fp from "Investor Profile"
		else if(existTempTbResult == true && existFinalTbResult == false)
		{
			FPResponseSet tempObj = new FPResponseSet();
			tempObj = fpsaveandfetchdataforfpbao.getFpResponseFromTb(customerId);
			return tempObj;
		}
		
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - error in onLoadUserProfile",e);
		}
		
		return null;
	}
	
	@RequestMapping(value="/FinancialPlannerOutput", params="saveFinancialPlanner", method=RequestMethod.POST)
	public void saveFinancialPlanner(@RequestParam int saveFinancialPlanner,HttpSession session) {
		
		Map<String, Object> storedValues = (Map<String, Object>)  session.getAttribute(STORED_VALUES);
		String email = (String) storedValues.get("emailId");
				
		customerId = fpsaveandfetchdataforfpbao.getRegIdFromTb(email);
				
		//customerId = "21";
		
		//Deletes any previous Fp Response Data of same user from the final save Tb
		fpsaveandfetchdataforfpbao.deleteFpResponseFromFinalSaveTb(customerId);
		
        // Saves Fp response data in the final save Tb
		fpsaveandfetchdataforfpbao.finalSaveFpUserBasicInputDetails(fpUserResponseSet, customerId);
		fpsaveandfetchdataforfpbao.finalSaveFpUserOutstandingLoans(fpUserResponseSet.getOutstandingLoans(), customerId);
		fpsaveandfetchdataforfpbao.finalSaveFpUserInsuranceDetails(fpUserResponseSet.getHealthInsuranceCover(), fpUserResponseSet.getLifeInsuranceCover(), customerId);
		fpsaveandfetchdataforfpbao.finalSaveFpFinAssetDetails(fpUserResponseSet.getFinancialAssetList(),customerId);
		fpsaveandfetchdataforfpbao.finalSaveFpLifeGoals(fpUserResponseSet.getLifeGoals(),customerId);	
		
		//Deletes any previous Fp Assumption Data of same user from the Tb
		fpsaveandfetchdataforfpbao.deleteFpSavedAssumptionsFromTb(customerId);
		
		// Saves assumption for the user
		fpmaincalculations.defaultAssumptionsEditCheck();
		
		//Deletes Fp Response Data from the temp Tb
		fpsaveandfetchdataforfpbao.deleteFpResponseFromTempTb(customerId);
		
	}
	
	
/*	@RequestMapping(value="/ReFinancialPlannerOutput", method=RequestMethod.POST)
	public void reCalculateFinancialPlannerAfterRp(HttpSession session) {
		
		//HttpSession session = request.getSession();
		Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
	    String email = (String) storedValues.get("emailId");
		
	    //Fetch id from temp_registration_tb based on emailId
	    String customerId = fpsaveandfetchdataforfpbao.getRegIdFromTb(email);
	    fpUserResponseSet = fpsaveandfetchdataforfpbao.getFpResponseFromTb(customerId);
	    fpmasterassumption = fpsaveandfetchdataforfpbao.getFpsavedAssumptionsFromTb(customerId);
	    
	    fpmaincalculations.setResponses(fpUserResponseSet);
	    fpmaincalculations.setAssumptions(fpmasterassumption);
	    fpmaincalculations.setcustomerId(customerId);
	    fpOutput = fpmaincalculations.getFinancialPlannerOutput();
	    
	}*/
	
	
	@RequestMapping(value="/FinancialPlannerOutput", method=RequestMethod.GET)
  	public FinancialPlannerOutput getFpOutput(HttpSession session) {
		
		Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
		String email = (String) storedValues.get("emailId");
		
		customerId = fpsaveandfetchdataforfpbao.getRegIdFromTb(email);
		
		//customerId = "21";
		
		fpUserResponseSet = fpsaveandfetchdataforfpbao.checkAndGetFpSavedResponseFromDatabase(customerId);
		//fpUserResponseSet = fpsaveandfetchdataforfpbao.getFpResponseFromTempTb(customerId);
		
		//LOGGER.log(Level.INFO,"Suman code - check fpresponse gender"+fpResponseSet.getGender());
		//LOGGER.log(Level.INFO,"Suman code - check fpresponse age"+fpResponseSet.getUserAge());
		
		//true = User is new user -> coming to Fp for first time
		//false = Existing user -> re-visiting Fp
		boolean newUser = fpsaveandfetchdataforfpbao.checkUserExistsInMasterCustomerTb(customerId);		
		
		if(newUser == true)
		{
			fpmasterassumption = fetchDefaultAssumptionsFromDatabase();
		}
		
		else if(newUser == false)
		{
			fpmasterassumption = fetchSavedAssumptionsFromDatabase();
		}
		
		fpmaincalculations.setResponses(fpUserResponseSet);
		fpmaincalculations.setAssumptions(fpmasterassumption);
		fpmaincalculations.setcustomerId(customerId);
		fpOutput = fpmaincalculations.getFinancialPlannerOutput();
		return fpOutput; 
	}
	
}
