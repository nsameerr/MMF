/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.entity.RecomendedCustomerPortfolioSecuritiesTb;
import java.util.List;
import java.util.Set;

/**
 *
 * @author 07958
 */
public interface ICreateEditPortfolioDAO {

    public Integer saveProtfolio(PortfolioTb portfolioTb);

    public Integer saveProtfolioDetails(PortfolioDetailsTb portfolioDetailsTb);

    public void savePortfolioSecurites(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs);
    
    public PortfolioTb getPorfolioById(int portfolioId);

    public void updatePortfolio(PortfolioTb portfolioTb);

    public void updatePortfolioDetails(PortfolioDetailsTb portfolioDetailsTb);
    
    public void savePortfolioSecurities(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs);

    public List<Object[]> getPorfolioNames(int advisorId, int portfolioTypeId, int benchmark);

    public void updatePortfolioSecurities(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs);

    public int updateCustomerPortfoliosOnEdit(int portfolioId);

    public List<CustomerPortfolioTb> getCustomerPortfoliosAssigned(int portfolioId);

    public void updateCustomerPortfolioDetails(CustomerPortfolioDetailsTb customerPortfolioDetailsTb);

    public void updateCustomerPortfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs);

    public void saveCustomerPortfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs);

    public List<PortfolioDetailsTb> getPortfolioDetails(int portfolioId);
    
    public void saveOrUpdateCustomerPortfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs);
    
    public List<Object> getRecomendedCustomerPortfolioSecurities(int portfolioId,CustomerPortfolioDetailsTb customerPortfolioDetailsTb);
    
    public void saveOrUpdateRecomendedCustomerPortfolioSecurities(Set<RecomendedCustomerPortfolioSecuritiesTb> recomendedCustomerPortfolioSecuritiesTbs, int customerPortfolioId);

    public MasterPortfolioTypeTb getPortfolioByStyleAndSize(int portfolioStyleId, int portfolioSizeId);
    
    public List<PortfolioDetailsTb> getOldPortfolioAsset(int portfolioId);
    
    public Integer updatePortfolioTypeOfPortfolio(int portfolioId,int portfolioTypeId);
    
    public void saveNewPortfolioDetails(PortfolioDetailsTb portfolioDetailsTb);
}
