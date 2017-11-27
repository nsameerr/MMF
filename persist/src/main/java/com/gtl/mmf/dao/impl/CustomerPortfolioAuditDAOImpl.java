/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.ICustomerPortfolioAuditDAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerPortfolioAuditDAOImpl implements ICustomerPortfolioAuditDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Integer auditBeforeAssignPortfolio(CustomerPortfolioTb customerPortfolioTb, Date lastUpdateOn, String activityType) {
        String sql = " INSERT INTO `customer_portfolio_securities_audit_tb` ("
                + " `customer_portfolio_securities_id`, `customer_portfolio_id`, `customer_portfolio_details_id`,"
                + " `portfolio_id`, `portfolio_details_id`, `portfolio_securities_id`,"
                + " `asset_class_id`, `security_id`, `current_units`,"
                + " `current_price`, `current_value`, `current_weight`, `exe_units`, `recommented_price`,"
                + " `avg_acquisition_price`, `initial_value`, `initial_weight`, `required_value`,"
                + " `required_units`, `rebalance_required`, `rebalance_reqd_date`, `new_allocation`,"
                + " `security_description`, `new_tolerance_negative_range`, `new_tolerance_positive_range`,"
                + " `venueCode`, `venueScriptCode`, `security_code`, `status`, `instrument_type`,"
                + " `expiration_date`, `strike_price` ,`last_upadte_on`, `activity_type` )"
                + " SELECT T1.`customer_portfolio_securities_id`, T1.`customer_portfolio_id`, T1.`customer_portfolio_details_id`,"
                + " T1.`portfolio_id`, T1.`portfolio_details_id`, T1.`portfolio_securities_id`,"
                + " T1.`asset_class_id`, T1.`security_id`, T1.`current_units`, "
                + " T1.`current_price`, T1.`current_value`,T1.`current_weight`, T1.`exe_units`, T1.`recommented_price`,"
                + " T1.`avg_acquisition_price`,T1.`initial_value`, T1.`initial_weight`, T1.`required_value`,"
                + " T1.`required_units`,T1.`rebalance_required`, T1.`rebalance_reqd_date`, T1.`new_allocation`,"
                + " T1.`security_description`,T1.`new_tolerance_negative_range`, T1.`new_tolerance_positive_range`,"
                + " T1.`venueCode`,T1.`venueScriptCode`, T1.`security_code`, T1.`status`, T1.`instrument_type`,"
                + " T1.`expiration_date`,T1.`strike_price`,:LastUpadteOn,:ActivityType"
                + " FROM `customer_portfolio_securities_tb` T1"
                + " WHERE T1.`customer_portfolio_id` = :CustomerPortfolioId";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setDate("LastUpadteOn", lastUpdateOn);
        query.setString("ActivityType", activityType);
        query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        query.executeUpdate();

        sql = " INSERT INTO `customer_portfolio_details_audit_tb`("
                + " `customer_portfolio_details_id`,`customer_portfolio_id`,"
                + " `asset_class_id`,`range_from`,`range_to`,`portfolio_id`,"
                + " `portfolio_details_id`,`current_weight`,`new_allocation`,"
                + " `current_allocation`,`last_update_on`,`activity_type`)"
                + " SELECT T1.`customer_portfolio_details_id`,T1.`customer_portfolio_id`,"
                + " T1.`asset_class_id`,T1.`range_from`,T1.`range_to`,T1.`portfolio_id`,"
                + " T1.`portfolio_details_id`,T1.`current_weight`,T1.`new_allocation`,"
                + " T1.`current_allocation`,:LastUpadteOn,:ActivityType"
                + " FROM `customer_portfolio_details_tb` T1"
                + " WHERE T1.`customer_portfolio_id` = :CustomerPortfolioId";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setDate("LastUpadteOn", lastUpdateOn);
        query.setString("ActivityType", activityType);
        query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        query.executeUpdate();

        sql = " INSERT INTO `customer_portfolio_audit_tb`"
                + " (`customer_portfolio_id`,`relation_id`,`customer_id`,`oms_login_id`,`advisor_id`,"
                + " `portfolio_id`,`current_value`,`portfolio_90_day_returns`,`portfolio_1_year_returns`,"
                + " `adviser_comment`,`customer_comment`,`outperformance`,`advisor_portfolio_start_value`,"
                + " `benchmark_start_value`,`advisor_portfolio_returns_from_start`,`benchmark_returns_from_start`,"
                + " `rebalance_required`,`rebalance_reqd_date`,`last_updated`,`status`,`portfolio_value`,"
                + " `comment_freq`,`no_rebalance_status`,`cash_amount`,`portfolio_modified`,"
                + " `LastExecutionUpdate`,`is_first_day_perfmance_calc`,`last_update_on`,`activity_type`)"
                + " SELECT T1.`customer_portfolio_id`,T1.`relation_id`,T1.`customer_id`,T1.`oms_login_id`,T1.`advisor_id`,"
                + " T1.`portfolio_id`,T1.`current_value`,T1.`portfolio_90_day_returns`,T1.`portfolio_1_year_returns`,"
                + " T1.`adviser_comment`,T1.`customer_comment`,T1.`outperformance`,T1.`advisor_portfolio_start_value`,"
                + " T1.`benchmark_start_value`,T1.`advisor_portfolio_returns_from_start`,T1.`benchmark_returns_from_start`,"
                + " T1.`rebalance_required`,T1.`rebalance_reqd_date`,T1.`last_updated`,T1.`status`,T1.`portfolio_value`,"
                + " T1.`comment_freq`,T1.`no_rebalance_status`,T1.`cash_amount`,T1.`portfolio_modified`,"
                + " T1.`LastExecutionUpdate`,T1.`is_first_day_perfmance_calc`,:LastUpadteOn,:ActivityType"
                + " FROM `customer_portfolio_tb` T1"
                + " WHERE T1.`customer_portfolio_id` = :CustomerPortfolioId";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setDate("LastUpadteOn", lastUpdateOn);
        query.setString("ActivityType", activityType);
        query.setInteger("CustomerPortfolioId", customerPortfolioTb.getCustomerPortfolioId());
        return query.executeUpdate();
    }

    public List<CustomerPortfolioTb> getCustomerPortfoliosAssigned(int portfolioId) {
        String hql = "FROM CustomerPortfolioTb WHERE portfolioTb.portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", portfolioId);
        return query.list();
    }
}
