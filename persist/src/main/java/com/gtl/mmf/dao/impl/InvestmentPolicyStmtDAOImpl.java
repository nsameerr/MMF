/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by  07662
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IInvestmentPolicyStmtDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioTb;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestmentPolicyStmtDAOImpl implements IInvestmentPolicyStmtDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static final int ZERO = 0;

    public List<Object> getPagedetails(int customerId) {
        List<Object> resultList = new ArrayList<Object>();
        String hql = "SELECT T2,T3 FROM CustomerQuestionResponseSetTb AS T1"
                + " INNER JOIN T1.questionmasterTb AS T2"
                + " INNER JOIN T1.questionoptionsmasterTb AS T3"
                + " INNER JOIN T1.customerAdvisorMappingTb AS T4"
                + " WHERE T4.masterCustomerTb=:CustomerId";

        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("CustomerId", customerId);
        resultList.add(query.list());

        hql = "SELECT T1 FROM CustomerRiskProfileTb AS T1"
                + " INNER JOIN T1.customerAdvisorMappingTb AS T2"
                + " WHERE T2.masterCustomerTb=:CustomerId";

        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("CustomerId", customerId);
        //resultList.add(query.uniqueResult());
        resultList.add(query.list());

        return resultList;

    }

    public void saveInvestmentPolicy(CustomerRiskProfileTb customerRiskProfileTb, CustomerAdvisorMappingTb customerAdvisorMappingTb) {
        String hql = "UPDATE CustomerRiskProfileTb"
                + " SET expReturn = :ExpReturn,"
                + " masterPortfolioTypeTb = :masterPortfolioTypeTb,"
                + " masterBenchmarkIndexTb = :MasterBenchmarkIndexTb,"
                + " investHorizon = :InvestHorizon,"
                + " liquidityReqd = :LiquidityReqd,"
                + " incomeReqd = :IncomeReqd,"
                + " masterPortfolioSizeTbByPortfolioSize = :masterPortfolioSizeTbByPortfolioSize,"
                + " masterPortfolioStyleTbByPortfolioStyle = :masterPortfolioStyleTbByPortfolioStyle"
                + " WHERE riskProfileId = :RiskProfileId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setFloat("ExpReturn", customerRiskProfileTb.getExpReturn());
        query.setEntity("masterPortfolioTypeTb", customerRiskProfileTb.getMasterPortfolioTypeTb());
        query.setEntity("MasterBenchmarkIndexTb", customerRiskProfileTb.getMasterBenchmarkIndexTb());
        query.setInteger("InvestHorizon", customerRiskProfileTb.getInvestHorizon());
        query.setFloat("LiquidityReqd", customerRiskProfileTb.getLiquidityReqd());
        query.setFloat("IncomeReqd", customerRiskProfileTb.getIncomeReqd());
        query.setEntity("masterPortfolioSizeTbByPortfolioSize", customerRiskProfileTb.getMasterPortfolioSizeTbByPortfolioSize());
        query.setEntity("masterPortfolioStyleTbByPortfolioStyle", customerRiskProfileTb.getMasterPortfolioStyleTbByPortfolioStyle());
        query.setInteger("RiskProfileId", customerRiskProfileTb.getRiskProfileId());
        query.executeUpdate();

        hql = "UPDATE CustomerAdvisorMappingTb"
                + " SET reviewFreq = :ReviewFreq,"
                + "  status_date = :StatusDate,"
                + " relationStatus = :RelationStatus"
                + " WHERE relationId = :RelationId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("ReviewFreq", customerAdvisorMappingTb.getReviewFreq());
        query.setTimestamp("StatusDate", customerAdvisorMappingTb.getStatusDate());
        query.setShort("RelationStatus", customerAdvisorMappingTb.getRelationStatus());
        query.setInteger("RelationId", customerAdvisorMappingTb.getRelationId());
        query.executeUpdate();

    }

    public List<Object> getPortfolioAssignPageDetails(int relationId) {
        List<Object> responseItems = new ArrayList<Object>();
        String sql = "SELECT T1.`risk_profile_id`,T1.`relation_id`,T1.`risk_score`,T1.`exp_return`,"
                + " T1.`benchmark`,T1.`invest_horizon`,T1.`liquidity_reqd`,T1.`income_reqd`,"
                + " T1.`portfolio_type`,T4.`value`,T8.`portfolio_style`,T8.`portfolio_style_type`,T5.`field_label`,"
                + " T2.`relation_id` AS RelationId,T2.`customer_id`,T2.`advisor_id`,"
                + " T6.`adviser_comment`,T6.`customer_comment`,T6.`customer_portfolio_id`,"
                + " T6.`comment_freq`,T6.`portfolio_id`,T2.`allocated_funds`,T7.`oms_login_id`,T1.`portfolio_size`,"
                + " T8.`portfolio_style_id` FROM `customer_risk_profile_tb` T1"
                + " INNER JOIN `customer_advisor_mapping_tb` T2 ON (T2.`relation_id` = T1.`relation_id`)"
                + " INNER JOIN `master_portfolio_type_tb` T3 ON (T1.`portfolio_type` = T3.`id`)"
                + " INNER JOIN `master_portfolio_style_tb` T8 ON (T1.`initial_portfolio_style` = T8.`portfolio_style_id`)"
                + " LEFT JOIN `master_benchmark_index_tb` T4 ON (T4.`id` = T1.`benchmark`)"
                + " LEFT JOIN `contract_freq_lookup_tb` T5 ON (T5.`field_type` = 'REVIEW_FEQ' AND  T5.`field_value` = T2.`review_freq`)"
                + " LEFT JOIN `customer_portfolio_tb` T6 ON (T6.`relation_id` = T2.`relation_id`)"
                + " LEFT JOIN `master_customer_tb` T7 ON (T7.`customer_id` = T2.`customer_id`)"
                + " WHERE T2.`relation_id` = :relationId";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("relationId", relationId);
        responseItems.add(query.uniqueResult());

        sql = "SELECT T1.`portfolio_id`,T1.`portfolio_name`,T1.`portfolio_type`,T1.`portfolio_value`"
                + " FROM `portfolio_tb` T1"
                + " INNER JOIN customer_advisor_mapping_tb T2 ON T1.`advisor_id` = T2.advisor_id"
                + " WHERE  T2.`relation_id` = :relationId";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("relationId", relationId);
        responseItems.add(query.list());
        return responseItems;
    }

    public List<PortfolioDetailsTb> getPortfolioPieCharDetails(PortfolioTb portfolioTb) {
        String hql = "SELECT T1 FROM PortfolioDetailsTb AS T1"
                + " WHERE T1.portfolioTb =:PortfolioTb";
        Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setInteger("PortfolioTb", portfolioTb.getPortfolioId());
        return hqlQuery.list();
    }

    public void saveAssignPortfolio(CustomerPortfolioTb customerPortfolioTb, Boolean isportfolioChanged,Integer customerId) {
        String hql;
        Query query;
        int customer_portfolio_id;
        CustomerAdvisorMappingTb customerAdvisorMappingTb = customerPortfolioTb.getCustomerAdvisorMappingTb();
        if (isportfolioChanged) {
            // For  initilal assign portfolio  case.
            customer_portfolio_id = (Integer) sessionFactory.getCurrentSession().save(customerPortfolioTb);
            customerPortfolioTb.setCustomerPortfolioId(customer_portfolio_id);
            
        } else {
            // For review without portfolio change
            
//            hql = "UPDATE CustomerPortfolioTb T1"
//                    + " SET T1.adviserComment = :AdviserComment,"
//                    + " T1.lastUpdated = :LastUpdated,"
//                    + " T1.commentFreq = :CommentFreq,"
//                    + " T1.benchmarkStartValue = :BenchmarkStartValue"
//                    + " WHERE T1.customerPortfolioId = :CustomerPortfolioId";
//            query = sessionFactory.getCurrentSession().createQuery(hql);
//            query.setString("AdviserComment", customerPortfolioTb.getAdviserComment());
//            query.setDate("LastUpdated", customerPortfolioTb.getLastUpdated());
//            query.setInteger("CommentFreq", customerPortfolioTb.getCommentFreq());
//            query.setBigDecimal("BenchmarkStartValue", customerPortfolioTb.getBenchmarkStartValue());
//            query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
//            query.executeUpdate();

            sessionFactory.getCurrentSession().update(customerPortfolioTb);

        }
        hql = "UPDATE CustomerAdvisorMappingTb"
                + " SET relationStatus = :RelationStatus,"
                + " status_date = :StatusDate,investorViewed = :investorViwed "
                + " WHERE relationId = :RelationId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMappingTb.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMappingTb.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMappingTb.getRelationId());
        query.setBoolean("investorViwed", false);
        query.executeUpdate();
        
        /*hql = "UPDATE CustomerPortfolioTb T1,CustomerAdvisorMappingTb T2 "
                + "SET T1.portfolioStatus =:Status WHERE T1.masterCustomerTb =:MasterCustomerTb "
                + "AND T1.masterCustomerTb=T2.masterCustomerTb "
                + "AND T1.customerAdvisorMappingTb = T2.relationId AND T2.relationStatus =:terminateStatus";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("Status","CONTRACT TERMINATED");
        query.setEntity("MasterCustomerTb",customerPortfolioTb.getMasterCustomerTb());
        query.setInteger("terminateStatus",403);
        query.executeUpdate();*/
        String sql ="UPDATE `customer_portfolio_tb` T1,`customer_advisor_mapping_tb` T2 "
                + "SET T1.`portfolio_status` =:Status WHERE T1.`customer_id` =:c_id "
                + "AND T1.`customer_id`=T2.`customer_id` AND T1.`relation_id` = T2.`relation_id` "
                + "AND T2.`relation_status` =:terminateStatus";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setString("Status","CONTRACT TERMINATED");
        query.setInteger("c_id",customerId);
        query.setInteger("terminateStatus",403);
        query.executeUpdate();
    }

    public List<Object> customerIPSView(int relationId) {
        List<Object> responseItems = new ArrayList<Object>();
        String sql = "SELECT T1.`risk_profile_id`,T1.`relation_id`,T1.`risk_score`,T1.`exp_return`,"
                + " T1.`benchmark`,T1.`invest_horizon`,T1.`liquidity_reqd`,T1.`income_reqd`,"
                + " T1.`portfolio_type`,T4.`value`,T8.`portfolio_style`,T8.`portfolio_style_type`,T5.`field_label`,"
                + " T2.`relation_id` AS RelationId,T2.`customer_id`,T2.`advisor_id`,"
                + " T6.`adviser_comment`,T6.`customer_comment`,T6.`customer_portfolio_id`,"
                + " T6.`comment_freq`,T6.`portfolio_id`,T2.`allocated_funds`,T7.`oms_login_id`,T1.`portfolio_size`,"
                + " T8.`portfolio_style_id` FROM `customer_risk_profile_tb` T1"
                + " INNER JOIN `customer_advisor_mapping_tb` T2 ON (T2.`relation_id` = T1.`relation_id`)"
                + " INNER JOIN `master_portfolio_type_tb` T3 ON (T1.`portfolio_type` = T3.`id`)"
                + " INNER JOIN `master_portfolio_style_tb` T8 ON (T1.`initial_portfolio_style` = T8.`portfolio_style_id`)"
                + " LEFT JOIN `master_benchmark_index_tb` T4 ON (T4.`id` = T1.`benchmark`)"
                + " LEFT JOIN `contract_freq_lookup_tb` T5 ON (T5.`field_type` = 'REVIEW_FEQ' AND  T5.`field_value` = T2.`review_freq`)"
                + " LEFT JOIN `customer_portfolio_tb` T6 ON (T6.`relation_id` = T2.`relation_id`)"
                + " LEFT JOIN `master_customer_tb` T7 ON (T7.`customer_id` = T2.`customer_id`)"
                + " WHERE T2.`relation_id` = :RelationId";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("RelationId", relationId);
        responseItems.add(query.uniqueResult());

        sql = "SELECT T1.`portfolio_id`,T1.`portfolio_name`,T1.`portfolio_type`,T1.`portfolio_value`"
                + " FROM `portfolio_tb` T1"
                + " INNER JOIN customer_advisor_mapping_tb T2 ON T1.`advisor_id` = T2.advisor_id"
                + " WHERE  T2.`relation_id` = :RelationId";

        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("RelationId", relationId);
        responseItems.add(query.list());
        return responseItems;
    }

    public void customerReviewIPS(CustomerPortfolioTb customerPortfolioTb) {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = customerPortfolioTb.getCustomerAdvisorMappingTb();
        String hql = "UPDATE CustomerPortfolioTb"
                + " SET customerComment = :CustomerComment,"
                + "lastUpdated = :LastUpdated,"
                + "commentFreq = :CommentFreq"
                + " WHERE customerPortfolioId = :CustomerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("CustomerComment", customerPortfolioTb.getCustomerComment());
        query.setDate("LastUpdated", customerPortfolioTb.getLastUpdated());
        query.setInteger("CommentFreq", customerPortfolioTb.getCommentFreq());
        query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        query.executeUpdate();

        //Updating Status date
        hql = "UPDATE CustomerAdvisorMappingTb"
                + " SET relationStatus = :RelationStatus,"
                + "status_date = :StatusDate,advisorViewed = :advisorViwed"
                + " WHERE relationId = :RelationId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMappingTb.getRelationStatus());
        query.setTimestamp("StatusDate", customerAdvisorMappingTb.getStatusDate());
        query.setInteger("RelationId", customerAdvisorMappingTb.getRelationId());
        query.setBoolean("advisorViwed", false);
        query.executeUpdate();
    }

    public void customerAcceptIPS(CustomerPortfolioTb customerPortfolioTb) {
        String sql;
        SQLQuery sqlQuery;
        CustomerAdvisorMappingTb customerAdvisorMappingTb = customerPortfolioTb.getCustomerAdvisorMappingTb();

        String hql = "UPDATE CustomerPortfolioTb"
                + " SET lastUpdated = :LastUpdated,"
                + " rebalance_required = :RebalanceRequired,"
                + " rebalance_reqd_date = :RebalanceReqdDate,"
                + " no_rebalance_status = :NoRebalanceStatus,"
                + " portfolio_assigned = :portfolioAssigned"
                + " WHERE customerPortfolioId = :CustomerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDate("LastUpdated", customerPortfolioTb.getLastUpdated());
        query.setBoolean("RebalanceRequired", customerPortfolioTb.getRebalanceRequired());
        query.setDate("RebalanceReqdDate", customerPortfolioTb.getRebalanceReqdDate());
        query.setBoolean("NoRebalanceStatus", customerPortfolioTb.getNoRebalanceStatus());
        query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        query.setDate("portfolioAssigned", customerPortfolioTb.getPortfolioAssigned());
        query.executeUpdate();

        //Updating Status date
        hql = "UPDATE CustomerAdvisorMappingTb"
                + " SET relationStatus = :RelationStatus,"
                + "status_date = :StatusDate,advisorViewed = :advisorViwed"
                + " WHERE relationId = :RelationId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("RelationStatus", customerAdvisorMappingTb.getRelationStatus());
        query.setInteger("RelationId", customerAdvisorMappingTb.getRelationId());
        query.setTimestamp("StatusDate", customerAdvisorMappingTb.getStatusDate());
        query.setBoolean("advisorViwed", false);
        query.executeUpdate();

        sql = "INSERT INTO customer_portfolio_details_tb (customer_portfolio_id, asset_class_id, range_from, range_to,"
                + "portfolio_id, portfolio_details_id,new_allocation)"
                + " SELECT T2.customer_portfolio_id, T1.asset_class_id, T1.range_from, T1.range_to,"
                + " T1.portfolio_id, T1.portfolio_details_id,T1.new_allocation"
                + " FROM portfolio_details_tb AS T1"
                + " INNER JOIN customer_portfolio_tb AS T2 ON (T2.portfolio_id = T1.portfolio_id)"
                + " WHERE T2.customer_portfolio_id = :CustomerPortfolioId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.executeUpdate();

        sql = "INSERT INTO `customer_portfolio_securities_tb`"
                + "(`customer_portfolio_id`,`customer_portfolio_details_id`,`portfolio_id`,"
                + "`portfolio_details_id`,`portfolio_securities_id`,`asset_class_id`,`security_id`,"
                + "`recommented_price`,`new_allocation`,`security_description`,"
                + "`new_tolerance_negative_range`,`new_tolerance_positive_range`,`venueCode`,`venueScriptCode`,`security_code`,"
                + "`instrument_type`,`expiration_date`,`strike_price`,`status`)"
                + " SELECT T2.`customer_portfolio_id`, T2.`customer_portfolio_details_id`, T2.`portfolio_id`,"
                + " T2.`portfolio_details_id`, T1.`portfolio_securities_id`, T1.`asset_class_id`, T1.`security_id`,"
                + " T1.`recommented_price`,T1.`new_allocation`,T1.`security_description`,"
                + " T1.`new_tolerance_positive_range`,T1.`new_tolerance_positive_range`,T1.`venue_code`,T1.`venue_script_code`,T1.`security_code`,"
                + " T1.`instrument_type`,T1.`expiration_date`,T1.`strike_price`,T1.`status`"
                + " FROM `portfolio_securities_tb` AS T1"
                + " INNER JOIN `customer_portfolio_details_tb` AS T2 ON(T2.`portfolio_details_id` = T1.`portfolio_details_id`)"
                + " WHERE T2.`customer_portfolio_id` = :CustomerPortfolioId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.executeUpdate();

//      inserting client recomended portfolio securities 
        String Query = "INSERT INTO `recomended_customer_portfolio_securities_tb`"
                + "(`customer_portfolio_id`,`customer_portfolio_details_id`,`portfolio_id`,"
                + "`portfolio_details_id`,`portfolio_securities_id`,`asset_class_id`,`security_id`,"
                + " `current_price`,`recommented_price`,required_units,`new_allocation`,`security_description`,"
                + "`new_tolerance_negative_range`,`new_tolerance_positive_range`,`venueCode`,`venueScriptCode`,`security_code`,"
                + "`instrument_type`,`expiration_date`,`strike_price`,`status`)"
                + " SELECT T2.`customer_portfolio_id`, T2.`customer_portfolio_details_id`, T2.`portfolio_id`,"
                + " T2.`portfolio_details_id`, T1.`portfolio_securities_id`, T1.`asset_class_id`, T1.`security_id`,"
                + " T1.`current_price`,T1.`current_price`,FLOOR((((T1.`new_allocation`/100)*" + customerPortfolioTb.getAllocatedFund() + ")/T1.`current_price`)) AS required_units,T1.`new_allocation`,T1.`security_description`,"
                + " T1.`new_tolerance_positive_range`,T1.`new_tolerance_positive_range`,T1.`venue_code`,T1.`venue_script_code`,T1.`security_code`,"
                + " T1.`instrument_type`,T1.`expiration_date`,T1.`strike_price`,T1.`status`"
                + " FROM `portfolio_securities_tb` AS T1"
                + " INNER JOIN `customer_portfolio_details_tb` AS T2 ON(T2.`portfolio_details_id` = T1.`portfolio_details_id`)"
                + " WHERE T2.`customer_portfolio_id` = :CustomerPortfolioId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(Query);
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.executeUpdate();

        hql = "UPDATE CustomerPortfolioTb T1"
                + " SET T1.recomendedCashBal = T1.allocatedFund - (SELECT SUM(T2.requiredUnits * T2.currentPrice) "
                + " FROM RecomendedCustomerPortfolioSecuritiesTb T2 "
                + " WHERE T2.customerPortfolioTb.customerPortfolioId = :CustomerPortfolioId)"
                + " WHERE T1.customerPortfolioId = :CustomerPortfolioId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        query.executeUpdate();

        hql = "UPDATE PortfolioTb"
                + " SET noOfCustomersAssigned = IFNULL(noOfCustomersAssigned,0) + 1"
                + " WHERE portfolioId = :PortfolioId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("PortfolioId", customerPortfolioTb.getPortfolioTb().getPortfolioId());
        query.executeUpdate();

//      inserting customer TWR asset wise return table
        sql = "INSERT INTO `customer_twr_portfolio_return_tb`(`customer_portfolio_id`,`customer_portfolio_details_id`,"
                + " `relation_id`,`customer_id`,`asset_id`)"
                + " SELECT T1.`customer_portfolio_id`,T1.`customer_portfolio_details_id`,T2.`relation_id`,T2.`customer_id`,T1.`asset_class_id`"
                + " FROM `customer_portfolio_details_tb` T1"
                + " INNER JOIN `customer_portfolio_tb` AS T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)"
                + " WHERE T2.`customer_portfolio_id` = :CustomerPortfolioId";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        sqlQuery.executeUpdate();
        
    }

    public MasterPortfolioTypeTb getPortfolioByStyleAndSize(Integer portfolioStyleId, Integer portfolioSizeId) {
        String hql = "FROM MasterPortfolioTypeTb WHERE masterPortfolioSizeTb = :masterPortfolioSizeTb AND"
                + " masterPortfolioStyleTb = :masterPortfolioStyleTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("masterPortfolioSizeTb", portfolioSizeId);
        query.setInteger("masterPortfolioStyleTb", portfolioStyleId);
        return (MasterPortfolioTypeTb) query.uniqueResult();
    }
    
    public boolean getCustomerDetails(Integer customer_id){
        boolean is_customer = false;
        String hql = "FROM CustomerPortfolioTb WHERE masterCustomerTb =:CustomerId AND portfolioStatus =:status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("CustomerId",customer_id);
        query.setString("status","ACTIVE");
        if(query.list() != null || !query.list().isEmpty()){
            is_customer =  true;
        }
        return is_customer;
    }
    
    public List<CustomerPortfolioSecuritiesTb> getPortfolioSecuritiesForCustomer(Integer CustomerPortfolioId){
       String Hql ="FROM CustomerPortfolioSecuritiesTb WHERE customerPortfolioTb =:CustomerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(Hql);
        query.setInteger("CustomerPortfolioId",CustomerPortfolioId);
        return query.list();
    }
    
    public Integer getCustomerPortfolioIdOfUser(Integer CustomerId,Integer advisor_id){
        String sql = "SELECT `customer_portfolio_id` FROM `customer_portfolio_tb` "
                + "WHERE `customer_id`=:CustomerId AND `relation_id`=(SELECT `relation_id` "
                + "FROM `customer_advisor_mapping_tb` WHERE `customer_id`=:CustomerId "
                + "AND `relation_status`=:terminateStatus AND `advisor_id`=:advisor_id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("CustomerId",CustomerId);
        query.setInteger("terminateStatus",403);
        query.setInteger("advisor_id",advisor_id);
        return (Integer) query.uniqueResult();
        
    }
    
    public void updateSecuritiesForCustomer(List<CustomerPortfolioSecuritiesTb> seuritylist){
        for(CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : seuritylist){
            sessionFactory.getCurrentSession().saveOrUpdate(customerPortfolioSecuritiesTb);
        }
    }
    
     public CustomerPortfolioTb getOldPortfolioDetails(Integer customer_id){
         String hql = "FROM CustomerPortfolioTb WHERE masterCustomerTb =:CustomerId AND portfolioStatus =:status";
         Query query = sessionFactory.getCurrentSession().createQuery(hql);
         query.setInteger("CustomerId",customer_id);
         query.setString("status","ACTIVE");
        return (CustomerPortfolioTb) query.uniqueResult();
     }
}
