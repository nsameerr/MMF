/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IAdvisoryServiceContractBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IAdvisoryServiceContractDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerQuestionResponseSetTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.QuestionmasterTb;
import com.gtl.mmf.entity.QuestionoptionsmasterTb;
import static com.gtl.mmf.service.util.IConstants.HUNDRED;
import static com.gtl.mmf.service.util.IConstants.REVIEW_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.RISK_SCORE_DIVISOR;
import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class AdvisoryServiceContractBAOImpl implements IAdvisoryServiceContractBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.AdvisoryServiceContractBAOImpl");
    private static final int DFLT_PERFN_FEE = 20;
    private static final int DFLT_THRESOLD_FQCY = 12;

    @Autowired
    private IAdvisoryServiceContractDAO advisoryServiceContractDAO;

    /**
     * Preparing advisory Service Contract details
     *
     * @param relationId -Integer number representing relation between invester
     * and advisor.
     * @param status - Current relation Status between invester and advisor.
     * @return ContractDetailsVO - which contain contract details.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public ContractDetailsVO getContractDetails(Integer relationId, int status) {
        ContractDetailsVO contractDetailsVO = new ContractDetailsVO();

        // Checking wheather advsor/investor is in contract signed stage
        if (status == EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue()
                || status == EnumAdvisorMappingStatus.CONTRACT_REVIEW.getValue()
                || status == EnumAdvisorMappingStatus.CONTRACT_SIGNED.getValue()
                || status == EnumAdvisorMappingStatus.CONTRACT_SENT.getValue()) {

            //Collecting contract informations 
            CustomerAdvisorMappingTb mappingData = advisoryServiceContractDAO.getContractDetails(relationId);
            contractDetailsVO.setAllocatedFunds(mappingData.getAllocatedFunds());
            contractDetailsVO.setRelationId(mappingData.getRelationId());
            contractDetailsVO.setCustomerId(mappingData.getMasterCustomerTb().getCustomerId());
            contractDetailsVO.setPerformanceFeePercent(BigDecimal.valueOf(DFLT_PERFN_FEE));
            contractDetailsVO.setPerformanceFeeThreshold(BigDecimal.valueOf(DFLT_THRESOLD_FQCY));
            if (status != EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue()) {
                contractDetailsVO.setDurationCount(mappingData.getDurationCount());
                contractDetailsVO.setDurationFrequency(mappingData.getDurationFrequencyMonth());
                contractDetailsVO.setEndDate(mappingData.getContractEnd());
                contractDetailsVO.setFixedManagementFee(mappingData.getManagementFeeFixed());
                contractDetailsVO.setMgmtFeeTypeVariable(mappingData.getMgmtFeeTypeVariable());
                contractDetailsVO.setPerformanceFeeFrequency(mappingData.getPerfFeeFreq());
                contractDetailsVO.setPerformanceFeePercent(mappingData.getPerfFeePercent());
                contractDetailsVO.setPerformanceFeeThreshold(mappingData.getPerfFeeThreshold());
                contractDetailsVO.setStartDate(mappingData.getContractStart());
                contractDetailsVO.setVariableManagementFee(mappingData.getManagementFeeVariable());
                if (contractDetailsVO.getMgmtFeeTypeVariable()) {
                    contractDetailsVO.setMgmtFeeVariablePayableFrequecy(mappingData.getMgmtFeeFreq());
                } else {
                    contractDetailsVO.setMgmtFeeFixedPayableFrequecy(mappingData.getMgmtFeeFreq());
                }

                // Checking for review comments added
                if (mappingData.getCustomerReview() != null) {
                    String completeCustomerReview = mappingData.getCustomerReview();
                    contractDetailsVO.setPreviousReviews(Arrays.asList(completeCustomerReview.split(REVIEW_SEPERATOR)));
                }
                contractDetailsVO.setReviewFrequency(mappingData.getCommentFreq());
            }
        }
        return contractDetailsVO;
    }

    /**
     * Investor Submitting Questionnaire
     *
     * @param questionnaireMAp - Contains the Questions,options and answers
     * selected by the invester.
     * @param advisorDetailsVO - Advisor Details
     * @param portfolioTypeMap - Contains the detals of portfolios under the
     * advisor
     * @param investorName - Name of the invester submitting questionnaire
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void submitQuestionnaire(Map<Integer, QuestionnaireVO> questionnaireMAp, AdvisorDetailsVO advisorDetailsVO,
            Map<Integer, PortfolioTypeVO> portfolioTypeMap, String investorName, Double fund) throws ClassNotFoundException {

        List<CustomerQuestionResponseSetTb> customerQuestionResponseSetTbList = new ArrayList<CustomerQuestionResponseSetTb>();

        // Iterating through each question response
        for (Map.Entry<Integer, QuestionnaireVO> entry : questionnaireMAp.entrySet()) {

            QuestionnaireVO questionnaireVO = (QuestionnaireVO) entry.getValue();
            CustomerQuestionResponseSetTb customerQuestionResponseSetTb = new CustomerQuestionResponseSetTb();
            customerQuestionResponseSetTb.setResponseSetId(null);
            customerQuestionResponseSetTb.setResponseSetDate(new Date());

            CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
            customerAdvisorMappingTb.setRelationId(advisorDetailsVO.getRelationId());

            customerQuestionResponseSetTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);

            //Setting Question Id
            QuestionmasterTb questionmasterTb = new QuestionmasterTb();
            questionmasterTb.setId(questionnaireVO.getQuestionId());
            customerQuestionResponseSetTb.setQuestionmasterTb(questionmasterTb);

            //Setting Option Id
            QuestionoptionsmasterTb questionoptionsmasterTb = new QuestionoptionsmasterTb();
            questionoptionsmasterTb.setId(questionnaireVO.getQstoptionId());
            customerQuestionResponseSetTb.setQuestionoptionsmasterTb(questionoptionsmasterTb);

            //Setting Option value
            customerQuestionResponseSetTb.setOptionValue(questionnaireVO.getQstoptionValue());
            customerQuestionResponseSetTbList.add(customerQuestionResponseSetTb);
        }

        //Saving Investor Questionnair RESPONSE
        advisoryServiceContractDAO.submitInvestorQuestionnair(customerQuestionResponseSetTbList);

        //get sum score result from customerQuestionResponseSetTb  
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationId(advisorDetailsVO.getRelationId());
        customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.RESPONSE_SENT.getValue());

        //updateing Notification date when submiting Questionnaire
        customerAdvisorMappingTb.setStatusDate(new Date());
        MasterPortfolioTypeTb masterPortfolioTypeTb = new MasterPortfolioTypeTb();
        CustomerRiskProfileTb customerRiskProfileTb = new CustomerRiskProfileTb();
        customerRiskProfileTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
        int totalScore = advisoryServiceContractDAO.getTotalScoreOfQuestinnaire(advisorDetailsVO.getRelationId());

        //Calculating  risk score using new equation 
        // riskScore = ((float)totalScore/114)*100;
        Float riskScore = ((float) totalScore / RISK_SCORE_DIVISOR) * HUNDRED;
        customerRiskProfileTb.setRiskScore(riskScore.intValue());
        for (Map.Entry<Integer, PortfolioTypeVO> entry : portfolioTypeMap.entrySet()) {
            PortfolioTypeVO portfolioTypeVO = entry.getValue();

            //Selecting portfolio for invester based on risk score
            if (portfolioTypeVO.getMaxAum() != null) {
                if (riskScore >= portfolioTypeVO.getMinScore() && riskScore <= portfolioTypeVO.getMaxScore()
                        && fund.intValue() >= portfolioTypeVO.getMinAum() && fund.intValue() <= portfolioTypeVO.getMaxAum()) {
                    masterPortfolioTypeTb = advisoryServiceContractDAO.getMasterPortfolioType(portfolioTypeVO.getId());
                    //  masterPortfolioTypeTb.setId(portfolioTypeVO.getId());
                    break;
                }
            }else{
                if (riskScore >= portfolioTypeVO.getMinScore() && riskScore <= portfolioTypeVO.getMaxScore()
                        && fund.intValue() >= portfolioTypeVO.getMinAum()) {
                    masterPortfolioTypeTb = advisoryServiceContractDAO.getMasterPortfolioType(portfolioTypeVO.getId());
                    break;
                }
            }

        }

        //Seeting portfolio which selected by advisor irrespective of risk score
        customerRiskProfileTb.setMasterPortfolioTypeTb(masterPortfolioTypeTb);

        //Seeting portfolio Assigned based on risk score
        customerRiskProfileTb.setMasterPortfolioStyleTbByInitialPortfolioStyle(masterPortfolioTypeTb.getMasterPortfolioStyleTb());
        customerRiskProfileTb.setMasterPortfolioStyleTbByPortfolioStyle(masterPortfolioTypeTb.getMasterPortfolioStyleTb());
        customerRiskProfileTb.setMasterPortfolioSizeTbByInitialPortfolioSize(masterPortfolioTypeTb.getMasterPortfolioSizeTb());
        customerRiskProfileTb.setMasterPortfolioSizeTbByPortfolioSize(masterPortfolioTypeTb.getMasterPortfolioSizeTb());
        advisoryServiceContractDAO.saveRiskProfile(customerRiskProfileTb, customerAdvisorMappingTb);
        String notificationMsg = InvestorNotificationMsgService.getAdvNotificationMessage(false, EnumCustomerMappingStatus.RESPONSE_SENT.getValue(), investorName);
        MailUtil.getInstance().sendNotificationMail(advisorDetailsVO.getFirstName(), advisorDetailsVO.getEmail(), notificationMsg);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Double getInvestorFundDetail(Integer cusId) {
        MasterCustomerTb customerData = advisoryServiceContractDAO.getInvestorFundDetails(cusId);
        return customerData.getTotalFunds();
    }
    
    /**
     * @author Sumeet Pol
     * method is backward compatible for saving risk score and assigned portfolio to customer and send notification back
     * new method corresponding to new risk profile questionnaire
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public void submitQuestionnaireNew(Map<Integer, QuestionnaireVO> questionnaireMAp,int riskScore,AdvisorDetailsVO advisorDetailsVO, Map<Integer, PortfolioTypeVO> portfolioTypeMap, String investorName, Double fund) {
		try {
			
	        List<CustomerQuestionResponseSetTb> customerQuestionResponseSetTbList = new ArrayList<CustomerQuestionResponseSetTb>();

	        // Iterating through each question response
	        for (Map.Entry<Integer, QuestionnaireVO> entry : questionnaireMAp.entrySet()) {

	            QuestionnaireVO questionnaireVO = (QuestionnaireVO) entry.getValue();
	            CustomerQuestionResponseSetTb customerQuestionResponseSetTb = new CustomerQuestionResponseSetTb();
	            customerQuestionResponseSetTb.setResponseSetId(null);
	            customerQuestionResponseSetTb.setResponseSetDate(new Date());

	            CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
	            customerAdvisorMappingTb.setRelationId(advisorDetailsVO.getRelationId());

	            customerQuestionResponseSetTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);

	            //Setting Question Id
	            QuestionmasterTb questionmasterTb = new QuestionmasterTb();
	            questionmasterTb.setId(questionnaireVO.getQuestionId());
	            customerQuestionResponseSetTb.setQuestionmasterTb(questionmasterTb);

	            //Setting Option Id
	            QuestionoptionsmasterTb questionoptionsmasterTb = new QuestionoptionsmasterTb();
	            questionoptionsmasterTb.setId(questionnaireVO.getQstoptionId());
	            customerQuestionResponseSetTb.setQuestionoptionsmasterTb(questionoptionsmasterTb);

	            //Setting Option value
	            customerQuestionResponseSetTb.setOptionValue(questionnaireVO.getQstoptionValue());
	            customerQuestionResponseSetTbList.add(customerQuestionResponseSetTb);
	        }

	        //Saving Investor Questionnair RESPONSE
	        advisoryServiceContractDAO.submitInvestorQuestionnair(customerQuestionResponseSetTbList);
			
			//get sum score result from customerQuestionResponseSetTb  
			CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
			customerAdvisorMappingTb.setRelationId(advisorDetailsVO.getRelationId());
			customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.RESPONSE_SENT.getValue());

			//updateing Notification date when submiting Questionnaire
			customerAdvisorMappingTb.setStatusDate(new Date());
			MasterPortfolioTypeTb masterPortfolioTypeTb = new MasterPortfolioTypeTb();
			CustomerRiskProfileTb customerRiskProfileTb = new CustomerRiskProfileTb();
			customerRiskProfileTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
			//int totalScore = advisoryServiceContractDAO.getTotalScoreOfQuestinnaire(advisorDetailsVO.getRelationId());

			//Calculating  risk score using new equation 
			// riskScore = ((float)totalScore/114)*100;
			//Float riskScore = ((float) totalScore / RISK_SCORE_DIVISOR) * HUNDRED;
			customerRiskProfileTb.setRiskScore(riskScore); //---------------------> set our riskscore
			
			  for (Map.Entry<Integer, PortfolioTypeVO> entry : portfolioTypeMap.entrySet()) {
		            PortfolioTypeVO portfolioTypeVO = entry.getValue();

		            //Selecting portfolio for invester based on risk score
		            if (portfolioTypeVO.getMaxAum() != null) {
		                if (riskScore >= portfolioTypeVO.getMinScore() && riskScore <= portfolioTypeVO.getMaxScore()
		                        && fund.intValue() >= portfolioTypeVO.getMinAum() && fund.intValue() <= portfolioTypeVO.getMaxAum()) {
		                    masterPortfolioTypeTb = advisoryServiceContractDAO.getMasterPortfolioType(portfolioTypeVO.getId());
		                    //  masterPortfolioTypeTb.setId(portfolioTypeVO.getId());
		                    break;
		                }
		            }else{
		                if (riskScore >= portfolioTypeVO.getMinScore() && riskScore <= portfolioTypeVO.getMaxScore()
		                        && fund.intValue() >= portfolioTypeVO.getMinAum()) {
		                    masterPortfolioTypeTb = advisoryServiceContractDAO.getMasterPortfolioType(portfolioTypeVO.getId());
		                    break;
		                }
		            }

		        }
			//masterPortfolioTypeTb = advisoryServiceContractDAO.getMasterPortfolioType(portfolioTypeVO.getId()); //--- >check it out

			//Seeting portfolio which selected by advisor irrespective of risk score
			customerRiskProfileTb.setMasterPortfolioTypeTb(masterPortfolioTypeTb);

			//Seeting portfolio Assigned based on risk score
			customerRiskProfileTb.setMasterPortfolioStyleTbByInitialPortfolioStyle(masterPortfolioTypeTb.getMasterPortfolioStyleTb());
			customerRiskProfileTb.setMasterPortfolioStyleTbByPortfolioStyle(masterPortfolioTypeTb.getMasterPortfolioStyleTb());
			customerRiskProfileTb.setMasterPortfolioSizeTbByInitialPortfolioSize(masterPortfolioTypeTb.getMasterPortfolioSizeTb());
			customerRiskProfileTb.setMasterPortfolioSizeTbByPortfolioSize(masterPortfolioTypeTb.getMasterPortfolioSizeTb());
			advisoryServiceContractDAO.saveRiskProfile(customerRiskProfileTb, customerAdvisorMappingTb);
			String notificationMsg = InvestorNotificationMsgService.getAdvNotificationMessage(false, EnumCustomerMappingStatus.RESPONSE_SENT.getValue(), investorName);
			MailUtil.getInstance().sendNotificationMail(advisorDetailsVO.getFirstName(), advisorDetailsVO.getEmail(), notificationMsg);
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "error in submitting new risk profile questionnaire", e);
		}
		

	}
}
