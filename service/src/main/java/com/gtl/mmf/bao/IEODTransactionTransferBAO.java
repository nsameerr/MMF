/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;

public interface IEODTransactionTransferBAO {

    public void buildTransactiontramsferThread(List<CustomerPortfolioTb> customerPortfolioTbList, Date eodDate);

    public void transferDaliyTransactionAndCash(CustomerPortfolioTb customerPortfolioTb, Date eodDate);

    public void findingOutCashFlowForCustomer(CustomerPortfolioTb customerPortfolioTb);
}
