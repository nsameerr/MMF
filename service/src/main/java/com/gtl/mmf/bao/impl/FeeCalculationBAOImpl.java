/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IFeeCalculationBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IFeeCalculationDAO;
import com.gtl.mmf.entity.Advisorbucketwisepayouts;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerManagementFeeTb;
import com.gtl.mmf.entity.CustomerPerformanceFeeTb;
import com.gtl.mmf.entity.Tieredpromocommissionmatrix;
import com.gtl.mmf.entity.Tiers;
import com.gtl.mmf.entity.Totaladvisorpayout;
import com.gtl.mmf.entity.TotaladvisorpayoutId;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZEROD;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 09607
 */
public class FeeCalculationBAOImpl implements IFeeCalculationBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.FeeCalculationBAOImpl");

    @Autowired
    IFeeCalculationDAO iFeeCalculationDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String CalculateQuarterlyAdvisoryFee() {

        StringBuffer ecsContent = new StringBuffer();
        StringBuffer relationTocheck = new StringBuffer();
        relationTocheck.append("IN(").append("'").append(EnumCustomerMappingStatus.IPS_ACCEPTED.getValue()).append("',")
                .append("'").append(EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue()).append("',")
                .append("'").append(EnumCustomerMappingStatus.ORDER_PLACED_IN_OMS.getValue()).append("')");
        List<Tiers> tiersList = iFeeCalculationDAO.getTiersTB();
        List<Tieredpromocommissionmatrix> tiersMatrixList = iFeeCalculationDAO.getTieredpromocommissionmatrixTB();
        List<Object> dataListt = iFeeCalculationDAO.createUserListforDebtPay(relationTocheck.toString());

        HashMap<String, Double> commisionsMap = this.getCommisionsMap(tiersMatrixList);
        HashMap<Integer, Object> managementFeeMap
                = this.convertListToMap((List<Object>) dataListt.get(ZERO));
        HashMap<Integer, Object> performanceFeeMap
                = this.convertListToMap((List<Object>) dataListt.get(ONE));
        List<CustomerAdvisorMappingTb> mappingList = (List<CustomerAdvisorMappingTb>) dataListt.get(2);

        HashMap<String, Advisorbucketwisepayouts> feeMap = new HashMap<String, Advisorbucketwisepayouts>();
        HashMap<Integer, Totaladvisorpayout> totalFeeAdvisor = new HashMap<Integer, Totaladvisorpayout>();
        LOGGER.log(Level.INFO, "####################  --- Starting Calculation ----  ####################");
        for (CustomerAdvisorMappingTb mappingtb : mappingList) {
            if (getStatusActiveUser().contains(EnumCustomerMappingStatus.fromInt(Integer.valueOf(mappingtb.getRelationStatus())))) {
                LOGGER.log(Level.INFO, "####################  -----   Calculation started for User relation id [{0}] ------ ####################", mappingtb.getRelationId());
                String mgntTiresId;
                String perfTiresId;
                Double mgmntFeeCommision = 0.0;
                Double perfFeeCommision = 0.0;
                Double mgmntFee = 0.0;
                Double perfFee = 0.0;
                double perfFeeShare = 0;
                double managemntFeeShare = 0;
                StringBuffer mangntKey = new StringBuffer();
                StringBuffer perfKey = new StringBuffer();
                int advisorId = mappingtb.getMasterAdvisorTb().getAdvisorId();
                StringBuffer feemapKey = new StringBuffer();

                /* Constructing Key for Advisor bucket wise fee map:(advisorId_PromoId)*/
                feemapKey = feemapKey.append(mappingtb.getMasterAdvisorTb().getAdvisorId()).append("_").append(mappingtb.getPromodefinitions().getPromoId());
                Advisorbucketwisepayouts payout = null;

                int promoId = mappingtb.getPromodefinitions().getPromoId();

                /* Checking Fee Already added for selected advisor*/
                Totaladvisorpayout advisorpayout = totalFeeAdvisor.containsKey(advisorId) ? totalFeeAdvisor.get(advisorId) : new Totaladvisorpayout();

                if (managementFeeMap.size() > ZERO || performanceFeeMap.size() > ZERO) {
                    CustomerManagementFeeTb managementFee = null;
                    CustomerPerformanceFeeTb performanceFee = null;

                    if (managementFeeMap.size() > ZERO) {
                        managementFee = (CustomerManagementFeeTb) managementFeeMap.get(mappingtb.getRelationId());
                        if (!feeMap.containsKey(feemapKey.toString())) {
                            mgntTiresId = getTiersId(tiersList, managementFee.getMgmtFee().doubleValue());
                            mangntKey = mangntKey.append(mappingtb.getPromodefinitions().getPromoId()).append("_").
                                    append(mgntTiresId).append("_").append("0");
                            mgmntFeeCommision = commisionsMap.get(mangntKey.toString());
                            managemntFeeShare = managementFee.getMgmtFee().doubleValue() * (mgmntFeeCommision / 100);
                            mgmntFee = managementFee.getMgmtFee().doubleValue() - managemntFeeShare;

                            LOGGER.log(Level.INFO, "Management Fee Tierid[{0}] promocode[{1}] commission[{2}] Management fee share[{3}] Management fee [{4}] Management fee Advisor[{5}]",
                                    new Object[]{mgntTiresId, mappingtb.getPromodefinitions().getPromoId(), mgmntFeeCommision, managemntFeeShare, managementFee.getMgmtFee().doubleValue(), mgmntFee});
                            if (performanceFeeMap.containsKey(mappingtb.getRelationId())) {
                                performanceFee = (CustomerPerformanceFeeTb) performanceFeeMap.get(mappingtb.getRelationId());
                                perfTiresId = getTiersId(tiersList, performanceFee.getPerfFee().doubleValue());
                                perfKey = perfKey.append(mappingtb.getPromodefinitions().getPromoId()).append("_").
                                        append(perfTiresId).append("_").append("1");
                                perfFeeCommision = commisionsMap.get(perfKey.toString());
                                perfFeeShare = performanceFee.getPerfFee().doubleValue() * (perfFeeCommision / 100);
                                perfFee = performanceFee.getPerfFee().doubleValue() - perfFeeShare;
                                LOGGER.log(Level.INFO, "Performance Fee Tierid[{0}] promocode[{1}] commission[{2}] Performance fee share[{3}] Performance fee [{4}] Performance fee Advisor[{5}]",
                                        new Object[]{perfTiresId, mappingtb.getPromodefinitions().getPromoId(), perfFeeCommision, perfFeeShare, performanceFee.getPerfFee(), perfFee});
                            }

                            payout = new Advisorbucketwisepayouts();
                            payout.setMasterAdvisorTb(mappingtb.getMasterAdvisorTb());
                            payout.setPromodefinitions(mappingtb.getPromodefinitions());
                        } else {
                            LOGGER.log(Level.INFO, "####################  -----PromoId already in the map for same advisor[{0}] promoid[{1}] -----  ####################", new Object[]{mappingtb.getMasterAdvisorTb().getAdvisorId(), mappingtb.getPromodefinitions().getPromoId()});
                            payout = feeMap.get(feemapKey.toString());
                            Double newFee = (managementFee.getMgmtFee().doubleValue() + payout.getMgntfee().doubleValue());
                            mgntTiresId = getTiersId(tiersList, newFee);
                            mangntKey = mangntKey.append(mappingtb.getPromodefinitions().getPromoId()).append("_").
                                    append(mgntTiresId).append("_").append("0");
                            mgmntFeeCommision = commisionsMap.get(mangntKey.toString());
                            managemntFeeShare = newFee * (mgmntFeeCommision / 100);
                            mgmntFee = newFee - managemntFeeShare;
                            LOGGER.log(Level.INFO, "Management Fee Tierid[{0}] promocode[{1}] commission[{2}] Management fee share[{3}] new Fee (Management fee [{4}] + Previous Payout [{5}]) Management fee Advisor[{6}]",
                                    new Object[]{mgntTiresId, mappingtb.getPromodefinitions().getPromoId(), mgmntFeeCommision, managemntFeeShare, managementFee.getMgmtFee().doubleValue(), payout.getMgntfee().doubleValue(), mgmntFee});
                            if (performanceFeeMap.containsKey(mappingtb.getRelationId())) {
                                performanceFee = (CustomerPerformanceFeeTb) performanceFeeMap.get(mappingtb.getRelationId());
                                Double newPerfFee = performanceFee.getPerfFee().doubleValue() + payout.getPerffee().doubleValue();
                                perfTiresId = getTiersId(tiersList, newPerfFee);
                                perfKey = perfKey.append(mappingtb.getPromodefinitions().getPromoId()).append("_").
                                        append(perfTiresId).append("_").append("1");
                                perfFeeCommision = commisionsMap.get(perfKey.toString());
                                perfFeeShare = newPerfFee * (perfFeeCommision / 100);
                                perfFee = newPerfFee - perfFeeShare;
                                LOGGER.log(Level.INFO, "Performance Fee Tierid[{0}] promocode[{1}] commission[{2}] Performance fee share[{3}] new fee (Performance fee [{4}] + Previous Payout [{5}]) Performance fee Advisor[{6}]",
                                        new Object[]{perfTiresId, mappingtb.getPromodefinitions().getPromoId(), perfFeeCommision, perfFeeShare, performanceFee.getPerfFee(), payout.getPerffee().doubleValue(), perfFee});
                            }

                        }

                    }
                    if (payout != null) {
                        payout.setMgntfee(managementFee != null ? managementFee.getMgmtFee() : new BigDecimal(ZEROD));
                        payout.setPerffee(performanceFee != null ? performanceFee.getPerfFee() : new BigDecimal(ZEROD));
                        payout.setMfCommission(BigDecimal.valueOf(managemntFeeShare));
                        payout.setPfCommission(BigDecimal.valueOf(perfFeeShare));
                        payout.setMfPayout(BigDecimal.valueOf(mgmntFee));
                        payout.setPfPayout(BigDecimal.valueOf(perfFee));
                        payout.setDateAdded(new Date());
                    }

                }
                boolean samePromo = false;
                LOGGER.info("#####################  Adding to Fee map for Advisor fee Calculation  ####################");
                if (feeMap.containsKey(feemapKey.toString())) {
                    samePromo = true;
                    LOGGER.log(Level.INFO, "#################### Removing and Inserting new Entry  to Fee map for Advisor fee Calculation  #################### KEY [{0}]", feemapKey.toString());
                    feeMap.remove(feemapKey.toString());
                    feeMap.put(feemapKey.toString(), payout);
                } else {
                    LOGGER.log(Level.INFO, "#################### New Entry  to Fee map for Advisor fee Calculation  #################### Key [{0}]", feemapKey.toString());
                    feeMap.put(feemapKey.toString(), payout);
                }
                double newPayout = 0.0;
                LOGGER.log(Level.INFO, "####################  Total Fee for Payout   #################### advisorId [{0}]", advisorId);
                if (totalFeeAdvisor.containsKey(advisorId)) {
                    LOGGER.log(Level.INFO, "#################### Removing and Inserting new Entry  to Fee map for Advisor Total Payout Calculation  #################### KEY [{0}]", feemapKey.toString());
                    totalFeeAdvisor.remove(advisorId);
                    double prevPayout = advisorpayout.getPayout().doubleValue();
                    if (samePromo) {
                        newPayout = mgmntFee + perfFee;
                    } else {
                        newPayout = prevPayout + mgmntFee + perfFee;
                    }

                    advisorpayout.setPayout(BigDecimal.valueOf(newPayout));
                    totalFeeAdvisor.put(advisorId, advisorpayout);
                    LOGGER.log(Level.INFO, "####################  Total  Payout   #################### payout [{0}]", newPayout);
                } else {
                    LOGGER.log(Level.INFO, "#################### New Entry  to Fee map for Advisor fee Calculation  #################### Key [{0}]", feemapKey.toString());
                    TotaladvisorpayoutId id = new TotaladvisorpayoutId(advisorId, new Date());
                    advisorpayout.setMasterAdvisorTb(mappingtb.getMasterAdvisorTb());
                    advisorpayout.setId(id);
                    newPayout = mgmntFee + perfFee;
                    advisorpayout.setPayout(BigDecimal.valueOf(newPayout));
                    totalFeeAdvisor.put(advisorId, advisorpayout);
                    LOGGER.log(Level.INFO, "####################  Total  Payout   #################### payout [{0}]", newPayout);
                }

                LOGGER.log(Level.INFO, "#################### -----   Calculation Completed for User relation id [{0}]------ ####################", mappingtb.getRelationId());
            }
        }
        LOGGER.info("####################  Saving Advisor Bucket wise Fee Calculation Details  ####################");
        iFeeCalculationDAO.saveAdvisorFeeDetails(feeMap);
        LOGGER.info("#################### Completed Saving Advisor Bucket wise Fee Calculation Details  ####################");

        LOGGER.info("#################### Saving Advisor Total Payout Calculation Details  ####################");
        iFeeCalculationDAO.saveAdvisorTotalPayOut(totalFeeAdvisor);
        LOGGER.info("#################### Completed Advisor Total Payout Calculation Details  ####################");

        LOGGER.log(Level.INFO, "#################### --- Completd Calculation ----  #####################");
        return ecsContent.toString();
    }

    private String getTiersId(List<Tiers> tiersList, double value) {
        String id = null;

        for (Tiers tiersTb : tiersList) {
            if (tiersTb.getRangeEnd().equalsIgnoreCase("NA")) {
                id = value > Double.valueOf(tiersTb.getRangeStart()) ? tiersTb.getTierId().toString() : null;
                break;
            } else if (value > Double.valueOf(tiersTb.getRangeStart()) && value <= Double.valueOf(tiersTb.getRangeEnd())) {
                id = tiersTb.getTierId().toString();
                break;
            }
        }
        return id;
    }

    private HashMap<String, Double> getCommisionsMap(List<Tieredpromocommissionmatrix> commissionmatrixList) {
        HashMap<String, Double> commisionsMap = new HashMap<String, Double>();
        for (Tieredpromocommissionmatrix commissionMatrixTb : commissionmatrixList) {
            StringBuffer mkey = new StringBuffer();

            /*Creating key for Management fee Commission*/
            mkey.append(commissionMatrixTb.getPromodefinitions().getPromoId()).append("_").
                    append(commissionMatrixTb.getTiers().getTierId()).append("_").append("0");
            commisionsMap.put(mkey.toString(), commissionMatrixTb.getMgnmntFeeCommission().doubleValue());
            StringBuffer pkey = new StringBuffer();
            /*Creating key for Performancwe fee Commission*/
            pkey.append(commissionMatrixTb.getPromodefinitions().getPromoId()).append("_").
                    append(commissionMatrixTb.getTiers().getTierId()).append("_").append("1");
            commisionsMap.put(pkey.toString(), commissionMatrixTb.getPerfFeeCommission().doubleValue());
        }
        return commisionsMap;
    }

    private HashMap<Integer, Object> convertListToMap(List<Object> debtList) {
        HashMap<Integer, Object> debtMap = new HashMap<Integer, Object>();
        Integer reg_id = null;

        if (!debtList.isEmpty()) {
            for (Object data : debtList) {
                if (data instanceof CustomerManagementFeeTb) {
                    CustomerManagementFeeTb customerManagementFee = (CustomerManagementFeeTb) data;
                    reg_id = customerManagementFee.getCustomerAdvisorMappingTb().getRelationId();
                } else if (data instanceof CustomerPerformanceFeeTb) {
                    CustomerPerformanceFeeTb customerPerformanceFee = (CustomerPerformanceFeeTb) data;
                    reg_id = customerPerformanceFee.getCustomerAdvisorMappingTb().getRelationId();
                }
                debtMap.put(reg_id, data);
            }
        }
        return debtMap;

    }

    private List<EnumCustomerMappingStatus> getStatusActiveUser() {
        List<EnumCustomerMappingStatus> statuses = new ArrayList<EnumCustomerMappingStatus>();
        statuses.add(EnumCustomerMappingStatus.ORDER_PLACED_IN_OMS);
        statuses.add(EnumCustomerMappingStatus.REBALANCE_INITIATED);
        statuses.add(EnumCustomerMappingStatus.IPS_ACCEPTED);
        return statuses;
    }
}
