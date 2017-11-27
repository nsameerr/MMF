/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IEODUpdateHolidayCalenderDAO;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class EODUpdateHolidayCalenderDAOImpl implements IEODUpdateHolidayCalenderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void notifyAdmin(Integer status) {
        String hql = "UPDATE AdminNotificationTb SET adminViewed = :adminViewed,"
                + " notifyAdmin = :notifyAdmin, notificationDate = :date"
                + " WHERE statusCode = :status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("adminViewed", false);
        query.setBoolean("notifyAdmin", true);
        query.setInteger("status", status);
        query.setDate("date", new Date());
        query.executeUpdate();
    }

}
