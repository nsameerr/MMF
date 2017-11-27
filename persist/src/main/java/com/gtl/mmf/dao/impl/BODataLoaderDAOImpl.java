/**
 *
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IBODataLoaderDAO;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.MmfDailyClientBalanceTb;
import com.gtl.mmf.entity.MmfDailyTxnSummaryTb;
import com.gtl.mmf.entity.MmfRetPortfolioSplitup;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 08237
 *
 */
public class BODataLoaderDAOImpl implements IBODataLoaderDAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.BODataLoaderDAOImpl");
    @Autowired
    private SessionFactory sessionFactory;

    public int transferTxnData(Date scheduleDate) {
        int numRowInserted = sessionFactory.getCurrentSession().
                createSQLQuery("call copytxnfrombo_proc(:ScheduleDate)")
                .setParameter("ScheduleDate", scheduleDate)
                .executeUpdate();
        return numRowInserted;
    }

    public int transferCashbalance(Date scheduleDate) {
        int numRowInserted = sessionFactory.getCurrentSession().
                createSQLQuery("call copycashfrombo_proc(:ScheduleDate)")
                .setParameter("ScheduleDate", scheduleDate)
                .executeUpdate();
        return numRowInserted;
    }

    public int transferOffDayDummydata(Date scheduleDate) {
        int numRowInserted = sessionFactory.getCurrentSession().
                createSQLQuery("call copy_offday_frombo_proc(:ScheduleDate)")
                .setParameter("ScheduleDate", scheduleDate)
                .executeUpdate();
        return numRowInserted;
    }

    public int isJobScheduled(String type, String status) {
        int numRowInserted = ((BigInteger) sessionFactory.getCurrentSession().
                createSQLQuery("call job_schedule_check_proc(:type,:status)")
                .setParameter("type", type)
                .setParameter("status", status)
                .uniqueResult()).intValue();
        return numRowInserted;
    }

    public int clearTxnAndCash(String type, String status) {
        int numRowRemoved = ((BigInteger) sessionFactory.getCurrentSession().
                createSQLQuery("call clear_txn_cash_proc(:type,:status)")
                .setParameter("type", type)
                .setParameter("status", status)
                .uniqueResult()).intValue();
        return numRowRemoved;
    }

    public List<Object> jobScheduleList() {
        List<Object> responseList = new ArrayList<Object>();
        /**
         * Query 1 returns job schedule list from mmfdb Query 2 returns job
         * schedule list from mmf back office db;
         */
        responseList.add(sessionFactory.getCurrentSession().createQuery("FROM JobScheduleTb").list());
        //responseList.add(sessionFactory.getCurrentSession().createSQLQuery("call `daily_job_schedule_list_proc`()").list());
        return responseList;
    }

    public Object getDeletedTableNames(Date EODDate, String DbName) {
        String sql = " SELECT GROUP_CONCAT('`',table_name,'`')"
                + " FROM information_schema.tables"
                + " WHERE TABLE_SCHEMA = :DbName"
                + " AND TABLE_NAME LIKE CONCAT('%',(DATE_SUB(DATE_FORMAT(:MAXScheduleDate,'%Y-%m-%d'),INTERVAL 5 DAY)))";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("DbName", DbName);
        sqlQuery.setDate("MAXScheduleDate", EODDate);
        return sqlQuery.uniqueResult();

    }

    public void renameTablesAndClear5DayOlderData(String reNameQuery1, String reNameQuery2, String reNameQuery3, String dropQuery, Date maxScheduledate) {
        sessionFactory.getCurrentSession().createSQLQuery("call renametable_and_clear5day_older_data_frombo_proc(:ReNameQuery1,:ReNameQuery2,:ReNameQuery3,:DropQuery,:MaxScheduleDate)")
                .setParameter("ReNameQuery1", reNameQuery1)
                .setParameter("ReNameQuery2", reNameQuery2)
                .setParameter("ReNameQuery3", reNameQuery3)
                .setParameter("DropQuery", dropQuery)
                .setParameter("MaxScheduleDate", maxScheduledate)
                .executeUpdate();
    }

    public int transferCashFlowData() {
        int numRowInserted = sessionFactory.getCurrentSession().
                createSQLQuery("call copy_cash_flow_frombo_proc()")
                .executeUpdate();
        return numRowInserted;
    }

    public int transferCashbalance(List<MmfDailyClientBalanceTb> clientBalanceTbs) {
        for (MmfDailyClientBalanceTb mmfDailyClientBalanceTb : clientBalanceTbs) {
            sessionFactory.getCurrentSession().save(mmfDailyClientBalanceTb);
        }
        return 0;
    }

    public int transferTxnData(List<MmfDailyTxnSummaryTb> transactionTbs) {
        for (MmfDailyTxnSummaryTb mmfDailyTxnSummaryTb : transactionTbs) {
            sessionFactory.getCurrentSession().save(mmfDailyTxnSummaryTb);
        }
        return 0;
    }

    public int transferCashFlowData(List<CashFlowTb> cashFlow) {
        for (CashFlowTb cashFlowTb : cashFlow) {
            String sql = "SELECT * FROM `cash_flow_tb` "
                    + " WHERE `trade_code`= :tradeCode AND `pay_in`= :payIn AND `pay_out`= :payOut "
                    + " AND DATE_FORMAT(`tran_date`,'%Y-%m-%d') = DATE_FORMAT(:trnDate,'%Y-%m-%d')";
            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setString("tradeCode", cashFlowTb.getTradeCode()).setBigDecimal("payIn", cashFlowTb.getPayIn());
            sqlQuery.setBigDecimal("payOut", cashFlowTb.getPayOut()).setDate("trnDate", cashFlowTb.getTranDate());
            if (sqlQuery.list().isEmpty()) {
                sessionFactory.getCurrentSession().save(cashFlowTb);
            }
        }
        return 0;
    }

    public Integer transferOffDayDummydata(JobScheduleTb jobScheduleTb) {
        jobScheduleTb.setStatus("COMPLETED");
        Integer id = (Integer) sessionFactory.getCurrentSession().save(jobScheduleTb);
        return id;
    }

    public void transferPositionData(List<MmfRetPortfolioSplitup> posData) {
        for (MmfRetPortfolioSplitup MmfRetPortfolioSplitup : posData) {
            sessionFactory.getCurrentSession().save(MmfRetPortfolioSplitup);
        }
    }

    public int clearMmfCashFlow() {
        int rowaffected = 0;
        String hql = "truncate table cash_flow_tb";
        try {
            rowaffected = sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
        } catch (HibernateException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        }
        return rowaffected;
    }
}
