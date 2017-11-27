/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IUpdateCashFlowBAO;
import com.gtl.mmf.service.util.BeanLoader;
import org.jboss.logging.Logger;

/**
 *
 * @author 09860
 */
public class UpdateCashFlowTask {
    
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.UpdateCashFlowTask");
    
    public void updateAllocationFund(){
        IUpdateCashFlowBAO iUpdateCashFlowBAO = (IUpdateCashFlowBAO) BeanLoader.getBean("iUpdateCashFlowBAO");
        iUpdateCashFlowBAO.getCashFlowDetails();
        
    }
    
}
