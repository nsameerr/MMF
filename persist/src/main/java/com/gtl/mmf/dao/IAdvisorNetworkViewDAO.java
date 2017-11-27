/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAdvisorNetworkViewDAO {

    public List<Object[]> investorsUnderService(int advisorId);

    // Invetors who are not yet recived/sent any invite.
    public List<Object[]> investorsWithoutAdvisors(int advisorId, List<Short> statusWithoutAdvisors);

    public List<Object[]> getInvestorDetailsWithLinkedIn(List<String> linkdeinIds);

    public Object[] getPortfolioValues(Integer clientId, Date currentDate);

    public List<CustomerAdvisorMappingTb> statusList(Integer customerId);
}
