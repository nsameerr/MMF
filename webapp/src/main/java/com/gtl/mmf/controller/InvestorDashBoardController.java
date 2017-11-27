/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IPortfolioDetailsBAO;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

/**
 *
 * @author 07958
 */
@Named("investorDashBoardController")
@Scope("view")
public class InvestorDashBoardController {

    @Autowired
    private IPortfolioDetailsBAO portfolioDetailsBAO;
    private boolean isContract = false;
    private String contractStartMessage = EMPTY_STRING;

    private int clientId;
    List<PortfolioSecurityVO> portfolioSecurityVOlist = new ArrayList<PortfolioSecurityVO>();

    @PostConstruct
    public void afterinit() {
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        if (userSessionBean != null) {
            this.clientId = userSessionBean.getUserId();
            String omsid = userSessionBean.getOmsLoginId();
            Integer customerId = userSessionBean.getUserId();
            portfolioSecurityVOlist = new ArrayList<PortfolioSecurityVO>();
            portfolioSecurityVOlist = portfolioDetailsBAO.getInvestorDashboardData(omsid, customerId, portfolioSecurityVOlist);
            //isContract = portfolioDetailsBAO.checkForContractTermination(customerId);
            Integer relationStatus = userSessionBean.getRelationStatus();
            if (relationStatus != null) {
                isContract = relationStatus == ZERO ? true : false;
                if (isContract) {
                    contractStartMessage = "Your Previous Contract Successfully Terminated,Please Do Sign A New Contract";

                }
            }

         }

    }

    public List<PortfolioSecurityVO> getPortfolioSecurityVOlist() {
        return portfolioSecurityVOlist;
    }

    public void setPortfolioSecurityVOlist(List<PortfolioSecurityVO> portfolioSecurityVOlist) {
        this.portfolioSecurityVOlist = portfolioSecurityVOlist;
    }

    public boolean isIsContract() {
        return isContract;
    }

    public void setIsContract(boolean isContract) {
        this.isContract = isContract;
    }

    public String getContractStartMessage() {
        return contractStartMessage;
    }

    public void setContractStartMessage(String contractStartMessage) {
        this.contractStartMessage = contractStartMessage;
    }

}
