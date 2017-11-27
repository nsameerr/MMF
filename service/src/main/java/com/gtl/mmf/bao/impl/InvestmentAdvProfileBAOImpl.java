/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IInvestmentAdvProfileBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IInvestmentAdvProfileDAO;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.InvestmentRelationStatusVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class InvestmentAdvProfileBAOImpl implements IInvestmentAdvProfileBAO, IConstants {

    @Autowired
    private IInvestmentAdvProfileDAO investmentAdvProfileDAO;

    /**
     * This method returns relation status for a particular customer with
     * advisor
     *
     * @param relationId
     * @param customerId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public InvestmentRelationStatusVO getAdvisorRelationStatus(Integer relationId, Integer customerId) {
        InvestmentRelationStatusVO relationStatusVO = new InvestmentRelationStatusVO();

        //Reading total allocated fund
        Double totalFund = investmentAdvProfileDAO.getInvestorDetails(customerId);
        relationStatusVO.setTotalFund(totalFund == null ? ZERO_POINT_ZERO : totalFund);

        //No relation status
        if (relationId == ZERO) {
            relationStatusVO.setTotalAvailableFund(relationStatusVO.getTotalFund());
            relationStatusVO.setAllocatedFund(ZERO_POINT_ZERO);
            relationStatusVO.setContractStatus(getContractStatusMsg(ZERO, null));
            relationStatusVO.setRelationStatusText(StringCaseConverter.toProperCase((EnumCustomerMappingStatus.NO_RELATION).toString()));
        } else {

            // Loading investor/advisor relation informations like allocatedFunds,pendingFees,contractEnd,
            // relationStatus 
            Object[] advisorRelationValues = investmentAdvProfileDAO.getAdvisorRelationStatus(relationId);
            Short relationStatus = (Short) advisorRelationValues[3];
            EnumCustomerMappingStatus status = EnumCustomerMappingStatus.fromInt(Integer.valueOf(relationStatus));

            // checking statuses that not allocate fund and based on that setting total allocated fund
            if (getStatusNotAllocatedFund().contains(status)) {
                relationStatusVO.setAllocatedFund(ZERO_POINT_ZERO);
            } else {
                relationStatusVO.setAllocatedFund((Double) advisorRelationValues[ZERO]);
            }
            status = getAdvisorNoRelationStatus().contains(status) ? EnumCustomerMappingStatus.NO_RELATION : status;
            Date contractEnd = (Date) advisorRelationValues[2];
            relationStatusVO.setContractStatus(getContractStatusMsg(Integer.valueOf(relationStatus.shortValue()), contractEnd));
            relationStatusVO.setTotalAvailableFund(relationStatusVO.getTotalFund());
            relationStatusVO.setRelationStatus(status.getValue());
            relationStatusVO.setRelationStatusText(StringCaseConverter.toProperCase(status.toString()));
            if (relationStatus == EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue()) {
                relationStatusVO.setAllocatedFund(investmentAdvProfileDAO.getAllocatedFundForContractterminateduser(customerId).doubleValue());
            }
        }
        relationStatusVO.setFeePending(ZERO_POINT_ZERO);
        return relationStatusVO;
    }

    /**
     * This method is used to get statuses where there is no fund allocated
     *
     * @return list of statuses where fund is not allocated
     */
    private List<EnumCustomerMappingStatus> getStatusNotAllocatedFund() {
        List<EnumCustomerMappingStatus> statuses = new ArrayList<EnumCustomerMappingStatus>();
        statuses.add(EnumCustomerMappingStatus.NO_RELATION);
        statuses.add(EnumCustomerMappingStatus.ADVISOR_WITHDRAW);
        statuses.add(EnumCustomerMappingStatus.WITHDRAW);
        statuses.add(EnumCustomerMappingStatus.INVITE_DECLINED);
        statuses.add(EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED);
        return statuses;
    }

    /**
     * This method is used to get statuses where there is no relation between
     * advisor and investor
     *
     * @return list of advisor statuses
     */
    private List<EnumCustomerMappingStatus> getAdvisorNoRelationStatus() {
        List<EnumCustomerMappingStatus> statuses = new ArrayList<EnumCustomerMappingStatus>();
        statuses.add(EnumCustomerMappingStatus.INVITE_DECLINED);
        statuses.add(EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED);
        statuses.add(EnumCustomerMappingStatus.WITHDRAW);
        statuses.add(EnumCustomerMappingStatus.ADVISOR_WITHDRAW);
        statuses.add(EnumCustomerMappingStatus.CONTRACT_TERMINATED);
        return statuses;
    }

    /**
     * This method return contract status message for the Current status of
     * investor and advisor
     *
     * @param relationStatusValue Current status of investor and advisor
     * @param endDate
     * @return
     */
    private String getContractStatusMsg(Integer relationStatusValue, Date endDate) {
        String contractMsg = EMPTY_STRING;
        EnumCustomerMappingStatus relationStatus = EnumCustomerMappingStatus.fromInt(relationStatusValue);
        if (relationStatus == EnumCustomerMappingStatus.NO_RELATION
                || relationStatus == EnumCustomerMappingStatus.INVITE_SENT
                || relationStatus == EnumCustomerMappingStatus.INVITE_RECIEVED
                || relationStatus == EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED
                || relationStatus == EnumCustomerMappingStatus.INVITE_DECLINED
                || relationStatus == EnumCustomerMappingStatus.WITHDRAW
                || relationStatus == EnumCustomerMappingStatus.ADVISOR_WITHDRAW
                || relationStatus == EnumCustomerMappingStatus.INVITE_ACCEPTED) {
            contractMsg = "Not received";
        } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_RECIEVED) {
            contractMsg = "Received";
        } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_REVIEW) {
            contractMsg = "Review submitted";
        } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_SIGNED) {
            String date = DateUtil.dateToString(endDate, "dd/MM/yyyy");
            contractMsg = "Ends on " + date;
        } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_TERMINATED) {
            contractMsg = "Contract Terminated";
        }
        return contractMsg;
    }
    
    /**
     * This method for Enable terminate contract Button of investor and advisor
     * 
     *
     * @param relationId relation id of investor and advisor in CustomerAdvisorMappingTb
     * @param advsorId
     * @return
     */

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean getContractTerminationStatus(Integer relationId, Integer advsorId) {
        boolean contractTerminationStatus = false;
        Float zero = (Float)ZERO_POINT_ZERO.floatValue();
        if (relationId != null || advsorId != null) {
            Object[] contractStatusValues = investmentAdvProfileDAO.getContractTerminationStatus(relationId);
            if (contractStatusValues != null) {
                boolean Status = (Boolean) (contractStatusValues[0] == null ? false : contractStatusValues[0]) ;
                Integer advisorIdentity = (Integer) contractStatusValues[1];
                int currentStatus = (Short) contractStatusValues[2];
                Float blocked_cash =  (Float) (contractStatusValues[3] == null ? zero : contractStatusValues[3]);
                Float blocked_count = (Float) (contractStatusValues[4] == null ? zero : contractStatusValues[4]);
                Float temp_blocked_cash = (Float) (contractStatusValues[5] == null ? zero : contractStatusValues[5]);
                Float temp_blocked_count = (Float) (contractStatusValues[6] == null ? zero : contractStatusValues[6]);
                if (advisorIdentity == advsorId && Status && currentStatus != 403 
                        && blocked_cash.equals(zero) && blocked_count.equals(zero) 
                        && temp_blocked_cash.equals(zero) && temp_blocked_count.equals(zero)) {
                    contractTerminationStatus = true;
                } else {
                    contractTerminationStatus = false;
                }
            }
        } else {
            contractTerminationStatus = false;
        }
        return contractTerminationStatus;
    }
}
