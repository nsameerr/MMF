/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IAdvisorySettlemetBAO;
import com.gtl.mmf.dao.IAdvisorySettlemetDAO;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class AdvisorySettlemetBAOImpl implements IAdvisorySettlemetBAO{
    
    private  static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.AdvisorySettlemetBAOImpl");
    @Autowired
    IAdvisorySettlemetDAO iAdvisorySettlemetDAO;

    public void getCustomerFeeDetails() {
        List<Object> responseList = iAdvisorySettlemetDAO.getCustomerFeeList();
    }
    
}
