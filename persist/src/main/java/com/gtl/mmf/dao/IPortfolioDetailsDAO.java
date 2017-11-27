/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662 
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.List;

public interface IPortfolioDetailsDAO {

    public List<Object> getRebalancePortfolioPageDetails(CustomerPortfolioTb customerPortfolioTb);

    public List<Object> getReurnsforSpecifiedPeriod(CustomerPortfolioDetailsTb customerPortfolioDetailsTb,
            Integer noOfDays, String EOD_Date);

    public List<Object> getSecuritesOfAssetClass(CustomerPortfolioDetailsTb customerPortfolioDetailsTb,
            Integer noOfDays, String EOD_Date);

    public List<Object> getTotalReurnsforSpecifiedPeriod(CustomerPortfolioTb customerPortfolioTb, Integer noOfdays, String eODdate);
    
//    public  List<Object[]> getcustomerPortfoliotableDetails(Integer customerId);
    public  List getcustomerPortfoliotableDetails(Integer customerId);
    
   // public List<Object>getCustomerPortfolioSecuritytb(CustomerPortfolioTb custmerportfoliotb);
    public List<Object[]> getaquisitionAndGainLoss(Integer AssetId,Integer customerId);
    
    public boolean getCustomerIdOfUserExist(Integer customerId);
    
    public Short checkUserStatus(Integer customerId);
}
