/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by o7662
 */
package com.gtl.mmf.dao.impl;

import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.gtl.mmf.dao.IRebalancePortfolioDAO;
import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTransactionExecutionDetailsTb;
import com.gtl.mmf.entity.CustomerTransactionOrderDetailsTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.MmfDailyClientBalanceTb;
import com.gtl.mmf.entity.MmfDailyTxnSummaryTb;
import static java.lang.Boolean.FALSE;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RebalancePortfolioDAOImpl implements IRebalancePortfolioDAO {

    private static final int BATCH_SIZE = 20;
    private float ZERO = (float) 0.0000;

    @Autowired
    private SessionFactory sessionFactory;

    private static final String PRODUCTION = "PRODUCTION";

    /**
     * Generating re-balance page data from DB
     *
     * @param customerPortfolioTb
     * @return List<Object>
     */
    public List<Object> getRebalancePortfolioPageDetails(
            CustomerPortfolioTb customerPortfolioTb) {

        List<Object> responseItems = new ArrayList<Object>();

        String hql = "SELECT T1 FROM CustomerPortfolioTb AS T1"
                + " WHERE T1.masterCustomerTb =:MasterCustomerTb AND T1.portfolioStatus =:portfolio_status";
        Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setEntity("MasterCustomerTb",
                customerPortfolioTb.getMasterCustomerTb());
        hqlQuery.setString("portfolio_status", "ACTIVE");
        customerPortfolioTb = (CustomerPortfolioTb) hqlQuery.uniqueResult();
        responseItems.add(customerPortfolioTb);

        hql = "SELECT T1 FROM CustomerPortfolioDetailsTb AS T1"
                + " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb";
        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
        responseItems.add(hqlQuery.list());

        hql = "SELECT T1 FROM CustomerPortfolioSecuritiesTb AS T1"
                + " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb";
        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
        responseItems.add(hqlQuery.list());

        return responseItems;
    }

    /**
     * *
     *
     * Customer transaction details order details are saving into DB before
     * sending order
     *
     * @param customerTransactionOrderDetailsTbList
     */
    public void savetransactionBeforeExecute(
            List<CustomerTransactionOrderDetailsTb> customerTransactionOrderDetailsTbList) {

        int count_mode = 0;
        for (CustomerTransactionOrderDetailsTb customerTransactionOrderDetailsTb : customerTransactionOrderDetailsTbList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(
                    customerTransactionOrderDetailsTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        sessionFactory.getCurrentSession().flush();
    }

    public void savetransactionAfterExecute(
            CustomerPortfolioTb customerPortfolioTb) {
        Query hqlQuery;
        String hql;

        hql = "UPDATE CustomerPortfolioTb"
                + " SET noRebalanceStatus =:NoRebalanceStatus,"
                + " omsLoginId =:OMSLoginId,"
                + " portfolio_modified =:PortfolioModified,"
                + " rebalanceRequired =:RebalanceRequired,"
                + " lastExecutionUpdate =:LastExecutionUpdate"
                + " WHERE customerPortfolioId =:CustomerPortfolioId";
        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setBoolean("NoRebalanceStatus",
                customerPortfolioTb.getNoRebalanceStatus());
        hqlQuery.setString("OMSLoginId", customerPortfolioTb.getOmsLoginId());
        hqlQuery.setBoolean("PortfolioModified", customerPortfolioTb.getPortfolioModified());
        hqlQuery.setBoolean("RebalanceRequired",
                customerPortfolioTb.getRebalanceRequired());
        hqlQuery.setDate("LastExecutionUpdate",
                customerPortfolioTb.getLastExecutionUpdate());
        hqlQuery.setInteger("CustomerPortfolioId",
                customerPortfolioTb.getCustomerPortfolioId());
        hqlQuery.executeUpdate();

        hql = "UPDATE CustomerAdvisorMappingTb"
                + " SET relationStatus =:RelationStatus,"
                + " status_date = :statusDate "
                + " WHERE relationId =:RelationId";
        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setShort("RelationStatus", customerPortfolioTb
                .getCustomerAdvisorMappingTb().getRelationStatus());
        hqlQuery.setInteger("RelationId", customerPortfolioTb
                .getCustomerAdvisorMappingTb().getRelationId());

        //Updating Status date after placing order
        hqlQuery.setTimestamp("statusDate", new Date());
        hqlQuery.executeUpdate();
    }

    public void reclaculteOrderDetails(
            List<CustomerTransactionExecutionDetailsTb> customerTransactionOrderDetailsTbList,
            CustomerPortfolioTb customerPortfolioTb) {

        Query hqlQuery;
        String hql;
        int count_mode = 0;

        hql = "UPDATE CustomerPortfolioTb"
                + " SET lastExecutionUpdate =:LastExecutionUpdate,"
                + " cashAmount =:CashAmount"
                + " WHERE customerPortfolioId =:CustomerPortfolioId";
        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setDate("LastExecutionUpdate",
                customerPortfolioTb.getLastExecutionUpdate());
        hqlQuery.setFloat("CashAmount", customerPortfolioTb.getCashAmount());
        hqlQuery.setInteger("CustomerPortfolioId",
                customerPortfolioTb.getCustomerPortfolioId());
        hqlQuery.executeUpdate();

        for (CustomerTransactionExecutionDetailsTb customerTransactionExecutionDetailsTb : customerTransactionOrderDetailsTbList) {
            count_mode++;
            double exeUnits = customerTransactionExecutionDetailsTb.getBuySell()
                    .equalsIgnoreCase(BuySell.BUY.toString()) ? customerTransactionExecutionDetailsTb
                    .getSecurityUnits().doubleValue()
                    : -(customerTransactionExecutionDetailsTb.getSecurityUnits().doubleValue());
            hql = "UPDATE CustomerPortfolioSecuritiesTb T1"
                    + " SET T1.exeUnits = T1.exeUnits +:ExeUnits,"
                    + " T1.currentPrice =:CurrentPrice"
                    + " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb"
                    + " AND T1.portfolioTb =:PortfolioTb"
                    + " AND T1.venueCode =:VenueCode AND T1.venueScriptCode =:VenueScriptCode";
            hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
            hqlQuery.setBigDecimal("ExeUnits", new BigDecimal(exeUnits));
            hqlQuery.setBigDecimal("CurrentPrice",
                    customerTransactionExecutionDetailsTb.getSecurityPrice());
            hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
            hqlQuery.setEntity("PortfolioTb",
                    customerTransactionExecutionDetailsTb.getPortfolioTb());
            hqlQuery.setString("VenueCode",
                    customerTransactionExecutionDetailsTb.getVenueCode());
            hqlQuery.setString("VenueScriptCode",
                    customerTransactionExecutionDetailsTb.getVenueScriptCode());
            hqlQuery.executeUpdate();

            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        sessionFactory.getCurrentSession().flush();

        String sql = "UPDATE customer_portfolio_details_tb AS T1"
                + " INNER JOIN ("
                + " SELECT SUM(exe_units * current_price) AS Total,customer_portfolio_details_id"
                + " FROM customer_portfolio_securities_tb"
                + " WHERE customer_portfolio_id ="
                + customerPortfolioTb.getCustomerPortfolioId()
                + " GROUP BY asset_class_id"
                + ") AS T2 ON (T2.`customer_portfolio_details_id` = T1.`customer_portfolio_details_id`)"
                + " INNER JOIN `customer_portfolio_tb` AS T3 ON (T3.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " INNER JOIN `customer_advisor_mapping_tb` AS T4 ON (T4.`relation_id` = T3.`relation_id`)"
                + " SET T1.`current_allocation` = T2.Total/T3.cash_amount"
                + " WHERE T3.`customer_portfolio_id` ="
                + customerPortfolioTb.getCustomerPortfolioId();
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();

    }

    /**
     * This method is used to transfer transaction summery data into MMF DB. 1.
     * Adding execution summary into execution details table. 2. Adding cash
     * balance after execution. 3. Updating securities information with new data
     * after execution. 4.updating customer portfolio value 5.Block customer
     * from placing order till blocked cash =0
     *
     * @param customerPortfolioTb
     * @param eodDate
     */
    public void saveTransactionsEOD(CustomerPortfolioTb customerPortfolioTb, Date eodDate, String environment) {
        String sql;
        SQLQuery sqlQuery;
        sql = "INSERT INTO customer_transaction_execution_details_tb (`portfolio_id`,`trans_id`,`order_id`,`security_code`,"
                + "`venue_code`,`venue_script_code`,`customer_id`,`security_units`,`security_price`,`order_date`,`oms_userid`,`buy_sell`,`processed`)"
                + " SELECT :PortfolioId,:TransId,`orderno`,`security`,`product`,`venue_script_code`,:CustId,`quantity`+`units`,`price`,`trndate`,:OMSId,`buysell`,:processedStatus"
                + " FROM `mmf_daily_txn_summary_tb` WHERE tradecode =:LoginId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("PortfolioId", customerPortfolioTb.getPortfolioTb().getPortfolioId());
        sqlQuery.setLong("TransId", 0L);
        sqlQuery.setInteger("CustId", customerPortfolioTb.getMasterCustomerTb().getCustomerId());
        sqlQuery.setString("OMSId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setString("LoginId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setBoolean("processedStatus", false);
        sqlQuery.executeUpdate();

        //In the case of MF Buy blocked cash update with temporary blocked cash 
        //On eod if a customer has temporary blocked cash,It copied to blocked cash and set temp_block_cash to zero 
        if (customerPortfolioTb.getBlockedCash() == ZERO) {
            sql = "UPDATE`customer_portfolio_tb` SET `blocked_cash` = `temp_blocked_cash`,`temp_blocked_cash`=:Zero "
                    + "WHERE customer_id = :custId AND customer_portfolio_id = :custPortfolioId AND `oms_login_id` =:OMSId AND `portfolio_status` =:status";
            sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setInteger("custId", customerPortfolioTb.getMasterCustomerTb().getCustomerId());
            sqlQuery.setInteger("custPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
            sqlQuery.setString("OMSId", customerPortfolioTb.getOmsLoginId());
            sqlQuery.setFloat("Zero", ZERO);
            sqlQuery.setString("status", "ACTIVE");
            sqlQuery.executeUpdate();
        }
        
       //In the case of MF Sell blocked count update with temporary blocked count 
        //On eod if a customer has temporary blocked count,It copied to blocked count and set temp block count to zero
         if (customerPortfolioTb.getBlockedCount() == ZERO) {
            sql = "UPDATE`customer_portfolio_tb` SET `blocked_count` = `temp_blocked_count`,`temp_blocked_count`=:Zero "
                    + "WHERE customer_id = :custId AND customer_portfolio_id = :custPortfolioId AND `oms_login_id` =:OMSId AND `portfolio_status` =:status";
            sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setInteger("custId", customerPortfolioTb.getMasterCustomerTb().getCustomerId());
            sqlQuery.setInteger("custPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
            sqlQuery.setString("OMSId", customerPortfolioTb.getOmsLoginId());
            sqlQuery.setFloat("Zero", ZERO);
            sqlQuery.setString("status", "ACTIVE");
            sqlQuery.executeUpdate();
        }
         
        sql = "INSERT INTO customer_transaction_acquisition_details_tb (customer_id,portfolio_id,customer_portfolio_id,order_id,security_id,venue_code,venue_script_code,"
                + " transaction_unit,boughtOrSold_price, acquisition_cost,transaction_date,oms_id)"
                + " SELECT :CustId,:PortfolioId,:CustomerPortfolioId,`orderno`,`security`,`product`,`venue_script_code`,"
                + " (CASE WHEN `buysell`='B' THEN `quantity`+`units`  ELSE -(`quantity`+`units`) END),`price`,`volume`,`trndate`,:OMSId"
                + " FROM `mmf_daily_txn_summary_tb` WHERE tradecode =:LoginId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustId", customerPortfolioTb.getMasterCustomerTb().getCustomerId());
        sqlQuery.setInteger("PortfolioId", customerPortfolioTb.getPortfolioTb().getPortfolioId());
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setString("OMSId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setString("LoginId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.executeUpdate();

        sql = "INSERT INTO `customer_daily_cash_balance_tb`(`customer_portfolio_id`,`customer_id`,`omsLoginId`,`ledgerbalanec`,`tradedate`,`portfolio_id`,`lastupdatedon`)"
                + " SELECT :CustomerPortfolioId,:CustomerId,T1.`tradecode`,T1.`ledgerbalanec`,T1.`trndate`,:PortfolioId,:LastUpdatedOn"
                + " FROM `mmf_daily_client_balance_tb` T1 WHERE T1.`tradecode` =:UserId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerPortfolioId",
                customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setInteger("CustomerId", customerPortfolioTb
                .getMasterCustomerTb().getCustomerId());
        sqlQuery.setInteger("PortfolioId", customerPortfolioTb.getPortfolioTb()
                .getPortfolioId());
        sqlQuery.setDate("LastUpdatedOn", new Date());
        sqlQuery.setString("UserId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.executeUpdate();

        sql = " UPDATE `customer_portfolio_securities_tb` T1 "
                + "  INNER JOIN "
                + " ( SELECT TRN.`customer_id`,TRN.`portfolio_id`,TRN.`venue_code`,"
                + " TRN.`venue_script_code`,TRN.`security_code`,(TRN.securityUnits+t2.`exe_units`) AS Units,"
                + "  TRN.securityPrice,TRN.securityUnits,t2.`customer_portfolio_id`"
                + "  FROM `customer_portfolio_securities_tb` T2 "
                + "  INNER JOIN "
                + "  ( "
                + "SELECT T3.`customer_id`,T3.`portfolio_id`,T3.`venue_code`,T3.`venue_script_code`, "
                + " T3.`security_code`,SUM(CASE WHEN T3.`buy_sell` = 'B'"
                + "  THEN T3.`security_units` ELSE -T3.`security_units` END) AS securityUnits, "
                + " T3.`security_price` AS securityPrice,T3.`buy_sell` ,T3.`security_units`"
                + "  FROM customer_transaction_execution_details_tb T3 "
                + "  WHERE  T3.`customer_id` =:CustomerId "
                + " AND DATE_FORMAT(t3.`order_date`,'%Y-%m-%d')= DATE_FORMAT(:CurrentDate,'%Y-%m-%d')"
                + " GROUP BY T3.`venue_code`,T3.`venue_script_code`,T3.`security_code`,T3.`buy_sell`"
                + " ) AS TRN "
                + " ON (TRN.`portfolio_id` = T2.`portfolio_id` AND TRN.`venue_code` = T2.`venueCode`"
                + " AND TRN.`venue_script_code` = T2.`venueScriptCode`  AND TRN.`security_code` = T2.`security_code`"
                + " ) WHERE T2.`customer_portfolio_id` =:CustomerPortfoliId ) AS OT "
                + "  ON (OT.`portfolio_id` = T1.`portfolio_id` AND OT.`venue_code` = T1.`venueCode`"
                + "  AND OT.`venue_script_code` = T1.`venueScriptCode`  AND OT.`security_code` = T1.`security_code`"
                + "  AND OT.customer_portfolio_id= T1.`customer_portfolio_id`) "
                + "  INNER JOIN `customer_portfolio_tb` T4 ON (T4.`portfolio_id` = OT.`portfolio_id`"
                + "  AND T4.`customer_id` = OT.`customer_id` AND t4.`customer_portfolio_id`="
                + "  OT.customer_portfolio_id) "
                + " SET T1.`exe_units` = OT.Units,T1.`current_price`= OT.securityPrice,T1.`blocked_count` = T1.`blocked_count`-OT.securityUnits"
                + "  WHERE  T4.`customer_id` = :CustomerId AND t4.`customer_portfolio_id` =:CustomerPortfoliId";

        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerId", customerPortfolioTb
                .getMasterCustomerTb().getCustomerId());
        sqlQuery.setInteger("CustomerPortfoliId", customerPortfolioTb
                .getCustomerPortfolioId());
        sqlQuery.setDate("CurrentDate", eodDate);
        sqlQuery.executeUpdate();

        // Querry to update last bought price for security from txn sumary table
        sql = "UPDATE customer_portfolio_securities_tb T1,mmf_daily_txn_summary_tb T2"
                + " SET T1.lastBoughtPrice = T2.price"
                + " WHERE T1.customer_portfolio_id= :custPortfolioId AND T2.tradecode= :omsUserId AND"
                + " T1.venueScriptCode = T2.venue_script_code AND T2.security = T1.security_code";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("omsUserId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setInteger("custPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.executeUpdate();
        
        
        sql = "INSERT IGNORE INTO `customer_locked_securities_tb` (`customer_id`,`security_code`,`venue_script_code`,"
                + "`trade_code`,`securitiy_units`,`security_price`,`processed`)"
                + "SELECT T1.`customer_id`,T1.`security_code`,T2.`venue_script_code`,"
                + "T1.`oms_userid`,T1.`security_units`,T1.`security_price`,:Status "
                + "FROM `customer_transaction_execution_details_tb` T1"
                + "INNER JOIN `customer_transaction_order_details_tb` T2 ON DATE_FORMAT(T1.`order_date`,'%d-%m-%y')=DATE_FORMAT(T2.`order_date`,'%d-%m-%y') AND T1.`security_code`= T2.`security_code` "
                + "WHERE T1.`oms_userid` =:omsId AND T2.`asset_class_id`=:AssetId AND T1.`processed`=:Status";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("AssetId", 13);
        sqlQuery.setString("omsId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setBoolean("Status",FALSE);
        sqlQuery.executeUpdate();
        
        /**
         * For updating customer_portfolio_securities_tb with data from the
         * position details. updating units bought based on settled and
         * unsettled from position details. code is commented for pre-production
         * & development working. must uncomment for production
         *
         */
        if (environment.equalsIgnoreCase(PRODUCTION)) {
            sql = "CALL update_customer_position_details()";
            sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.executeUpdate();
        }

        sql = "UPDATE customer_portfolio_tb CT"
                + " INNER JOIN"
                + " ("
                + " SELECT T1.`ledgerbalanec` AS TotalCost FROM mmf_daily_client_balance_tb T1"
                + " WHERE T1.`tradecode` = :OMSLoginId AND T1.`trndate` ="
                + "(SELECT MAX(T2.`trndate`) FROM mmf_daily_client_balance_tb T2 WHERE T2.`tradecode` = :OMSLoginId)"
                + " ) AS TRN"
                + " INNER JOIN customer_advisor_mapping_tb T3 ON(T3.`customer_id` = CT.`customer_id`)"
                + " INNER JOIN master_customer_tb T4 ON(T4.`customer_id` = CT.`customer_id` AND T4.`is_active_user`= :Active)"
                + " SET CT.`cash_amount` = TRN.TotalCost"
                + " WHERE CT.`customer_portfolio_id` =:CustomerPortfolioId AND CT.`portfolio_status`=:status";

        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("OMSLoginId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setBoolean("Active", Boolean.TRUE);
        sqlQuery.setString("status","ACTIVE");
        sqlQuery.executeUpdate();

        sql = "UPDATE customer_portfolio_details_tb AS T1"
                + " INNER JOIN ("
                + " SELECT SUM(exe_units * current_price) AS Total,customer_portfolio_details_id"
                + " FROM customer_portfolio_securities_tb"
                + " WHERE customer_portfolio_id =:CustomerPortfolioId"
                + " GROUP BY asset_class_id"
                + ") AS T2 ON (T2.`customer_portfolio_details_id` = T1.`customer_portfolio_details_id`)"
                + " INNER JOIN `customer_portfolio_tb` AS T3 ON (T3.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " INNER JOIN `customer_advisor_mapping_tb` AS T4 ON (T4.`relation_id` = T3.`relation_id`)"
                + " SET T1.`current_allocation` = T2.Total/(T3.cash_amount +"
                + "(SELECT SUM(exe_units * current_price)"
                + " FROM customer_portfolio_securities_tb"
                + " WHERE customer_portfolio_id =:CustomerPortfolioId))"
                + " WHERE T3.`customer_portfolio_id` =:CustomerPortfolioId AND T3.`portfolio_status`=:status";

        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setString("status", "ACTIVE");
        sqlQuery.executeUpdate();

        //SET blocked cash or count when txansaction summary came
        sql = "UPDATE `customer_portfolio_tb` CT "
                + "INNER JOIN `customer_portfolio_securities_tb` T1 "
                + "ON(T1.`customer_portfolio_id`= CT.`customer_portfolio_id`) "
                + "INNER JOIN (SELECT SUM(T2.`security_units`*T2.`security_price`) AS TotalCost,"
                + "T2.`buy_sell` AS BuySell FROM `customer_transaction_execution_details_tb` T2,"
                + "`customer_portfolio_securities_tb` T1,`customer_portfolio_tb` T3 "
                + "WHERE T1.`security_code` = T2.`security_code` AND T1.`venueCode`=T2.`venue_code` "
                + "AND T1.`venueScriptCode`=T2.`venue_script_code`AND T2.`processed`=0 "
                + "AND T1.`customer_portfolio_id`= T3.`customer_portfolio_id` AND T2.`buy_sell`='B') AS TXN "
                + "INNER JOIN  `customer_transaction_execution_details_tb` T5 ON "
                + "(T1.`security_code` = T5.`security_code`AND T1.`venueScriptCode`=T5.`venue_script_code` "
                + "AND T1.`venueCode`=T5.`venue_code`) "
                + "SET T1.`blocked_count`= CASE WHEN T5.`buy_sell` ='S' "
                + "THEN T1.`blocked_count`- T5.`security_units` ELSE T1.`blocked_count` END,"
                + "CT.`blocked_cash`= CASE WHEN TXN.BuySell ='B' THEN CT.`blocked_cash`- TXN.TotalCost "
                + "ELSE CT.`blocked_cash` END WHERE   CT.`oms_login_id`=:OMSLoginId "
                + "AND T5.`processed`=:processedStatus AND CT.`portfolio_status`=:status";

        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("OMSLoginId", customerPortfolioTb.getOmsLoginId());
        sqlQuery.setBoolean("processedStatus", false);
        sqlQuery.setString("status", "ACTIVE");
        sqlQuery.executeUpdate();

        // Block place order in oms when place order in oms After.It allows to place order in oms when blocked cash equal to zero 
        sql = "UPDATE  customer_portfolio_tb AS T1,customer_advisor_mapping_tb AS T2 SET "
                + " T1.current_value=T1.cash_amount+ (SELECT SUM(exe_units * current_price)"
                + " FROM customer_portfolio_securities_tb"
                + " WHERE customer_portfolio_id =:CustomerPortfolioId"
                + " GROUP BY portfolio_id),"
                + " T2.relation_status = IF(IFNULL(T1.blocked_cash,0) > 0 OR IFNULL(T1.`blocked_count`,0) > 0,:blockStatus,T2.relation_status)"
                + " WHERE T1.customer_portfolio_id =:CustomerPortfolioId AND T1.`relation_id` = T2.`relation_id` AND T1.`portfolio_status`=:status";

        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setString("status", "ACTIVE");
        sqlQuery.setInteger("blockStatus", 400);
        sqlQuery.executeUpdate();

    }

    public void saveTransactionsExecutionDetailsTb(
            List<CustomerTransactionExecutionDetailsTb> customerTransactionExecutionDetailslist) {
        int count_mode = 0;

        for (CustomerTransactionExecutionDetailsTb customerTransactionExecutionDetailsTb : customerTransactionExecutionDetailslist) {
            count_mode++;
            sessionFactory.getCurrentSession().save(
                    customerTransactionExecutionDetailsTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        sessionFactory.getCurrentSession().flush();
    }

    public void saveTransactionBypassOMS(List<MmfDailyTxnSummaryTb> mmfDailyTxnSummaryTbList, JobScheduleTb jobScheduleTb) {
        int count_mode = 0;
        for (MmfDailyTxnSummaryTb mmfDailyTxnSummaryTb : mmfDailyTxnSummaryTbList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(mmfDailyTxnSummaryTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        saveJobSchedule(jobScheduleTb);
    }

    public void saveDailyClientBalanceTb(MmfDailyClientBalanceTb mmfDailyClientBalanceTb, JobScheduleTb jobScheduleTb) {
        sessionFactory.getCurrentSession().save(mmfDailyClientBalanceTb);
        sessionFactory.getCurrentSession().flush();
        saveJobSchedule(jobScheduleTb);

    }

    public void saveJobSchedule(JobScheduleTb jobScheduleTb) {
        String date = null;
        Date maxDate = getEODDate(jobScheduleTb.getJobType());
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        if (maxDate != null) {
            date = DATE_FORMAT.format(maxDate);
        }
        String jobDate = DATE_FORMAT.format(jobScheduleTb.getScheduledate());
        if (date == null) {
            sessionFactory.getCurrentSession().save(jobScheduleTb);
            sessionFactory.getCurrentSession().flush();
        } else if (!date.equals(jobDate)) {
            sessionFactory.getCurrentSession().save(jobScheduleTb);
            sessionFactory.getCurrentSession().flush();
        }
    }

    public Date getEODDate(String type) {

        String hql = "SELECT MAX(scheduledate) FROM JobScheduleTb WHERE job_type =:type";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("type", type);
        return (Date) query.uniqueResult();
    }

    public void saveSellPortfolioDetails(AddRedeemLogTb addRedeemLogTb) {
        sessionFactory.getCurrentSession().save(addRedeemLogTb);
    }

    public void saveCustomerBP(String omsLoginId, float bp) {
        String sql = "UPDATE  customer_portfolio_tb  SET "
                + " buyingPower =:cash WHERE oms_login_id =:omsUser";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("omsUser", omsLoginId).setFloat("cash", bp);
        sqlQuery.executeUpdate();
    }

    public void insertIntoCashTxnForTheDayTb(CustomerPortfolioTb customerPortfolioTb) {

        String sql = "INSERT IGNORE INTO cash_txn_for_the_day(`customer_id`,`customer_portfolio_id`,`datetime`,`security_name`,"
                + " `venue_code`,`venue_scriptcode`,`units`,`cash_flow`,`oms_userid`,`customer_transaction_exec_id`,buy_sell)"
                + " SELECT T1.`customer_id`,T1.`customer_portfolio_id`,T4.`order_date`,T3.`security_code`,T3.`venueCode`,"
                + " T3.`venueScriptCode`,T3.`exe_units`-T3.`yesterDayUnitCount`,(T3.`exe_units`-T3.`yesterDayUnitCount`)* T4.`security_price`,"
                + " T4.`oms_userid`,T4.`customer_transaction_exec_id`,T4.`buy_sell` FROM customer_portfolio_tb T1"
                + " INNER JOIN customer_portfolio_details_tb T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " INNER JOIN customer_portfolio_securities_tb T3 ON (T3.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id`)"
                + " LEFT JOIN customer_transaction_execution_details_tb T4 "
                + " ON(T4.`venue_code` = T3.`venueCode` AND T4.`venue_script_code` = T3.`venueScriptCode` AND T4.`security_code` = T3.`security_code`"
                + " AND T4.`portfolio_id` = T1.`portfolio_id` AND T4.`customer_id` = T1.`customer_id` AND"
                + " DATE_FORMAT(T4.`order_date`,'%Y-%m-%d')= (SELECT MIN(DATE_FORMAT(`order_date`,'%Y-%m-%d')) FROM customer_transaction_execution_details_tb"
                + " WHERE `oms_userid`= T1.`oms_login_id` AND `venue_script_code`= T3.`venueScriptCode` AND `venue_code`= T3.`venueCode`))"
                + " WHERE T1.`customer_portfolio_id` = :cusPortfolioId AND (T3.`exe_units`- T3.`yesterDayUnitCount`) > 0 AND  T4.`processed`= :status";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("cusPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setBoolean("status", Boolean.FALSE);
        sqlQuery.executeUpdate();

        /* sql = "UPDATE customer_transaction_execution_details_tb T1,cash_txn_for_the_day T2"
         + " SET T1.processed = :status"
         + " WHERE T1.customer_transaction_exec_id = T2.customer_transaction_exec_id"
         + " AND T1.oms_userid = :omsId";
         sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
         sqlQuery.setString("omsId", customerPortfolioTb.getOmsLoginId());
         //sqlQuery.setString("venueCodeOfMF", "NSEMF");
         sqlQuery.setBoolean("status", Boolean.TRUE);
         sqlQuery.executeUpdate();*/
        //Code done in an assumption ,there wont be any partial execution.
        /* sql = "UPDATE customer_transaction_execution_details_tb T1,cash_txn_for_the_day T2"
         + " SET T1.status_flag = :status"
         + " WHERE T1.customer_transaction_exec_id = T2.customer_transaction_exec_id"
         + " AND T1.oms_userid = :omsId";
         sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
         sqlQuery.setString("omsId", customerPortfolioTb.getOmsLoginId());
         sqlQuery.setBoolean("status", Boolean.FALSE);
         sqlQuery.executeUpdate();*/
        if (customerPortfolioTb.getBlockedCash() > 0) {
           sql = "UPDATE customer_portfolio_tb,`customer_transaction_execution_details_tb` T1 "
                   + "SET `blocked_cash` = `blocked_cash`-(T1.`security_units` * T1.`security_price`) "
                   + "WHERE `oms_login_id` =:omsUserId AND `portfolio_status` =:portfolio_status AND "
                   + "EXISTS (SELECT * FROM `customer_transaction_execution_details_tb` T3,`customer_transaction_order_details_tb` T2 "
                   + "WHERE  T3.`order_date`= T2.`order_date` AND T3.`venue_code`= T2.`venue_code` AND T3.`security_code`= T2.`security_code` "
                   + "AND T3.`buy_sell`='B' AND  T3.`oms_userid`=:omsUserId AND T3.`processed`=:processedFlag)";
            sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setString("omsUserId", customerPortfolioTb.getOmsLoginId());
            sqlQuery.setBoolean("processedFlag", Boolean.FALSE);
            sqlQuery.setString("portfolio_status", "ACTIVE");
            sqlQuery.executeUpdate();
        }
        if(customerPortfolioTb.getBlockedCount() >0){
             /*"UPDATE customer_portfolio_tb SET `blocked_count` = 0 "
                    + "WHERE `oms_login_id` = :omsUserId  "
                    + "AND  EXISTS (SELECT * FROM `cash_txn_for_the_day` WHERE `oms_userid`= :omsUserId  AND `venue_code`=:venueCodeOfMF AND `buy_sell`='S')";*/
          sql = "UPDATE customer_portfolio_tb,`customer_transaction_execution_details_tb` T1 SET `blocked_count` = `blocked_count`- T1.`security_units` "
                  + "WHERE `oms_login_id` =:omsUserId AND `portfolio_status` =:portfolio_status AND  "
                  + "EXISTS (SELECT * FROM `customer_transaction_execution_details_tb` T3,`customer_transaction_order_details_tb` T2 "
                  + "WHERE  T3.`order_date`= T2.`order_date` AND T3.`venue_code`= T2.`venue_code` AND T3.`security_code`= T2.`security_code` "
                  + "AND T3.`buy_sell`='S' AND  T3.`oms_userid`=:omsUserId AND T3.`processed`=:processedFlag)";
            sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setString("omsUserId", customerPortfolioTb.getOmsLoginId());
            sqlQuery.setBoolean("processedFlag", Boolean.FALSE);
            sqlQuery.setString("portfolio_status", "ACTIVE");
            sqlQuery.executeUpdate();
        }

    }

    public void saveBlockedCashDetails(Integer customerPortfolioId, Double cashFlow,Double blocked_count) {
        String hql = "UPDATE CustomerPortfolioTb SET tempBlockedCash = :cash,tempBlockedCount =:blockCount"
                + " WHERE customerPortfolioId = :cusPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("cusPortfolioId", customerPortfolioId);
        query.setFloat("cash", (float) cashFlow.doubleValue());
        query.setFloat("blockCount",(float) blocked_count.doubleValue());
        query.executeUpdate();
    }

    public void updateBlockedCountForSecurity(Integer customerPortfolioId,CustomerTransactionOrderDetailsTb customerTransactionOrderDetailsTb) {
        String sql = "UPDATE `customer_portfolio_securities_tb` T1,customer_transaction_order_details_tb T2 "
                + "SET T1.`blocked_count`=:blocked_count "
                + "WHERE T1.`security_code` =:security_code "
                + "AND T1.`venueCode`=:venue_code AND T1`venueScriptCode`=:venue_script_code"
                + "AND T1.`customer_portfolio_id`=:customerPortfolioId";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setFloat("blocked_count", (float) customerTransactionOrderDetailsTb.getSecurityUnits().floatValue());
        sqlQuery.setInteger("customerPortfolioId", customerPortfolioId);
        sqlQuery.setString("security_code", customerTransactionOrderDetailsTb.getSecurityCode());
        sqlQuery.setString("venue_code", customerTransactionOrderDetailsTb.getVenueCode());
        sqlQuery.setString("venue_script_code", customerTransactionOrderDetailsTb.getVenueScriptCode());
        sqlQuery.executeUpdate();
    }

    //To update blocked Cash/Count details if order reject
    public Integer updateBlockedAmountForSecurity(Double price, Double securityUnits, String omsLoginId) {
        String sql = "UPDATE `customer_portfolio_securities_tb` T1, `customer_transaction_order_details_tb` T2,`customer_portfolio_tb` T3 "
                + "SET T3.`blocked_cash`= CASE WHEN T2.`buy_sell`='BUY' "
                + "THEN (T3.`blocked_cash`-:price) ELSE T3.`blocked_cash` END,"
                + "T1.`blocked_count`=CASE WHEN T2.`buy_sell`='SELL' THEN (T1.`blocked_count`- :securityUnits) ELSE T1.`blocked_count` END,"
                + "T3.`blocked_count`=CASE WHEN T2.`buy_sell`='SELL' THEN (T3.`blocked_count`- :securityUnits) ELSE T3.`blocked_count` END "
                + "WHERE T1.`security_code` = T2.`security_code` AND T1.`venueCode`=T2.`venue_code` "
                + "AND T1.`venueScriptCode`=T2.`venue_script_code` AND T1.`customer_portfolio_id`= T3.`customer_portfolio_id` "
                + "AND T3.`oms_login_id`=:omsLoginId";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setDouble("price", price);
        sqlQuery.setDouble("securityUnits", securityUnits);
        sqlQuery.setString("omsLoginId", omsLoginId);
        return sqlQuery.executeUpdate();
    }

}
