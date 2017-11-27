/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IInvestorPortfolioBAO;

import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("viewPortFolioController")
@Scope("view")
public class ViewPortFolioController{

    private static final String RE_DIRECTION_REBALANCE = "/pages/rebalance_portfolio?redirect=true";
    private CustomerPortfolioVO customerPortfolioVO;
    private AdvisorDetailsVO advisorDetailsVO;
    private List<PieChartDataVO> pieChartDataVOList;
    private List<PortfolioDetailsVO> portfolioDetailsVOList;
    private String reDirectionUrl;
	@Autowired
    private IInvestorPortfolioBAO investorPortfolioBAO;

    @PostConstruct
    public void afterinit() {
    	Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        userSessionBean.setFromURL("/pages/view_portfolio?faces-redirect=true"); 
        if (storedValues != null) {
            advisorDetailsVO = (AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR);
        }
        if(advisorDetailsVO == null)
        {
        	advisorDetailsVO = new AdvisorDetailsVO();
        	advisorDetailsVO.setRelationId(userSessionBean.getRelationId());
        }
        
        customerPortfolioVO = new CustomerPortfolioVO();
        pieChartDataVOList = new ArrayList<PieChartDataVO>();
        portfolioDetailsVOList = new ArrayList<PortfolioDetailsVO>();
        customerPortfolioVO.setRelationId(advisorDetailsVO.getRelationId());
        investorPortfolioBAO.getViewPortfoliopageDetails(customerPortfolioVO, pieChartDataVOList, portfolioDetailsVOList);
     
        
    }

    public void reBalanceportfolio() {
        reDirectionUrl = RE_DIRECTION_REBALANCE;
    }

    public void skipPortfolio() {
        reDirectionUrl = "/pages/investordashboard.xhtml?faces-redirect=true";
    }

    public CustomerPortfolioVO getCustomerPortfolioVO() {
        return customerPortfolioVO;
    }

    public String pieChartData() {
        return PortfolioAllocationChartUtil.generatePieChartData(pieChartDataVOList);
    }

    public List<PortfolioDetailsVO> getPortfolioDetailsVOList() {
        return portfolioDetailsVOList;
    }

    public String getredirectionUrl() {
        return reDirectionUrl;
    }	
}
