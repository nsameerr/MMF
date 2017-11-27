/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IInvestmentPolicyStmtBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import com.gtl.mmf.service.vo.CustomerRiskProfileVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.QuestionResponseVO;
import com.gtl.mmf.service.util.LookupDataLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("investmentPolicyStmtControlller")
@Scope("view")
public class InvestmentPolicyStmtContriller {

    private static final int VALUE_100 = 100;
    private static final int VALUE_0 = 0;
    private static final int VALUE_25 = 25;
    private static final String PERCENTILE_TO = "% to";
    @Autowired
    private IInvestmentPolicyStmtBAO investmentPolicyStmtBAO;

    private static final String PROFILE_PAGE = "/pages/investorprofile?faces-redirect=true";
    private static final String REDIR_PAGE = "/pages/assign_portfolio?faces-redirect=true";
    private InvestorDetailsVO investorProfile;
    private List<QuestionResponseVO> questionResponseVOList;
    private CustomerRiskProfileVO customerRiskProfileVO;
    private String reDirectionURL;
    Map<String, Object> storedValues;
    private static final int CUSTOM_STYLE = 6;

    @PostConstruct
    public void afterinit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        if (storedValues != null) {
            investorProfile = (InvestorDetailsVO) storedValues.get("selectedInvestor");
        }
        questionResponseVOList = new ArrayList<QuestionResponseVO>();
        customerRiskProfileVO = new CustomerRiskProfileVO();
        investmentPolicyStmtBAO.getPageDetails(investorProfile.getCustomerId(), questionResponseVOList, customerRiskProfileVO);
    }

    public void savePolicyStatement() {
        boolean valid = true;
        FacesContext fc = FacesContext.getCurrentInstance();
        String error;
        if (!(customerRiskProfileVO.getExpReturn() >= VALUE_0 && customerRiskProfileVO.getExpReturn() <= VALUE_100)) {
            error = "Value with in the range " + VALUE_0 + " and " + VALUE_100;
            fc.addMessage("invt_plicy_stm_form:expReturn", new FacesMessage(SEVERITY_ERROR, error, null));
            valid = false;
        }
        if (!(customerRiskProfileVO.getLiquidityRetd() >= VALUE_0 && customerRiskProfileVO.getLiquidityRetd() <= VALUE_100)) {
            error = "Value with in the range " + VALUE_0 + " and " + VALUE_100;
            fc.addMessage("invt_plicy_stm_form:liquidityRetd", new FacesMessage(SEVERITY_ERROR, error, null));
            valid = false;
        }
        if (!(customerRiskProfileVO.getIncomeRetd() >= VALUE_0 && customerRiskProfileVO.getIncomeRetd() <= VALUE_100)) {
            error = "Value with in the range " + VALUE_0 + " and " + VALUE_100;
            fc.addMessage("invt_plicy_stm_form:incomeRetd", new FacesMessage(SEVERITY_ERROR, error, null));
            valid = false;
        }
        if (!(customerRiskProfileVO.getInvestHorizon() >= VALUE_0 && customerRiskProfileVO.getInvestHorizon() <= VALUE_25)) {
            error = "Value with in the range " + VALUE_0 + " and " + VALUE_25;
            fc.addMessage("invt_plicy_stm_form:investHorizon", new FacesMessage(SEVERITY_ERROR, error, null));
            fc.renderResponse();
            valid = false;
        }
        if (valid) {
            investmentPolicyStmtBAO.saveInvestmentPolicy(customerRiskProfileVO);
            ((InvestorDetailsVO) storedValues.get(SELECTED_INVESTOR)).setStatus(EnumAdvisorMappingStatus.IPS_CREATED.getValue());
            reDirectionURL = REDIR_PAGE;
        } else {
            reDirectionURL = EMPTY_STRING;
        }
    }

    public InvestorDetailsVO getInvestorProfile() {
        return investorProfile;
    }

    public String profileNameLink() {
        return PROFILE_PAGE;
    }

    public String redirectionPage() {
        return reDirectionURL;
    }

    public void resetPage() {
        customerRiskProfileVO.setExpReturn(VALUE_0);
        customerRiskProfileVO.setIncomeRetd(VALUE_0);
        customerRiskProfileVO.setLiquidityRetd(VALUE_0);
        customerRiskProfileVO.setInvestHorizon(VALUE_0);
        customerRiskProfileVO.setBenchMark(VALUE_0);
        customerRiskProfileVO.setReviewFreequency(VALUE_0);
    }

    public List<QuestionResponseVO> getResponseQuestions() {
        return questionResponseVOList;
    }

    public Map<Integer, PortfolioTypeVO> getProtfolioTypesData() {
        return LookupDataLoader.getPortfolioTypes();
    }
    
    public Map<Integer, PortfolioTypeVO> getProtfolioStyleData() {
        return LookupDataLoader.getPortfolioStyle();
    }
    
    public Map<Integer, PortfolioTypeVO> getProtfolioSizeData() {
        return LookupDataLoader.getPortfolioSize();
    }

    public Map<String, Integer> getReviewFreequencyData() {
        return LookupDataLoader.getReviewFreequency();
    }

    public Map<Integer, String> getBenchMarksdata() {
        return LookupDataLoader.getBenchMark();
    }

    public CustomerRiskProfileVO getCustomerRiskProfileVO() {
        return customerRiskProfileVO;
    }

    public void setCustomerRiskProfileVO(CustomerRiskProfileVO customerRiskProfileVO) {
        this.customerRiskProfileVO = customerRiskProfileVO;
    }

    public String getEquityRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_equity() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_equity() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_equity() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_equity() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_equity() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_equity() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getDebtRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_debt() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_debt() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_debt() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_debt() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_debt() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_debt() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getFORange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_fo() >= VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_fo() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_fo() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_fo() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_fo() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_fo() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getCashRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_cash() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_cash() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_cash() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_cash() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_cash() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_cash() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getGoldRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_gold() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_gold() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_gold() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_gold() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_gold() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_gold() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getInternationalRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_international() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_international() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_international() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_international() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_international() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_international() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getIndexRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_equity() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_equity() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_equity() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_equity() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_equity() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_equity() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getMidcapRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_midcap() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_midcap() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_midcap() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_midcap() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_midcap() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_midcap() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getEquityDiversifiedRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_diversifiedEquity() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_diversifiedEquity() > VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_diversifiedEquity() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_diversifiedEquity() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_diversifiedEquity() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_diversifiedEquity() + "%";
        } else {
            return EMPTY_STRING;
        }
    }

    public String getBalancedRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_balanced()>= VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_balanced()> VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_balanced() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_balanced() + "%";
        } else {
            return EMPTY_STRING;
        }
    }
    
    public String getMFRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_mutual_fund()>= VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_mutual_fund()> VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_mutual_fund() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_mutual_fund() + "%";
        } else {
            return EMPTY_STRING;
        }
    }
    
    public String getDebtLiquidRange() {
        if (LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_debt_liquid() > VALUE_0
                && LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_debt_liquid()> VALUE_0) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_debt_liquid() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_debt_liquid() + "%";
        } else if (customerRiskProfileVO.getPortfolioStyle() == CUSTOM_STYLE) {
            return LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_min_debt_liquid() + PERCENTILE_TO
                    + LookupDataLoader.getPortfolioTypes().get(customerRiskProfileVO.getPortfolioType()).getRange_max_debt_liquid() + "%";
        } else {
            return EMPTY_STRING;
        }
    }
    
    /**
     * When portfolio style is changed by advisor.
     * @param event 
     */
    public void portflioStyleChangeListner(AjaxBehaviorEvent event) {
        Integer portfolioStyleSelected = customerRiskProfileVO.getPortfolioStyle();
        Integer portfolioSizeSelected = customerRiskProfileVO.getPortfolioSize();
        Integer portfolioTypeId = investmentPolicyStmtBAO.getPortfolioByStyleAndSize(portfolioStyleSelected, portfolioSizeSelected);
        customerRiskProfileVO.setPortfolioType(portfolioTypeId);
    } 
}
