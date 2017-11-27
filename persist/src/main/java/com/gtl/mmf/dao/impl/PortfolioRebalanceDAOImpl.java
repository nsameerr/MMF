/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.common.JobScheduleType;
import com.gtl.mmf.common.JobType;
import com.gtl.mmf.dao.IPortfolioRebalanceDAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.persist.vo.RebalanceStateAssetVO;
import com.gtl.mmf.persist.vo.RebalanceStateSecurityVO;
import com.gtl.mmf.persist.vo.RebalanceStateVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class PortfolioRebalanceDAOImpl implements IPortfolioRebalanceDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static final int BATCH_SIZE = 10;
    private static final Integer ZERO = 0;
    private static final int MINUS_ONE = -1;

    public Long getPortfoliosToCheckSize() {
        String hql = "SELECT COUNT(*) FROM PortfolioTb WHERE status =:statusCheck";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("statusCheck", true);
        return (Long) query.uniqueResult();
    }

    public Long getClientPortfoliosToCheckSize() {
        String hql = "SELECT COUNT(*) FROM CustomerPortfolioTb WHERE status =:statusCheck";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("statusCheck", true);
        return (Long) query.uniqueResult();
    }

    public List<PortfolioTb> getPortfoliosToCheck() {
        String hql = "FROM PortfolioTb WHERE status =:statusCheck";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("statusCheck", true);
        return query.list();
    }

    public List<CustomerPortfolioTb> getClientPortfoliosToCheck() {
        String hql = "FROM CustomerPortfolioTb WHERE status =:statusCheck";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("statusCheck", true);
        return query.list();
    }

    public void updateRebalancePortfolio(RebalanceStateVO rebalanceStateVO) {
        String hql = "UPDATE PortfolioTb SET rebalanceRequired = :rebalanceRequired, rebalanceReqdDate = :rebalanceReqdDate"
                + ",rebalanceViewed =:rebalanceViwed"
                + " WHERE portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("rebalanceRequired", rebalanceStateVO.getRebalanceRequired())
                .setTimestamp("rebalanceReqdDate", rebalanceStateVO.getRebalanceRequiredDate())
                .setInteger("portfolioId", rebalanceStateVO.getPortfolioId());
        query.setBoolean("rebalanceViwed", rebalanceStateVO.isRebalanceViwed());

        query.executeUpdate();
    }

    public CustomerPortfolioTb getCustomerDetails(Integer customerPortfolioId) {
        String hql = "FROM CustomerPortfolioTb WHERE customerPortfolioId = :customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerPortfolioId", customerPortfolioId);
        return (CustomerPortfolioTb) query.uniqueResult();
    }

    public PortfolioTb getAdvisorDetails(Integer portfolioId) {
        String hql = "FROM PortfolioTb WHERE portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", portfolioId);
        return (PortfolioTb) query.uniqueResult();
    }

    public void updateRebalancePortfolioSecurities(List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs, int portfolioId) {
        int count_mode = 0;
        String hql = "UPDATE PortfolioSecuritiesTb"
                + " SET currentPrice = :currentPrice, currentValue = :currentValue, "
                + "currentWeight = :currentWeight, rebalanceRequired = :rebalanceRequired,"
                + " rebalanceReqdDate = :rebalanceReqdDate,yesterDayUnitCount=:yesterDayUnitCount"
                + " WHERE portfolioTb.portfolioId = :portfolioId AND portfolioSecuritiesId = :portfolioSecuritiesId";
        for (RebalanceStateSecurityVO rebalanceStateSecurityVO : rebalanceStateSecurityVOs) {
            count_mode++;
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setDouble("currentPrice", rebalanceStateSecurityVO.getCurrentPrice())
                    .setDouble("currentValue", rebalanceStateSecurityVO.getCurrentValue())
                    .setDouble("currentWeight", rebalanceStateSecurityVO.getCurrentWeight())
                    .setBoolean("rebalanceRequired", rebalanceStateSecurityVO.isRebalanceRequired())
                    .setTimestamp("rebalanceReqdDate", rebalanceStateSecurityVO.getRebalanceRequiredDate())
                    .setInteger("portfolioSecuritiesId", rebalanceStateSecurityVO.getSecurityId())
                    .setBigDecimal("yesterDayUnitCount", new BigDecimal(rebalanceStateSecurityVO.getYesterDayUnitCount()))
                    .setInteger("portfolioId", portfolioId);
            query.executeUpdate();
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public void updateRebalancePortfolioDetails(List<RebalanceStateAssetVO> rebalanceStateAssetVOs, int portfolioId) {
        int count_mode = 0;
        String hql = "UPDATE PortfolioDetailsTb"
                + " SET currentValue = :currentValue, currentWeight = :currentWeight, rebalanceRequired = :rebalanceRequired, rebalanceReqdDate = :rebalanceReqdDate"
                + " WHERE portfolioTb.portfolioId = :portfolioId AND masterAssetTb.id = :assetId";
        for (RebalanceStateAssetVO rebalanceAssetSecurityVO : rebalanceStateAssetVOs) {
            count_mode++;
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setDouble("currentValue", rebalanceAssetSecurityVO.getCurrentValue())
                    .setDouble("currentWeight", rebalanceAssetSecurityVO.getCurrentWeight())
                    .setBoolean("rebalanceRequired", rebalanceAssetSecurityVO.isRebalanceRequired())
                    .setTimestamp("rebalanceReqdDate", rebalanceAssetSecurityVO.getRebalanceRequiredDate())
                    .setInteger("assetId", rebalanceAssetSecurityVO.getAssetId())
                    .setInteger("portfolioId", portfolioId);
            query.executeUpdate();
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public void updateRebalanceCustomerPortfolio(RebalanceStateVO rebalanceStateVO) {
        String hql = "UPDATE CustomerPortfolioTb SET rebalanceRequired = :rebalanceRequired,"
                + " rebalanceReqdDate = :rebalanceReqdDate,rebalanceViewed=:rebalanceViewed"
                + " WHERE customerPortfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("rebalanceRequired", rebalanceStateVO.getRebalanceRequired())
                .setTimestamp("rebalanceReqdDate", rebalanceStateVO.getRebalanceRequiredDate())
                .setInteger("portfolioId", rebalanceStateVO.getPortfolioId())
                .setBoolean("rebalanceViewed", false);
        query.executeUpdate();
    }

    public void updateRebalanceCustomerPortfolioDetails(List<RebalanceStateAssetVO> rebalanceStateAssetVOs, int cutomerPortfolioId) {
        int count_mode = 0;
        String hql = "UPDATE CustomerPortfolioDetailsTb"
                + " SET currentWeight = :currentWeight"
                + " WHERE portfolioDetailsTb.portfolioDetailsId= :portfolioDetailsId"
                + " AND customerPortfolioTb.customerPortfolioId =:customerPortfolioId";
        for (RebalanceStateAssetVO rebalanceAssetSecurityVO : rebalanceStateAssetVOs) {
            count_mode++;
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setDouble("currentWeight", rebalanceAssetSecurityVO.getCurrentWeight())
                    .setInteger("portfolioDetailsId", rebalanceAssetSecurityVO.getAssetId())
                    .setInteger("customerPortfolioId", cutomerPortfolioId);
            query.executeUpdate();
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public void updateRebalanceCustomerPortfolioSecurities(List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs, int cutomerPortfolioId) {
        int count_mode = 0;
        String hql = "UPDATE CustomerPortfolioSecuritiesTb"
                + " SET currentPrice = :currentPrice, currentValue = :currentValue,"
                + " currentWeight = :currentWeight, rebalanceRequired = :rebalanceRequired,"
                + " rebalanceReqdDate = :rebalanceReqdDate "
                + " WHERE portfolioSecuritiesTb.portfolioSecuritiesId =:portfolioSecuritiesId "
                + " And customerPortfolioTb.customerPortfolioId=:customerPortfolioId";
        for (RebalanceStateSecurityVO rebalanceStateSecurityVO : rebalanceStateSecurityVOs) {
            count_mode++;
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setFloat("currentPrice", (Float) rebalanceStateSecurityVO.getCurrentPrice().floatValue())
                    .setFloat("currentValue", (Float) rebalanceStateSecurityVO.getCurrentValue().floatValue())
                    .setFloat("currentWeight", (Float) rebalanceStateSecurityVO.getCurrentWeight().floatValue())
                    .setBoolean("rebalanceRequired", rebalanceStateSecurityVO.isRebalanceRequired())
                    .setTimestamp("rebalanceReqdDate", rebalanceStateSecurityVO.getRebalanceRequiredDate())
                    .setInteger("portfolioSecuritiesId", rebalanceStateSecurityVO.getSecurityId())
                    .setInteger("customerPortfolioId", cutomerPortfolioId);

            query.executeUpdate();
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public void updateNotificationStatus(Integer status,String jobType, Boolean dummyFlag) {
        Integer rowCount;
        SQLQuery sqlQuery;
        /**
         * FOR TEST CASE MANAGEMENT ONLY
         * if no order is placed in the trading system for the previous day
         * and.There will be no entry in JobScheduleTb
         * for that day. This if() loop will insert DUMMY entry in JobScheduleTb
         * when there is no trading.
         * In the real scenario while transfer data from BODB, dummy entry will
         * be inserted from back office side.no need to insert it manually
         */
        if (dummyFlag) {
                String hql = "INSERT INTO `job_schedule_tb`"
                        + " (`job_type`,`status`,`scheduledate`,`lastupdatedon`)"
                        + " VALUES(:Type,:Status,:ScheduleDate,:ScheduleDate)";
                sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(hql);
                sqlQuery.setString("Type", jobType);
                sqlQuery.setString("Status", JobScheduleType.COMPLETED.toString());
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, MINUS_ONE); 
                sqlQuery.setParameter("ScheduleDate", cal.getTime());
                sqlQuery.executeUpdate();
        }
        
        
        // Allows place order in oms only when there is no blocked cash in customer portfolio table
        String hql = "UPDATE CustomerAdvisorMappingTb SET relationStatus = :NewStatus WHERE relationStatus = :Status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("NewStatus", 401);
        query.setInteger("Status", status);
        query.executeUpdate();
       
    }

}
