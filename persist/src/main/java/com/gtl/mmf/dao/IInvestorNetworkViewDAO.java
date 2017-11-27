/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import java.util.List;

/**
 *
 * @author 07958
 */
public interface IInvestorNetworkViewDAO {
    
    public List<Object[]> getAllAdvisors(int customerId,int status);
}
