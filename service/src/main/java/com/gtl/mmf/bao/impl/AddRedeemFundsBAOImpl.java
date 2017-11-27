/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IAddRedeemFundsBAO;
import com.gtl.mmf.dao.IAddRedeemFundsDAO;
import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.AddRedeemFundsVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class for Add/Redeem transaction Handling
 *
 * @author 07958
 *
 * @author 07958
 */
public class AddRedeemFundsBAOImpl implements IAddRedeemFundsBAO {

    private static final String CLOSE_INDEX = "CLOSE_INDEX";
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.AddRedeemFundsBAOImpl");
    @Autowired
    private IAddRedeemFundsDAO addRedeemFundsDAO;

    /**
     * Updating investor allocation amount details after add/reduce
     *
     * @param addRedeemFundsVO Contains Add/reduce Fund Details.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void modifyAllocation(AddRedeemFundsVO addRedeemFundsVO) {

        /*Allocated fund calculation
         Option 1.adding 
         fundallocated fund =  allocated fund+ Fund_ADDED 
        
         Option 2.reduce Fund
         allocated fund =  allocated fund(After reedem fund only value is reduced from allocated fund)*/
        Double newAllocatedFund = this.getNewAllocatedFund(addRedeemFundsVO);

        /*Cash balance calculation
         Option 1 fund added
         Total cah balance = available cash balance + fund added
        
         Option 2 fund reduce
         Total cah balance  will get from Backoffce (this data will get during EOD process.
         Cash balance is calculated only After executing order in case of reduce fund)*/
        Double newCashAmount = this.getNewCashAmount(addRedeemFundsVO);
        Double newBalanceAmountAvailable = this.getNewBalanceAmountAvailable(addRedeemFundsVO);
        // Update allocated fund : Customer advisor mapping
        addRedeemFundsDAO.updateCustomerMappingAllcation(newAllocatedFund, addRedeemFundsVO.getRelationId());
        // Update cash : Customer portfolio
        addRedeemFundsDAO.updateCustomerPortfolioAllcation((float) newCashAmount.doubleValue(),
                addRedeemFundsVO.getCustomerPortfolioId(), newAllocatedFund);
        Double totalAmount = newAllocatedFund;//+newBalanceAmountAvailable;

        // Update available fund : master customer
        addRedeemFundsDAO.updateAvailableFund(newBalanceAmountAvailable, totalAmount, addRedeemFundsVO.getCustomerId());
    }

    /**
     * Inserting add/reduce information into addRedeemLog_tb
     *
     * @param addRedeemFundsVO Contains Add/reduce Fund Details to be saved.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void logAddRedeemFundDetails(AddRedeemFundsVO addRedeemFundsVO) {

        // Insert into log table
        addRedeemFundsDAO.logAddRedeemFundDetails(this.getAddRedeemLogTb(addRedeemFundsVO));
    }

    /**
     * Loading informations to be displayed in the add/redeem page page when
     * page is loading
     *
     * @param customerId - Investor id which is used to collect informations to
     * be displayed in page.
     * @return - AddRedeemFundsVO - Contain ,Current Portfolio value,Allocated
     * fund etc
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public AddRedeemFundsVO getAddRedeemFundDetails(int customerId) {
        LOGGER.log(Level.INFO, "Getting add reedeem fund details customer id : {0}", customerId);
        CustomerPortfolioTb customerPortfolioTb = addRedeemFundsDAO.getCustomerPortfolio(customerId);

        // Getting add or redeem fund details.
        AddRedeemFundsVO addRedeemFundsVO = new AddRedeemFundsVO();
        addRedeemFundsVO.setTotalAvailableFund(customerPortfolioTb.getMasterCustomerTb().getTotalAvailableFunds());
        addRedeemFundsVO.setBalanceFundAvailable(customerPortfolioTb.getMasterCustomerTb().getTotalAvailableFunds());
        addRedeemFundsVO.setCustomerId(customerPortfolioTb.getMasterCustomerTb().getCustomerId());
        addRedeemFundsVO.setBenchmarkIndexId(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getId());
        addRedeemFundsVO.setBenchmarkIndexName(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
        addRedeemFundsVO.setCurrentValue(customerPortfolioTb.getCurrentValue() == null ? 0f : customerPortfolioTb.getCurrentValue());
        addRedeemFundsVO.setFinalValue(customerPortfolioTb.getCurrentValue() == null
                ? 0d : customerPortfolioTb.getCurrentValue().doubleValue());
        // Getting portfolio details.
        addRedeemFundsVO.setCustomerPortfolioId(customerPortfolioTb.getCustomerPortfolioId());
        addRedeemFundsVO.setPortfolioId(customerPortfolioTb.getPortfolioTb().getPortfolioId());
        addRedeemFundsVO.setPorfolioStyleName(customerPortfolioTb.getPortfolioTb().getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyle());
        addRedeemFundsVO.setBenchmarkName(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
        addRedeemFundsVO.setPortfolioName(customerPortfolioTb.getPortfolioTb().getPortfolioName());
        addRedeemFundsVO.setNoreBalanceStatus(customerPortfolioTb.getNoRebalanceStatus());
        addRedeemFundsVO.setAlocatedFunds(customerPortfolioTb.getCustomerAdvisorMappingTb().getAllocatedFunds());
        addRedeemFundsVO.setRebalanceRequired(customerPortfolioTb.getRebalanceRequired());
        addRedeemFundsVO.setLastExeDate(customerPortfolioTb.getLastExecutionUpdate());
        addRedeemFundsVO.setRelationId(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationId());
        addRedeemFundsVO.setCashAmount(customerPortfolioTb.getCashAmount()+ customerPortfolioTb.getBlockedCash());
        addRedeemFundsVO.setOrderStatus(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationStatus());
        addRedeemFundsVO.setOmsLoginId(customerPortfolioTb.getOmsLoginId()== null? EMPTY_STRING : customerPortfolioTb.getOmsLoginId());
        Set<CustomerPortfolioDetailsTb> customerPortfolioDetailsTbs = customerPortfolioTb.getCustomerPortfolioDetailsTbs();
        // Order to show in ui
        TreeSet<CustomerPortfolioDetailsTb> customerPortfolioDetailsSorted = new TreeSet<CustomerPortfolioDetailsTb>(new ComparatorImpl());
        customerPortfolioDetailsSorted.addAll(customerPortfolioDetailsTbs);
        addRedeemFundsVO.setPortfolioDetailsVOs(getCustomerportfolioDetails(customerPortfolioDetailsSorted));
        return addRedeemFundsVO;
    }

    /**
     * Setting customer portfolio security Details
     *
     * @param customerPortfolioSecuritiesTbs -Contain details about Securities
     * in the Portfolio Assigned to the investor.
     * @return List<PortfolioSecurityVO> - Returning a list contains
     * informations about individual securities in the portfolio.
     */
    private List<PortfolioSecurityVO> getPorfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : customerPortfolioSecuritiesTbs) {
            if (customerPortfolioSecuritiesTb.getStatus() || customerPortfolioSecuritiesTb.getExeUnits().doubleValue() > ZERO) {
                PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
                portfolioSecurityVO.setCustomerPortfolioId(customerPortfolioSecuritiesTb.getCustomerPortfolioTb().getCustomerPortfolioId());
                portfolioSecurityVO.setPortfolioId(customerPortfolioSecuritiesTb.getPortfolioTb().getPortfolioId());
                portfolioSecurityVO.setPortfolioDetailsId(customerPortfolioSecuritiesTb.getPortfolioDetailsTb().getPortfolioDetailsId());
                portfolioSecurityVO.setPortfolioSecurityId(customerPortfolioSecuritiesTb.getPortfolioSecuritiesTb().getPortfolioSecuritiesId());
                portfolioSecurityVO.setAssetClass(customerPortfolioSecuritiesTb.getMasterAssetTb().getAssetName());
                portfolioSecurityVO.setAssetClassId(customerPortfolioSecuritiesTb.getMasterAssetTb().getId());
                portfolioSecurityVO.setSecurityId(customerPortfolioSecuritiesTb.getSecurityId());
                portfolioSecurityVO.setScripDecription(customerPortfolioSecuritiesTb.getSecurityDescription());
                portfolioSecurityVO.setNewAllocation(customerPortfolioSecuritiesTb.getNewAllocation() == null ? ZERO : customerPortfolioSecuritiesTb.getNewAllocation().doubleValue());
                portfolioSecurityVO.setNewToleranceNegativeRange(customerPortfolioSecuritiesTb.getNewToleranceNegativeRange());
                portfolioSecurityVO.setNewTolerancePositiveRange(customerPortfolioSecuritiesTb.getNewTolerancePositiveRange());
                portfolioSecurityVO.setCurrentPrice(0.0);
                portfolioSecurityVO.setStatus(customerPortfolioSecuritiesTb.getStatus());
                portfolioSecurityVO.setCurrentUnits(customerPortfolioSecuritiesTb.getCurrentUnits());
                portfolioSecurityVO.setExecutedUnits(customerPortfolioSecuritiesTb.getExeUnits().doubleValue());
                portfolioSecurityVO.setInitialPrice(customerPortfolioSecuritiesTb.getRecommentedPrice().doubleValue());
                portfolioSecurityVO.setVenueCode(customerPortfolioSecuritiesTb.getVenueCode());
                portfolioSecurityVO.setVenueScriptCode(customerPortfolioSecuritiesTb.getVenueScriptCode());
                portfolioSecurityVO.setSecurityCode(customerPortfolioSecuritiesTb.getSecurityCode());
                String label = customerPortfolioSecuritiesTb.getVenueCode() + "_" + customerPortfolioSecuritiesTb.getSecurityCode()
                        + "_" + customerPortfolioSecuritiesTb.getVenueScriptCode();
                portfolioSecurityVO.setSecurityLabel(label.replaceAll("\\.", "_").replace(" ", "_"));
                portfolioSecurityVOs.add(portfolioSecurityVO);
            }
        }
        return portfolioSecurityVOs;
    }

    /**
     * Contains Portfolio details information for the assigned portfolio
     *
     * @param customerPortfolioDetailsTbs
     * @return List<PortfolioDetailsVO> - Returning a list contains informations
     * about individual details in the portfolio.
     */
    private List<PortfolioDetailsVO> getCustomerportfolioDetails(Set<CustomerPortfolioDetailsTb> customerPortfolioDetailsTbs) {
        List<PortfolioDetailsVO> portfolioDetailsVOs = new ArrayList<PortfolioDetailsVO>();
        for (CustomerPortfolioDetailsTb customerPortfolioDetailsTb : customerPortfolioDetailsTbs) {
            PortfolioDetailsVO portfolioDetailsVO = new PortfolioDetailsVO();
            portfolioDetailsVO.setAssetId(customerPortfolioDetailsTb.getMasterAssetTb().getId());
            portfolioDetailsVO.setAssetClassName(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
            portfolioDetailsVO.setMinimumRange(customerPortfolioDetailsTb.getRangeFrom() == null ? ZERO : customerPortfolioDetailsTb.getRangeFrom().doubleValue());
            portfolioDetailsVO.setMaximumRange(customerPortfolioDetailsTb.getRangeTo() == null ? ZERO : customerPortfolioDetailsTb.getRangeTo().doubleValue());
            portfolioDetailsVO.setNewAllocation(customerPortfolioDetailsTb.getNewAllocation() == null ? ZERO : customerPortfolioDetailsTb.getNewAllocation().doubleValue());
            portfolioDetailsVO.setCurrentAllocation(customerPortfolioDetailsTb.getCurrentAllocation() == null ? ZERO : customerPortfolioDetailsTb.getCurrentAllocation());
            portfolioDetailsVO.setSecurities(getPorfolioSecurities(customerPortfolioDetailsTb.getCustomerPortfolioSecuritiesTbs()));
            portfolioDetailsVOs.add(portfolioDetailsVO);
        }
        return portfolioDetailsVOs;
    }

    /**
     * Calculating new allocated fund After Add/redeem 1.Add Fund :
     * allocatedFund = allocatedFund + Fund Added 2.Reduce Fund: allocatedFund =
     * allocatedFund - Total_transaction_amount 3.Total_transaction_amount =
     * sum(security_units_executed for each security * Current_price)
     *
     * @param addRedeemFundsVO - Contain Fund added/reduced by the investor.
     * @return Double value Which is the new allocated fund for the investor.
     */
    private Double getNewAllocatedFund(AddRedeemFundsVO addRedeemFundsVO) {
        return addRedeemFundsVO.getAddFund() ? addRedeemFundsVO.getAddRedeemFund() + addRedeemFundsVO.getAlocatedFunds()
                : addRedeemFundsVO.getAlocatedFunds() - addRedeemFundsVO.getTotalTransAmt();
    }

    /**
     * Calculating new cash balance after add/redeem 1. Add Fund : cash_balance
     * = cash_balance + Fund Added 2. Reduce Fund: cash_balance = cash_balance
     * (After executing selling order then only changes reflect in cash_balance
     * )
     *
     * @param addRedeemFundsVO-Contain Fund added/reduced by the investor.
     * @return Double value Which is the new Cash balance for investor after
     * add/redeem.
     */
    private Double getNewCashAmount(AddRedeemFundsVO addRedeemFundsVO) {
        return addRedeemFundsVO.getAddFund() ? addRedeemFundsVO.getCashAmount() + addRedeemFundsVO.getAddRedeemFund()
                : addRedeemFundsVO.getCashAmount();
    }

    private Double getNewBalanceAmountAvailable(AddRedeemFundsVO addRedeemFundsVO) {
        return addRedeemFundsVO.getAddFund() ? (addRedeemFundsVO.getAddRedeemFund() > addRedeemFundsVO
                .getTotalAvailableFund()) ? 0.0 : addRedeemFundsVO.getTotalAvailableFund() - addRedeemFundsVO.getAddRedeemFund()
                : addRedeemFundsVO.getTotalAvailableFund() + addRedeemFundsVO.getAddRedeemFund();
    }

    private class ComparatorImpl implements Comparator<CustomerPortfolioDetailsTb> {

        public int compare(CustomerPortfolioDetailsTb o1, CustomerPortfolioDetailsTb o2) {
            return o1.getMasterAssetTb().getAssetOrder().compareTo(o2.getMasterAssetTb().getAssetOrder());
        }
    }

    /**
     * Creating data for storing in AddRedeemLogTb after each add/redeem
     *
     * @param addRedeemFundsVO - Contain Add/Reedem transaction details.
     * @return AddRedeemLogTb - Retuns Add/Reedem transaction details for saving
     * in DB.
     */
    private AddRedeemLogTb getAddRedeemLogTb(AddRedeemFundsVO addRedeemFundsVO) {
        AddRedeemLogTb addRedeemLogTb = new AddRedeemLogTb();
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        customerPortfolioTb.setCustomerPortfolioId(addRedeemFundsVO.getCustomerPortfolioId());
        addRedeemLogTb.setCustomerPortfolioTb(customerPortfolioTb);
//        addRedeemLogTb.setForAmount(BigDecimal.valueOf(addRedeemFundsVO.getAddRedeemFund()));
//        addRedeemLogTb.setDateOfEntry(new Date());
//        addRedeemLogTb.setPortfolioValueBefore(BigDecimal.valueOf(addRedeemFundsVO.getCurrentValue()));
//        addRedeemLogTb.setCashBefore(BigDecimal.valueOf(addRedeemFundsVO.getCashAmount()));
//        addRedeemLogTb.setAddfund(addRedeemFundsVO.getAddFund());
//        addRedeemLogTb.setBalanceBefore(BigDecimal.valueOf(addRedeemFundsVO.getBalanceFundAvailable()));
        return addRedeemLogTb;
    }

    /**
     * Reading add/redeem fund details for current date.
     *
     * @param addRedeemFundsVO - Contain Add/Redeem transaction details.
     * @return List<Object[]> contain flag representing add fund/redeem and the
     * amount added/redeemed_amount,
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Object[]> getlogAddRedeemFundDetails(AddRedeemFundsVO addRedeemFundsVO) {
        return addRedeemFundsDAO.getlogAddRedeemFundDetails(addRedeemFundsVO.getCustomerPortfolioId());
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void addFundDetails(CashFlowTb cashFlowTb) {
         addRedeemFundsDAO.updateCashFlowTb(cashFlowTb);
    }

}
