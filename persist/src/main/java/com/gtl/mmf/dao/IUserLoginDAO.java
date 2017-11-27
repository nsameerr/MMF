/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.AdminuserTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.TempRegistrationTb;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IUserLoginDAO {

    public List advisorLogin(String username, String password);

    public List investorLogin(String username, String password, List<Integer> statusToAvoid);

    public AdminuserTb adminLogin(AdminuserTb admin);

    public void changeAdvisorDetails(MasterAdvisorTb masterAdvisor);

    public void changeInvestorDetails(MasterCustomerTb masterCustomer);

    public List advisorTwoFactorLogin(String username, String password, String dob);

    public List investorTwoFactorLogin(String username, String password, String dob, List<Integer> statusToAvoid);

    public boolean getLinkedinExpireIn(String currentDateTime, String username);
    
    public List registrationLogin(String username, String password,String type);
    
    //method to get customer status from master_tb
    public Integer customerStatus(String email,String password,String type);

    public void changeTempDetails(TempRegistrationTb tempRegistrationTb);
    
    public List tempUserlogin(String username, String password,int status);
    
    public boolean financialPlannerStatus(String regId);
    
    public String getUserPersonalDetails(String email,boolean userType,String fieldname);

    public Boolean getUserType(String email, String password);
}
