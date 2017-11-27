/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IExceptionLogDAO;
import java.util.Date;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ExceptionLogDAOImpl implements IExceptionLogDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void logErrorToDb(String errorMessage, String exception) {
        SQLQuery sqlQuery;
        String sql = " INSERT INTO `mmf_exception_log_tb`(`error_message`,`exception`,`LastUpdateOn`)"
                + " SELECT :ErrorMessage,:Exception,:LastUpdateOn";
        sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setString("ErrorMessage", errorMessage);
        sqlQuery.setString("Exception", exception);
        sqlQuery.setDate("LastUpdateOn", new Date());
        sqlQuery.executeUpdate();
    }

}
