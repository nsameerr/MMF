/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IUserLoginDAO;
import com.gtl.mmf.entity.AdminuserTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.TempRegistrationTb;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 07958
 */
@Repository
public class UserLoginDAOImpl implements IUserLoginDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List advisorLogin(String username, String password) {
        String adviserHql = "From MasterAdvisorTb where email=:Username and password=:password AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(adviserHql);
        query.setString("Username", username);
        query.setString("password", password);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public List investorLogin(String username, String password, List<Integer> statusToAvoid) {
        String sql = " SELECT {customer.*}, c.relation_id relId FROM master_customer_tb customer"
                + " LEFT JOIN customer_advisor_mapping_tb c ON c.customer_id=customer.customer_id"
                + " AND c.relation_status NOT IN (:status)"
                + " WHERE customer.email=:emailId and customer.password=:password AND customer.`is_active_user` = :Active";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addEntity("customer", MasterCustomerTb.class)
                .addScalar("relId", IntegerType.INSTANCE)
                .setString("emailId", username)
                .setString("password", password)
                .setBoolean("Active", Boolean.TRUE)
                .setParameterList("status", statusToAvoid);
        return sqlQuery.list();
    }

    public AdminuserTb adminLogin(AdminuserTb admin) {
        String HQL = "FROM AdminuserTb where username=:username and password=:password and userType=:usertype";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("username", admin.getUsername());
        query.setString("password", admin.getPassword());
        query.setString("usertype", admin.getUserType());
        return (AdminuserTb) query.uniqueResult();
    }

    public List investorTwoFactorLogin(String username, String password, String dob, List<Integer> statusToAvoid) {
        String sql = " SELECT {customer.*}, c.relation_id relId,c.relation_status relStatus FROM master_customer_tb customer"
                + " LEFT JOIN customer_advisor_mapping_tb c ON c.customer_id=customer.customer_id"
                + " AND c.relation_status NOT IN (:status)"
                + " WHERE customer.email=:emailId and customer.password=:password"
                + " AND  date_format(customer.dob,'%Y%m%d')= date_format(:dob,'%Y%m%d') AND customer.`is_active_user` = :Active";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addEntity("customer", MasterCustomerTb.class)
                .addScalar("relId", IntegerType.INSTANCE)
                .addScalar("relStatus", IntegerType.INSTANCE)
                .setString("emailId", username)
                .setString("password", password)
                .setString("dob", dob)
                .setBoolean("Active", Boolean.TRUE)
                .setParameterList("status", statusToAvoid);
        return sqlQuery.list();
    }

    public List advisorTwoFactorLogin(String username, String password, String dob) {
        String adviserHql = " From MasterAdvisorTb where email=:Username"
                + " and password=:password and date_format(dob,'%Y%m%d')= date_format(:dob,'%Y%m%d') AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(adviserHql);
        query.setString("Username", username);
        query.setString("password", password);
        query.setString("dob", dob);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public void changeAdvisorDetails(MasterAdvisorTb masterAdvisor) {
        sessionFactory.getCurrentSession().saveOrUpdate(masterAdvisor);
    }

    public void changeInvestorDetails(MasterCustomerTb masterCustomer) {
        sessionFactory.getCurrentSession().saveOrUpdate(masterCustomer);
    }

    public boolean getLinkedinExpireIn(String currentDateTime, String username) {
        String HQL = " From MasterApplicantTb"
                + " where  email =:username and  date_format(linkedinExpireDate,'%Y/%m/%d') <=:currentDateTime"
                + " AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("currentDateTime", currentDateTime);
        query.setString("username", username);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list().isEmpty();
    }

    public List registrationLogin(String username, String password, String type) {
        String adviserHql = "From TempRegistrationTb where email=:Username and"
                + " password=:password and userType=:type";
        Query query = sessionFactory.getCurrentSession().createQuery(adviserHql);
        query.setString("Username", username);
        query.setString("password", password);
        query.setString("type", type);
        return query.list();
    }

    public Integer customerStatus(String email, String password, String type) {
        Integer status = null;
        String HQL = " SELECT mastr.status FROM master_applicant_tb mastr "
                + "LEFT JOIN temp_registration_tb temp ON temp.email = mastr.email "
                + "WHERE temp.password =:password and temp.email=:email "
                + "and temp.user_type=:type";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(HQL);
        query.setParameter("email", email);
        query.setParameter("password", password);
        query.setParameter("type", type);
        List list = query.list();
        if (!list.isEmpty()) {
            status = (Integer) list.get(0);
        }
        return status;
    }

    public void changeTempDetails(TempRegistrationTb tempRegistrationTb) {
        sessionFactory.getCurrentSession().saveOrUpdate(tempRegistrationTb);
    }

    public List tempUserlogin(String username, String password, int status) {
        String tempHql = "From TempRegistrationTb where email=:Username and password=:password AND initLogin = :status";
        Query query = sessionFactory.getCurrentSession().createQuery(tempHql);
        query.setString("Username", username);
        query.setString("password", password);
        query.setInteger("status", status);
        return query.list();
    }

	public boolean financialPlannerStatus(String customerId) {
		String tempHql = " From FpMasterUserInputDetailsTb where customerId=:CustomerId";	
		Query query = sessionFactory.getCurrentSession().createQuery(tempHql);
		query.setString("CustomerId", customerId);
		boolean result = query.list().isEmpty();
		return result;
	}

	public String getUserPersonalDetails(String email, boolean userType, String fieldname) {
		String hql = "";
		if (userType) {
			// true is advisor
			if(fieldname.equals("firstname")){
				 hql = "SELECT firstname FROM TempAdv WHERE email =:email " ;
			}
		} else {
			// false is investor
			if(fieldname.equals("firstname")){
				 hql = "SELECT firstname FROM TempInv WHERE email =:email " ;
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("email", email);
//		String name = (String)query.list().get(0);
		if(! query.list().isEmpty())
			return (String)query.list().get(0);
		else
			return "";
		
	}
	
	public Boolean getUserType(String email, String password){
		Boolean isAdvisor = null;
		
		String tempHql = "From TempRegistrationTb where email=:Username";
        Query query = sessionFactory.getCurrentSession().createQuery(tempHql);
        query.setString("Username", email);
        //query.setString("password", password);
        
        TempRegistrationTb tempReg= (TempRegistrationTb) query.uniqueResult();
        if(tempReg != null){
        	if("ADVISOR".equals(tempReg.getUserType())){
        		isAdvisor = true;        	
        	}else{
        		isAdvisor = false;
        	}
        		
        }
        else{
	        tempHql = "From MasterApplicantTb where email=:Username";
	        query = sessionFactory.getCurrentSession().createQuery(tempHql);
	        query.setString("Username", email);
	        
	        MasterApplicantTb masterApplicantTb = (MasterApplicantTb) query.uniqueResult();
	        if(masterApplicantTb != null){
	        	if(masterApplicantTb.getAdvisor())
	        		isAdvisor=true;	        	
	        	else
	        		isAdvisor=false;	        		        		
	        }
        }
		return isAdvisor;
	}
}
