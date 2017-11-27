/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.linkedin.util.LinkedInConnectionsVO;
import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.linkedin.util.ServiceParamVO;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IAdvisorNetworkViewBAO;
import com.gtl.mmf.bao.ILookupDataLoaderBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.dao.IAdvisorNetworkViewDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import com.gtl.mmf.service.util.LinkedInMemFilter;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class AdvisorNetworkViewBAOImpl implements IAdvisorNetworkViewBAO, IConstants {

    private static final int LINKED_IN_QUERY_SIZE = 10;
    @Autowired
    private IAdvisorNetworkViewDAO advisorNetworkViewDAO;
    @Autowired
    private ILookupDataLoaderBAO lookupDataLoaderBAO;
    private List<InvestorDetailsVO> advisorNetwork;
    private List<String> linkedInMembersId;
    private List<LinkedInConnectionsVO> linkedInConnectionsVO;
    private Map<String, LinkedInConnectionsVO> linkedInConnectionsMap;

    /**
     * Loading investor network view details to display in network page in
     * advisor side
     *
     * @param advisorId
     * @param accesToken - Which is used to get LinkedIN response
     * @param linkedInId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<InvestorDetailsVO> populateInvestorNetwork(int advisorId, String accesToken,
            String linkedInId, boolean linkedInConnected) {
        // List passed to web as Z. 1. First add investors without advisor to Z
        advisorNetwork = this.investorsWithoutAdvisors(advisorId);
        // 2. Add investors under services to Z
        advisorNetwork.addAll(this.investorsUnderService(advisorId));
        // 3. Populate linked in network
        if (this.populateLinkedInNetwork(accesToken, linkedInConnected)) {
            // 4. update Z with current linked in data
            this.updateAdvisorNetworkWithConnections(accesToken, linkedInConnected);
            // 5. add linkedInusers in List to network view
            this.addLinkedInConnectionsToNetwork();
        }
        // 6. update advisorNetwork with linked details
        this.updateAdvisorNetworkWithLinkedin(accesToken, linkedInConnected);
        return advisorNetwork;
    }

    private void updateAdvisorNetworkWithLinkedin(String accessToken, boolean linkedInConnected) {
        for (InvestorDetailsVO investor : advisorNetwork) {
            LinkedInVO linkedInResponseVO = null;
            if (investor.getConnectionLevel() == null) {
                if (linkedInConnected && investor.isLinkedInConnected()) {
                    ServiceParamVO serPVO = new ServiceParamVO();
                    serPVO.setConvertedIds(investor.getLinkedInId());
                    linkedInResponseVO = LinkedInUtil.getInstance().getLinkedInResponseVO(accessToken,
                            serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
                }
                if (linkedInResponseVO != null) {
                    String distance = linkedInResponseVO.getDistance();
                    investor.setConnectionLevel(distance == null || "-1".equals(distance)
                            ? CONNECTION_LEVEL_DEFAULT : distance);
                    investor.setConnectionShared(linkedInResponseVO.getConnections()
                            == null ? SHARED_CONNECTIONS_DEFAULT : linkedInResponseVO.getConnections().getTotal());
                } else if (investor.isLinkedInConnected()) {
                    investor.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    investor.setConnectionShared(SHARED_CONNECTIONS_DEFAULT);
                } else if (!investor.isLinkedInConnected()) {
                    investor.setConnectionLevel("100");
                    investor.setConnectionShared(SHARED_CONNECTIONS_DEFAULT);
                }

            }

        }
    }

    private void addLinkedInConnectionsToNetwork() {
        int linkedInListSize = linkedInMembersId.size();
        List<String> linkedInMembersToQuery;
        for (int i = ZERO; i < linkedInListSize; i = i + LINKED_IN_QUERY_SIZE) {
            if ((linkedInListSize - i) < LINKED_IN_QUERY_SIZE) {
                int leftSize = linkedInListSize - i;
                linkedInMembersToQuery = linkedInMembersId.subList(i, i + leftSize);
            } else {
                linkedInMembersToQuery = linkedInMembersId.subList(i, i + LINKED_IN_QUERY_SIZE);
            }
            List<Object[]> investorDetailsToAdd = advisorNetworkViewDAO
                    .getInvestorDetailsWithLinkedIn(linkedInMembersToQuery);
            for (Object[] investorToAdd : investorDetailsToAdd) {
                InvestorDetailsVO investorDetails = new InvestorDetailsVO();
                investorDetails.setCustomerId((Integer) investorToAdd[0]);
                investorDetails.setWorkOrganization((String) investorToAdd[2] != null
                        ? (String) investorToAdd[2] : EMPTY_STRING);
                investorDetails.setJobTitle((String) investorToAdd[3] != null
                        ? (String) investorToAdd[3] : EMPTY_STRING);
                Date startDate = (Date) investorToAdd[4];
                investorDetails.setStartDate(DateUtil.dateToString(startDate, DD_SLASH_MM_SLASH_YYYY));
                investorDetails.setLinkedInId((String) investorToAdd[5] != null
                        ? (String) investorToAdd[5] : EMPTY_STRING);
                investorDetails.setPictureURL((String) investorToAdd[6] != null
                        ? (String) investorToAdd[6] : EMPTY_STRING);
                investorDetails.setFirstName((String) investorToAdd[1]
                        + " " + (String) investorToAdd[7] + " " + (String) investorToAdd[8]);
                investorDetails.setRegId((String) investorToAdd[9]);
                investorDetails.setEmail((String) investorToAdd[10]);
                investorDetails.setLinkedInConnected((Boolean) investorToAdd[11]);
                investorDetails.setStatus(EnumAdvisorMappingStatus.NO_RELATION.getValue());
                Object[] portfolioValues = advisorNetworkViewDAO.getPortfolioValues(investorDetails
                        .getCustomerId(), new Date());
                Double maxReturns90Days = ((BigDecimal) portfolioValues[ZERO] == null
                        ? ZERO_POINT_ZERO : (BigDecimal) portfolioValues[ZERO]).doubleValue();
                Double maxReturnsOneYear = ((BigDecimal) portfolioValues[ONE] == null
                        ? ZERO_POINT_ZERO : (BigDecimal) portfolioValues[ONE]).doubleValue();
                investorDetails.setMaxReturn90Days(maxReturns90Days);
                investorDetails.setMaxReturn365Days(maxReturnsOneYear);
                investorDetails.setLinkedinProfileUrl((String) investorToAdd[12] != null
                        ? (String) investorToAdd[12] : EMPTY_STRING);
                advisorNetwork.add(investorDetails);
            }
        }
    }

    private void updateAdvisorNetworkWithConnections(String accesToken, boolean linkedInConnected) {
        List<String> tempLinkedInMembersId = new ArrayList<String>();
        tempLinkedInMembersId.addAll(linkedInMembersId);
        LinkedInVO linkedInResponseVO = null;
        for (InvestorDetailsVO investorDetails : advisorNetwork) {
            if (tempLinkedInMembersId.contains(investorDetails.getLinkedInId())) {
                linkedInMembersId.remove(investorDetails.getLinkedInId());
                if (linkedInConnected && investorDetails.isLinkedInConnected()) {
                    ServiceParamVO serPVO = new ServiceParamVO();
                    serPVO.setConvertedIds(investorDetails.getLinkedInId());
                    linkedInResponseVO = LinkedInUtil.getInstance().getLinkedInResponseVO(accesToken,
                            serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
                }
                if (linkedInResponseVO != null) {
                    String distance = linkedInResponseVO.getDistance();
                    investorDetails.setConnectionLevel(distance == null || distance.equals("-1")
                            || distance.equals("100") ? CONNECTION_LEVEL_DEFAULT : distance);
                } else if (investorDetails.isLinkedInConnected()) {

                    investorDetails.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                } else if (!investorDetails.isLinkedInConnected()) {
                    investorDetails.setConnectionLevel("100");
                }

            }
        }
        this.updateLinkedinConnectionsWithIds();
    }

    private boolean populateLinkedInNetwork(String accessToken, boolean linkedInConnected) {
        boolean linkedinConnections;
        LinkedInVO linkedInResponseVO = null;
        // 3.1 Linked in connections with connection level
        if (linkedInConnected) {
            linkedInResponseVO = LinkedInUtil.getInstance().getLinkedInResponseVO(accessToken, null,
                    ServiceTypeEnum.CONNECTIONS_LEVEL);
        }
        // 3.2 linked in connections in mmf
        if (linkedInResponseVO == null) {
            linkedinConnections = false;
        } else if (linkedInResponseVO.getValues() == null) {
            linkedinConnections = false;
        } else {
            linkedInMembersId = LinkedInMemFilter.getFilteredMembers(lookupDataLoaderBAO
                    .loadLinkedInMemId(false), linkedInResponseVO.getValues());
            linkedInConnectionsVO = new ArrayList<LinkedInConnectionsVO>();
            linkedInConnectionsVO.addAll(linkedInResponseVO.getValues());
            this.updateLinkedinConnectionsWithIds();
            linkedinConnections = true;
        }
        return linkedinConnections;
    }

    private void updateLinkedinConnectionsWithIds() {
        linkedInConnectionsMap = new HashMap<String, LinkedInConnectionsVO>();
        List<LinkedInConnectionsVO> linkedInConnections = new ArrayList<LinkedInConnectionsVO>();
        linkedInConnections.addAll(linkedInConnectionsVO);
        for (LinkedInConnectionsVO linkedInConnection : linkedInConnections) {
            if (!linkedInMembersId.contains(linkedInConnection.getId())) {
                linkedInConnectionsVO.remove(linkedInConnection);
            } else {
                linkedInConnectionsMap.put(linkedInConnection.getId(), linkedInConnection);
            }
        }
    }

    private List<InvestorDetailsVO> investorsUnderService(int advisorId) {
        List<InvestorDetailsVO> listInvestors = new ArrayList<InvestorDetailsVO>();
        List<Object[]> investorsUnderService = advisorNetworkViewDAO.investorsUnderService(advisorId);
        for (Object[] mappingItem : investorsUnderService) {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = (CustomerAdvisorMappingTb) mappingItem[ZERO];
            MasterPortfolioTypeTb masterPortfolioTypeTb = (MasterPortfolioTypeTb) mappingItem[ONE];
            CustomerRiskProfileTb customerRiskProfileTb = (CustomerRiskProfileTb) mappingItem[TWO];
            InvestorDetailsVO investorDetails = new InvestorDetailsVO();
            investorDetails.setCustomerId(customerAdvisorMappingTb.getMasterCustomerTb().getCustomerId());
            investorDetails.setWorkOrganization(customerAdvisorMappingTb.getMasterCustomerTb().getWorkOrganization() != null
                    ? customerAdvisorMappingTb.getMasterCustomerTb().getWorkOrganization() : EMPTY_STRING);
            Date startDate = customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getRegisterDatetime();
            investorDetails.setStartDate(DateUtil.dateToString(startDate, DD_SLASH_MM_SLASH_YYYY));
            EnumAdvisorMappingStatus status = EnumAdvisorMappingStatus.fromInt(Integer.valueOf(customerAdvisorMappingTb.getRelationStatus()));
            status = (status == EnumAdvisorMappingStatus.INVESTOR_WITHDRAW
                    || status == EnumAdvisorMappingStatus.INVESTOR_INVITE_DECLINED)
                            ? EnumAdvisorMappingStatus.NO_RELATION : status;
            investorDetails.setStatus(status.getValue());
            investorDetails.setStatusValue(StringCaseConverter.toProperCase(status.toString()));
            investorDetails.setJobTitle(customerAdvisorMappingTb.getMasterCustomerTb().getJobTitle() != null
                    ? customerAdvisorMappingTb.getMasterCustomerTb().getJobTitle() : EMPTY_STRING);
            investorDetails.setRelationId(customerAdvisorMappingTb.getRelationId());
            investorDetails.setLinkedInId(customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getLinkedinMemberId() != null
                    ? customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getLinkedinMemberId() : EMPTY_STRING);
            investorDetails.setPictureURL(customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getPictureUrl() != null
                    ? customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getPictureUrl() : EMPTY_STRING);
            investorDetails.setRegId(customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getRegistrationId());
            investorDetails.setEmail(customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getEmail());
            investorDetails.setFirstName(customerAdvisorMappingTb.getMasterCustomerTb().getFirstName()
                    + " " + customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getMiddleName()
                    + " " + customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().getLastName());
            //Display system identified risk profile Style
            investorDetails.setPortFolioStyle(customerRiskProfileTb == null ? EMPTY_STRING
                    : customerRiskProfileTb.getMasterPortfolioStyleTbByInitialPortfolioStyle().getPortfolioStyle());
            /*investorDetails.setPortFolioStyle(masterPortfolioTypeTb == null ? EMPTY_STRING 
                    : masterPortfolioTypeTb.getMasterPortfolioStyleTb().getPortfolioStyle());*/
            Object[] portfolioValues = advisorNetworkViewDAO.getPortfolioValues(investorDetails.getCustomerId(), new Date());
            Set customerPortfolioTbs = customerAdvisorMappingTb.getCustomerPortfolioTbs();
            if (customerPortfolioTbs.size() > ZERO) {
                CustomerPortfolioTb customerPortfolioTb = (CustomerPortfolioTb) customerPortfolioTbs.iterator().next();
                investorDetails.setPortFolio(customerPortfolioTb.getPortfolioTb().getPortfolioName());
                investorDetails.setInvestedAssets(customerPortfolioTb.getCurrentValue() == null ? ZERO_POINT_ZERO
                        : customerPortfolioTb.getCurrentValue().doubleValue());
                investorDetails.setBenchmark(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
                investorDetails.setAllocatedInvestments(customerPortfolioTb.getAllocatedFund() == null ? ZERO_POINT_ZERO
                        : customerPortfolioTb.getAllocatedFund().doubleValue());
            } else {
                investorDetails.setPortFolio(EMPTY_STRING);
                investorDetails.setBenchmark(EMPTY_STRING);
                //added for setting allocated fund for the page investment_policy_statement_create
                investorDetails.setAllocatedInvestments(customerAdvisorMappingTb.getAllocatedFunds());
            }
            if (portfolioValues[ZERO] == null) {
                investorDetails.setMaxReturn90Days(ZERO_POINT_ZERO);
            } else {
                Double maxReturns90Days = ((BigDecimal) portfolioValues[ZERO]).doubleValue();
                investorDetails.setMaxReturn90Days(maxReturns90Days * 100.0);
            }
            if (portfolioValues[ONE] == null) {
                investorDetails.setMaxReturn365Days(ZERO_POINT_ZERO);
            } else {
                Double maxReturnsOneYear = ((BigDecimal) portfolioValues[ONE]).doubleValue();
                investorDetails.setMaxReturn365Days(maxReturnsOneYear * 100.0);
            }
            investorDetails.setLinkedInConnected(customerAdvisorMappingTb.getMasterCustomerTb()
                    .getMasterApplicantTb().getLinkedInConnected());
            investorDetails.setLinkedinProfileUrl(customerAdvisorMappingTb.getMasterCustomerTb()
                    .getMasterApplicantTb().getLinkedinProfileUrl());
            listInvestors.add(investorDetails);
        }
        return listInvestors;
    }

    private List<InvestorDetailsVO> investorsWithoutAdvisors(int advisorId) {
        List<InvestorDetailsVO> listInvestors = new ArrayList<InvestorDetailsVO>();
        List<Object[]> investorsWithoutAdvisorsDet = advisorNetworkViewDAO.investorsWithoutAdvisors(advisorId, getStatusWithoutAdvisors());
        for (Object[] investorDet : investorsWithoutAdvisorsDet) {
            InvestorDetailsVO investorDetails = new InvestorDetailsVO();
            investorDetails.setCustomerId((Integer) investorDet[0]);
            investorDetails.setWorkOrganization((String) investorDet[2] != null
                    ? (String) investorDet[2] : EMPTY_STRING);
            investorDetails.setJobTitle((String) investorDet[3] != null
                    ? (String) investorDet[3] : EMPTY_STRING);
            Date startDate = (Date) investorDet[4];
            investorDetails.setStartDate(DateUtil.dateToString(startDate, DD_SLASH_MM_SLASH_YYYY));
            investorDetails.setLinkedInId((String) investorDet[5] != null
                    ? (String) investorDet[5] : EMPTY_STRING);
            investorDetails.setPictureURL((String) investorDet[6] != null
                    ? (String) investorDet[6] : EMPTY_STRING);
            investorDetails.setStatus(EnumAdvisorMappingStatus.NO_RELATION.getValue());
            investorDetails.setStatusValue(StringCaseConverter.toProperCase(EnumAdvisorMappingStatus.NO_RELATION.toString()));
            investorDetails.setFirstName((String) investorDet[1] + " " + investorDet[7] + " " + investorDet[8]);
            investorDetails.setRegId((String) investorDet[9]);
            investorDetails.setEmail((String) investorDet[10]);
            Object[] portfolioValues = advisorNetworkViewDAO.getPortfolioValues(investorDetails.getCustomerId(), new Date());
            Double maxReturns90Days = ((BigDecimal) portfolioValues[ZERO] == null
                        ? ZERO_POINT_ZERO : ((BigDecimal) portfolioValues[ZERO]).doubleValue());
            Double maxReturnsOneYear = ((BigDecimal) portfolioValues[ONE] == null
                        ? ZERO_POINT_ZERO : ((BigDecimal) portfolioValues[ONE]).doubleValue());
            investorDetails.setMaxReturn90Days(maxReturns90Days);
            investorDetails.setMaxReturn365Days(maxReturnsOneYear);
            investorDetails.setLinkedInConnected((Boolean) investorDet[11]);
            investorDetails.setLinkedinProfileUrl((String) investorDet[12] != null
                    ? (String) investorDet[12] : EMPTY_STRING);
            listInvestors.add(investorDetails);
        }
        return listInvestors;
    }

    private List<Short> getStatusWithoutAdvisors() {
        List<Short> statuses = new ArrayList<Short>();
        statuses.add((short) EnumAdvisorMappingStatus.INVITE_SENT.getValue());
        statuses.add((short) EnumAdvisorMappingStatus.INVITE_RECIEVED.getValue());
        statuses.add((short) EnumAdvisorMappingStatus.WITHDRAW.getValue());
        statuses.add((short) EnumAdvisorMappingStatus.INVESTOR_WITHDRAW.getValue());
        statuses.add((short) EnumAdvisorMappingStatus.INVITE_DECLINED.getValue());
        statuses.add((short) EnumAdvisorMappingStatus.INVESTOR_INVITE_DECLINED.getValue());
        statuses.add((short) EnumAdvisorMappingStatus.CONTRACT_TERMINATED.getValue());
        return statuses;
    }

    //checking wheather invester is signed contract with any advisor 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean CheckStatusFlag(Integer customerId) {
        List<CustomerAdvisorMappingTb> statuslist = advisorNetworkViewDAO.statusList(customerId);
        boolean statusflag = false;
        if (statuslist != null && !statuslist.isEmpty()) {
            statusflag = true;
        } else {
            statusflag = false;
        }
        return statusflag;
    }
}
