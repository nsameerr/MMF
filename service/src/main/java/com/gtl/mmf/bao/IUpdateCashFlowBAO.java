/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.entity.EcsDebtPaymentFileContentTb;
import java.util.List;

/**
 *
 * @author 09860
 */
public interface IUpdateCashFlowBAO {

    public void getCashFlowDetails();

    public List<EcsDebtPaymentFileContentTb> customerFeeCalculation();

    public void saveEcsDetails(List<EcsDebtPaymentFileContentTb> customerFeeCalculation);
    
}
