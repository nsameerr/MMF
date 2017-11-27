/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IAdvisorNetworkViewDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class AdvisorNetworkViewDAOImpl implements IAdvisorNetworkViewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Object[]> investorsWithoutAdvisors(int advisorId, List<Short> statusWithoutAdvisors) {
        String hql = "SELECT a.customerId, a.firstName, a.workOrganization, a.jobTitle, c.registerDatetime, c.linkedinMemberId, c.pictureUrl,"
                + " c.middleName, c.lastName,c.registrationId, c.email,c.linkedInConnected,c.linkedinProfileUrl"
                + " FROM MasterCustomerTb AS a, MasterApplicantTb c "
                + " WHERE (a.customerId NOT IN (SELECT b.masterCustomerTb.customerId FROM CustomerAdvisorMappingTb AS b"
                + " WHERE b.relationStatus NOT IN(:statusWithoutAdvisors) OR b.masterAdvisorTb=:AdviosrId) AND c.status = :UserActive)"
                + " AND c.registrationId=a.registrationId"
                + " AND a.isActiveUser = :Active AND c.isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("AdviosrId", advisorId);
        query.setBoolean("Active", Boolean.TRUE);
        query.setInteger("UserActive", 100);
        query.setParameterList("statusWithoutAdvisors", statusWithoutAdvisors);
        return query.list();
    }

    public List<Object[]> investorsUnderService(int advisorId) {
        String hql = "SELECT T3,T2,T1 FROM CustomerRiskProfileTb AS T1"
                + " INNER JOIN T1.masterPortfolioTypeTb AS T2"
                + " RIGHT OUTER JOIN T1.customerAdvisorMappingTb AS T3"
                + " WHERE T3.masterAdvisorTb=:AdvisorId";

        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("AdvisorId", advisorId);
        return query.list();
    }

    public List<Object[]> getInvestorDetailsWithLinkedIn(List<String> linkdeinIds) {
        String hql = "SELECT a.customerId, a.firstName, a.workOrganization, a.jobTitle, c.registerDatetime, c.linkedinMemberId,  c.pictureUrl,"
                + " c.middleName, c.lastName, c.registrationId, c.email,c.linkedInConnected,c.linkedinProfileUrl"
                + " FROM MasterCustomerTb AS a, MasterApplicantTb c "
                + " WHERE c.registrationId=a.registrationId AND c.linkedinMemberId IN (:linkdeinIds)"
                + " AND a.isActiveUser = :Active AND c.isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("Active", Boolean.TRUE);
        query.setBoolean("Active", Boolean.TRUE);
        query.setParameterList("linkdeinIds", linkdeinIds);
        return query.list();
    }

    public Object[] getPortfolioValues(Integer clientId, Date currentDate) {
        String sql = "CALL user_networkview_sp(:clientId,:advisorUser,:currentDate)";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setParameter("clientId", clientId)
                .setParameter("advisorUser", false)
                .setDate("currentDate", currentDate);
        return (Object[]) sqlQuery.uniqueResult();
    }

    public List<CustomerAdvisorMappingTb> statusList(Integer customerId) {
        String hql = "FROM CustomerAdvisorMappingTb WHERE masterCustomerTb.customerId =:customerId AND relationStatus IN (5,6,7,8,11,125,150,175,400,401)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerId", customerId);
        return query.list();
    }
}
