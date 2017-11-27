/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IPortfolioRebalanceBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.common.JobType;
import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.dao.IPortfolioRebalanceDAO;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.persist.vo.RebalanceStateVO;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import static com.gtl.mmf.service.util.IConstants.APPLICATION_JSON;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.HTTPS_PROXYHOST;
import static com.gtl.mmf.service.util.IConstants.HTTPS_PROXYPORT;
import static com.gtl.mmf.service.util.IConstants.HTTP_PROXYHOST;
import static com.gtl.mmf.service.util.IConstants.HTTP_PROXYPORT;
import static com.gtl.mmf.service.util.IConstants.INDEX;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.MINUS_ONE;
import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import static com.gtl.mmf.service.util.IConstants.MMM_DD_YYYY;
import static com.gtl.mmf.service.util.IConstants.PRODUCTION;
import static com.gtl.mmf.service.util.IConstants.PROXY_ENABLED;
import static com.gtl.mmf.service.util.IConstants.SERVICE_JOB_SCHEDULE;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.TEXT_BALANCED;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT_LIQUID;
import static com.gtl.mmf.service.util.IConstants.TEXT_EQUITY;
import static com.gtl.mmf.service.util.IConstants.TEXT_FNO;
import static com.gtl.mmf.service.util.IConstants.TEXT_GOLD;
import static com.gtl.mmf.service.util.IConstants.TEXT_INTERNATIONAL;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.RestClient;
import com.gtl.mmf.service.util.ServiceThreadUtil;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is used to handle re-balancing operations of advisor portfolios
 *
 * @author 07958
 */
public class PortfolioRebalanceBAOImpl implements IPortfolioRebalanceBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.PortfolioRebalanceBAOImpl");
    @Autowired
    private IPortfolioRebalanceDAO iPortfolioRebalanceDAO;
    @Autowired
    private IExceptionLogDAO exceptionLogDAO;

    /**
     * This method is used to get al the active portfolios of advisors and
     * Partitioned it into list.
     *
     * @param assets
     * @return Partitioned list of list of portfolios
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<List<Object>> getPortfoliosToCheck(Map<String, Short> assets) {
        List<List<Object>> partitionedPortfolioList = null;

        //Getting portfolios that are active.
        Long portfolioSize = iPortfolioRebalanceDAO.getPortfoliosToCheckSize();
        portfolioSize = (portfolioSize == null) ? ZERO : portfolioSize;
        LOGGER.log(Level.INFO, "Recommended portfolio rebalance - total {0} portfolios to check for rebalance.", portfolioSize);
        if (portfolioSize > ZERO) {

            /*
             Finding out Partitioned list size.
             option 1 portfolio size < 5. 
             Partitioned list size = 1.
             Otherwise Partitioned list size = 5.
             */
            int partitionedListSize = ServiceThreadUtil.getPartitionedListSize(portfolioSize);

            //Creating Partitioned list.
            partitionedPortfolioList = ServiceThreadUtil.getPartitionedList(partitionedListSize);
            List<PortfolioTb> portfoliosToCheck = iPortfolioRebalanceDAO.getPortfoliosToCheck();
            for (PortfolioTb portfolioTb : portfoliosToCheck) {
                PortfolioVO portfolioVO = new PortfolioVO();
                portfolioVO.setPortfolioId(portfolioTb.getPortfolioId());
                portfolioVO.setAdvisorId(portfolioTb.getMasterAdvisorTb().getAdvisorId());
                portfolioVO.setPortfolioName(portfolioTb.getPortfolioName());
                portfolioVO.setBenchmarkIndexSelected(portfolioTb.getMasterBenchmarkIndexTb().getId());
                portfolioVO.setPortfolioTypeSelectedKey(portfolioTb.getMasterPortfolioTypeTb().getId());
                portfolioVO.setPortfolioSizeSelectedKey(portfolioTb.getMasterPortfolioTypeTb().getMasterPortfolioSizeTb().getPortfolioSizeId());
                portfolioVO.setPortfolioStyleSelectedKey(portfolioTb.getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyleId());
                portfolioVO.setBalanceCashWhnLastUpdated(portfolioTb.getBalanceCash());
                Set<PortfolioDetailsTb> portfolioDetailsTbs = portfolioTb.getPortfolioDetailsTbs();
                for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {
                    PortfolioDetailsVO portfolioDetailsVO = getPortfolioDetailsToAssign(portfolioDetailsTb.getMasterAssetTb().getId(), portfolioVO, assets);
                    portfolioDetailsVO.setPortfolioDetailsId(portfolioDetailsTb.getPortfolioDetailsId());
                    portfolioDetailsVO.setAssetId(portfolioDetailsTb.getMasterAssetTb().getId());
                    portfolioDetailsVO.setInitialAllocation(portfolioDetailsTb.getNewAllocation().doubleValue());
                    portfolioDetailsVO.setInitialSetPrice(portfolioDetailsTb.getCurrentValue().doubleValue());
                    portfolioDetailsVO.setNewAllocation(portfolioDetailsTb.getNewAllocation().doubleValue());
                    portfolioDetailsVO.setMaximumRange(portfolioDetailsTb.getRangeTo().doubleValue());
                    portfolioDetailsVO.setMinimumRange(portfolioDetailsTb.getRangeFrom().doubleValue());
                    Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();

                    //Getting Security informations  
                    generatePortfolioSecurites(portfolioSecuritiesTbs, portfolioDetailsVO);
                }
                int index = portfoliosToCheck.indexOf(portfolioTb);
//                List<Object> portfolioVOs = partitionedPortfolioList.get(index % partitionedListSize);
                //Code is commented inorder to manage single thread
                List<Object> portfolioVOs = partitionedPortfolioList.get(0);
                portfolioVOs.add(portfolioVO);
            }
        }
        return partitionedPortfolioList;
    }

    /**
     * Loading all portfolios assigned to investor for checking re-balance. The
     * list is partitioned and passed into the thread
     *
     * @return List<List<Object>>
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<List<Object>> getClientPortfoliosToCheck() {
        List<List<Object>> partitionedClientPortfolioList = null;
        Long portfolioSize = iPortfolioRebalanceDAO.getClientPortfoliosToCheckSize();
        portfolioSize = (portfolioSize == null) ? ZERO : portfolioSize;
        LOGGER.log(Level.INFO, "Client portfolio rebalance - total {0} portfolios to check for rebalance.", portfolioSize);
        if (portfolioSize > ZERO) {
            int partitionedListSize = ServiceThreadUtil.getPartitionedListSize(portfolioSize);
            partitionedClientPortfolioList = ServiceThreadUtil.getPartitionedList(partitionedListSize);
            List<CustomerPortfolioTb> clientPortfoliosToCheck = iPortfolioRebalanceDAO.getClientPortfoliosToCheck();
            for (CustomerPortfolioTb customerPortfolioTb : clientPortfoliosToCheck) {
                CustomerPortfolioVO customerPortfolioVO = new CustomerPortfolioVO();
                customerPortfolioVO.setCustomerPortfolioId(customerPortfolioTb.getCustomerPortfolioId());
                customerPortfolioVO.setRelationId(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationId());
                customerPortfolioVO.setCustomerId(customerPortfolioTb.getMasterCustomerTb().getCustomerId());
                customerPortfolioVO.setAdvisorId(customerPortfolioTb.getMasterAdvisorTb().getAdvisorId());
                customerPortfolioVO.setPortfolioId(customerPortfolioTb.getPortfolioTb().getPortfolioId());
                customerPortfolioVO.setPortfolioName(customerPortfolioTb.getPortfolioTb().getPortfolioName());
                customerPortfolioVO.setPorfolioStyleName(customerPortfolioTb.getPortfolioTb().getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyle());
                customerPortfolioVO.setBenchmarkIndexSelected(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getId());
                customerPortfolioVO.setCashAmount(customerPortfolioTb.getCashAmount());
                customerPortfolioVO.setOmsLoginId(customerPortfolioTb.getOmsLoginId());
                customerPortfolioVO.setRebalanceEnabledForAdvisor(customerPortfolioTb.getPortfolioTb().getRebalanceRequired());
                customerPortfolioVO.setPortfolioDetailsVOs(generateCustomerPortfolioDetails(customerPortfolioTb));
                customerPortfolioVO.setNoreBalanceStatus(customerPortfolioTb.getNoRebalanceStatus() == null ? Boolean.FALSE
                        : customerPortfolioTb.getNoRebalanceStatus());
                int index = clientPortfoliosToCheck.indexOf(customerPortfolioTb);
                List<Object> customerPortfolioVOs = partitionedClientPortfolioList.get(index % partitionedListSize);
                customerPortfolioVOs.add(customerPortfolioVO);
            }
        }
        return partitionedClientPortfolioList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateRebalancePortfolio(RebalanceStateVO rebalanceStateVO) {
        iPortfolioRebalanceDAO.updateRebalancePortfolio(rebalanceStateVO);
        iPortfolioRebalanceDAO.updateRebalancePortfolioDetails(rebalanceStateVO.getRebalanceStateAssetVOs(), rebalanceStateVO.getPortfolioId());
        iPortfolioRebalanceDAO.updateRebalancePortfolioSecurities(rebalanceStateVO.getRebalanceStateSecurityVOs(), rebalanceStateVO.getPortfolioId());
        // Sending mail - rebalance portfolio
        boolean rebalanceRequired = rebalanceStateVO.getRebalanceRequired() == null ? false
                : rebalanceStateVO.getRebalanceRequired().booleanValue();
        if (rebalanceRequired) {
            try {
                PortfolioTb portfolioTb = iPortfolioRebalanceDAO.getAdvisorDetails(rebalanceStateVO.getPortfolioId());
                StringBuffer firstName = new StringBuffer();
                firstName.append(portfolioTb.getMasterAdvisorTb().getFirstName()).append(SPACE)
                        .append(portfolioTb.getMasterAdvisorTb().getMiddleName()).append(SPACE)
                        .append(portfolioTb.getMasterAdvisorTb().getLastName());
                String email = portfolioTb.getMasterAdvisorTb().getEmail();
                String portfolioName = portfolioTb.getPortfolioName();
                MailUtil.getInstance().senRebalanceNotification(firstName.toString(), email, portfolioName, true);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, "Rebalance notification mail failed for portfolio id {0} - Exception message : {1}\n",
                        new Object[]{rebalanceStateVO.getPortfolioId(), StackTraceWriter.getStackTrace(ex)});
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateRebalanceCustomerPortfolio(RebalanceStateVO rebalanceStateVO) {
        iPortfolioRebalanceDAO.updateRebalanceCustomerPortfolio(rebalanceStateVO);
        iPortfolioRebalanceDAO.updateRebalanceCustomerPortfolioDetails(rebalanceStateVO.getRebalanceStateAssetVOs(), rebalanceStateVO.getPortfolioId());
        iPortfolioRebalanceDAO.updateRebalanceCustomerPortfolioSecurities(rebalanceStateVO.getRebalanceStateSecurityVOs(), rebalanceStateVO.getPortfolioId());
        boolean rebalanceRequired = rebalanceStateVO.getRebalanceRequired() == null ? false
                : rebalanceStateVO.getRebalanceRequired().booleanValue();
        if (rebalanceRequired) {
            try {
                CustomerPortfolioTb customerPortfolioTb = iPortfolioRebalanceDAO.getCustomerDetails(rebalanceStateVO.getPortfolioId());
                String email = customerPortfolioTb.getMasterCustomerTb().getEmail();
                StringBuffer firstName = new StringBuffer();
                firstName.append(customerPortfolioTb.getMasterCustomerTb().getFirstName()).append(SPACE)
                        .append(customerPortfolioTb.getMasterCustomerTb().getMiddleName()).append(SPACE)
                        .append(customerPortfolioTb.getMasterCustomerTb().getLastName());
                MailUtil.getInstance().senRebalanceNotification(firstName.toString(), email, "", false);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, "Customer rebalance notification mail failed for portfolio id {0} - Exception message : {1}",
                        new Object[]{rebalanceStateVO.getPortfolioId(), StackTraceWriter.getStackTrace(ex)});
            }
        }
    }

    private List<PortfolioDetailsVO> generateCustomerPortfolioDetails(CustomerPortfolioTb customerPortfolioTb) {
        List<PortfolioDetailsVO> portfolioDetailsVOs = new ArrayList<PortfolioDetailsVO>();
        Set<CustomerPortfolioDetailsTb> customerPortfolioDetailsTbs = customerPortfolioTb.getCustomerPortfolioDetailsTbs();
        for (CustomerPortfolioDetailsTb customerPortfolioDetailsTb : customerPortfolioDetailsTbs) {
            PortfolioDetailsVO portfolioDetailsVO = new PortfolioDetailsVO();
            portfolioDetailsVO.setPortfolioDetailsId(customerPortfolioDetailsTb.getPortfolioDetailsTb().getPortfolioDetailsId());
            portfolioDetailsVO.setAssetId(customerPortfolioDetailsTb.getMasterAssetTb().getId());
            portfolioDetailsVO.setInitialAllocation(Double.valueOf(customerPortfolioDetailsTb.getNewAllocation()));
            portfolioDetailsVO.setMaximumRange(customerPortfolioDetailsTb.getRangeTo().doubleValue());
            portfolioDetailsVO.setMinimumRange(customerPortfolioDetailsTb.getRangeFrom().doubleValue());
            portfolioDetailsVO.setSecurities(generateCustomerPortfolioSecurities(customerPortfolioDetailsTb));
            portfolioDetailsVOs.add(portfolioDetailsVO);
        }
        return portfolioDetailsVOs;
    }

    private List<PortfolioSecurityVO> generateCustomerPortfolioSecurities(CustomerPortfolioDetailsTb customerPortfolioDetailsTb) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs = customerPortfolioDetailsTb.getCustomerPortfolioSecuritiesTbs();
        for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : customerPortfolioSecuritiesTbs) {
            PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
            portfolioSecurityVO.setPortfolioSecurityId(customerPortfolioSecuritiesTb.getPortfolioSecuritiesTb().getPortfolioSecuritiesId());
            portfolioSecurityVO.setPortfolioId(customerPortfolioDetailsTb.getPortfolioDetailsTb().getPortfolioTb().getPortfolioId());
            portfolioSecurityVO.setPortfolioDetailsId(customerPortfolioDetailsTb.getPortfolioDetailsTb().getPortfolioDetailsId());
            portfolioSecurityVO.setAssetClassId(customerPortfolioDetailsTb.getMasterAssetTb().getId());
            portfolioSecurityVO.setSecurityId(customerPortfolioSecuritiesTb.getSecurityId());
            portfolioSecurityVO.setInitialValue(
                    customerPortfolioSecuritiesTb.getCurrentValue() == null ? ZERO_POINT_ZERO : customerPortfolioSecuritiesTb.getCurrentValue().doubleValue());
            portfolioSecurityVO.setInitialAllocation(customerPortfolioSecuritiesTb.getNewAllocation().doubleValue());
            portfolioSecurityVO.setInitialWeight(
                    customerPortfolioSecuritiesTb.getInitialWeight() == null ? ZERO_POINT_ZERO : customerPortfolioSecuritiesTb.getCurrentValue().doubleValue());
            portfolioSecurityVO.setExecutedUnits(customerPortfolioSecuritiesTb.getExeUnits().doubleValue());
            portfolioSecurityVO.setInitialToleranceNegativeRange(customerPortfolioSecuritiesTb.getNewToleranceNegativeRange());
            portfolioSecurityVO.setInitialTolerancePositiveRange(customerPortfolioSecuritiesTb.getNewTolerancePositiveRange());
            portfolioSecurityVO.setInitialPrice(Double.valueOf(customerPortfolioSecuritiesTb.getRecommentedPrice()));
            portfolioSecurityVO.setVenueCode(customerPortfolioSecuritiesTb.getVenueCode());
            portfolioSecurityVO.setVenueScriptCode(customerPortfolioSecuritiesTb.getVenueScriptCode());
            portfolioSecurityVO.setCurrentWeight(
                    customerPortfolioSecuritiesTb.getCurrentWeight() == null ? ZERO_POINT_ZERO : customerPortfolioSecuritiesTb.getCurrentWeight().doubleValue());
            portfolioSecurityVO.setAssetClass(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
            portfolioSecurityVO.setCurrentPrice(customerPortfolioSecuritiesTb.getCurrentPrice().doubleValue());
            portfolioSecurityVOs.add(portfolioSecurityVO);
        }
        return portfolioSecurityVOs;
    }

    /**
     * setting portfolio securities for the selected portfolio
     *
     * @param portfolioSecuritiesTbs list of securities in a particular
     * portfolio
     * @param portfolioDetailsVO
     */
    private void generatePortfolioSecurites(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs, PortfolioDetailsVO portfolioDetailsVO) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            //Considering only those securities whose status is true.
            if (portfolioSecuritiesTb.getStatus() && portfolioSecuritiesTb.getMasterAssetTb().getId() == portfolioDetailsVO.getAssetId()) {
                PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
                portfolioSecurityVO.setPortfolioId(portfolioSecuritiesTb.getPortfolioTb().getPortfolioId());
                portfolioSecurityVO.setPortfolioDetailsId(portfolioSecuritiesTb.getPortfolioDetailsTb().getPortfolioDetailsId());
                portfolioSecurityVO.setAssetClassId(portfolioSecuritiesTb.getMasterAssetTb().getId());
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
                portfolioSecurityVO.setExecutedUnits(portfolioSecuritiesTb.getExeUnits().doubleValue());
                portfolioSecurityVO.setPortfolioSecurityId(portfolioSecuritiesTb.getPortfolioSecuritiesId());
                portfolioSecurityVO.setStatus(portfolioSecuritiesTb.getStatus());
                portfolioSecurityVO.setAssetClass(portfolioSecuritiesTb.getMasterAssetTb().getAssetName());
                portfolioSecurityVO.setCurrentPrice(portfolioSecuritiesTb.getCurrentPrice().doubleValue());
                portfolioSecurityVOs.add(portfolioSecurityVO);
            }
        }
        portfolioDetailsVO.setSecurities(portfolioSecurityVOs);
    }

    private PortfolioDetailsVO getPortfolioDetailsToAssign(Short assetId, PortfolioVO portfolioVO, Map<String, Short> assets) {
        PortfolioDetailsVO portfolioDetailsVO = null;
        if (assets.get(TEXT_EQUITY) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getEquity());
        } else if (assets.get(TEXT_DEBT) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getDebt());
        } else if (assets.get(TEXT_FNO) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getFno());
        } else if (assets.get(TEXT_CASH) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getCash());
//        } else if (assets.get(TEXT_MUTUAL_FUND) == assetId) {
//            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getMutualfund());
        } else if (assets.get(TEXT_INTERNATIONAL) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getInternational());
        } else if (assets.get(MIDCAP) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getMidcap());
        } else if (assets.get(INDEX) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getIndex());
        } else if (assets.get(EQUITY_DIVERSIFIED) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getEquityDiversified());
        } else if (assets.get(TEXT_GOLD) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getGold());
        } else if (assets.get(TEXT_BALANCED) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getBalanced());
        } else if (assets.get(TEXT_DEBT_LIQUID) == assetId) {
            portfolioDetailsVO = getAssetClassDetails(assetId, portfolioVO.getDebtLiquid());
        }
        return portfolioDetailsVO;
    }

    private PortfolioDetailsVO getAssetClassDetails(Short assetId, PortfolioDetailsVO portfolioDetailsVO) {
        portfolioDetailsVO.setAssetId(assetId);
        return portfolioDetailsVO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void changeCustomerNotificationStatus() {
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        if (environment.equalsIgnoreCase(PRODUCTION)) {
            LOGGER.log(Level.INFO, "Back office data transfer service invocation starting");
            String jobtxn = callServiceToGetBOData(SERVICE_JOB_SCHEDULE);
            LOGGER.log(Level.INFO, "Convert job scedule details json to table object");
            List<JobScheduleTb> JobScheduleTxnTb = convertToListTxnJob(jobtxn);//job schedule list from back office
            if (!JobScheduleTxnTb.isEmpty()) {
                for (JobScheduleTb jobScheduleTb : JobScheduleTxnTb) {
                    try {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(jobScheduleTb.getScheduledate());
                        cal.add(Calendar.DATE, MINUS_ONE); // just prevoius day for cheking whether previous day job completed
                        LOGGER.log(Level.INFO, "Check previous day job completed");
                        if (!isTransferRequired(jobScheduleTb.getScheduledate())) {
                            // code to put dummy entry in MMF JobScheduleTb as no data is recieved from BODB for the day.
                            iPortfolioRebalanceDAO.updateNotificationStatus(EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue(), jobScheduleTb.getJobType(), true);
                        } else {
                            iPortfolioRebalanceDAO.updateNotificationStatus(EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue(), EMPTY_STRING, false);
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, "EOD job changeCustomerNotificationStatus failed. Exception : {0}", StackTraceWriter.getStackTrace(ex));
                    }
                }
            } else {
                iPortfolioRebalanceDAO.updateNotificationStatus(EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue(), JobType.CASH_DUMMY.toString(), true);
            }
        } else {
            /**
             * For pre-production & development environment, we need to put
             * dummy entry in MMF JobScheduleTb to manage the work flow .
             */
            iPortfolioRebalanceDAO.updateNotificationStatus(EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue(), JobType.CASH_DUMMY.toString(), true);
        }
    }

    /**
     * Method to call web service in order to access back office data
     *
     * @param url - web service URL
     * @return
     */
    private String callServiceToGetBOData(String url) {
        ClientResponse response = null;
        try {
            Boolean proxyEnabled = Boolean.parseBoolean(com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(PROXY_ENABLED));
            if (proxyEnabled) {
                System.setProperty(HTTP_PROXYHOST, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYHOST));
                System.setProperty(HTTP_PROXYPORT, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYPORT));
            }
            LOGGER.log(Level.INFO, "Calling service {0} ", PropertiesLoader.getPropertiesValue(url));
            WebResource webResource = RestClient.getClient().resource(PropertiesLoader.getPropertiesValue(url));
            response = webResource.accept(APPLICATION_JSON).get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
            LOGGER.log(Level.INFO, "mmfbowebservice {0} called ", PropertiesLoader.getPropertiesValue(url));
        } catch (RuntimeException ex) {
            ExceptionLogUtil.logError("Calling sertvice to get Backoffice data failed.", ex);
            ExceptionLogUtil.mailError("Calling sertvice to get Backoffice data failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Calling sertvice to get Backoffice data failed.", StackTraceWriter.getStackTrace(ex));
        }
        return response.getEntity(String.class);

    }

    private List<JobScheduleTb> convertToListTxnJob(String jsonString) {
        List<JobScheduleTb> list = new ArrayList<JobScheduleTb>();
        try {
            JSONArray dobDtl = new JSONArray(jsonString);
            boolean jobAvail = dobDtl.getJSONObject(0).has("error");
            if (!jobAvail) {
                for (int i = 0; i < dobDtl.length(); i++) {
                    JSONObject object = dobDtl.getJSONObject(i);
                    JobScheduleTb mdc = new JobScheduleTb();
                    mdc.setJobType(object.getString("jobType"));
                    mdc.setLastupdatedon(new Date());
                    mdc.setScheduledate(DateUtil.stringToDate(object.getString("scheduledDate"), MMM_DD_YYYY));
                    mdc.setStatus(object.getString("status"));
                    list.add(mdc);
                }
            }
        } catch (JSONException ex) {
            ExceptionLogUtil.logError("Transfer Job details from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer Job details from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer Job details from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Transfer Job details from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer Job details from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer Job details from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        }
        return list;
    }

    /* This method is used to check whether any transaction submitted to OMS is
     * completed and data need to transfer from back-office DB to MMF DB
     *
     * @param jobScheduleTbList
     * @param jobType
     * @param scheduleDate
     * @return boolean true if order submitted is processed.
     * @throws Exception
     */
    private Boolean isTransferRequired(Date scheduleDate) throws Exception {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, MINUS_ONE);
            Date mmfDate = DateUtil.stringToDate(DateUtil.dateToString(cal.getTime(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            Date boDate = DateUtil.stringToDate(DateUtil.dateToString(scheduleDate, DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            Boolean isTrns = mmfDate.compareTo(boDate) == 0 ? Boolean.TRUE : Boolean.FALSE;
            return isTrns;
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("isTransferRequired check failed.", ex);
            ExceptionLogUtil.mailError("isTransferRequired check failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("isTransferRequired check failed.", StackTraceWriter.getStackTrace(ex));
            return Boolean.FALSE;
        }
    }
}
