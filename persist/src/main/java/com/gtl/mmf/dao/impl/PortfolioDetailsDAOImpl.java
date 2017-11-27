/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * crated by 07662
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IPortfolioDetailsDAO;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PortfolioDetailsDAOImpl implements IPortfolioDetailsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Object> getRebalancePortfolioPageDetails(CustomerPortfolioTb customerPortfolioTb) {

        List<Object> responseItems = new ArrayList<Object>();

        String hql = "SELECT T1 FROM CustomerPortfolioTb AS T1"
                + " WHERE T1.masterCustomerTb =:MasterCustomerTb AND T1.portfolioStatus =:Status";
        Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setEntity("MasterCustomerTb", customerPortfolioTb.getMasterCustomerTb());
        hqlQuery.setString("Status", "ACTIVE");
        customerPortfolioTb = (CustomerPortfolioTb) hqlQuery.uniqueResult();
        responseItems.add(customerPortfolioTb);

//        hql = "SELECT T1 FROM CustomerPortfolioDetailsTb AS T1"
//                + " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb";
//        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
//        hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
//        responseItems.add(hqlQuery.list());
        hql = "FROM CustomerPortfolioDetailsTb"
                + " WHERE customerPortfolioTb =:CustomerPortfolioTb";
        hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
        responseItems.add(hqlQuery.list());

        return responseItems;
    }

    public List<Object> getReurnsforSpecifiedPeriod(CustomerPortfolioDetailsTb customerPortfolioDetailsTb,
            Integer noOfdays, String EOD_Date) {
        List<Object> respnseList = new ArrayList<Object>();
        String sql = "CALL client_twr_portfolio_returns_sp("
                + ":CustomerPorfolioId,"
                + ":AssetId,"
                + ":Currentdate,"
                + ":NoOfDaysToBeBacked)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("CustomerPorfolioId", customerPortfolioDetailsTb.getCustomerPortfolioTb().getCustomerPortfolioId());
        sqlQuery.setParameter("AssetId", customerPortfolioDetailsTb.getMasterAssetTb().getId());
        sqlQuery.setParameter("Currentdate", EOD_Date);
        sqlQuery.setParameter("NoOfDaysToBeBacked", noOfdays);
        respnseList.add((Object[]) sqlQuery.uniqueResult());

        sql = "CALL recommended_twr_portfolio_returns_sp("
                + ":PorfolioId,"
                + ":AssetId,"
                + ":Currentdate,"
                + ":NoOfDaysToBeBacked)";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("PorfolioId", customerPortfolioDetailsTb.getPortfolioTb().getPortfolioId());
        sqlQuery.setParameter("AssetId", customerPortfolioDetailsTb.getMasterAssetTb().getId());
        sqlQuery.setParameter("Currentdate", EOD_Date);
        sqlQuery.setParameter("NoOfDaysToBeBacked", noOfdays);
        respnseList.add(sqlQuery.list());
        return respnseList;

    }

    public List<Object> getSecuritesOfAssetClass(CustomerPortfolioDetailsTb customerPortfolioDetailsTb,
            Integer noOfDays, String EOD_Date) {
        List<Object> responseList = new ArrayList<Object>();
        if (noOfDays > 0) {
            String hql = "SELECT T1 FROM CustomerPortfolioSecuritiesPerformanceTb AS T1"
                    + " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb"
                    + " AND T1.masterAssetTb =:MasterAssetTb"
                    + " AND DATE_FORMAT(T1.datetime,'%Y-%m-%d') BETWEEN "
                    + " DATE_FORMAT(:NoOfDaysToBeBacked,'%Y-%m-%d')"
                    + " AND DATE_FORMAT(:Currentdate,'%Y-%m-%d')";
            Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
            hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioDetailsTb.getCustomerPortfolioTb());
            hqlQuery.setEntity("MasterAssetTb", customerPortfolioDetailsTb.getMasterAssetTb());
            hqlQuery.setParameter("Currentdate", EOD_Date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, -noOfDays);
            hqlQuery.setParameter("NoOfDaysToBeBacked", cal.getTime());
            responseList.add(hqlQuery.list());

            hql = "SELECT T1 FROM PortfolioSecuritiesPerformanceTb AS T1"
                    + " WHERE T1.portfolioTb =:PortfolioTb"
                    + " AND T1.masterAssetTb =:MasterAssetTb"
                    + " AND DATE_FORMAT(T1.datetime,'%Y-%m-%d') BETWEEN "
                    + " DATE_FORMAT(:NoOfDaysToBeBacked,'%Y-%m-%d')"
                    + " AND DATE_FORMAT(:Currentdate,'%Y-%m-%d')";
            hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
            hqlQuery.setEntity("PortfolioTb", customerPortfolioDetailsTb.getPortfolioTb());
            hqlQuery.setEntity("MasterAssetTb", customerPortfolioDetailsTb.getMasterAssetTb());
            hqlQuery.setParameter("Currentdate", EOD_Date);
            hqlQuery.setParameter("NoOfDaysToBeBacked", cal.getTime());
            responseList.add(hqlQuery.list());
        } else {
            String hql = "SELECT T1 FROM CustomerPortfolioSecuritiesPerformanceTb AS T1"
                    + " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb"
                    + " AND T1.masterAssetTb =:MasterAssetTb";
            Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
            hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioDetailsTb.getCustomerPortfolioTb());
            hqlQuery.setEntity("MasterAssetTb", customerPortfolioDetailsTb.getMasterAssetTb());
            responseList.add(hqlQuery.list());

            hql = "SELECT T1 FROM PortfolioSecuritiesPerformanceTb AS T1"
                    + " WHERE T1.portfolioTb =:PortfolioTb"
                    + " AND T1.masterAssetTb =:MasterAssetTb";
            hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
            hqlQuery.setEntity("PortfolioTb", customerPortfolioDetailsTb.getPortfolioTb());
            hqlQuery.setEntity("MasterAssetTb", customerPortfolioDetailsTb.getMasterAssetTb());
            responseList.add(hqlQuery.list());
        }

        return responseList;

    }

    public List<Object> getTotalReurnsforSpecifiedPeriod(CustomerPortfolioTb customerPortfolioTb, Integer noOfdays, String eODdate) {
        List<Object> respnseList = new ArrayList<Object>();
        String sql = "CALL client_twr_portfolio_total_returns_sp("
                + ":CustomerPorfolioId,"
                + ":Currentdate,"
                + ":NoOfDaysToBeBacked)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("CustomerPorfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.setParameter("Currentdate", eODdate);
        sqlQuery.setParameter("NoOfDaysToBeBacked", noOfdays);
        respnseList.add(sqlQuery.list());

        sql = "CALL recommended_twr_total_portfolio_returns_sp("
                + ":PorfolioId,"
                + ":Currentdate,"
                + ":NoOfDaysToBeBacked)";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("PorfolioId", customerPortfolioTb.getPortfolioTb().getPortfolioId());
        sqlQuery.setParameter("Currentdate", eODdate);
        sqlQuery.setParameter("NoOfDaysToBeBacked", noOfdays);
        respnseList.add(sqlQuery.list());

        return respnseList;
    }

    // To Display portfolio value,Acquisition Value in investor dashboard
    public List getcustomerPortfoliotableDetails(Integer customerId) {
        List response = new ArrayList();
            String SQL = "SELECT a.`current_value`,a.`cash_amount`,"
                    + " IFNULL(SUM(b.`average_rate`* b.`exe_units`),0) AS AguisitionValue,"
                    + "a.`blocked_cash`"
                    + " FROM `customer_portfolio_tb` a,`customer_portfolio_securities_tb` b "
                    + " WHERE a.`customer_id` = :customerid   AND  a.`customer_portfolio_id` = b.`customer_portfolio_id` "
                    + "AND a.`portfolio_status`= :status"
                    + " AND (b.`status`=" + true + " OR b.`exe_units` != :Zero)";
            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
            sqlQuery.setParameter("customerid", customerId);
            sqlQuery.setDouble("Zero", 0.0);
            sqlQuery.setString("status", "ACTIVE");
            List<Object> resultset1 = sqlQuery.list();
            response.add(resultset1);
            Object[] data = (Object[]) resultset1.get(0);
            if (data[1] == null) {
                SQL = "SELECT `total_funds` FROM `master_customer_tb` WHERE `customer_id` = :customerId";
                sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
                sqlQuery.setParameter("customerId", customerId);
                Object data2 = sqlQuery.uniqueResult();
                response.add(data2);
            }
        return response;
    }

    public List<Object[]> getaquisitionAndGainLoss(Integer AssetId, Integer customerId) {
        String SQL = "SELECT IFNULL(SUM(b.`average_rate`* b.`exe_units`),0) AS acqVal,IFNULL(SUM(b.`current_price` * b.`exe_units`),0) AS currentVal "
                + " FROM `customer_portfolio_tb` a,`customer_portfolio_securities_tb` b "
                + " WHERE  a.`customer_id` =:customerId  AND b.`asset_class_id`=:AssetId AND  a.`customer_portfolio_id` = b.`customer_portfolio_id`"
                + " AND (b.`status`=" + true + " OR b.`exe_units` != :Zero)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
        sqlQuery.setParameter("customerId", customerId);
        sqlQuery.setParameter("AssetId", AssetId);
        sqlQuery.setDouble("Zero", 0.0);
        return sqlQuery.list();
    }

    public boolean getCustomerIdOfUserExist(Integer customerId) {
        boolean is_cid = false;
        String hql = "FROM CustomerPortfolioTb WHERE masterCustomerTb =:customerId";
        Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setInteger("customerId", customerId);
        if(hqlQuery.list() != null || !hqlQuery.list().isEmpty()){
           is_cid = hqlQuery.list().size() > 1 ? true : false;
        }
        return is_cid;
    }
    
    public Short checkUserStatus(Integer customerId){
        String sql = "SELECT `relation_status` FROM `customer_advisor_mapping_tb` WHERE "
                + "`relation_id`=(SELECT `relation_id` FROM `customer_portfolio_tb` WHERE `customer_id`=:customerId) ";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("customerId", customerId);
        return (Short) sqlQuery.uniqueResult();
    }

}
