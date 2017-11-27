/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * cretaed by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IInvestmentPolicyStmtBAO;
import static com.gtl.mmf.service.util.IConstants.BTN_CANCEL;
import static com.gtl.mmf.service.util.IConstants.BTN_SUBMIT;
import static com.gtl.mmf.service.util.IConstants.BTN_VIEW_IPS_CREATED;
import static com.gtl.mmf.service.util.IConstants.DEFALUT_REVIEW_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.HUNDRED;
import static com.gtl.mmf.service.util.IConstants.INDEX;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
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
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.CustomerRiskProfileVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.util.MMFException;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("assignPortfolioController")
@Scope("view")
public class AssignPortfolioController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.AssignPortfolioController");
    private static final String REDIRECTION_PAGE = "/pages/advisordashboard?faces-redirect=true";
    private InvestorDetailsVO investorProfile;
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

    private double asset_allocated;

    @PostConstruct
    public void afterinit() {
        try {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
            userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            if (storedValues != null) {
                investorProfile = (InvestorDetailsVO) storedValues.get(SELECTED_INVESTOR);
            }
            buttonsList = new ArrayList<ButtonDetailsVo>();
            customerRiskProfileVO = new CustomerRiskProfileVO();
            portfoliosMap = new LinkedHashMap<Integer, PortfolioVO>();
            pieChartDataVOList = new ArrayList<PieChartDataVO>();
            customerPortfolioVO = new CustomerPortfolioVO();
            securityVoList = new ArrayList<PortfolioSecurityVO>();
            investmentPolicyStmtBAO.getPortfolioAssignPageDetails(investorProfile.getRelationId(), customerRiskProfileVO,
                    portfoliosMap, pieChartDataVOList, customerPortfolioVO, securityVoList);
            customerPortfolioVO.setUserName(userSessionBean.getFirstName());

            portfolioTypeVO = LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType());
            setButtonsList(LookupDataLoader.getAdvisorButtonsList().get(investorProfile.getStatus()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void refreshPieChartData() {
        if (portfoliosMap.get(customerPortfolioVO.getPortfolioId()) == null) {
            pieChartDataVOList.clear();
            setAsset_allocated(ZERO_POINT_ZERO);
        } else {
            customerPortfolioVO.setAdvisorPortfolioStartValue(portfoliosMap.get(customerPortfolioVO.getPortfolioId()).getPortfolioValue());
            pieChartDataVOList = investmentPolicyStmtBAO.refreshPieChartData(customerPortfolioVO.getPortfolioId());
        }
    }

    public void doActions(ActionEvent event) {
        String buttonText = (String) event.getComponent().getAttributes().get("value");
        if (buttonText.equalsIgnoreCase(BTN_SUBMIT)) {
            saveAssignPortFolio();
        } else if (buttonText.equalsIgnoreCase(BTN_VIEW_IPS_CREATED)) {
            reDirectionURL = "/pages/investment_policy_statement_create?faces-redirect=true";
        } else if (buttonText.equalsIgnoreCase(BTN_CANCEL)) {
            reDirectionURL = "/pages/advisor_network_view?faces-redirect=true";
        }
    }

    private void saveAssignPortFolio() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            String error;
            if (customerPortfolioVO.getPortfolioId() <= ZERO) {
                error = "Select a portfolio ";
                fc.addMessage("assign_portfolio_form:porfoliosList", new FacesMessage(SEVERITY_ERROR, error, null));
                reDirectionURL = EMPTY_STRING;
            } else {
                if (customerPortfolioVO.getAdvisorComment().equalsIgnoreCase(DEFALUT_REVIEW_ADVISOR)) {
                    customerPortfolioVO.setAdvisorComment(EMPTY_STRING);
                }
                investmentPolicyStmtBAO.saveAssignPortFolio(customerPortfolioVO, investorProfile, userSessionBean.getFirstName(), isPortfolioChanged());
                reDirectionURL = REDIRECTION_PAGE;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MMFException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void saveCustomerReviewIPS() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            String error;
            if (customerPortfolioVO.getCustomerComment().equals(DEFALUT_REVIEW_ADVISOR)) {
                error = "Write your comments ";
                fc.addMessage("assign_portfolio_form:review", new FacesMessage(SEVERITY_ERROR, error, null));
            } else {
                investmentPolicyStmtBAO.saveAssignPortFolio(customerPortfolioVO, investorProfile, userSessionBean.getFirstName(), isPortfolioChanged());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MMFException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public Boolean isPortfolioChanged() {
//        if (customerPortfolioVO.getPortfolioIdBeforeChange() == ZERO) {
//            return Boolean.TRUE;
//        } else if (customerPortfolioVO.getPortfolioIdBeforeChange() != customerPortfolioVO.getPortfolioId()) {
//            return Boolean.TRUE;
//        } else {
//            return Boolean.FALSE;
//        }
        if (customerPortfolioVO.getCustomerPortfolioId() == ZERO) {
            return Boolean.TRUE;
        } else {
            //Review case
            return Boolean.FALSE;
        }
    }

    public String redirectionURL() {
        return reDirectionURL;
    }

    public InvestorDetailsVO getInvestorProfile() {
        return investorProfile;
    }

    public CustomerRiskProfileVO getCustomerRiskProfileVO() {
        return customerRiskProfileVO;
    }

    public PortfolioTypeVO assetClassRanges() {
        return portfolioTypeVO;
    }

    public List<PieChartDataVO> getPieChartDataVOList() {
        return pieChartDataVOList;

    }

    public String pieChartData() {
        return PortfolioAllocationChartUtil.generatePieChartData(pieChartDataVOList);
    }

    public Map<Integer, PortfolioVO> getPortfoliosmap() {
        return portfoliosMap;
    }

    public void setPortfoliosmap(Map<Integer, PortfolioVO> portfoliosMap) {
        this.portfoliosMap = portfoliosMap;
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

    public double getAsset_allocated() {
        return asset_allocated;
    }

    public void setAsset_allocated(double asset_allocated) {
        this.asset_allocated = asset_allocated;
    }

//    public Double CalculateAssetAllocated(Double allocated_fund, List<PieChartDataVO> portfolioData) {
//        double allocatedAsset = ZERO_POINT_ZERO;
//        double total_assigned_percentage = ZERO_POINT_ZERO;
//        Iterator<PieChartDataVO> it = portfolioData.iterator();
//        while (it.hasNext()) {
//            PieChartDataVO assigned_data = it.next();
//            if (!assigned_data.getLabel().equals("Cash")) {
//                total_assigned_percentage = total_assigned_percentage + assigned_data.getData();
//            }
//        }
//        allocatedAsset = (allocated_fund * total_assigned_percentage) / HUNDRED;
//        return allocatedAsset;
//    }

    public String getDebtLiquidRange() {
        return TEXT_DEBT_LIQUID;
    }
}
