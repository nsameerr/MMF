/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import static com.gtl.linkedin.util.INotificationMessages.INVESTOR_PORTFOLIO_CHANGED;

import com.gtl.linkedin.util.NotificationsLoader;
import com.gtl.linkedin.util.URLServiceImpl;
import com.gtl.mmf.bao.ICreateEditPortfolioBAO;
import com.gtl.mmf.dao.ICreateEditPortfolioDAO;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.MasterPortfolioSizeTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.entity.RecomendedCustomerPortfolioSecuritiesTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.GoogleMarketDataUtil;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.vo.MarketDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.util.StackTraceWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class CreateEditPortfolioBAOImpl implements ICreateEditPortfolioBAO, IConstants {

    @Autowired
    private ICreateEditPortfolioDAO createEditPortfolioDAO;
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.CreateEditPortfolioBAOImpl");

    /**
     * This method is used to save the portfolio created by the advisor under a
     * portfolio type.
     *
     * @param portfolioVO - Contains details about the portfolio to be created,
     * Securities in each asset class.
     * @param portfolioTypeVO - It contains the specifications about the type of
     * portfolio to create, max/min range of allocation for each asset class.
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PortfolioTb createPortfolio(PortfolioVO portfolioVO, PortfolioTypeVO portfolioTypeVO) {

        //Converting data in portfolioVO to portfolioTb 
        PortfolioTb portfolioTb = portfolioVO.toPortfolioTb();
        portfolioTb.setDateCreated(new Date());
        // Saving portfolio
        Integer portfolioIndex = createEditPortfolioDAO.saveProtfolio(portfolioTb);

        //Converting portfolioVO data into PortfolioDetailsTb.Seeting Data in each asset class.
        List<PortfolioDetailsTb> portfolioDetailsTbs = portfolioVO.toPortfolioDetailsTb(portfolioTypeVO, portfolioIndex, portfolioTb);
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {

            //Saving asset class details into DB
            Integer portfolioDetailsId = createEditPortfolioDAO.saveProtfolioDetails(portfolioDetailsTb);
            Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();

            //setting asset class ID to each security in that aset class
            for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
                portfolioSecuritiesTb.getPortfolioDetailsTb().setPortfolioDetailsId(portfolioDetailsId);
                portfolioSecuritiesTb.setStatus(SECURITY_ACTIVE_STATUS);
            }

            // Saving sacurities allocated in each asset class into DB
            createEditPortfolioDAO.savePortfolioSecurites(portfolioSecuritiesTbs);
        }
        return portfolioTb;
    }

    /**
     * This method update the Portfolio Selected by the advisor and also update
     * customer portfolio if it is assigned to a investor
     *
     * @param portfolioVO - portfolio selected by advisor for updating
     * @param portfolioTypeVO -Portfolio style selected
     * @param assets -
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PortfolioTb updatePortfolio(PortfolioVO portfolioVO, PortfolioTypeVO portfolioTypeVO, Map<String, Short> assets) {
        //Converting data in portfolioVO to portfolioTb
        PortfolioTb portfolioTb = portfolioVO.toPortfolioTb();
        portfolioTb.setLastUpdated(new Date());

        // Saving portfolio
        createEditPortfolioDAO.updatePortfolio(portfolioTb);

        //Converting portfolioVO data into PortfolioDetailsTb.Seeting Data in each asset class. 
        List<PortfolioDetailsTb> portfolioDetailsTbs = portfolioVO.toPortfolioDetailsTb(
                portfolioTypeVO, portfolioTb.getPortfolioId(), portfolioTb);
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {
            Set<PortfolioSecuritiesTb> portfolioSecuritiesToUpdate = new HashSet<PortfolioSecuritiesTb>();
            Set<PortfolioSecuritiesTb> portfolioSecuritiesToSave = new HashSet<PortfolioSecuritiesTb>();
            Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();

            // Iterating througn all the securities in each asset class and identifying newly added securities and 
            //Securities to be updated.
            for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
                if (portfolioSecuritiesTb.getPortfolioSecuritiesId() == null) {
                    portfolioSecuritiesToSave.add(portfolioSecuritiesTb);
                } else {
                    portfolioSecuritiesToUpdate.add(portfolioSecuritiesTb);
                }
            }
            createEditPortfolioDAO.updatePortfolioSecurities(portfolioSecuritiesToUpdate);
            createEditPortfolioDAO.savePortfolioSecurities(portfolioSecuritiesToSave);
            createEditPortfolioDAO.updatePortfolioDetails(portfolioDetailsTb);
        }

        //Updating customer portfolio when advisor changes portfolio
        this.updateCustomerPortfolio(portfolioVO);
        return portfolioTb;
    }

    public MarketDataVO getMarketData(String scripName) {
        String json;
        List<MarketDataVO> listMarketDataVO = null;
        try {
            URLServiceImpl urlservice = new URLServiceImpl();
            json = urlservice.getResponseJSON(GoogleMarketDataUtil.getUrl().concat(scripName));
            listMarketDataVO = GoogleMarketDataUtil.getMarketDataVO(json.substring(3));
        } catch (IOException ex) {
            LOGGER.severe(StackTraceWriter.getStackTrace(ex));
        } finally {
            return listMarketDataVO.get(ZERO);
        }
    }

    /**
     * This method is used to select the portfolio names and Ids for all the
     * portfolios under selected portfolio style
     *
     * @param advisorId - Id of Advisor logged in
     * @param portfolioStyleId - Portfolio Style selected by the advisor
     * @param assets
     * @param benchmark - benchmark selected
     * @return Map of portfolio name,ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, String> getPorfolioNames(int advisorId, int portfolioTypeId, Map<String, Short> assets, int benchmark) {
        Map<Integer, String> portfolioNamesMap = new HashMap<Integer, String>();
        List<Object[]> portfolioNames = createEditPortfolioDAO.getPorfolioNames(advisorId, portfolioTypeId, benchmark);
        for (Object[] portfolio : portfolioNames) {
            Integer portfolioId = (Integer) portfolio[ZERO];
            String portfolioName = (String) portfolio[ONE];
            portfolioNamesMap.put(portfolioId, portfolioName);
        }
        return portfolioNamesMap;
    }

    /**
     * This method is used to retrieve a portfolio by its ID.
     *
     * @param advisorId
     * @param portfolioId - ID of the portfolio to be selected
     * @return Object which represents the selected portfolio
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PortfolioVO getPortfolioById(int advisorId, int portfolioId) {
        Map<String, Short> assets = LookupDataLoader.getAssetClasses();
        PortfolioTb portfolioTb = createEditPortfolioDAO.getPorfolioById(portfolioId);

        MasterPortfolioTypeTb portfolioTypeTb = portfolioTb.getMasterPortfolioTypeTb();
        PortfolioVO portfolioVO = new PortfolioVO(assets, new PortfolioTypeVO(portfolioTypeTb), portfolioTb.getMasterBenchmarkIndexTb().getId());
        portfolioVO.setPortfolioId(portfolioTb.getPortfolioId());
        portfolioVO.setAdvisorId(advisorId);
        portfolioVO.setPortfolioName(portfolioTb.getPortfolioName());
        portfolioVO.setBenchmarkIndexSelected(portfolioTb.getMasterBenchmarkIndexTb().getId());
        portfolioVO.setBenchmarkName(portfolioTb.getMasterBenchmarkIndexTb().getValue());
        portfolioVO.setPortfolioStyleSelectedKey(portfolioTb.getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyleId());
        portfolioVO.setDateCreated(portfolioTb.getDateCreated());
        portfolioVO.setNoOfCustomersAssigned(portfolioTb.getNoOfCustomersAssigned());
        portfolioVO.setPortfolioTypeSelectedKey(portfolioTb.getMasterPortfolioTypeTb().getId());
        portfolioVO.setPortfolioSizeSelectedKey(portfolioTb.getMasterPortfolioTypeTb().getMasterPortfolioSizeTb().getPortfolioSizeId());
        portfolioVO.setBalanceCashWhnLastUpdated(portfolioTb.getBalanceCash());
        Set<PortfolioDetailsTb> portfolioDetailsTbs = portfolioTb.getPortfolioDetailsTbs();

        //Setting asset classes
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {

            //Setting asset  class from portfolioDetailsTb
            PortfolioDetailsVO portfolioDetailsVO = PortfolioUtil.getAssetclassVO(portfolioDetailsTb.getMasterAssetTb().getId(), portfolioVO);
            portfolioDetailsVO.setPortfolioDetailsId(portfolioDetailsTb.getPortfolioDetailsId());
            portfolioDetailsVO.setAssetId(portfolioDetailsTb.getMasterAssetTb().getId());
            portfolioDetailsVO.setInitialAllocation(portfolioDetailsTb.getNewAllocation().doubleValue());
            portfolioDetailsVO.setInitialSetPrice(portfolioDetailsTb.getCurrentValue().doubleValue());
            portfolioDetailsVO.setNewAllocation(portfolioDetailsTb.getNewAllocation().doubleValue());
//            portfolioDetailsVO.setExistingWeight(portfolioDetailsTb.getInitialWeight().doubleValue());
            portfolioDetailsVO.setExistingWeight(portfolioDetailsTb.getCurrentWeight().doubleValue());
            portfolioDetailsVO.setMaximumRange(portfolioDetailsTb.getRangeTo() == null
                    ? ZERO_POINT_ZERO : portfolioDetailsTb.getRangeTo().doubleValue());
            portfolioDetailsVO.setMinimumRange(portfolioDetailsTb.getRangeFrom() == null
                    ? ZERO_POINT_ZERO : portfolioDetailsTb.getRangeFrom().doubleValue());
            Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();
            generatePortfolioSecurites(portfolioSecuritiesTbs, portfolioDetailsVO);
        }
        return portfolioVO;
    }

    /**
     * This method is used to set the securities in each asset class
     *
     * @param portfolioSecuritiesTbs - SET which contain all the securities ina
     * particular asset class
     * @param portfolioDetailsVO - Selected asset class
     */
    private void generatePortfolioSecurites(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs, PortfolioDetailsVO portfolioDetailsVO) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            if (portfolioSecuritiesTb.getMasterAssetTb().getId() == portfolioDetailsVO.getAssetId()) {
                PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
                portfolioSecurityVO.setPortfolioSecurityId(portfolioSecuritiesTb.getPortfolioSecuritiesId());
                portfolioSecurityVO.setPortfolioId(portfolioSecuritiesTb.getPortfolioTb().getPortfolioId());
                portfolioSecurityVO.setPortfolioDetailsId(portfolioSecuritiesTb.getPortfolioDetailsTb().getPortfolioDetailsId());
                portfolioSecurityVO.setAssetClassId(portfolioSecuritiesTb.getMasterAssetTb().getId());
                portfolioSecurityVO.setAssetClass(portfolioSecuritiesTb.getMasterAssetTb().getAssetName());
                portfolioSecurityVO.setSecurityId(portfolioSecuritiesTb.getSecurityId());
                portfolioSecurityVO.setScripDecription(portfolioSecuritiesTb.getSecurityDescription());
                portfolioSecurityVO.setInitialPrice(portfolioSecuritiesTb.getRecommentedPrice().doubleValue());
                portfolioSecurityVO.setInitialAllocation(portfolioSecuritiesTb.getNewAllocation().doubleValue());
                portfolioSecurityVO.setInitialTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                portfolioSecurityVO.setNewTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                portfolioSecurityVO.setNewAllocation(portfolioSecuritiesTb.getNewAllocation().doubleValue());
                portfolioSecurityVO.setVenueCode(portfolioSecuritiesTb.getVenueCode());
                portfolioSecurityVO.setVenueScriptCode(portfolioSecuritiesTb.getVenueScriptCode());
                portfolioSecurityVO.setSecurityCode(portfolioSecuritiesTb.getSecurityCode());
//                portfolioSecurityVO.setInitialUnits(portfolioSecuritiesTb.getExeUnits());
                portfolioSecurityVO.setExecutedUnits(portfolioSecuritiesTb.getExeUnits().doubleValue());
                portfolioSecurityVO.setInitialWeight(portfolioSecuritiesTb.getInitialWeight().doubleValue());
                portfolioSecurityVO.setStatus(portfolioSecuritiesTb.getStatus());
                // Portfolio securities to edit - don't modify security id
                portfolioSecurityVO.setAllocatedPreviously(true);
                portfolioSecurityVO.setCurrentPrice(portfolioSecuritiesTb.getCurrentPrice().doubleValue());
                portfolioSecurityVO.setCurrentValue(portfolioSecuritiesTb.getCurrentValue().doubleValue());
                portfolioSecurityVO.setCurrentWeight(portfolioSecuritiesTb.getCurrentWeight().doubleValue());
                portfolioSecurityVOs.add(portfolioSecurityVO);
            }
        }
        portfolioDetailsVO.setSecurities(portfolioSecurityVOs);
    }

    /**
     * Update customer portfolio whenever advisor make changes in portfolio
     *
     * @param portfolioVO -Portfolio selected
     */
    private void updateCustomerPortfolio(PortfolioVO portfolioVO) {
        int portfolioId = portfolioVO.getPortfolioId();

        // Update portfolios - set portfolioModified as TRUE
        createEditPortfolioDAO.updateCustomerPortfoliosOnEdit(portfolioId);

        // Get customerPortfolioTbs from db and loop all
        List<CustomerPortfolioTb> customerPortfolioTbs = createEditPortfolioDAO.getCustomerPortfoliosAssigned(portfolioId);
        for (CustomerPortfolioTb customerPortfolioTb : customerPortfolioTbs) {
            // Modify customerPortfolioDetailsTb and persist
            Set<CustomerPortfolioDetailsTb> customerPortfolioDetailsTbs = customerPortfolioTb.getCustomerPortfolioDetailsTbs();
//            Set<PortfolioDetailsTb> portfolioDetailsTbs = portfolioTb.getPortfolioDetailsTbs();
            List<PortfolioDetailsTb> portfolioDetailsTbs = createEditPortfolioDAO.getPortfolioDetails(portfolioId);
            // Modify customerPortfolioSecuritiesTbs and persist
            this.updateCustomerPortfolioDetails(portfolioDetailsTbs, customerPortfolioDetailsTbs);
            // Send mail to investor
            StringBuffer customerName = new StringBuffer();
            customerName.append(customerPortfolioTb.getMasterCustomerTb().getFirstName()).append(SPACE)
                    .append(customerPortfolioTb.getMasterCustomerTb().getMiddleName())
                    .append(SPACE).append(customerPortfolioTb.getMasterCustomerTb().getLastName());
            String advisorName = customerPortfolioTb.getMasterAdvisorTb().getFirstName();
            String email = customerPortfolioTb.getMasterCustomerTb().getEmail();
            this.sendMailOnPortfolioEdit(email, customerName.toString(), advisorName);
        }
    }

    /**
     * Sending mail to investor whenever a portfolio is changed
     *
     * @param email - investor mail ID
     * @param customerName - investor name
     * @param advisorName -advisor
     */
    private void sendMailOnPortfolioEdit(String email, String customerName, String advisorName) {
        try {
            String notificationMsg = NotificationsLoader.getPropertiesValue(INVESTOR_PORTFOLIO_CHANGED);
            String content = String.format(notificationMsg, advisorName);
            MailUtil.getInstance().sendNotificationMail(customerName, email, content);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Email notification sending failed to investor : {0} on edit portfolio. Exception : {1}", new Object[]{email, StackTraceWriter.getStackTrace(ex)});
        }
    }

    /**
     * Updating customer portfolio asset class details when portfolio is
     * modified
     *
     * @param portfolioDetailsTbs
     * @param customerPortfolioDetailsTbs
     */
    private void updateCustomerPortfolioDetails(List<PortfolioDetailsTb> portfolioDetailsTbs, Set<CustomerPortfolioDetailsTb> customerPortfolioDetailsTbs) {
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {
            for (CustomerPortfolioDetailsTb customerPortfolioDetailsTb : customerPortfolioDetailsTbs) {
                if (portfolioDetailsTb.getPortfolioDetailsId() == customerPortfolioDetailsTb.getPortfolioDetailsTb().getPortfolioDetailsId()) {
                    customerPortfolioDetailsTb.setNewAllocation(portfolioDetailsTb.getNewAllocation().floatValue());
                    this.updateCustomerPortfolioSecurities(portfolioDetailsTb, customerPortfolioDetailsTb);
                    createEditPortfolioDAO.updateCustomerPortfolioDetails(customerPortfolioDetailsTb);
                    break;
                }
            }
        }
    }

    /**
     * This method is used to update portfolio securities. IT will add new
     * securities added to asset class and set status of removed securities into
     * false for not considering that security
     *
     * @param portfolioDetailsTb
     * @param customerPortfolioDetailsTb
     */
    private void updateCustomerPortfolioSecurities(PortfolioDetailsTb portfolioDetailsTb, CustomerPortfolioDetailsTb customerPortfolioDetailsTb) {
        Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs = customerPortfolioDetailsTb.getCustomerPortfolioSecuritiesTbs();
        Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();
        Set<CustomerPortfolioSecuritiesTb> newCustomerPortfolioSecuritiesTbs = new HashSet<CustomerPortfolioSecuritiesTb>();
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            boolean securityExists = false;
            for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : customerPortfolioSecuritiesTbs) {

                //Checking for securities that removed when updated by advisor
                if (portfolioSecuritiesTb.getPortfolioSecuritiesId() == customerPortfolioSecuritiesTb.getPortfolioSecuritiesTb().getPortfolioSecuritiesId()) {
                    customerPortfolioSecuritiesTb.setNewAllocation(portfolioSecuritiesTb.getStatus()
                            ? portfolioSecuritiesTb.getNewAllocation().floatValue() : ZERO);
                    customerPortfolioSecuritiesTb.setCurrentPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                    customerPortfolioSecuritiesTb.setRecommentedPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                    customerPortfolioSecuritiesTb.setNewToleranceNegativeRange(portfolioSecuritiesTb.getNewToleranceNegativeRange());
                    customerPortfolioSecuritiesTb.setNewTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                    customerPortfolioSecuritiesTb.setStatus(portfolioSecuritiesTb.getStatus());
                    newCustomerPortfolioSecuritiesTbs.add(customerPortfolioSecuritiesTb);
                    securityExists = true;
                    break;
                }
            }

            //Adding newly inculded security into a list for saving to DB
            if (!securityExists) {
                CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb = new CustomerPortfolioSecuritiesTb();
                customerPortfolioSecuritiesTb.setCustomerPortfolioDetailsTb(customerPortfolioDetailsTb);
                customerPortfolioSecuritiesTb.setPortfolioDetailsTb(portfolioDetailsTb);
                customerPortfolioSecuritiesTb.setPortfolioSecuritiesTb(portfolioSecuritiesTb);
                customerPortfolioSecuritiesTb.setMasterAssetTb(portfolioSecuritiesTb.getMasterAssetTb());
                customerPortfolioSecuritiesTb.setCustomerPortfolioTb(customerPortfolioDetailsTb.getCustomerPortfolioTb());
                customerPortfolioSecuritiesTb.setPortfolioTb(portfolioDetailsTb.getPortfolioTb());
                customerPortfolioSecuritiesTb.setSecurityId(portfolioSecuritiesTb.getSecurityId());
                customerPortfolioSecuritiesTb.setNewAllocation(portfolioSecuritiesTb.getNewAllocation().floatValue());

                //Setting zero in place of null value in customer_portfolio_securities_tb
                customerPortfolioSecuritiesTb.setCurrentPrice(Float.valueOf("0.0"));
                customerPortfolioSecuritiesTb.setCurrentUnits(ZERO);
                customerPortfolioSecuritiesTb.setExeUnits(BigDecimal.ZERO);
                customerPortfolioSecuritiesTb.setCurrentValue(Float.valueOf("0.0"));
                customerPortfolioSecuritiesTb.setCurrentWeight(Float.valueOf("0.0"));
                customerPortfolioSecuritiesTb.setRecommentedPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                customerPortfolioSecuritiesTb.setRebalanceRequired(true);
                customerPortfolioSecuritiesTb.setRebalanceReqdDate(new Date());
                customerPortfolioSecuritiesTb.setSecurityDescription(portfolioSecuritiesTb.getSecurityDescription());
                customerPortfolioSecuritiesTb.setNewTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                customerPortfolioSecuritiesTb.setNewToleranceNegativeRange(portfolioSecuritiesTb.getNewToleranceNegativeRange());
                customerPortfolioSecuritiesTb.setVenueCode(portfolioSecuritiesTb.getVenueCode());
                customerPortfolioSecuritiesTb.setVenueScriptCode(portfolioSecuritiesTb.getVenueScriptCode());
                customerPortfolioSecuritiesTb.setSecurityCode(portfolioSecuritiesTb.getSecurityCode());
                customerPortfolioSecuritiesTb.setStatus(true);
                newCustomerPortfolioSecuritiesTbs.add(customerPortfolioSecuritiesTb);
            }
        }
        createEditPortfolioDAO.updateCustomerPortfolioDetails(customerPortfolioDetailsTb);

        //Saving newly added securities and updating already existing securities
        createEditPortfolioDAO.saveOrUpdateCustomerPortfolioSecurities(newCustomerPortfolioSecuritiesTbs);
        this.updateRecomendedCustomerPortfolioSecurities(portfolioDetailsTb, customerPortfolioDetailsTb);
        //createEditPortfolioDAO.saveCustomerPortfolioSecurities(newCustomerPortfolioSecuritiesTbs);
    }

    /**
     * Updating recommended customer portfolio
     *
     * @param portfolioDetailsTb
     * @param customerPortfolioDetailsTb
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    private void updateRecomendedCustomerPortfolioSecurities(PortfolioDetailsTb portfolioDetailsTb, CustomerPortfolioDetailsTb customerPortfolioDetailsTb) {

        int portfolioId = portfolioDetailsTb.getPortfolioTb().getPortfolioId();
        List<Object> responseList = createEditPortfolioDAO.getRecomendedCustomerPortfolioSecurities(portfolioId, customerPortfolioDetailsTb);
        List<RecomendedCustomerPortfolioSecuritiesTb> recomendedcustomerPortfolioSecuritiesTbs
                = (List<RecomendedCustomerPortfolioSecuritiesTb>) responseList.get(0);
        Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();
        Set<RecomendedCustomerPortfolioSecuritiesTb> newCustomerPortfolioSecuritiesTbs = new HashSet<RecomendedCustomerPortfolioSecuritiesTb>();
//        Double minAum = ((Integer) responseList.get(1)).doubleValue();
        Double alloction = customerPortfolioDetailsTb.getCustomerPortfolioTb().getAllocatedFund();
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            boolean securityExists = false;
            for (RecomendedCustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : recomendedcustomerPortfolioSecuritiesTbs) {
                int units = 0;
                if (portfolioSecuritiesTb.getPortfolioSecuritiesId() == customerPortfolioSecuritiesTb.getPortfolioSecuritiesTb().getPortfolioSecuritiesId()) {
                    customerPortfolioSecuritiesTb.setNewAllocation(portfolioSecuritiesTb.getNewAllocation().floatValue());
                    customerPortfolioSecuritiesTb.setCurrentPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                    if (portfolioSecuritiesTb.getCurrentPrice().floatValue() != ZERO) {
                        units = (int) Math.floor((((customerPortfolioSecuritiesTb.getNewAllocation() / HUNDRED) * alloction) / customerPortfolioSecuritiesTb.getCurrentPrice()));
                    }
                    customerPortfolioSecuritiesTb.setRecommentedPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                    customerPortfolioSecuritiesTb.setNewToleranceNegativeRange(portfolioSecuritiesTb.getNewToleranceNegativeRange());
                    customerPortfolioSecuritiesTb.setNewTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                    customerPortfolioSecuritiesTb.setStatus(portfolioSecuritiesTb.getStatus());
                    customerPortfolioSecuritiesTb.setRequiredUnits((new BigDecimal(units)).setScale(2, RoundingMode.FLOOR));
                    newCustomerPortfolioSecuritiesTbs.add(customerPortfolioSecuritiesTb);
                    securityExists = true;
                    break;
                }
            }

            //Adding newly inculded security into a list for saving to DB
            if (!securityExists) {
                RecomendedCustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb = new RecomendedCustomerPortfolioSecuritiesTb();
                customerPortfolioSecuritiesTb.setCustomerPortfolioDetailsTb(customerPortfolioDetailsTb);
                customerPortfolioSecuritiesTb.setPortfolioDetailsTb(portfolioDetailsTb);
                customerPortfolioSecuritiesTb.setPortfolioSecuritiesTb(portfolioSecuritiesTb);
                customerPortfolioSecuritiesTb.setMasterAssetTb(portfolioSecuritiesTb.getMasterAssetTb());
                customerPortfolioSecuritiesTb.setCustomerPortfolioTb(customerPortfolioDetailsTb.getCustomerPortfolioTb());
                customerPortfolioSecuritiesTb.setPortfolioTb(portfolioDetailsTb.getPortfolioTb());
                customerPortfolioSecuritiesTb.setSecurityId(portfolioSecuritiesTb.getSecurityId());
                customerPortfolioSecuritiesTb.setNewAllocation(portfolioSecuritiesTb.getNewAllocation().floatValue());

                //Setting zero in place of null value in customer_portfolio_securities_tb
                customerPortfolioSecuritiesTb.setCurrentPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                customerPortfolioSecuritiesTb.setCurrentUnits(BigDecimal.ZERO);
                customerPortfolioSecuritiesTb.setExeUnits(BigDecimal.ZERO);
                customerPortfolioSecuritiesTb.setCurrentValue(Float.valueOf("0.0"));
                customerPortfolioSecuritiesTb.setCurrentWeight(Float.valueOf("0.0"));
                customerPortfolioSecuritiesTb.setRecommentedPrice(portfolioSecuritiesTb.getCurrentPrice().floatValue());
                customerPortfolioSecuritiesTb.setSecurityDescription(portfolioSecuritiesTb.getSecurityDescription());
                customerPortfolioSecuritiesTb.setNewTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                customerPortfolioSecuritiesTb.setNewToleranceNegativeRange(portfolioSecuritiesTb.getNewToleranceNegativeRange());
                customerPortfolioSecuritiesTb.setVenueCode(portfolioSecuritiesTb.getVenueCode());
                customerPortfolioSecuritiesTb.setVenueScriptCode(portfolioSecuritiesTb.getVenueScriptCode());
                customerPortfolioSecuritiesTb.setSecurityCode(portfolioSecuritiesTb.getSecurityCode());
                customerPortfolioSecuritiesTb.setStatus(true);
                int units = (int) (((customerPortfolioSecuritiesTb.getNewAllocation() / HUNDRED) * alloction) / customerPortfolioSecuritiesTb.getRecommentedPrice());
                BigDecimal required_units = new BigDecimal(units);
                customerPortfolioSecuritiesTb.setRequiredUnits(required_units.setScale(2, RoundingMode.FLOOR));
                newCustomerPortfolioSecuritiesTbs.add(customerPortfolioSecuritiesTb);
            }
        }

        //Saving newly added securities and updating already existing securities
        createEditPortfolioDAO.saveOrUpdateRecomendedCustomerPortfolioSecurities(newCustomerPortfolioSecuritiesTbs,
                customerPortfolioDetailsTb.getCustomerPortfolioTb().getCustomerPortfolioId());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer getPortfolioByStyleAndSize(int portfolioStyleId, int portfolioSizeId) {
        MasterPortfolioTypeTb masterPortfolioTypeTb = createEditPortfolioDAO.getPortfolioByStyleAndSize(portfolioStyleId, portfolioSizeId);
        return masterPortfolioTypeTb.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<PortfolioDetailsVO> getExistingPortfolioAssets(int portfolioId) {
        List<PortfolioDetailsVO> detailslist = new ArrayList<PortfolioDetailsVO>();
        List<PortfolioDetailsTb> portfolioDetailsTbs = createEditPortfolioDAO.getOldPortfolioAsset(portfolioId);
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {
            PortfolioDetailsVO portfolioDetailsVO = new PortfolioDetailsVO();
            portfolioDetailsVO.setAssetId(portfolioDetailsTb.getMasterAssetTb().getId() == null ? ZERO : portfolioDetailsTb.getMasterAssetTb().getId());
            portfolioDetailsVO.setAssetClassName(portfolioDetailsTb.getMasterAssetTb().getAssetName() == null
                    ? EMPTY_STRING : portfolioDetailsTb.getMasterAssetTb().getAssetName());
            portfolioDetailsVO.setCurrentAllocation((Float) (portfolioDetailsTb.getNewAllocation() == null
                    ? ZERO : portfolioDetailsTb.getNewAllocation()));

        }
        return detailslist;
    }

    //Update portfolio type when advisor change its size
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean updatePortfolioTypeOfPortfolio(int portfolioId, int portfolioTypeId) {
        boolean isupadte = false;
        Integer NumRows = createEditPortfolioDAO.updatePortfolioTypeOfPortfolio(portfolioId, portfolioTypeId);
        if (NumRows != null) {
            isupadte = NumRows != 0 ? true : false;
        }
        return isupadte;
    }

    /**
     * This method update the assets of Portfolio Selected by the advisor and also update
     * customer portfolio if it is assigned to a investor
     *
     * @param portfolioVO - portfolio selected by advisor for updating
     * @param portfolioTypeVO -Portfolio style selected
     * @param assets -
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void transformPortfolioWithNewAssetClass(PortfolioVO portfolioVO, PortfolioTypeVO portfolioTypeVO, Map<String, Short> assets) {
        PortfolioTb portfolioTb = portfolioVO.toPortfolioTb();
        portfolioTb.setLastUpdated(new Date());
        
        //Converting portfolioVO data into PortfolioDetailsTb.Seeting Data in each asset class.
        List<PortfolioDetailsTb> getNewportfolioDetailsTbs = portfolioVO.toNewPortfolioDetailsTb(portfolioTypeVO,
                portfolioTb.getPortfolioId(), portfolioTb);

        for (PortfolioDetailsTb portfolioDetailsTb : getNewportfolioDetailsTbs) {
            createEditPortfolioDAO.saveNewPortfolioDetails(portfolioDetailsTb);
        }
    }

}
