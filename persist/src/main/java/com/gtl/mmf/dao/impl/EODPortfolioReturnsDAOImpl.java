/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.common.JobScheduleType;
import com.gtl.mmf.dao.IEODPortfolioReturnsDAO;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTwrPortfolioReturnTb;
import com.gtl.mmf.entity.RecomendedCustomerPortfolioSecuritiesTb;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EODPortfolioReturnsDAOImpl implements IEODPortfolioReturnsDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static final Integer ZERO = 0;
    private static final int MINUS_ONE = -1;
    private static final int BATCH_SIZE = 10;

    public List<Object> getAllClientPortfolioDetails() {
        List<Object> responeList = new ArrayList<Object>();

        String hql = " FROM CustomerPortfolioTb WHERE portfolioStatus =:status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("status","ACTIVE");
        responeList.add(query.list());

        hql = "SELECT MAX(scheduledate) FROM JobScheduleTb WHERE status =:Status";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("Status", JobScheduleType.COMPLETED.toString());
        responeList.add(query.uniqueResult());

        return responeList;
    }

    public Date getEODDate() {

        String hql = "SELECT MAX(scheduledate) FROM JobScheduleTb WHERE status =:Status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("Status", JobScheduleType.COMPLETED.toString());
        return (Date) query.uniqueResult();
    }

    public void calculateClientPortfolioReturns(CustomerPortfolioTb customerPortfolioTb, String EODdate) {
        String sql = "CALL eod_client_portfolio_returns_sp(:EOD_Date,:CustomerPortfolioId)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("EOD_Date", EODdate);
        sqlQuery.setParameter("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.executeUpdate();
    }

    public void calculateREcommendedPortfolioRerturns(String EOD_date, String currentDate) {
        String sql = "CALL eod_master_portfolio_returns_sp(:EOD_Date,:current_Date)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("EOD_Date", EOD_date);
        sqlQuery.setParameter("current_Date", currentDate);
        sqlQuery.executeUpdate();
    }

    public List<Integer> getAllCustomerPortfolio() {
        String hql = "SELECT customerPortfolioId FROM CustomerPortfolioTb WHERE portfolioStatus=:portfolio_status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("portfolio_status","ACTIVE");
        return query.list();
    }

    public Integer updateCustomerPFPerformance(int portfolioId) throws Exception {
        String sql = "CALL eod_client_portfolio_performance_sp(:portfolioId)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setParameter("portfolioId", portfolioId)
                .executeUpdate();
    }

    public List<Integer> getRecommededPortfolios() {
        String hql = "SELECT T3.portfolioId FROM MasterAdvisorTb AS T1 "
                + "INNER JOIN T1.masterApplicantTb AS T2 "
                + "INNER JOIN T1.portfolioTbs AS T3 "
                + "WHERE T2.status = :activeStatus AND T2.isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("Active", Boolean.TRUE);
        return query.setInteger("activeStatus", 100)
                .list();
    }

    public Integer updateRecmdPFPerformance(int portfolioId) throws Exception {
        String sql = "CALL eod_recmd_portfolio_performance_sp(:portfolioId)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setParameter("portfolioId", portfolioId)
                .executeUpdate();
    }

    public Integer updateBenchmarkPerformance() throws Exception {
        String sql = "CALL eod_benchmark_performance_sp()";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.executeUpdate();
    }

    public List<Integer> getRelationsToCalculateMgmtFee(Date currentDate) {
        String sql = "SELECT a.relation_id FROM customer_advisor_mapping_tb a"
                + " WHERE a.contract_start IS NOT NULL"
                + " AND DATE(DATE_ADD(IFNULL(a.mgmt_fee_last_calculated, a.contract_start), INTERVAL 3 MONTH)) = :currentDate";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setDate("currentDate", currentDate).list();
    }

    public List<Integer> getRelationsToCalculatePerfFee(Date currentDate) {
        String sql = "SELECT a.relation_id FROM customer_advisor_mapping_tb a"
                + " WHERE a.contract_start IS NOT NULL"
                + " AND DATE(DATE_ADD(IFNULL(a.perf_fee_last_calculated, a.contract_start),"
                + " INTERVAL a.duration_frequency_month MONTH)) = :currentDate";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setDate("currentDate", currentDate).list();
    }

    public Object[] updateManagementFeeOfRelation(Integer relationId, Date currentDate, Date ecsPayDate) {
        String sql = "CALL eod_update_management_fee_sp(:relationId,:currentDate,:dueDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return (Object[]) sqlQuery.setInteger("relationId", relationId)
                .setTimestamp("currentDate", currentDate)
                .setTimestamp("dueDate", ecsPayDate)
                .uniqueResult();
    }

    public Object[] updatePerformanceFeeOfRelation(Integer relationId, Date currentDate, Date ecsPayDate) {
        String sql = "CALL eod_update_performance_fee_sp(:relationId,:currentDate,:dueDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return (Object[]) sqlQuery.setInteger("relationId", relationId)
                .setTimestamp("currentDate", currentDate)
                .setTimestamp("dueDate", ecsPayDate)
                .uniqueResult();
    }

    public List<Object[]> getAddOrReedemStatusForCustomer(int portfolioId, Date currentDate) {
        String sql = "SELECT t1.addfund, t1.for_amount,"
                + " t2.venue_name FROM  add_redeem_log_tb t1,"
                + " customer_portfolio_tb t2 WHERE "
                + "t1.customer_portfolio_id = :portfolioId AND "
                + "DATE_FORMAT(t1.date_of_entry,'%Y-%m-%d') = "
                + "DATE_FORMAT(:edate,'%Y-%m-%d')";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("portfolioId", portfolioId);
        sqlQuery.setDate("edate", currentDate);
        return sqlQuery.list();
    }

    public Integer updateBenchmarkUnit(CustomerPortfolioTb customerPortfolioTb) {
        String hql = " UPDATE  CustomerPortfolioTb"
                + " SET benchmarkUnit =:benchmarkUnit"
                + " where customerPortfolioId =:customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBigDecimal("benchmarkUnit", customerPortfolioTb.getBenchmarkUnit());
        query.setInteger("customerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        return query.executeUpdate();
    }

    /**
     * Updating recommended customer portfolio with new transaction details and
     * current market price
     *
     * @param portfolioSecuritiesTbbList
     * @param cashFlow
     * @param customerPortfolioId
     */
    public void upadetRecomendedCustomerPortfolio(List<RecomendedCustomerPortfolioSecuritiesTb> portfolioSecuritiesTbbList,
            boolean cashFlow, int customerPortfolioId) {
        int count_mode = 0;
        for (RecomendedCustomerPortfolioSecuritiesTb securitiesTb : portfolioSecuritiesTbbList) {
            count_mode++;
            sessionFactory.getCurrentSession().saveOrUpdate(securitiesTb);
            sessionFactory.getCurrentSession().flush();
        }

        if (cashFlow) {
            String hql = "UPDATE CustomerPortfolioTb T1"
                    + " SET T1.recomendedCashBal = T1.allocatedFund - (SELECT SUM(T2.requiredUnits * T2.currentPrice) "
                    + " FROM RecomendedCustomerPortfolioSecuritiesTb T2 "
                    + " WHERE T2.customerPortfolioTb.customerPortfolioId = :CustomerPortfolioId)"
                    + " WHERE T1.customerPortfolioId = :CustomerPortfolioId";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("CustomerPortfolioId", customerPortfolioId);
            query.executeUpdate();
        }

    }

    public Integer eodClientPortflioDailyOpeningClosingCalculation(int Customer_PortfolioId, Date currentDate) {
        String sql = "CALL eod_client_portflio_daily_opening_closing_proc(:currentDate,:Customer_PortfolioId)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setInteger("Customer_PortfolioId", Customer_PortfolioId)
                .setTimestamp("currentDate", currentDate)
                .executeUpdate();
    }

    /**
     * This method is used to get the transaction amount.
     *
     * @param Customer_Id
     * @param add_reedem_date
     * @return
     */
    public BigDecimal getTransactionAmount(int Customer_Id, Date add_reedem_date) {
        String hql = "SELECT sum(security_units*security_price) AS reedemFund FROM "
                + " customer_transaction_execution_details_tb T1 "
                + " INNER JOIN customer_portfolio_tb T2 ON (T2.customer_id= T1.customer_id "
                + " AND T2.portfolio_id = T1.portfolio_id) "
                + " WHERE DATE_FORMAT(T1.order_date,'%Y-%m-%d') =DATE_FORMAT(:CURRENT_DATE,'%Y-%m-%d') "
                + " AND T1.customer_id = :customer_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.setInteger("customer_id", Customer_Id);
        query.setDate("CURRENT_DATE", add_reedem_date);
        return (BigDecimal) query.uniqueResult();
    }

    public Integer updateReedemAmount(int Customer_Id, Date add_reedem_date, BigDecimal amount) {
        String hql = " UPDATE  add_redeem_log_tb"
                + " SET for_amount =:Amount"
                + " where customer_portfolio_id =:customerPortfolioId AND "
                + "  DATE_FORMAT(date_of_entry,'%Y-%m-%d') =DATE_FORMAT(:E_Date,'%Y-%m-%d')";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.setBigDecimal("Amount", amount);
        query.setInteger("customerPortfolioId", Customer_Id);
        query.setDate("E_Date", add_reedem_date);
        return query.executeUpdate();
    }

    public String getVenueName(int Customer_Id) {
        String sql = "SELECT venueName FROM CustomerPortfolioTb where"
                + " customerPortfolioId = :customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setInteger("customerPortfolioId", Customer_Id);
        return query.uniqueResult().toString();
    }

    public List<Object> getRecomendedPortfolioSecuritiesTB(CustomerPortfolioTb customerPortfolioTb) {
        List<Object> responseList = new ArrayList<Object>();
        String hql = " FROM RecomendedCustomerPortfolioSecuritiesTb WHERE "
                + "customerPortfolioTb.customerPortfolioId =:customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());

        /**
         * finding minimum AUM to calculate required_units required_units = PPV/
         * CMP PPV = (allocation for security * min AUM of allocated fund)/ 100
         *
         */
//        String sql2 = "SELECT IFNULL((SELECT min_aum FROM master_portfolio_size_tb "
//                + " WHERE :allocatedfund BETWEEN min_aum AND max_aum) "
//                + " ,(SELECT MAX(min_aum) FROM master_portfolio_size_tb))";
//        SQLQuery sqlq = sessionFactory.getCurrentSession().createSQLQuery(sql2);
//        sqlq.setDouble("allocatedfund", customerPortfolioTb.getAllocatedFund());

        responseList.add(query.list());
//        responseList.add(sqlq.uniqueResult());

        return responseList;
    }

    public List<CustomerPortfolioSecuritiesTb> getCustomerPortfolioSecuritiesTB(int portfolioId) {
        String sql = " FROM CustomerPortfolioSecuritiesTb WHERE "
                + "customerPortfolioTb.customerPortfolioId =:customerPortfolioId";
        Query hquery = sessionFactory.getCurrentSession().createQuery(sql);
        hquery.setInteger("customerPortfolioId", portfolioId);
        return hquery.list();
    }

    public void upadetCustomerPortfolioSecurites(List<CustomerPortfolioSecuritiesTb> securityListCustomer) {
        for (CustomerPortfolioSecuritiesTb cSecuritiesTb : securityListCustomer) {
            sessionFactory.getCurrentSession().saveOrUpdate(cSecuritiesTb);
            sessionFactory.getCurrentSession().flush();
        }
    }

    public List<CashFlowTb> getPayInOut(String omsLoginId, Date eodDate) {
        String hql = "FROM CashFlowTb WHERE tradeCode = :omsid"
                + " AND DATE_FORMAT(tranDate,'%Y-%m-%d') = DATE_FORMAT( :eod,'%Y-%m-%d')";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("omsid", omsLoginId);
        query.setDate("eod", eodDate);
        return (List<CashFlowTb>) query.list();
    }
    
    public List<Object> getReurnsforSpecifiedPeriod(CustomerPortfolioTb customerPortfolioTb,int asset,Integer noOfdays, String EOD_Date) {
        List<Object> respnseList = new ArrayList<Object>();
        String sql = "CALL client_twr_portfolio_returns_sp("
                + ":CustomerPorfolioId,"
                + ":AssetId,"
                + ":Currentdate,"
                + ":NoOfDaysToBeBacked)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("CustomerPorfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setParameter("AssetId", asset);
        sqlQuery.setParameter("Currentdate", EOD_Date);
        sqlQuery.setParameter("NoOfDaysToBeBacked", noOfdays);
        respnseList.add((Object[]) sqlQuery.uniqueResult());

        sql = "CALL recommended_twr_portfolio_returns_sp("
                + ":PorfolioId,"
                + ":AssetId,"
                + ":Currentdate,"
                + ":NoOfDaysToBeBacked)";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("PorfolioId", customerPortfolioTb.getPortfolioTb().getPortfolioId());
        sqlQuery.setParameter("AssetId", asset);
        sqlQuery.setParameter("Currentdate", EOD_Date);
        sqlQuery.setParameter("NoOfDaysToBeBacked", noOfdays);
        respnseList.add(sqlQuery.list());
        return respnseList;

    }

    public List<CustomerTwrPortfolioReturnTb> getCustomerPortfolioTwrReturnTB(Integer customerPortfolioId) {
        String sql = " FROM CustomerTwrPortfolioReturnTb WHERE"
                + " customerPortfolioTb.customerPortfolioId =:customerPortfolioId"
                + " AND assetId NOT IN (:asset)";
        Query hquery = sessionFactory.getCurrentSession().createQuery(sql);
        hquery.setInteger("customerPortfolioId", customerPortfolioId);
        hquery.setInteger("asset", 10);
        return hquery.list();
    }

    public void updateCustomerTwrPortfolioReturnTbsList(List<CustomerTwrPortfolioReturnTb> customerTwrPortfolioReturnTbsList) {
        for (CustomerTwrPortfolioReturnTb customerTwrPortfolioReturnTb : customerTwrPortfolioReturnTbsList) {
           sessionFactory.getCurrentSession().update(customerTwrPortfolioReturnTb);
           sessionFactory.getCurrentSession().flush();
        }
    }
    
}
