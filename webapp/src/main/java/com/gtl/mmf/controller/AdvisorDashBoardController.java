/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.gtl.mmf.bao.IAdvisorDashBoardBAO;
import static com.gtl.mmf.service.util.IConstants.CREATE;
import static com.gtl.mmf.service.util.IConstants.FROM_LINKEDINCONNECTED;
import static com.gtl.mmf.service.util.IConstants.FROM_NOTIFICATION;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_ID;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import com.gtl.mmf.service.vo.ClientDetailsVO;
import com.gtl.mmf.service.vo.ClientPerformanceVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.PortfolioPerformanceVO;
import com.gtl.mmf.service.vo.PortfolioRebalanceTriggerVO;
import com.gtl.mmf.webapp.util.AdvisorRedirectionUtil;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author 07958
 */
@Named("advisorDashBoardController")
@Scope("view")
public class AdvisorDashBoardController {

    private static final String CREATE_EDIT_PORTFOLIO_PAGE = "/pages/create_edit_portfolio?faces-redirect=true";
    private static final String CLIENT_INVESTMENT_PERFORMANCE_PAGE = "/pages/clientinvestmentperformance?faces-redirect=true";
    private static final String CLIENT_INDEX = "ClientPerfIndex";
    private int advisorId;
    @Autowired
    private IAdvisorDashBoardBAO advisorDashBoardBAO;
    private List<ClientDetailsVO> clientStatusDetails;
    private List<PortfolioRebalanceTriggerVO> portfoliosForRebalance;
    private List<ClientPerformanceVO> clientPerformanceVOs;
    private List<PortfolioPerformanceVO> portfolioPerformanceVOs;
    boolean linkedInConnected;

    @PostConstruct
    public void afterinit() {
        UserSessionBean userSessionBean = (UserSessionBean) getSessionMap().get(USER_SESSION);
        if (userSessionBean != null) {
            this.advisorId = userSessionBean.getUserId();
        }
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext
                .getCurrentInstance().getExternalContext().
                getSessionMap().get(STORED_VALUES);

        if (storedValues.get(FROM_LINKEDINCONNECTED) != null) {
            linkedInConnected = (Boolean) storedValues.get(FROM_LINKEDINCONNECTED);
        }
        clientStatusDetails = advisorDashBoardBAO.getClientStatusDetails(advisorId,
                userSessionBean.getAccessToken());
        // Portfolio Rebalance Triggers table
        portfoliosForRebalance = new ArrayList<PortfolioRebalanceTriggerVO>();
        clientPerformanceVOs = new ArrayList<ClientPerformanceVO>();
        portfolioPerformanceVOs = new ArrayList<PortfolioPerformanceVO>();
      
        //Loading informations to be displayed on the advisor dashboard tables.
        advisorDashBoardBAO.getDashboardDatas(advisorId, clientPerformanceVOs, portfolioPerformanceVOs, portfoliosForRebalance);
    }

    public String onClickPortfolioPerformance() {
        return this.setCreateEditPortfolioDetails();
    }

    public String onClickClientsPerformance() {
        InvestorDetailsVO investorProfile = new InvestorDetailsVO();
        Integer portfolioId = Integer.parseInt(getRequestParameterMap().get(PORTFOLIO_ID));
        Integer clientIndex = Integer.parseInt(getRequestParameterMap().get(CLIENT_INDEX));
        investorProfile.setRelationId(clientPerformanceVOs.get(clientIndex).getRelationId());
        investorProfile.setCustomerId(clientPerformanceVOs.get(clientIndex).getCustomerId());
        investorProfile.setStatus(clientPerformanceVOs.get(clientIndex).getClientStataus());
        investorProfile.setFirstName(clientPerformanceVOs.get(clientIndex).getCustomerName());
        investorProfile.setEmail(clientPerformanceVOs.get(clientIndex).getEmail());
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(PORTFOLIO_ID, portfolioId);
        storedValues.put(SELECTED_INVESTOR, investorProfile);
        getSessionMap().put(STORED_VALUES, storedValues);
        return CLIENT_INVESTMENT_PERFORMANCE_PAGE;
    }

    public String onClickPortfolioTrigger() {
        return this.setCreateEditPortfolioDetails();
    }

    public String onSelectInvestor() {
        int selectedInvestorIndex = Integer.valueOf(getRequestParameterMap().get("index")).intValue();
        ClientDetailsVO selectedInvestor = clientStatusDetails.get(selectedInvestorIndex);
        AdvisorRedirectionUtil advisorRedirectionUtil = new AdvisorRedirectionUtil();
        return advisorRedirectionUtil.advisorRredirectionPath(selectedInvestor.getInvestorDetailsVO());
    }

    private String setCreateEditPortfolioDetails() {
        Integer portfolioId = Integer.valueOf(getRequestParameterMap().get(PORTFOLIO_ID)).intValue();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(CREATE, false);
        storedValues.put(PORTFOLIO_ID, portfolioId);
        storedValues.put(FROM_NOTIFICATION, true);
       // storedValues.remove(PORTFOLIO);
        getSessionMap().put(STORED_VALUES, storedValues);
        return CREATE_EDIT_PORTFOLIO_PAGE;
    }

    private Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    private Map<String, String> getRequestParameterMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

    public List<ClientDetailsVO> getClientStatusDetails() {
        return clientStatusDetails;
    }

    public void setClientStatusDetails(List<ClientDetailsVO> clientStatusDetails) {
        this.clientStatusDetails = clientStatusDetails;
    }

    public List<PortfolioRebalanceTriggerVO> getPortfoliosForRebalance() {
        return portfoliosForRebalance;
    }

    public List<ClientPerformanceVO> getClientPerformanceVOs() {
        return clientPerformanceVOs;
    }

    public List<PortfolioPerformanceVO> getPortfolioPerformanceVOs() {
        return portfolioPerformanceVOs;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public void hideDialogLinkedinSuccess() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext
                .getCurrentInstance().getExternalContext().
                getSessionMap().get(STORED_VALUES);
        if (storedValues.get(FROM_LINKEDINCONNECTED) != null) {
            storedValues.remove(FROM_LINKEDINCONNECTED);
        }
        linkedInConnected = false;
    }
}
