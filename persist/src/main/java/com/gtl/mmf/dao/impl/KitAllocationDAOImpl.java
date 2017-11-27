/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IKitAllocationDAO;
import com.gtl.mmf.entity.KitAllocationTb;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class KitAllocationDAOImpl implements IKitAllocationDAO{
    
    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.KitAllocationDAOImpl");
    
    @Autowired
    private SessionFactory sessionFactory;
    static final int ZERO = 0;

    public List<Object> getRegistrationKitDetails() {
//        String hql = "FROM KitAllocationTb";
//        Query query = sessionFactory.getCurrentSession().createQuery(hql);
//        return query.list();
        
        List<Object> responseList = new ArrayList<Object>();
        Integer kitNmbr = 0;
        String sql = "SELECT MAX(T1.kit_number) FROM master_applicant_tb T1,kit_allocation_tb T2"
                + " WHERE T2.`kit_status` = :status AND "
                + " T1.kit_number BETWEEN T2.fromValue AND T2.toValue";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setString("status", "IN USE");
        if (query.uniqueResult() != null) {
            kitNmbr = (Integer) query.uniqueResult();
        }

        String hql = "FROM KitAllocationTb";
        Query hqlQry = sessionFactory.getCurrentSession().createQuery(hql);
        responseList.add(hqlQry.list());
        responseList.add(kitNmbr);
        return responseList;
    }

    public int saveNewAllocation(KitAllocationTb kitAllocationTb) {
        int count = 1;
        String hql = "SELECT COUNT(*) FROM KitAllocationTb WHERE kitStatus = :status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("status", "IN USE");
        Long row = (Long) query.uniqueResult();
        if(row == ZERO) {
            kitAllocationTb.setKitStatus("IN USE");
            count = 0;
        }
        sessionFactory.getCurrentSession().save(kitAllocationTb);
        return count; 
    }

    public boolean checkNumberExistence(Integer startValue, Integer endValue) {
        boolean exist = false;
        String sql = "SELECT id,fromValue,toValue FROM kit_allocation_tb"
                + " WHERE :first BETWEEN fromValue AND toValue OR"
                + " :second BETWEEN fromValue AND toValue";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("first", startValue);
        query.setInteger("second", endValue);
        if(!query.list().isEmpty()){
           exist = true; 
        }
        return exist;
    }

    public void updateNotificationStatus(Integer notificationStatusCode) {
        String hql = "UPDATE AdminNotificationTb SET adminViewed = :adminViewed,"
                + " notifyAdmin = :notifyAdmin, notificationDate = :date"
                + " WHERE statusCode = :code";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("adminViewed", true);
        query.setBoolean("notifyAdmin", false);
        query.setInteger("code", notificationStatusCode);
        query.setDate("date", null);
        query.executeUpdate();
    }
    
}
