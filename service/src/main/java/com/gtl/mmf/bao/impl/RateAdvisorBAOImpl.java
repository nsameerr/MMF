/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IRateAdvisorBAO;
import com.gtl.mmf.dao.IRateAdvisorDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import com.gtl.mmf.service.vo.RateAdvisorVO;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trainee3
 */
public class RateAdvisorBAOImpl implements IRateAdvisorBAO, IConstants {

    private static final String UNDRERPERFORM_TXT = "Underperform";
    private static final String NEUTRAL_TXT = "Neutral";
    private static final String OUTPERFORM_TXT = "Outperform";
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.RateAdvisorBAOImpl");
    private static final String EVAL_PERIOD_DATE_FORMAT = "d MMMM yyyy";
    private static final Integer OUT_PERFORM = 1;
    private static final Integer NEUTRAL = 0;
    private static final Integer UNDER_PERFORM = -1;
    @Autowired
    private IRateAdvisorDAO rateAdvisorDAO;

    /**
     * *
     * Collecting Advisor details to rate.
     *
     * @param relationId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public RateAdvisorVO getAdvisorDetails(Integer relationId) {
        RateAdvisorVO rateAdvisorVO = new RateAdvisorVO();
        CustomerAdvisorMappingTb customerAdvisorMappingTb = rateAdvisorDAO.getAdvisordetails(relationId);
        rateAdvisorVO.setRelationId(relationId);
        rateAdvisorVO.setAdvisorName(getAdvisorName(customerAdvisorMappingTb.getMasterAdvisorTb().getFirstName(),
                customerAdvisorMappingTb.getMasterAdvisorTb().getMiddleName(), customerAdvisorMappingTb.getMasterAdvisorTb().getLastName()));
        rateAdvisorVO.setOrganization(customerAdvisorMappingTb.getMasterAdvisorTb().getWorkOrganization());
        rateAdvisorVO.setJobTitle(customerAdvisorMappingTb.getMasterAdvisorTb().getJobTitle());
        Date startDate = customerAdvisorMappingTb.getRateAdvisorLastDate();
//        Date endDate = getEndDate(startDate);
        rateAdvisorVO.setEvaluationPeriod(getEvaluationPeriod(startDate, getEndDate(startDate)));
        Set<CustomerPortfolioTb> customerPortfolioTbs = customerAdvisorMappingTb.getCustomerPortfolioTbs();
        if (customerPortfolioTbs != null && !customerPortfolioTbs.isEmpty()) {
            CustomerPortfolioTb customerPortfolioTb = customerPortfolioTbs.iterator().next();
            rateAdvisorVO.setPortfolioAssigned(true);
            Float advisorPortfolioReturnsFromStart = ((BigDecimal) customerPortfolioTb.getAdvisorPortfolioReturnsFromStart()).floatValue();
            Float benchmarkReturnsFromStart = customerPortfolioTb.getBenchmarkReturnsFromStart() != null
                    ? ((BigDecimal) customerPortfolioTb.getBenchmarkReturnsFromStart()).floatValue() : ZERO;
//            rateAdvisorVO.setPortfolioReturn(PortfolioPerformanceUtil.getPortfolioPerformance(customerPortfolioSubReturns));
            rateAdvisorVO.setPortfolioReturn(advisorPortfolioReturnsFromStart);
            rateAdvisorVO.setBenchMark(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
//            rateAdvisorVO.setBenchMarkReturn(benchmarkReturns == null ? 0.0 : benchmarkReturns.doubleValue());
            rateAdvisorVO.setBenchMarkReturn(benchmarkReturnsFromStart);
            Integer outperformance = this.getPerformance(((BigDecimal) customerPortfolioTb.getAdvisorPortfolioReturnsFromStart()).floatValue(), benchmarkReturnsFromStart);
            rateAdvisorVO.setOutperformance(outperformance);
            rateAdvisorVO.setOutPerformanceText(getOutPerformaceText(outperformance));
        }
        return rateAdvisorVO;
    }

    /**
     * *
     * Updating Advisor after Rating by client
     *
     * @param rateAdvisorVO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer updateAdvisorRating(RateAdvisorVO rateAdvisorVO) {
        LOGGER.log(Level.INFO, "Advisor rating done by customer with relationId {0}", rateAdvisorVO.getRelationId());
        return rateAdvisorDAO.updateAdvisorRating(rateAdvisorVO.getTotalRate(), rateAdvisorVO.getRecommendationFrequency(),
                rateAdvisorVO.getRecommendationQuality(), rateAdvisorVO.getResponsivenessQuality(),
                rateAdvisorVO.getOutperformance(), rateAdvisorVO.getRelationId());
    }

    private static String getEvaluationPeriod(Date startDate, Date endDate) {
        return DateUtil.dateToString(startDate, EVAL_PERIOD_DATE_FORMAT) + "-" + DateUtil.dateToString(endDate, EVAL_PERIOD_DATE_FORMAT);
    }

    private String getAdvisorName(String firstName, String middleName, String lastName) {
        return new StringBuilder(firstName).append(SPACE).append(middleName != null ? middleName : EMPTY_STRING)
                .append(SPACE).append(lastName != null ? lastName : EMPTY_STRING).toString();
    }

    private Date getEndDate(Date startDate) {
        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        startDateCal.add(Calendar.MONTH, -3);
        return startDateCal.getTime();
    }

    /**
     * *
     * Identifying the Performance category of Advisor based on portfolio
     * returns and benchmark returns portfolio returns from start > benchmark
     * returns from start =OUT_PERFORM portfolio returns from start < benchmark
     * returns from start =UNDER_PERFORM @param advisorPFReturnFromStart
     * @
     *
     * p
     * aram benchmarkReturnsFromStart
     * @return
     */
    private Integer getPerformance(float advisorPFReturnFromStart, float benchmarkReturnsFromStart) {
        return advisorPFReturnFromStart > benchmarkReturnsFromStart ? OUT_PERFORM
                : advisorPFReturnFromStart < benchmarkReturnsFromStart ? UNDER_PERFORM : NEUTRAL;
    }

    /**
     * Retrieving Performance text fro advisor
     *
     * @param outperformace
     * @return
     */
    private String getOutPerformaceText(int outperformace) {
        String outperformanceText = EMPTY_STRING;
        switch (outperformace) {
            case 1:
                outperformanceText = OUTPERFORM_TXT;
                break;
            case -1:
                outperformanceText = UNDRERPERFORM_TXT;
                break;
            case 0:
                outperformanceText = NEUTRAL_TXT;
                break;
        }
        return outperformanceText;
    }
}
