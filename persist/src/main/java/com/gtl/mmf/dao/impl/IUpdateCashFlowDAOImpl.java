/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.dao.IUpdateCashFlowDAO;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class IUpdateCashFlowDAOImpl implements IUpdateCashFlowDAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.IUpdateCashFlowDAOImpl");
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private IExceptionLogDAO exceptionLogDAO;
    
    private static final String PRODUCTION = "PRODUCTION";

    public List<Object[]> loadDailyCashFlow(Date eodDate) {
//        String sql = "SELECT T1.`trade_code`,SUM(T1.`pay_in`),SUM(T1.`pay_out`)"
//                + " FROM (SELECT DISTINCT `trade_code`,`pay_in`,`pay_out` FROM `cash_flow_tb` "
//                + " WHERE DATE_FORMAT(`tran_date`,'%Y-%m-%d')= DATE_FORMAT(:eod,'%Y-%m-%d'))T1"
//                + " GROUP BY  T1.`trade_code`";
//        SQLQuery sqlq = sessionFactory.getCurrentSession().createSQLQuery(sql);
//        sqlq.setDate("eod", eodDate);
//        return sqlq.list();
        String sql = "SELECT T1.`trade_code`,SUM(T1.`pay_in`),SUM(T1.`pay_out`)"
                + " FROM (SELECT DISTINCT `trade_code`,`pay_in`,`pay_out` FROM `cash_flow_tb` "
                + " WHERE `processed` = :status)T1"
                + " GROUP BY  T1.`trade_code`";
        SQLQuery sqlq = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlq.setBoolean("status", false);
        return sqlq.list();
    }

    public void recalculateAllocatedFund(CashFlowTb cashFlowTb, String environmentType) {
        try {
            if (!environmentType.equalsIgnoreCase(PRODUCTION)) {
                //for test case management only
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, -1);
                String sqlquery = "CALL updateCashBalnceOmsDisabledCase(:omsLoginId,:payIn,:payOut,:date)";
                SQLQuery sqlQuery1 = sessionFactory.getCurrentSession().createSQLQuery(sqlquery);
                sqlQuery1.setString("omsLoginId", cashFlowTb.getTradeCode())
                        .setParameter("payIn", cashFlowTb.getPayIn())
                        .setParameter("payOut", cashFlowTb.getPayOut())
                        .setDate("date", cal.getTime()).executeUpdate();
            }
            String sql = "CALL updateCashFlowDetails(:tradeCode,:payIn,:payOut,:trndate)";
            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setParameter("tradeCode", cashFlowTb.getTradeCode());
            sqlQuery.setParameter("payIn", cashFlowTb.getPayIn());
            sqlQuery.setParameter("payOut", cashFlowTb.getPayOut());
            sqlQuery.setParameter("trndate", cashFlowTb.getTranDate());
            sqlQuery.executeUpdate();

        } catch (HibernateException ex) {
            exceptionLogDAO.logErrorToDb("Update pay-in pay-out stored procedure failed.", StackTraceWriter.getStackTrace(ex));
        }

    }

    public List<Object> getAllCustomersHavingRelation() {
        List<Object> resultList = new ArrayList<Object>();
        String hql = "From CustomerAdvisorMappingTb";
        Query sqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        resultList.add(sqlQuery.list());
        return resultList;
    }

    public Object[] calculateInvestorManagementFee(CustomerAdvisorMappingTb mapping, Date ecsPaydate, boolean quarter, CustomerPortfolioTb customerTb) {
        Object[] managementFee = null;

        String sql = "CALL sp_ManagementFeeCalculation(:relationId,"
                + " :quarter,:feeVariable,:totalFundAllocated,:feeType,"
                + " :feeFixed,:contractDate,:ecsDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        managementFee = (Object[]) sqlQuery.setParameter("relationId", mapping.getRelationId())
                .setBoolean("quarter", quarter)
                .setParameter("feeVariable", mapping.getManagementFeeVariable())
                .setParameter("totalFundAllocated", mapping.getAllocatedFunds())
                .setParameter("feeType", mapping.getMgmtFeeTypeVariable())
                .setParameter("feeFixed", mapping.getManagementFeeFixed())
                .setTimestamp("contractDate", mapping.getContractStart())
                .setTimestamp("ecsDate", ecsPaydate).uniqueResult();

        if (customerTb != null && customerTb.getCashFlowDflag()) {
            String sql2 = "UPDATE customer_portfolio_tb SET cashFlowDFlag = :status,cashFlow = :cash"
                    + " WHERE relation_id = :relation AND portfolio_status=:portfolio_status";
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql2);
            query.setBoolean("status", false);
            query.setBigDecimal("cash", BigDecimal.ZERO);
            query.setInteger("relation", customerTb.getCustomerAdvisorMappingTb().getRelationId());
            query.setString("portfolio_status","ACTIVE");
            query.executeUpdate();
        }

        return managementFee;
    }

    public Object[] calculateInvestorPerformanceFee(CustomerAdvisorMappingTb customer) {
        String sql = "CALL performance_fee_over_portfolio_returns_sp(:relationId,:cDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return (Object[]) sqlQuery.setInteger("relationId", customer.getRelationId()).setDate("cDate", new Date()).uniqueResult();
    }

    public List<Object> createUserListforFailedDebtPay(Integer reltaionId) {

        List<Object> responeList = new ArrayList<Object>();
        try {
            String hql = "From CustomerManagementFeeTb WHERE ecsPaid =:Status AND "
                    + "submittedEcs =:Submitted And customerAdvisorMappingTb.relationId=:relationId";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setBoolean("Status", false);
            query.setBoolean("Submitted", false);
            query.setInteger("relationId", reltaionId);
            responeList.add(query.list());

            String sql = "From CustomerPerformanceFeeTb WHERE ecsPaid =:Status AND"
                    + " submittedEcs =:Submitted And customerAdvisorMappingTb.relationId=:relationId";
            Query sQuery = sessionFactory.getCurrentSession().createQuery(sql);
            sQuery.setBoolean("Status", false);
            sQuery.setBoolean("Submitted", false);
            sQuery.setInteger("relationId", reltaionId);
            responeList.add(sQuery.list());
        } catch (HibernateException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        }
        return responeList;
    }

    public CustomerPortfolioTb getCustomerPortfolio(int relationId) {
        String hql = "FROM CustomerPortfolioTb WHERE customerAdvisorMappingTb.relationId = :customerId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", relationId);
        return (CustomerPortfolioTb) query.uniqueResult();
    }

}
