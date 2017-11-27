/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IUserForgotPwdDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author trainee3
 */
public class UserForgotPwdDAOImpl implements IUserForgotPwdDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int advisorPwdUpdate(String username, String mobile, String regid, String password) {
        int status1 = 0;
        int status2 = 0;
//        String adviserHql = "UPDATE MasterAdvisorTb set  password=:password,initLogin =:initLogin where email=:username and mobile=:mobile and registration_id=:regid";
        String adviserHql = "UPDATE MasterAdvisorTb SET  password=:password,initLogin =:initLogin WHERE email=:username";
        Query query = sessionFactory.getCurrentSession().createQuery(adviserHql);
        query.setString("username", username);
//        query.setString("mobile", mobile);
//        query.setString("regid", regid);
        query.setString("password", password);
        query.setInteger("initLogin", 0);
        status1 = query.executeUpdate();
        
        String hql = "UPDATE TempRegistrationTb SET  password=:password,initLogin=:status WHERE email=:username";
        Query query2 = sessionFactory.getCurrentSession().createQuery(hql);
        query2.setString("username", username);
        query2.setString("password", password);
        query2.setInteger("status", 101); 
        status2 = query2.executeUpdate();
        
        return (status1 == 0 ? status2:status1);
    }

    public int investorPwdUpdate(String username, String mobile, String regid, String password) {
        int status1 = 0;
        int status2 = 0;
//        String investorHql = "UPDATE MasterCustomerTb set  password=:password,initLogin =:initLogin where email=:username and mobile=:mobile and registration_id=:regid";
        String investorHql = "UPDATE MasterCustomerTb SET  password=:password,initLogin =:initLogin WHERE email=:username";
        Query query = sessionFactory.getCurrentSession().createQuery(investorHql);
        query.setString("username", username);
//        query.setString("mobile", mobile);
//        query.setString("regid", regid);
        query.setString("password", password);
        query.setInteger("initLogin", 0);
        status1 = query.executeUpdate();
        
        String hql = "UPDATE TempRegistrationTb SET  password=:password ,initLogin=:status WHERE email=:username";
        Query query2 = sessionFactory.getCurrentSession().createQuery(hql);
        query2.setString("username", username);
        query2.setString("password", password);
        query2.setInteger("status", 101); 
        status2 = query2.executeUpdate();
        
        return (status1 == 0 ? status2:status1);
    }

    public int adminPwdUpdate(String email, String mobile, String password, String username) {
        String investorHql = "UPDATE AdminuserTb set  password=:password where email=:email and mobile=:mobile and username =:username";
        Query query = sessionFactory.getCurrentSession().createQuery(investorHql);
        query.setString("email", email);
        query.setString("mobile", mobile);
        query.setString("password", password);
        query.setString("username", username);
        return query.executeUpdate();
    }

}
