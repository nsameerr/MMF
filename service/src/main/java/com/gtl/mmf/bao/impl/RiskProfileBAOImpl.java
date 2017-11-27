
package com.gtl.mmf.bao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IRiskProfileBAO;
import com.gtl.mmf.dao.IRiskProfileDAO;
import com.gtl.mmf.entity.CustomerRiskProfileResultTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.RiskProfileGroupDescriptionMasterTb;
import com.gtl.mmf.entity.RiskProfileRecommendedPortfolioMasterTb;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import com.gtl.mmf.service.vo.riskprofile.RPResponseSet;
import com.gtl.mmf.service.vo.riskprofile.RecommendedAssetAllocation;
import com.gtl.mmf.service.vo.riskprofile.RiskProfileResult;

public class RiskProfileBAOImpl implements IRiskProfileBAO{
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.riskprofilebaoimpl");
	
	@Autowired
	private IRiskProfileDAO riskprofiledao;
	
	private List<RiskProfileGroupDescriptionMasterTb> rpGroupDescriptionMasterTb;
	private List<RiskProfileRecommendedPortfolioMasterTb> riskProfileRecommendedPortfolio;
	List<MasterAssetTb> masterAssset;
	
	@Transactional
	public String fetchCustomerRegistrationIdUsingEmail(String emailId)
	{
		String registrationId = riskprofiledao.fetchRegistrationIdFromTb(emailId);
		
		return registrationId;
	}
	
	
	@Transactional
	public int getMaxRiskScore(int noOfQuestions)
	{
		int maxScore = (noOfQuestions * 5);
		
		LOGGER.log(Level.INFO,"Suman code - Rp Response ->  Maxscore " + maxScore); 
				
		return maxScore;
	}
	
	@Transactional
	public int computeAndSaveRiskScore(RPResponseSet responseSet)
	{
		int sumOfAllScores = 0;
		int finalRiskScore;
		int noOfQuestions, maxScore, rpGroupId=0, recommendedPortfolioId=0;
		
		noOfQuestions = responseSet.getRPResponse().size();
		
		for(int i = 0; i < noOfQuestions; i++)
		{
			int qid = responseSet.getRPResponse().get(i).getQuestionId();
			int answerId = responseSet.getRPResponse().get(i).getAnswerId(); 
			int optionValue = riskprofiledao.getOptionValueFromTb(qid,answerId);
			
			LOGGER.log(Level.INFO,"Suman code - Rp Response ->  Aid " + answerId + "  OptionValue  " + optionValue);
			
			sumOfAllScores = sumOfAllScores + optionValue;
		}
		
		LOGGER.log(Level.INFO,"Suman code - Rp Response ->  sumofScores " + sumOfAllScores);
		maxScore = getMaxRiskScore(noOfQuestions);
		finalRiskScore = (int)((100/maxScore) * sumOfAllScores);
		
		rpGroupDescriptionMasterTb = riskprofiledao.getRiskProfileGroupDetailsFromTb();
		
		for(int i=0; i<rpGroupDescriptionMasterTb.size(); i++)
		{
			if(finalRiskScore >= rpGroupDescriptionMasterTb.get(i).getGroupScoreLowerLimit() && finalRiskScore < rpGroupDescriptionMasterTb.get(i).getGroupScoreUpperLimit())
			{
				rpGroupId = rpGroupDescriptionMasterTb.get(i).getRiskProfileGroupId();
				recommendedPortfolioId = rpGroupDescriptionMasterTb.get(i).getRiskProfileRecommendedPortfolioId();	
			}
		}
		//TODO 
		float totalAllocatedFunds = 1001;//(float)riskprofiledao.getTotalAllocatedFunds("160621032807");
		LOGGER.log(Level.INFO,"Suman code - Rp ->  totalAllocatedFunds " +totalAllocatedFunds); 		
		
		int portfolioSizeId = riskprofiledao.getPortfolioSizeId((int)totalAllocatedFunds);
		LOGGER.log(Level.INFO,"Suman code - Rp ->  portfolioSizeId " +portfolioSizeId);
		//getRiskProfileRecommendedPortfolio(riskProfileGroupDescription.getRiskProfileRecommendedPortfolioId());

		riskprofiledao.saveRiskScore(responseSet.getCustomerRegistrationId(), finalRiskScore, rpGroupId, recommendedPortfolioId);
		return finalRiskScore;
	}
	
	
	@Transactional
	public RiskProfileResult getRiskProfileOutput(String customerRegistrationId){
		//Final Result Object
		RiskProfileResult rpResult = new RiskProfileResult();
		//Get Risk Profile Result from db using Id
		CustomerRiskProfileResultTb custRpResult = riskprofiledao.getCustomerRpResultFromTb(customerRegistrationId);
		//Declaration
		int riskScore = custRpResult.getRiskScore();
		int rpGrpId = custRpResult.getRiskProfileGroupId();
		int rpRecoPortfolioId = custRpResult.getRiskProfileRecommemdedPortfolioId();
		
		LOGGER.log(Level.INFO,"Rp Output Page Data");
		LOGGER.log(Level.INFO,"Rp Response ->  score :" + riskScore + "  grpId :" + rpGrpId + "recoId :" + rpRecoPortfolioId);
		
		//added customerId and RiskScore in result object
		rpResult.setCustomerRegistrationId(customerRegistrationId);
		rpResult.setRiskScore(riskScore);
		
		//fetch the tb containg all rpGroupDesc
		rpGroupDescriptionMasterTb = riskprofiledao.getRiskProfileGroupDetailsFromTb();
		
		//Select the matching portfolio desc according to riskscore
		for(int i=0; i<rpGroupDescriptionMasterTb.size(); i++)
		{
			if(rpGrpId == rpGroupDescriptionMasterTb.get(i).getRiskProfileGroupId())
			{
				//rpResult.setRiskProfileGroupName(rpGroupDescriptionMasterTb.get(i).getRiskProfileGroupName());
				rpResult.setRiskProfileDescription(rpGroupDescriptionMasterTb.get(i).getRiskProfileDescription());
				LOGGER.log(Level.INFO,"Rp Description " + rpGroupDescriptionMasterTb.get(i).getRiskProfileDescription());
				break;
			}
		}
		
		riskProfileRecommendedPortfolio = riskprofiledao.getRecommendedPortfolioFromTb(rpRecoPortfolioId);
		LOGGER.log(Level.INFO,"Suman code - Recommended Portfolio");
		for(int i=0; i<riskProfileRecommendedPortfolio.size(); i++)
		{
			int assetId =  riskProfileRecommendedPortfolio.get(i).getAssetClassId();
			float allocation = riskProfileRecommendedPortfolio.get(i).getAssetClassAllocation();
			LOGGER.log(Level.INFO,"assetId  " + assetId + "  %allocation  " +allocation);
		}
		
		//Filling RecommendedAssetAllocation in rpResult
		masterAssset = riskprofiledao.getMasterAssetTb();
		List<RecommendedAssetAllocation> recommendedPortfolioAllocations = new ArrayList<RecommendedAssetAllocation>();
		int assetId;
		float percentageAllocation;
		String assetName="", assetColoueCode="";
		
		
		for(int i=0; i<riskProfileRecommendedPortfolio.size(); i++)
		{
			RecommendedAssetAllocation temp = new RecommendedAssetAllocation();
			
			assetId = riskProfileRecommendedPortfolio.get(i).getAssetClassId();
			percentageAllocation = riskProfileRecommendedPortfolio.get(i).getAssetClassAllocation();
			
			for(int j=0; j<masterAssset.size(); j++)
			{
				if(assetId == masterAssset.get(j).getId())
				{
					assetName = masterAssset.get(j).getAssetName();
					assetColoueCode = masterAssset.get(j).getAssetColor();
					break;
				}
					
			}
			temp.setAssetClassId(assetId);
			temp.setPercentageAllocation(percentageAllocation);
			temp.setAssetClassName(assetName);
			temp.setColourCode(assetColoueCode);
			
			recommendedPortfolioAllocations.add(i,temp);
			
		}
		
		rpResult.setRecommendedAssetAllocation(recommendedPortfolioAllocations);
		LOGGER.log(Level.INFO,"Suman code - Recommended Portfolio in Output Object");
		for(int i=0; i<rpResult.getRecommendedAssetAllocation().size(); i++)
		{
			LOGGER.log(Level.INFO,"assetId  " + rpResult.getRecommendedAssetAllocation().get(i).getAssetClassId());
			LOGGER.log(Level.INFO,"assetname  " + rpResult.getRecommendedAssetAllocation().get(i).getAssetClassName());
			LOGGER.log(Level.INFO,"%Allocation  " + rpResult.getRecommendedAssetAllocation().get(i).getPercentageAllocation());
			LOGGER.log(Level.INFO,"ColourCode  " + rpResult.getRecommendedAssetAllocation().get(i).getColourCode());
		}
		
		return rpResult;
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public Map<Integer, QuestionnaireVO> getRiskProfileBackwordFormat(RPResponseSet responseSet) {
		int noOfQuestions = responseSet.getRPResponse().size();
		 Map<Integer, QuestionnaireVO> tempMap = new LinkedHashMap<Integer, QuestionnaireVO>();
		QuestionnaireVO q = null;
		for(int i = 0; i < noOfQuestions; i++)
		{
			int qid = responseSet.getRPResponse().get(i).getQuestionId();
			int answerId = responseSet.getRPResponse().get(i).getAnswerId(); 
			int optionValue = riskprofiledao.getOptionValueFromTb(qid,answerId);
			
			int start = 40,j=1;
			if (i == 0){
				//question 1 options 39-40
				start =40;j=1;
				while(j<answerId){
					start++;j++;
				}
			} else if (i == 1){
				//question 2 options 39-40
				start =50;j=1;
				while(j<answerId){
					start++;j++;
				}
			} else if (i == 2){
				//question 3 options 39-40
				start =40;j=1;
				while(j<answerId){
					start++;j++;
				}
			} else if (i == 3){
				//question 4 options 39-40
				start =45;j=1;
				while(j<answerId){
					start++;j++;
				}
			} else if (i == 4){
				//question 5 options 39-40
				start =55;j=1;
				while(j<answerId){
					start++;j++;
				}
			}else if (i == 5){
				//question 6 options 39-40
				start =45;j=1;
				while(j<answerId){
					start++;j++;
				}
			}else if (i == 6){
				//question 7 options 39-40
				start =60;j=1;
				while(j<answerId){
					start++;j++;
				}
			}else if (i == 7){
				//question 8 options 39-40
				start =40;j=1;
				while(j<answerId){
					start++;j++;
				}
			}else if (i == 8){
				//question 9 options 39-40
				start =40;j=1;
				while(j<answerId){
					start++;j++;
				}
			}else if (i == 9){
				//question 10 options 39-40
				start =65;j=1;
				while(j<answerId){
					start++;j++;
				}
			}	
			q= new QuestionnaireVO();
			q.setQuestionId(i+21);
			q.setQstoptionId(start);
			q.setQstoptionValue(optionValue);
			LOGGER.log(Level.INFO,"question id: "+ (i+21)+ " stopid:"+ start+ "  OptionValue :" + optionValue);
			tempMap.put(i, q);
			
		}
		return tempMap;
	}
	
	
	
}