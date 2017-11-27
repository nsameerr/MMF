/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import java.util.Map;

import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.service.vo.MarketDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface ICreateEditPortfolioBAO {

    public PortfolioTb createPortfolio(PortfolioVO portfolioVO, PortfolioTypeVO portfolioTypeVO);
    
    public MarketDataVO getMarketData(String scripName);

    public PortfolioVO getPortfolioById(int advisorId, int portfolioId);
    
    public Map<Integer, String> getPorfolioNames(int advisorId, int portfolioTypeId, Map<String, Short> assets, int benchmark);

    public PortfolioTb updatePortfolio(PortfolioVO portfolioVO, PortfolioTypeVO portfolioTypeVO, Map<String, Short> assets);
        
    public Integer getPortfolioByStyleAndSize(int portfolioStyleId,int portfolioSizeId); 
    
    public List<PortfolioDetailsVO> getExistingPortfolioAssets(int portfolioId);
    
    public boolean updatePortfolioTypeOfPortfolio(int portfolioId,int portfolioTypeId);
    
    public void transformPortfolioWithNewAssetClass(PortfolioVO portfolioVO, PortfolioTypeVO portfolioTypeVO, Map<String, Short> assets);
}
