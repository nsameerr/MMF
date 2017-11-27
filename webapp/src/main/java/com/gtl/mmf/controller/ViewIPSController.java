/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IInvestmentPolicyStmtBAO;
import static com.gtl.mmf.service.util.IConstants.BTN_ACCEPT;
import static com.gtl.mmf.service.util.IConstants.BTN_CANCEL;
import static com.gtl.mmf.service.util.IConstants.BTN_REVIEW;
import static com.gtl.mmf.service.util.IConstants.DEFALUT_IPS_REVIEW_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.INDEX;
import static com.gtl.mmf.service.util.IConstants.IPS_SHARED;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TEXT_BALANCED;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT_LIQUID;
import static com.gtl.mmf.service.util.IConstants.TEXT_EQUITY;
import static com.gtl.mmf.service.util.IConstants.TEXT_FNO;
import static com.gtl.mmf.service.util.IConstants.TEXT_GOLD;
import static com.gtl.mmf.service.util.IConstants.TEXT_INTERNATIONAL;
import static com.gtl.mmf.service.util.IConstants.TEXT_MUTUAL_FUND;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.CustomerRiskProfileVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("viewIPSController")
@Scope("view")
public class ViewIPSController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.ViewIPSController");
    private static final String REDIRECT_PAGE = "/pages/investordashboard?faces-redirect=true";
    private static final String REDIRECT_ON_ACCEPT = "/pages/view_portfolio?faces-redirect=true";
    private AdvisorDetailsVO advisorDetailsVO;
    private CustomerRiskProfileVO customerRiskProfileVO;
    private Map<Integer, PortfolioVO> portfoliosMap;
    private PortfolioTypeVO portfolioTypeVO;
    private List<PieChartDataVO> pieChartDataVOList;
    private CustomerPortfolioVO customerPortfolioVO;
    private List<ButtonDetailsVo> buttonsList;
    private String reDirectionURL;
    private UserSessionBean userSessionBean;
    private List<PortfolioSecurityVO> securityVoList;

    @Autowired
    private IInvestmentPolicyStmtBAO investmentPolicyStmtBAO;

    @PostConstruct
    public void afterinit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        userSessionBean.setFromURL("/pages/investment_policy_statement_view?faces-redirect=true");
        if (storedValues != null) {
            advisorDetailsVO = (AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR);
        }
        customerRiskProfileVO = new CustomerRiskProfileVO();
        portfoliosMap = new LinkedHashMap<Integer, PortfolioVO>();
        pieChartDataVOList = new ArrayList<PieChartDataVO>();
        customerPortfolioVO = new CustomerPortfolioVO();
        securityVoList = new ArrayList<PortfolioSecurityVO>();
        investmentPolicyStmtBAO.customerViewIPS(advisorDetailsVO.getRelationId(), customerRiskProfileVO,
                portfoliosMap, pieChartDataVOList, customerPortfolioVO, securityVoList);
        customerPortfolioVO.setUserName(userSessionBean.getFirstName());
        portfolioTypeVO = LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType());
        setButtonsList(LookupDataLoader.getInvestorButtonsList().get(advisorDetailsVO.getStatus()));
    }

    private void customerReviewIPS() {
        final FacesContext fContext = FacesContext.getCurrentInstance();
        String error;
        if (customerPortfolioVO.getCustomerComment().equals(DEFALUT_IPS_REVIEW_INVESTOR)) {
            error = "Write your comments ";
            fContext.addMessage("ips_view_form:review", new FacesMessage(SEVERITY_ERROR, error, null));
            reDirectionURL = EMPTY_STRING;
        } else {
            try {
                investmentPolicyStmtBAO.customerReviewIPS(customerPortfolioVO, advisorDetailsVO, userSessionBean.getFirstName());
                reDirectionURL = REDIRECT_PAGE;
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        }
    }

    private void customerAcceptIPS() {
        final FacesContext fContext = FacesContext.getCurrentInstance();
        String error;
        if (customerPortfolioVO.getCustomerComment().equals(DEFALUT_IPS_REVIEW_INVESTOR)) {
            try {
                investmentPolicyStmtBAO.customerAcceptIPS(customerPortfolioVO, advisorDetailsVO, userSessionBean.getFirstName());
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
            reDirectionURL = REDIRECT_ON_ACCEPT;
        } else {
            error = "Remove your comments ";
            fContext.addMessage("ips_view_form:review", new FacesMessage(SEVERITY_ERROR, error, null));
            reDirectionURL = EMPTY_STRING;
        }
    }

    public void doActions(ActionEvent event) {
        String buttonText = (String) event.getComponent().getAttributes().get("value");
        if (buttonText.equalsIgnoreCase(BTN_ACCEPT)) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
            customerAcceptIPS();
            userSessionBean.setRelationId(customerPortfolioVO.getRelationId() == null ? 0 : customerPortfolioVO.getRelationId());
            userSessionBean.setRelationStatus(IPS_SHARED);
            session.setAttribute(USER_SESSION, userSessionBean);
        } else if (buttonText.equalsIgnoreCase(BTN_REVIEW)) {
            customerReviewIPS();
        } else if (buttonText.equalsIgnoreCase(BTN_CANCEL)) {
            //doActionCancel();
        }
    }

    public String redirectionURL() {
        return reDirectionURL;
    }

    public CustomerRiskProfileVO getCustomerRiskProfileVO() {
        return customerRiskProfileVO;
    }

    public PortfolioTypeVO assetClassRanges() {
        return portfolioTypeVO;
    }

    public String pieChartData() {
        return PortfolioAllocationChartUtil.generatePieChartData(pieChartDataVOList);
    }

    public Map<Integer, PortfolioVO> getPortfoliosmap() {
        return portfoliosMap;
    }

    public List<PieChartDataVO> getPieChartDataVOList() {
        return pieChartDataVOList;
    }

    public CustomerPortfolioVO getCustomerPortfolioVO() {
        return customerPortfolioVO;
    }

    public void setCustomerPortfolioVO(CustomerPortfolioVO customerPortfolioVO) {
        this.customerPortfolioVO = customerPortfolioVO;
    }

    public List<ButtonDetailsVo> getButtonsList() {
        return buttonsList;
    }

    public void setButtonsList(List<ButtonDetailsVo> buttonsList) {
        this.buttonsList = buttonsList;
    }

    public String getEquityRange() {
        return TEXT_EQUITY;
    }

    public String getDebtRange() {
        return TEXT_DEBT;
    }

    public String getFORange() {
        return TEXT_FNO;
    }

    public String getCashRange() {
        return TEXT_CASH;
    }

    public String getGoldRange() {
        return TEXT_GOLD;
    }

    public String getInternationalRange() {
        return TEXT_INTERNATIONAL;
    }

    public String getIndex() {
        return INDEX;
    }

    public String getMidcap() {
        return MIDCAP;
    }

    public String getDiversifiedEquity() {
        return EQUITY_DIVERSIFIED;
    }

    public String getMutualFund() {
        return TEXT_MUTUAL_FUND;
    }

    public String getBalanced() {
        return TEXT_BALANCED;
    }

    public List<PortfolioSecurityVO> getSecurityVoList() {
        return securityVoList;
    }

    public String getDebtLiquidRange() {
        return TEXT_DEBT_LIQUID;
    }
}
