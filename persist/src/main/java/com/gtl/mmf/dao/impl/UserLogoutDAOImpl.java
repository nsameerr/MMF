package com.gtl.mmf.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.dao.IUserLogoutDAO;
import com.gtl.mmf.entity.AdminNotificationTb;
import com.gtl.mmf.entity.AdminuserTb;
import java.util.List;

public class UserLogoutDAOImpl implements IUserLogoutDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int advisorLogout(Integer advisorId) {
        String advisorHql = "UPDATE MasterAdvisorTb SET logged_in=:log_status WHERE advisor_id=:advisorId";
        Query query = sessionFactory.getCurrentSession().createQuery(advisorHql);
        query.setBoolean("log_status", false);
        query.setInteger("advisorId", advisorId);
        return query.executeUpdate();
    }

    public int investorLogout(Integer customerId) {
        String advisorHql = "UPDATE MasterCustomerTb SET logged_in=:log_status WHERE customer_id=:customerId";
        Query query = sessionFactory.getCurrentSession().createQuery(advisorHql);
        query.setBoolean("log_status", false);
        query.setInteger("customerId", customerId);
        return query.executeUpdate();
    }

    public AdminuserTb getAdminNotificationDetails() {
        String hql = "FROM AdminuserTb ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return (AdminuserTb) query.uniqueResult();
    }

    public List<AdminNotificationTb> getAllAdminNotification() {
        String hql = "FROM AdminNotificationTb ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return (List<AdminNotificationTb>) query.list();
    }

}
