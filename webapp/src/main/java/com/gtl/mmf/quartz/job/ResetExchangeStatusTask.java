/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.quartz.job;

import com.gtl.mmf.service.util.LookupDataLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trainee12
 */
public class ResetExchangeStatusTask {
    
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.ResetExchangeStatusTask");
    
    public void resetOnlineStatus() {
        LOGGER.log(Level.INFO, "Clearing venueStatusMap in LookupDataLoader");
        LookupDataLoader.getVenueStatusMap().clear();
        LOGGER.log(Level.INFO, "venueStatusMap cleared for the day");
    }
    
}
