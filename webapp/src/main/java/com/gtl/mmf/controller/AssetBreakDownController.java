/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IPortfolioDetailsBAO;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.ASSET_BREAKDOWN;
import static com.gtl.mmf.service.util.IConstants.ASSET_CLASS_ID;
import static com.gtl.mmf.service.util.IConstants.ASSET_CLASS_NAME;
import static com.gtl.mmf.service.util.IConstants.CONTEXT_PATH;
import static com.gtl.mmf.service.util.IConstants.DAY_VALUE;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.IPS_SHARED;
import static com.gtl.mmf.service.util.IConstants.NO_OF_DAYS;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ASSET_CLASS;
import static com.gtl.mmf.service.util.IConstants.TIME_LINE_OPTED;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("assetbreakdowncontroller")
@Scope("view")
public class AssetBreakDownController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.AssetBreakDownController");
    private static final String PORTFOLIO_DETAILS = "/pages/portfolio_details?faces-redirect=true";
    private CustomerPortfolioVO customerPortfolioVO;
    private List<PortfolioSecurityVO> portfolioSecurityVOlist;
    private String reDirectionUrl;
    private String assetClassName;
    private Integer noOfdays;
    private String timePeriod;
    private static final String CUSTOMER_TWR = "customerTWR";
    private static final String RCMDD_TWR = "recommmendedTWR";
    private Double customerTWR;
    private Double recommmendedTWR;

    @Autowired
    private IPortfolioDetailsBAO portfolioDetailsBAO;

    @PostConstruct
    public void init() {
        try {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Map<String, Object> assetdetailsMap = (Map<String, Object>) sessionMap.get(SELECTED_ASSET_CLASS);
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            if (userSessionBean.getRelationStatus() == IPS_SHARED.intValue() || userSessionBean.getRelationStatus() >= FOUR_HOUNDRED.intValue()) {
                userSessionBean.setFromURL(ASSET_BREAKDOWN);
                customerPortfolioVO = new CustomerPortfolioVO();
                portfolioSecurityVOlist = new ArrayList<PortfolioSecurityVO>();
                Integer assetClassId = Integer.parseInt(assetdetailsMap.get(ASSET_CLASS_ID).toString());
                assetClassName = assetdetailsMap.get(ASSET_CLASS_NAME).toString();
                noOfdays = Integer.parseInt(assetdetailsMap.get(NO_OF_DAYS).toString());
                timePeriod = assetdetailsMap.get(TIME_LINE_OPTED).toString();
                customerTWR = Double.parseDouble(assetdetailsMap.get(CUSTOMER_TWR).toString());
                recommmendedTWR = Double.parseDouble(assetdetailsMap.get(RCMDD_TWR).toString());
                customerPortfolioVO.setCustomerId(userSessionBean.getUserId());
                portfolioDetailsBAO.getAssetBreakdownPageDetails(customerPortfolioVO);
                portfolioSecurityVOlist = portfolioDetailsBAO.getSecuritesOfAssetClass(customerPortfolioVO, assetClassId, noOfdays);
            } else {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(CONTEXT_PATH + userSessionBean.getFromURL());
                } catch (IOException ex) {
                    Logger.getLogger(AssetBreakDownController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void onbackFromAssteBreakDown() {
        reDirectionUrl = PORTFOLIO_DETAILS;
    }

    public String getreDirectionUrl() {
        return reDirectionUrl;
    }

    public List<PortfolioSecurityVO> getPortfolioSecurityVOlist() {
        return portfolioSecurityVOlist;
    }

    public void setPortfolioSecurityVOlist(List<PortfolioSecurityVO> portfolioSecurityVOlist) {
        this.portfolioSecurityVOlist = portfolioSecurityVOlist;
    }

    public String getAssetClassName() {
        return assetClassName;
    }

    public void setAssetClassName(String assetClassName) {
        this.assetClassName = assetClassName;
    }

    public Integer getNoOfdays() {
        return noOfdays;
    }

    public void setNoOfdays(Integer noOfdays) {
        this.noOfdays = noOfdays;
    }

    public void onClickReturnDays() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        Map<String, String> assetdetailsMap = (Map<String, String>) sessionMap.get(SELECTED_ASSET_CLASS);
        Integer assetClassId = Integer.parseInt(assetdetailsMap.get(ASSET_CLASS_ID));
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        noOfdays = params.get(DAY_VALUE) == null ? ZERO : Integer.parseInt(params.get(DAY_VALUE));
        portfolioSecurityVOlist = portfolioDetailsBAO.getSecuritesOfAssetClass(customerPortfolioVO, assetClassId, noOfdays);
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public CustomerPortfolioVO getCustomerPortfolioVO() {
        return customerPortfolioVO;
    }

    public void setCustomerPortfolioVO(CustomerPortfolioVO customerPortfolioVO) {
        this.customerPortfolioVO = customerPortfolioVO;
    }
    
    public Double getCustomerTWR() {
        return customerTWR;
    }

    public void setCustomerTWR(Double customerTWR) {
        this.customerTWR = customerTWR;
    }

    public Double getRecommmendedTWR() {
        return recommmendedTWR;
    }

    public void setRecommmendedTWR(Double recommmendedTWR) {
        this.recommmendedTWR = recommmendedTWR;
    }

}
