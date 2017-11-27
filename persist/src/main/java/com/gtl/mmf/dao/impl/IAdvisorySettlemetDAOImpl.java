/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IAdvisorySettlemetDAO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class IAdvisorySettlemetDAOImpl implements IAdvisorySettlemetDAO{
    
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.IAdvisorySettlemetDAOImpl");
    @Autowired
    private SessionFactory sessionFactory;

    public List<Object> getCustomerFeeList() {
        List<Object> responeList = new ArrayList<Object>();
        try {
            String hql = "From CustomerManagementFeeTb WHERE ecsPaid =:Status AND "
                    + "submittedEcs =:Submitted ";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setBoolean("Status", false);
            query.setBoolean("Submitted", false);
            responeList.add(query.list());

            String sql = "From CustomerPerformanceFeeTb WHERE ecsPaid =:Status AND"
                    + " submittedEcs =:Submitted ";
            Query sQuery = sessionFactory.getCurrentSession().createQuery(sql);
            sQuery.setBoolean("Status", false);
            sQuery.setBoolean("Submitted", false);
            responeList.add(sQuery.list());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        }
        return responeList;
    }
    
}
