/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.InvestmentRelationStatusVO;

/**
 *
 * @author 07958
 */
public interface IInvestmentAdvProfileBAO {

    public InvestmentRelationStatusVO getAdvisorRelationStatus(Integer relationId, Integer customerId);
    
    public boolean getContractTerminationStatus(Integer relationId,Integer advsorId);
}
