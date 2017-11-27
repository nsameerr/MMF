/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IEODAdvisorRatingDAO;
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
public class EODAdvisorRatingDAOImpl implements IEODAdvisorRatingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Integer> getActiveAdvisors(Integer activeStatus) {
        String hql = "SELECT T1.advisorId FROM MasterAdvisorTb AS T1 LEFT JOIN T1.masterApplicantTb as T2"
                + " WHERE T2.status = :activeStatus"
                + " AND T1.isActiveUser = :Active AND T2.isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("Active", Boolean.TRUE);
        query.setBoolean("Active", Boolean.TRUE);
        return query.setInteger("activeStatus", activeStatus).list();
    }

    public List<Integer> getCustomerAdvisorMappingRelations(List<Short> activeRelationStatuses, Date currentDate) {
        String hql = "SELECT a.relationId FROM CustomerAdvisorMappingTb a WHERE a.relationStatus IN (:activeRelationStatuses)"
                + " AND date(a.contractEnd) >= :currentDate AND a.rateAdvisorFlag <> :rateAdvisorFlag";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setParameterList("activeRelationStatuses", activeRelationStatuses)
                .setDate("currentDate", currentDate).setBoolean("rateAdvisorFlag", true).list();
    }

    public Integer updateAdvisorRating(Integer relationId, Date currentDate) {
        String sql = "CALL eod_rate_advisor_check_sp(:relationId, :currentDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setInteger("relationId", relationId).setTimestamp("currentDate", currentDate).executeUpdate();
    }

    public List<Integer> updateAdvisorRatingList(List<Short> activeRelationStatuses, Date currentDate, Date lastRateDate) {
        String hql = "SELECT a.relationId FROM CustomerAdvisorMappingTb a"
                + " WHERE DATE(a.contractEnd) >= DATE(:currentDate) AND a.rateAdvisorFlag = FALSE"
                + " AND DATE_FORMAT(DATE(COALESCE(a.rateAdvisorLastDate,a.contractStart)),'%Y-%m-%d') = DATE_FORMAT(DATE(:lastRateDate),'%Y-%m-%d')"
                + " AND a.relationStatus IN :activeRelationStatuses";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setDate("currentDate", currentDate).setDate("lastRateDate", lastRateDate)
                .setParameterList("activeRelationStatuses", activeRelationStatuses).list();
    }

    public Integer updateAdvisorDetais(Integer advisorId, Date currentDate) {
        String sql = "CALL eod_update_advisor_details_sp(:advisorId,:currentDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setInteger("advisorId", advisorId).setDate("currentDate", currentDate).executeUpdate();
    }
}
