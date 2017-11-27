/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;


import com.gtl.mmf.dao.IInvestorNetworkViewDAO;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author 07958
 */
public class InvestorNetworkViewDAOImpl implements IInvestorNetworkViewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Object[]> getAllAdvisors(int customerId,int status) {
        String sql = "SELECT a.`advisor_id`, c.`first_name`, e.middle_name, e.last_name,"
                + " c.work_organization, c.job_title, d.primary_qualification_degree,"
                + " a.`dominant_style`, a.`max_returns_90_days`, a.`max_returns_1_year`,"
                + " a.`outperformance`, a.`avg_client_rating`, b.`relation_status`,"
                + " e.picture_url, e.linkedin_member_id, e.sebi_regno, b.`relation_id`,"
                + " e.`registration_id`, e.`email`, f.portfolio_style_type,e.linkedIn_connected,"
                + " e.`linkedin_profile_url` FROM advisor_details_tb a"
                + " LEFT OUTER JOIN customer_advisor_mapping_tb b ON a.`advisor_id` = b.`advisor_id` AND b.`customer_id` = :customerId"
                + " LEFT OUTER JOIN master_advisor_tb c ON c.`advisor_id` = a.`advisor_id` AND c.`is_active_user` = :Active"
                + " LEFT OUTER JOIN master_advisor_qualification_tb d ON d.adv_id = c.`qualification_tb_id`"
                + " INNER JOIN master_applicant_tb e ON e.registration_id = c.`registration_id` AND e.`is_active_user` = :Active AND e.`status` =:status"
                + " LEFT OUTER JOIN master_portfolio_style_tb f ON f.portfolio_style_id = a.`dominant_style`";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.addScalar("advisor_id", IntegerType.INSTANCE)
                .addScalar("first_name", StringType.INSTANCE)
                .addScalar("middle_name", StringType.INSTANCE)
                .addScalar("last_name", StringType.INSTANCE)
                .addScalar("work_organization", StringType.INSTANCE)
                .addScalar("job_title", StringType.INSTANCE)
                .addScalar("primary_qualification_degree", StringType.INSTANCE)
                .addScalar("dominant_style", IntegerType.INSTANCE)
                .addScalar("max_returns_90_days", BigDecimalType.INSTANCE)
                .addScalar("max_returns_1_year", BigDecimalType.INSTANCE)
                .addScalar("outperformance", BigDecimalType.INSTANCE)
                .addScalar("avg_client_rating", BigDecimalType.INSTANCE)
                .addScalar("relation_status", IntegerType.INSTANCE)
                .addScalar("picture_url", StringType.INSTANCE)
                .addScalar("linkedin_member_id", StringType.INSTANCE)
                .addScalar("sebi_regno", StringType.INSTANCE)
                .addScalar("relation_id", IntegerType.INSTANCE)
                .addScalar("registration_id", StringType.INSTANCE)
                .addScalar("email", StringType.INSTANCE)
                .addScalar("portfolio_style_type", StringType.INSTANCE)
                .addScalar("linkedIn_connected")
                .addScalar("linkedin_profile_url",StringType.INSTANCE)
                .setBoolean("Active", Boolean.TRUE)
                .setBoolean("Active", Boolean.TRUE)
                .setInteger("customerId", customerId)
                .setInteger("status", status).list();
    }
}
