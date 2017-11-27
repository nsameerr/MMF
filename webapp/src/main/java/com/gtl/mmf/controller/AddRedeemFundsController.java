/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IAddRedeemFundsBAO;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.entity.CashFlowTb;
import static com.gtl.mmf.service.util.IConstants.CONTEXT_PATH;
import static com.gtl.mmf.service.util.IConstants.DEVELOPMENT;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.IPS_SHARED;
import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import static com.gtl.mmf.service.util.IConstants.MMF_OMS_ENABLED;
import static com.gtl.mmf.service.util.IConstants.PRODUCTION;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.SymbolListUtil;
import com.gtl.mmf.service.vo.AddRedeemFundsVO;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This controller is used for handling add/redeem transactions
 *
 * @author 07958
 */
@Component("addRedeemFundsController")
@Scope("view")
public class AddRedeemFundsController {

    private static final Logger LOGGER = Logger
            .getLogger("com.gtl.mmf.controller.AddRedeemFundsController");
    private static final String ADD_REDEEM = "AddRedeem";
    private static final String MSG_SELECT_ADD_REDEEM = "Select add or redeem.";
    private static final String MSG_ADDED_FUND_EXCEDE_BALANCE = "Please add fund within your balance fund available";
    private static final String MSG_REDEEMED_FUND_EXCEDED = "Please redeem fund within you portfolio value available";
    private static final String MSG_INSERT_A_VALID_AMOUNT = "Please insert a valid amount.";
    @Autowired
    private IAddRedeemFundsBAO addRedeemFundsBAO;

    @Autowired
    private IRebalancePortfolioBAO rebalancePortfolioBAO;

    private int customerId;
    private AddRedeemFundsVO addRedeemFundsVO;
    private String redirectTo;
    private Set<SymbolRecordVO> watchLists;
    private String marketData;
    private Double balanceAvailable;
    private boolean calculate;
    private boolean holiday_Venue;
    //Message if weight of all asset class iz zero
    private static final String MSG_IS_MODIFYABLE = "Modification not allowed if current weight is zero for all asset class";

    /**
     * This method process the data that need to displayed on add/redeem page
     * before displaying the page
     */
    @PostConstruct
    public void afterInit() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            UserSessionBean userSessionBean = (UserSessionBean) externalContext
                    .getSessionMap().get(USER_SESSION);
            //Reading CustomerId from Session
            if (userSessionBean != null) {
                if (userSessionBean.getRelationStatus() == IPS_SHARED.intValue() || userSessionBean.getRelationStatus() >= FOUR_HOUNDRED.intValue()) {
                    this.customerId = userSessionBean.getUserId();
                    userSessionBean.setFromURL("/pages/addredeemfunds?faces-redirect=true");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(CONTEXT_PATH + userSessionBean.getFromURL());
                }
            }
            LOGGER.log(Level.INFO,
                    "Loading add or redeem funds investor id : {0}", customerId);

            //Loading informations to be displayed in the add/redeem page page when page is loading
            addRedeemFundsVO = addRedeemFundsBAO
                    .getAddRedeemFundDetails(customerId);

            // Create watch list.
            this.generateWatchList();

            //Updating asset details
            this.updateWatches();

            //Performing intial calculations with current allocated fund and portfolio
            // this.calculateTransactionDetails();
            //Setting final portfolio value
            addRedeemFundsVO.setFinalValue((double) addRedeemFundsVO
                    .getCurrentValue());

            //Setting cash balance as balance amount
            balanceAvailable = addRedeemFundsVO.getCashAmount().doubleValue();
            this.calculate = false;
            holiday_Venue = LookupDataLoader.isHolyday_NSE() && LookupDataLoader.isHolyday_BSE();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            redirectTo = "/pages/addredeemfunds.xhtml";
        }
    }

    /**
     * This method is used to update Customer portfolio details with fund
     * added/redeemed 1.This will Update security price for each security in
     * portfolio. 2.Update total weight of allocated fund utilized by each asset
     * class after add/redeem fund 3.Calculate new transaction details after
     * fund added/redeemed
     */
    public void updateWatches() {
        this.calculateTransactionDetails();
    }

    /**
     * Setting flag addFund based on combo box select(add/reduce)
     */
    public void switchAddRedeem() {
        Boolean addFund = Boolean.valueOf(getRequestParameterMap().get(
                ADD_REDEEM));
        LOGGER.log(Level.INFO, "Add fund status = {0}", addFund);
        this.addRedeemFundsVO.setAddFund(addFund);
    }

    /**
     * Caculating transaction details after add/reduce
     */
    public void calculateFundAfterAddRedeem() {
        /**
         * In case of add/redeem fund case switchAddRedeem() is triggered on
         * choosing ADD/REDEEM option from UI. ADD =
         * addRedeemFundsVO.setAddFund(true) REDEEM =
         * addRedeemFundsVO.setAddFund(false) In order to manage Sell portfolio
         * case, set addFund to false
         */
        this.addRedeemFundsVO.setAddFund(false);
        LOGGER.log(
                Level.INFO,
                "Add or redeem fund triggered.\n New fund inserted is {0}. addFund = {1}",
                new Object[]{addRedeemFundsVO.getAddRedeemFund(),
                    addRedeemFundsVO.getAddFund()});
        if (this.isValidAllcation()) {

            /*For calculating new transction details for only for fund added/reduced. 
             When calculate is true then only transcatin details will be calculated*/
            this.setCalculate(true);
            this.calculateTransactionDetails();
            addRedeemFundsVO.setFinalValue(this.getFinalValue());
            setBalanceAvailable(this.getNewCashAmount(addRedeemFundsVO));
        }
    }

    /**
     * This method will send an transaction order request for the amount
     * added/reduced and add an entry into db for this add/reduce action.
     *
     * @parem omsEnabled - used bypass OMS.
     * @param event trigger when current allocation needs to modify based on
     * fund added/reduced
     */
    public void doActionModifyAllocation(ActionEvent event) {
        LOGGER.info("Add/Redeem fund - modifying allocation.");
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        if (this.isValidAllcation()) {
            addRedeemFundsVO.setBalanceFundAvailable(this.getBalanceFundAvailable());

            /*
             IN add reedem when fund is added that will be directly refelected on cash balance.
             In case of reedem
             case 1 when OMS Enabled:
             We are sending aseeling order for the amount invester specified. so no entry is 
             added to log table. Entry is added only when the user reedem cash amount.
             case 2 OMS not enabled Entry is added to log for reedem only when selling oreder is 
             submitted
            
             */
//            addRedeemFundsBAO.modifyAllocation(addRedeemFundsVO);
            // Redirecting based on omsEnabled flag
            if (!environment.equalsIgnoreCase(DEVELOPMENT)) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

//                if (addRedeemFundsVO.getAddFund()) {
//                    addRedeemFundsBAO.logAddRedeemFundDetails(addRedeemFundsVO);
//                }
                // Setting a session variable to know the transaction is from add/Reduce      
                session.setAttribute("AddReedemOrderExecution", true);
                session.setAttribute("AddRedeemFundsVO", addRedeemFundsVO);
                redirectTo = "/pages/oms_login?faces-redirect=true";
            } else {
                // Creating Dummy OMS Order         
                OMSOrderVO omsOrderVO = new OMSOrderVO();
                omsOrderVO.setOmsUserid("Dummy_" + addRedeemFundsVO.getCustomerId());
                addRedeemFundsVO.setOmsLoginId("Dummy_" + addRedeemFundsVO.getCustomerId());
                addRedeemFundsBAO.logAddRedeemFundDetails(addRedeemFundsVO);
                //Calculating new transaction details for new fund added/reduced
                this.calculateTransactionDetails();

                // Setting Cash_balance for Executing order
                addRedeemFundsVO.setCashAmount(this.getNewCashAmount(addRedeemFundsVO).floatValue());

                //Sending Order for executing        
                addRedeemFundsVO.setFromAddReedem(true);
                rebalancePortfolioBAO.executeSecurityOrders(addRedeemFundsVO, addRedeemFundsVO.getPortfolioDetailsVOs(), omsOrderVO, true);
                redirectTo = "/pages/investordashboard?faces-redirect=true";
            }
        }
    }

    public void doActionCancel(ActionEvent event) {
        redirectTo = "/pages/investordashboard?faces-redirect=true";
    }

    public String actionRedirect() {
        return redirectTo;
    }

    /**
     * Check for valid allocation of fund added
     *
     * @return boolean if amount added/reduced is valid
     */
    private boolean isValidAllcation() {
        boolean validAllocation = true;
        // Validate 1. Add/Redeem selected
        if (addRedeemFundsVO.getAddFund() == null) {
            validAllocation = false;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            MSG_SELECT_ADD_REDEEM, MSG_SELECT_ADD_REDEEM));
        } else {
            if (addRedeemFundsVO.getAddRedeemFund() == ZERO_POINT_ZERO) {
                validAllocation = false;
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                MSG_INSERT_A_VALID_AMOUNT,
                                MSG_INSERT_A_VALID_AMOUNT));
            } else {
                // Validate 3. Redeemed fund <= Current portfolio value
                if (!addRedeemFundsVO.getAddFund()
                        && addRedeemFundsVO.getAddRedeemFund() > addRedeemFundsVO
                        .getCurrentValue()) {
                    validAllocation = false;
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    MSG_REDEEMED_FUND_EXCEDED,
                                    MSG_REDEEMED_FUND_EXCEDED));
                }
            }
        }
        return validAllocation;
    }

    private Double getBalanceFundAvailable() {
        return addRedeemFundsVO.getAddFund() ? addRedeemFundsVO.getAddRedeemFund() > addRedeemFundsVO
                .getBalanceFundAvailable() ? 0.0 : addRedeemFundsVO
                .getTotalAvailableFund() - addRedeemFundsVO.getAddRedeemFund()
                : addRedeemFundsVO.getTotalAvailableFund()
                + addRedeemFundsVO.getAddRedeemFund();
    }

    /**
     * Calculating final portfolio value
     *
     * @return Double 1)ADD. FinalValue = CurrentValue + Fund_Added 2)reduce.
     * FinalValue = CurrentValue - Fund_Reduced
     */
    private Double getFinalValue() {
        Double totalTansactAmount = addRedeemFundsVO.getTotalTransAmt() == null
                ? ZERO_POINT_ZERO : addRedeemFundsVO.getTotalTransAmt();
        return addRedeemFundsVO.getAddFund() ? addRedeemFundsVO
                .getCurrentValue() + totalTansactAmount
                : addRedeemFundsVO.getCurrentValue()
                - totalTansactAmount;
    }

    private void generateWatchList() {
        watchLists = new HashSet<SymbolRecordVO>();
        try {
            for (PortfolioDetailsVO portfolioDetailsVO : addRedeemFundsVO
                    .getPortfolioDetailsVOs()) {
                watchLists.addAll(SymbolListUtil.getWatchesList(
                        portfolioDetailsVO.getSecurities(),
                        portfolioDetailsVO.getAssetId()));
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }

    }

    /**
     * Calculating Transaction details for investor after add/redeem. 1.This
     * will Update security price for each security in portfolio. 2.Update total
     * weight of allocated fund utilized by each asset class after add/redeem
     * fund 3.Calculate new transaction details after fund added/redeemed
     */
    private void calculateTransactionDetails() {
        PortfolioUtil.updateCustomerPortfolioMarketPrice(addRedeemFundsVO);
        PortfolioUtil.updateValueAndWeight(addRedeemFundsVO);
        //When press the calculate button
        if (getCalculate()) {
            addRedeemFundsVO.setCashBalanceAmount(this.getNewCashAmount(addRedeemFundsVO));
            // calculating new transactin details for add/reduce fund
            PortfolioUtil.updateTransactionDetails(addRedeemFundsVO);
        }
    }

    private Map<String, String> getRequestParameterMap() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
    }

    public AddRedeemFundsVO getAddRedeemFundsVO() {
        return addRedeemFundsVO;
    }

    public void setAddRedeemFundsVO(AddRedeemFundsVO addRedeemFundsVO) {
        this.addRedeemFundsVO = addRedeemFundsVO;
    }

    public String getMarketData() {
        return marketData;
    }

    public void setMarketData(String marketData) {
        this.marketData = marketData;
    }

    public Double getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(Double balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public boolean getCalculate() {
        return calculate;
    }

    public void setCalculate(boolean calculate) {
        this.calculate = calculate;
    }

    /**
     *
     * @param addRedeemFundsVO - contains fund added/reduced information with
     * customer portfolio details
     * @return Double adding fund:cash amount = cash_amount+fund added reduce
     * fund :cash_amount = cash_amount (cash_amount is calculated only After
     * executing order in case of reduce fund )
     */
    private Double getNewCashAmount(AddRedeemFundsVO addRedeemFundsVO) {
        return addRedeemFundsVO.getAddFund() ? addRedeemFundsVO.getCashAmount()
                + addRedeemFundsVO.getAddRedeemFund() : addRedeemFundsVO.getCashAmount();
    }

    /**
     * This method is used to check whether an order is already place in OMS.
     *
     * @return boolean This method returns true if no order is placed and
     * calculate flag is true.
     */
    public boolean isexecuteportfolio() {
        return !(addRedeemFundsVO.getOrderStatus() == FOUR_HOUNDRED) && this.getCalculate();
    }

    public boolean isHoliday_Venue() {
        return holiday_Venue;
    }

    public void setHoliday_Venue(boolean holiday_Venue) {
        this.holiday_Venue = holiday_Venue;
    }

    /**
     * This method is used to check whether if current weight of all asset class
     * is zero.
     *
     * @return true if any of the asset class has weight > 0 else returns false.
     */
    public boolean isModifyable() {
        Boolean status = false;
        if (addRedeemFundsVO.getPortfolioDetailsVOs() != null) {
            for (PortfolioDetailsVO portfoliovo : addRedeemFundsVO.getPortfolioDetailsVOs()) {
                if (!portfoliovo.getAssetClassName().equalsIgnoreCase(TEXT_CASH)
                        && portfoliovo.getCurrentWeight().doubleValue() != ZERO_POINT_ZERO) {
                    status = true;
                    return status;
                }
            }
        }
        if (!status) {
            addRedeemFundsVO.setMessage(MSG_IS_MODIFYABLE);
        }
        return status;
    }

    /**
     * For testing purpose fund is added to CashFlowTb daily pay-in and pay-out
     * summary table. this value is used to update the allocation fund in tables
     * 1. customer_advisor_mapping_tb 2. customer_portfolio_tb 3.
     * master_customer_tb
     *
     * @param event
     */
    public void doActionAddFund(ActionEvent event) {
        if (addRedeemFundsVO != null && addRedeemFundsVO.getAddFund() != null) {
            if (addRedeemFundsVO.getAddRedeemFund().doubleValue() == ZERO_POINT_ZERO) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                MSG_INSERT_A_VALID_AMOUNT,
                                MSG_INSERT_A_VALID_AMOUNT));
            } else {
                CashFlowTb cashFlowTb = new CashFlowTb();
                Boolean addFund = addRedeemFundsVO.getAddFund();
                if (addFund) {
                    //Fund is added
                    cashFlowTb.setPayIn(new BigDecimal(addRedeemFundsVO.getAddRedeemFund()));
                } else {
                    //Fund is redeemed
                    cashFlowTb.setPayOut(new BigDecimal(addRedeemFundsVO.getAddRedeemFund()));
                }
                cashFlowTb.setTradeCode(addRedeemFundsVO.getOmsLoginId());
                cashFlowTb.setTranDate(new Date());
                addRedeemFundsBAO.addFundDetails(cashFlowTb);
                redirectTo = "/pages/investordashboard?faces-redirect=true";
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            MSG_SELECT_ADD_REDEEM,
                            MSG_SELECT_ADD_REDEEM));
        }

    }

}
