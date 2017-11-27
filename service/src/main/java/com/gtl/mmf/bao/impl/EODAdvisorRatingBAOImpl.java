/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IEODAdvisorRatingBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.dao.IEODAdvisorRatingDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class EODAdvisorRatingBAOImpl implements IEODAdvisorRatingBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.EODAdvisorRatingBAOImpl");
    private static final String HQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final int RATE_ADVISOR_PERIOD = 3;
    @Autowired
    private IEODAdvisorRatingDAO eodAdvisorRatingDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void checkAdvisorRating() {
        Calendar curretCal = Calendar.getInstance();
        curretCal.add(Calendar.MONTH, -RATE_ADVISOR_PERIOD);
        Date currentDate = new Date();
        List<Integer> updateAdvisorRatingList = eodAdvisorRatingDAO.updateAdvisorRatingList(getRelationActiveStatuses(), currentDate, curretCal.getTime());
        for (Integer relationId : updateAdvisorRatingList) {
            eodAdvisorRatingDAO.updateAdvisorRating(relationId, currentDate);
        }
    }

    /**
     * This method is used to update active advisor details while performing advisor rating
     * 1.update total_portfolios_managed,max_returns_90_days,max_returns_1_year
     * 2.Updating dominant style and customer rating
     * 3.Get Max - 90 days / 1 year returns and update
     * 4.Updating outperformance and outperformance count
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateAdvisorDetails() {
        Date currentDate = new Date();
        //Reading all active advisors 
        List<Integer> activeAdvisors = eodAdvisorRatingDAO.getActiveAdvisors(EnumStatus.ACTIVE.getValue());
        LOGGER.log(Level.INFO, "Updating {0} advisors details.", activeAdvisors.size());
        for (Integer advisorId : activeAdvisors) {
            LOGGER.log(Level.INFO, "Updating details of advisod : {0}", advisorId);
            eodAdvisorRatingDAO.updateAdvisorDetais(advisorId, currentDate);
        }
    }

    /**
     * This method is used to get the active statuses of advisor
     * @return a list which contain active statuses of advisor
     */
    private static List<Short> getRelationActiveStatuses() {
        List<Short> relationActiveStatus = new ArrayList<Short>();
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.CONTRACT_SIGNED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.RESPONSE_RECIEVED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.RESPONSE_REVIEW.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.IPS_SHARED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.IPS_CREATED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.IPS_REVIEWED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.IPS_ACCEPTED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.REBALANCE_INITIATED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.ACTIVE.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.SUSPENDED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.EXPIRED.getValue());
        relationActiveStatus.add((short) EnumAdvisorMappingStatus.ORDER_PLACED_IN_OMS.getValue());
        return relationActiveStatus;
    }
}
