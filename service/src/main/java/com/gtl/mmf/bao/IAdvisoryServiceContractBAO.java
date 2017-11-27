/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;

import java.util.Map;

/**
 *
 * @author 07958
 */
public interface IAdvisoryServiceContractBAO {

    public ContractDetailsVO getContractDetails(Integer relationId, int status);

    public void submitQuestionnaire(Map<Integer, QuestionnaireVO> questionnaireMAp, AdvisorDetailsVO advisorDetailsVO,
            Map<Integer, PortfolioTypeVO> PortfolioTypeMap, String investorName,Double fund) throws ClassNotFoundException;

    public Double getInvestorFundDetail(Integer relationId);
    
    public void submitQuestionnaireNew(Map<Integer, QuestionnaireVO> questionnnaireMap, int riskScore,AdvisorDetailsVO advisorDetailsVO,  Map<Integer, PortfolioTypeVO> PortfolioTypeMap,String investorName,Double fund );
}
