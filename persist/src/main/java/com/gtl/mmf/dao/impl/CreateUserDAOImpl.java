/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.ICreateUserDAO;
import com.gtl.mmf.entity.AdvisorDetailsTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 07958
 */
@Repository
public class CreateUserDAOImpl implements ICreateUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Integer insertNewAdvisor(MasterAdvisorTb masterAdvisor) {
        Integer id = null;
        sessionFactory.getCurrentSession().update(masterAdvisor.getMasterApplicantTb());
        sessionFactory.getCurrentSession().update(masterAdvisor.getMasterAdvisorQualificationTb());
        id = (Integer) sessionFactory.getCurrentSession().save(masterAdvisor);
        return id;
    }

    public Integer insertNewInvestor(MasterCustomerTb masterCustomer, MandateFormTb mandate) {
        Integer id = null;
        sessionFactory.getCurrentSession().update(masterCustomer.getMasterApplicantTb());
        sessionFactory.getCurrentSession().update(mandate);
        id = (Integer) sessionFactory.getCurrentSession().save(masterCustomer);
        return id;
    }

    public Integer insertNewAdvisorDetailsTb(AdvisorDetailsTb advisorDetailsTb) {
        return (Integer) sessionFactory.getCurrentSession().save(advisorDetailsTb);
    }
    
    
     public String getPasswordForUser(String email, String userType) {
        String HQL = null;
        
        HQL = " SELECT password From TempRegistrationTb WHERE email =:email AND userType =:type";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setParameter("email", email);
        query.setParameter("type", userType);
        List list = query.list();
        String password = (String) list.get(0);
        return password;
    }
     
     public IfcMicrMappingTb getBankdetailsOfUser(String ifsc_code){
         String hql = "FROM IfcMicrMappingTb WHERE ifsc=:IfscCode";
         Query query = sessionFactory.getCurrentSession().createQuery(hql);
         query.setString("IfscCode",ifsc_code);
         return (IfcMicrMappingTb) query.uniqueResult();
     }

}
