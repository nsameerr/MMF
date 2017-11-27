/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.InvestorNomineeDetailsTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;

/**
 *
 * @author 07958
 */
public interface IUpdateUserDAO {

    public void updateAdvisorDetails(MasterAdvisorTb masterAdvisor);

    public void updateInvestorDetails(MasterCustomerTb masterCustomer);

    public String getPasswordForUser(String email, String type);

    public void deletTempUserDetails(String email);

    public void updateNominationDetails(InvestorNomineeDetailsTb nomineeDetails);

    public void updateMandateDetails(MandateFormTb mandateFormTb);

}
