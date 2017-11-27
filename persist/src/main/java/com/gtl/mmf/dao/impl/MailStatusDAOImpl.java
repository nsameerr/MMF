/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IMailStatusDAO;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author trainee7
 */
public class MailStatusDAOImpl implements IMailStatusDAO{
    static final Logger logger = Logger.getLogger("com.gtl.mmf.dao.impl.MailStatusDAOImpl");    

    @Autowired
    private SessionFactory sessionFactory;
    public Integer updateMailSendStatus(String tablename, String email, boolean mailStatus) throws Exception{
        String hql;
        Query query;       
        try {
            if (tablename != null) {
                hql = "UPDATE "+ tablename +" SET mailSendSuccess = :Status"
                       + " WHERE email = :Email";
                query = sessionFactory.getCurrentSession().createQuery(hql);
                query.setBoolean("Status", mailStatus);
                query.setString("Email", email);
                return (Integer)query.executeUpdate();                      
            }
        } catch (HibernateException ex) {
            throw new Exception(ex);
        } catch (NullPointerException ex) {
            throw new Exception(ex);
        }  
        return null;
    }
      
}
