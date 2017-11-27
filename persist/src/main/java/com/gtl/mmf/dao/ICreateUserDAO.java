/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.AdvisorDetailsTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;

/**
 *
 * @author 07958
 */
public interface ICreateUserDAO {

    public Integer insertNewAdvisor(MasterAdvisorTb masterAdvisor);

    public Integer insertNewInvestor(MasterCustomerTb masterCustomer, MandateFormTb mandate);

    public Integer insertNewAdvisorDetailsTb(AdvisorDetailsTb advisorDetailsTb);
    
    public IfcMicrMappingTb getBankdetailsOfUser(String ifsc_code);
}
