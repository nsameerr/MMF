/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.linkedin.util.ServiceParamVO;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IInvestorNetworkViewBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IInvestorNetworkViewDAO;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.SHARED_CONNECTIONS_DEFAULT;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class InvestorNetworkViewBAOImpl implements IInvestorNetworkViewBAO, IConstants {

    @Autowired
    private IInvestorNetworkViewDAO investorNetworkViewDAO;

    /**
     * This method is used to load all the advisors registered with MMF to
     * display in the investor network
     *
     * @param customerId
     * @param accessToken
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<AdvisorDetailsVO> allAdvisors(int customerId, String accessToken, boolean linkedInConnected) {
        List<AdvisorDetailsVO> allAdvisorsDetails = new ArrayList<AdvisorDetailsVO>();
        List<Object[]> alldvisors = investorNetworkViewDAO.getAllAdvisors(customerId,EnumCustomerMappingStatus.ACTIVE.getValue());
        for (Object[] objects : alldvisors) {
            AdvisorDetailsVO advisorDetails = new AdvisorDetailsVO();
            advisorDetails.setAdvisorId((Integer) objects[0]);
            advisorDetails.setFirstName((String) objects[1] + " " + (String) objects[2] + " " + (String) objects[3]);
            advisorDetails.setWorkOrganization((String) objects[4] != null
                    ? (String) objects[4] : EMPTY_STRING);
            advisorDetails.setJobTitle((String) objects[5] != null
                    ? (String) objects[5] : EMPTY_STRING);
            advisorDetails.setQualification((String) objects[6]);
            advisorDetails.setMaxReturnsNintyDays(objects[8] == null ? 0
                    : ((BigDecimal) objects[8]).doubleValue());
            advisorDetails.setMaxReturnsOneYear(objects[9] == null ? 0
                    : ((BigDecimal) objects[9]).doubleValue());
            advisorDetails.setOutPerformance(objects[10] == null ? 0
                    : ((BigDecimal) objects[10]).doubleValue());
            advisorDetails.setAvgClientRating(objects[11] == null ? 0
                    : ((BigDecimal) objects[11]).doubleValue());
            Integer relationStatus = (Integer) objects[12];
            advisorDetails.setPictureURL((String) objects[13] != null
                    ? (String) objects[13] : EMPTY_STRING);
            advisorDetails.setLinkedInMemberid((String) objects[14] != null
                    ? (String) objects[14] : EMPTY_STRING);
            advisorDetails.setSebiVerificationNo((String) objects[15]);
            advisorDetails.setRelationId((Integer) objects[16]);
            advisorDetails.setRegId((String) objects[17]);
            advisorDetails.setEmail((String) objects[18]);
            advisorDetails.setPortfolioType((String) objects[19]);
            advisorDetails.setLinkedInConnected((Boolean) objects[20] != null
                    ? (Boolean) objects[20] : false);
            advisorDetails.setLinkedInProfile((String) objects[21] != null
                    ? (String) objects[21] : EMPTY_STRING);
            //Setting status of the Advisor
            if (relationStatus == null) {
                advisorDetails.setStatus(EnumCustomerMappingStatus.NO_RELATION.getValue());
                advisorDetails.setStatusValue(StringCaseConverter.toProperCase(EnumCustomerMappingStatus.NO_RELATION.toString()));
            } else {
                EnumCustomerMappingStatus status = EnumCustomerMappingStatus.fromInt(relationStatus);
                status = (status == EnumCustomerMappingStatus.WITHDRAW
                        || status == EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED
                        || status == EnumCustomerMappingStatus.ADVISOR_WITHDRAW)
                        ? EnumCustomerMappingStatus.NO_RELATION : status;
                advisorDetails.setStatus(status.getValue());
                advisorDetails.setStatusValue(StringCaseConverter.toProperCase(status.toString()));
            }
            //Setting LinkedIn relation status
            LinkedInVO linkedInResponseVO = null;
            if (linkedInConnected && advisorDetails.isLinkedInConnected()) {
                ServiceParamVO serPVO = new ServiceParamVO();
                serPVO.setConvertedIds(advisorDetails.getLinkedInMemberid());
                linkedInResponseVO = LinkedInUtil.getInstance()
                        .getLinkedInResponseVO(accessToken, serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
            }
            if (linkedInResponseVO != null) {
                advisorDetails.setConnectionLevel((linkedInResponseVO.getDistance() == null
                        || linkedInResponseVO.getDistance().equals("0")
                        || linkedInResponseVO.getDistance().equals("-1")
                        || linkedInResponseVO.getDistance().equals("100"))
                        ? CONNECTION_LEVEL_DEFAULT : linkedInResponseVO.getDistance());
                advisorDetails.setConnectionShared(linkedInResponseVO.getConnections()
                        == null ? SHARED_CONNECTIONS_DEFAULT : linkedInResponseVO.getConnections().getTotal());
            } else if (advisorDetails.isLinkedInConnected()) {
                advisorDetails.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                advisorDetails.setConnectionShared(SHARED_CONNECTIONS_DEFAULT);

            } else if (!advisorDetails.isLinkedInConnected()) {
                advisorDetails.setConnectionLevel("100");
                advisorDetails.setConnectionShared(SHARED_CONNECTIONS_DEFAULT);
            }

            allAdvisorsDetails.add(advisorDetails);
        }
        return allAdvisorsDetails;
    }
}
