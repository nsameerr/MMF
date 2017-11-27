/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.service.vo.AddRedeemFundsVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAddRedeemFundsBAO {

    public void modifyAllocation(AddRedeemFundsVO addRedeemFundsVO);

    public AddRedeemFundsVO getAddRedeemFundDetails(int customerId);
    
    public void logAddRedeemFundDetails(AddRedeemFundsVO addRedeemFundsVO);
    
    public List<Object[]> getlogAddRedeemFundDetails(AddRedeemFundsVO addRedeemFundsVO);

    public void addFundDetails(CashFlowTb cashFlowTb);
}
