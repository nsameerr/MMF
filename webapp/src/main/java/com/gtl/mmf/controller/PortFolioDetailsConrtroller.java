/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IPortfolioDetailsBAO;
import static com.gtl.mmf.service.util.IConstants.ASSET_CLASS_ID;
import static com.gtl.mmf.service.util.IConstants.ASSET_CLASS_NAME;
import static com.gtl.mmf.service.util.IConstants.CONTEXT_PATH;
import static com.gtl.mmf.service.util.IConstants.DAY_VALUE;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.IPS_SHARED;
import static com.gtl.mmf.service.util.IConstants.NO_OF_DAYS;
import static com.gtl.mmf.service.util.IConstants.OPTION;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_DETAILS_PAGE;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ASSET_CLASS;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TIME_LINE_OPTED;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("portfoliodetailscontroller")
@Scope("view")
public class PortFolioDetailsConrtroller {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.PortFolioDetailsConrtroller");
    private static final String RE_DIRECTION_REBALANCE = "/pages/rebalance_portfolio?faces-redirect=true";
    private static final String RE_DIRECTION_ADD_REEDEEM = "/pages/addredeemfunds.xhtml?faces-redirect=true";
    private static final String ASSET_BREAK_DOWN = "/pages/assetclass_breakdown.xhtml?faces-redirect=true";
    private CustomerPortfolioVO customerPortfolioVO;
    private List<PieChartDataVO> tragetPieChartDataVOList;
    private List<PieChartDataVO> customerPieChartDataVOList;
    private List<PortfolioDetailsVO> portfolioDetailsVOList;
    private List<PortfolioSecurityVO> portfolioSecurityVOlist;
    private String reDirectionUrl;
    private Integer dateperiod;
    private boolean enableAddRedeem;
    private String option;
    private static final String CUSTOMER_TWR = "customerTWR";
    private static final String RCMDD_TWR = "recommmendedTWR";
    private List<PortfolioDetailsVO> portfolioSecutityList;
    private Float blockedcash;
    private boolean isblockedcash = false;

    @Autowired
    private IPortfolioDetailsBAO portfolioDetailsBAO;

    @PostConstruct
    public void afterinit() {
        try {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            if (userSessionBean.getRelationStatus() == IPS_SHARED.intValue() || userSessionBean.getRelationStatus() >= FOUR_HOUNDRED) {
                userSessionBean.setFromURL(PORTFOLIO_DETAILS_PAGE);
                enableAddRedeem = userSessionBean.getRelationStatus() != IPS_SHARED.intValue();
                customerPortfolioVO = new CustomerPortfolioVO();
                tragetPieChartDataVOList = new ArrayList<PieChartDataVO>();
                customerPieChartDataVOList = new ArrayList<PieChartDataVO>();
                portfolioDetailsVOList = new ArrayList<PortfolioDetailsVO>();
                portfolioSecurityVOlist = new ArrayList<PortfolioSecurityVO>();
                dateperiod = 0;
                customerPortfolioVO.setCustomerId(userSessionBean.getUserId());
                portfolioDetailsBAO.getPortfolioDetailsPageData(customerPortfolioVO, tragetPieChartDataVOList,
                        customerPieChartDataVOList, portfolioDetailsVOList);
                portfolioDetailsBAO.rebalancePortfolio(customerPieChartDataVOList, portfolioDetailsVOList, customerPortfolioVO);
//                portfolioDetailsBAO.getReturnsForSpecifiedPeriod(customerPortfolioVO, portfolioDetailsVOList, dateperiod);
                portfolioDetailsBAO.getTwrReturnsForSpecifiedPeriod(customerPortfolioVO, dateperiod);
                if (customerPortfolioVO.getBlockedCash() != 0) {
                    isblockedcash = true;
                    blockedcash = customerPortfolioVO.getBlockedCash();
                } else {
                    isblockedcash = false;
                    blockedcash = (float) 0;
                }

            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(CONTEXT_PATH + userSessionBean.getFromURL());
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void onclickAddOrRedeem() {
        reDirectionUrl = RE_DIRECTION_ADD_REEDEEM;
    }

    public void onClickReplictePF() {
        reDirectionUrl = RE_DIRECTION_REBALANCE;
    }

    public void onClickReturnDays() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        dateperiod = params.get(DAY_VALUE) == null ? ZERO : Integer.parseInt(params.get(DAY_VALUE));
        option = params.get(OPTION) == null ? dateperiod.toString() : params.get(OPTION);
//        portfolioDetailsBAO.getReturnsForSpecifiedPeriod(customerPortfolioVO, portfolioDetailsVOList, dateperiod);
        portfolioDetailsBAO.getTwrReturnsForSpecifiedPeriod(customerPortfolioVO, dateperiod);
    }

    public void onSelectAssetClass() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> params = context.getRequestParameterMap();
        Map<String, Object> sessionMap = context.getSessionMap();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(ASSET_CLASS_ID, params.get(ASSET_CLASS_ID));
        storedValues.put(ASSET_CLASS_NAME, params.get(ASSET_CLASS_NAME));
        storedValues.put(TIME_LINE_OPTED, option == null ? dateperiod.toString() : option);
        storedValues.put(NO_OF_DAYS, dateperiod.toString());
        storedValues.put(CUSTOMER_TWR, params.get(CUSTOMER_TWR));
        storedValues.put(RCMDD_TWR, params.get(RCMDD_TWR));
        sessionMap.put(SELECTED_ASSET_CLASS, storedValues);
        reDirectionUrl = ASSET_BREAK_DOWN;
    }

    public String getreDirectionUrl() {
        return reDirectionUrl;
    }

    public String getTragetPieChartDataVOList() {
        return PortfolioAllocationChartUtil.generatePieChartData(tragetPieChartDataVOList);
    }

    public String getCustomerPieChartDataVOList() {
        return PortfolioAllocationChartUtil.generatePieChartData(customerPieChartDataVOList);
    }

    public List<PortfolioDetailsVO> getPortfolioDetailsVOList() {
        return portfolioDetailsVOList;
    }

    public void setPortfolioDetailsVOList(List<PortfolioDetailsVO> portfolioDetailsVOList) {
        this.portfolioDetailsVOList = portfolioDetailsVOList;
    }

    public Integer getDateperiod() {
        return dateperiod;
    }

    public void setDateperiod(Integer dateperiod) {
        this.dateperiod = dateperiod;
    }

    public String returnCasHText() {
        return TEXT_CASH;
    }

    public List<PortfolioSecurityVO> getPortfolioSecurityVOlist() {
        return portfolioSecurityVOlist;
    }

    public void setPortfolioSecurityVOlist(List<PortfolioSecurityVO> portfolioSecurityVOlist) {
        this.portfolioSecurityVOlist = portfolioSecurityVOlist;
    }

    public boolean isEnableAddRedeem() {
        return enableAddRedeem;
    }

    public void setEnableAddRedeem(boolean enableAddRedeem) {
        this.enableAddRedeem = enableAddRedeem;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public CustomerPortfolioVO getCustomerPortfolioVO() {
        return customerPortfolioVO;
    }

    public void setCustomerPortfolioVO(CustomerPortfolioVO customerPortfolioVO) {
        this.customerPortfolioVO = customerPortfolioVO;
    }

    public Float getBlockedcash() {
        return blockedcash;
    }

    public void setBlockedcash(Float blockedcash) {
        this.blockedcash = blockedcash;
    }

    public boolean isIsblockedcash() {
        return isblockedcash;
    }

    public void setIsblockedcash(boolean isblockedcash) {
        this.isblockedcash = isblockedcash;
    }

}
