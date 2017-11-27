/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IUpdateUserDAO;
import com.gtl.mmf.entity.InvestorNomineeDetailsTb;
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
public class UpdateUserDAOImpl implements IUpdateUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void updateAdvisorDetails(MasterAdvisorTb masterAdvisor) {
        sessionFactory.getCurrentSession().update(masterAdvisor.getMasterApplicantTb());
        sessionFactory.getCurrentSession().update(masterAdvisor);
        sessionFactory.getCurrentSession().saveOrUpdate(masterAdvisor.getMasterAdvisorQualificationTb());
    }

    public void updateInvestorDetails(MasterCustomerTb masterCustomer) {
        sessionFactory.getCurrentSession().update(masterCustomer.getMasterApplicantTb());
        sessionFactory.getCurrentSession().update(masterCustomer);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    public void deletTempUserDetails(String email) {
        String HQL = "DELETE From TempRegistrationTb WHERE email =:email";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public void updateNominationDetails(InvestorNomineeDetailsTb nomineeDetails) {
        sessionFactory.getCurrentSession().saveOrUpdate(nomineeDetails);
    }
    
    public void updateMandateDetails(MandateFormTb mandateFormTb) {
        sessionFactory.getCurrentSession().saveOrUpdate(mandateFormTb);
    }
    
}
