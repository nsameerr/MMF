/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IEODUpdateHolidayCalenderBAO;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class HolidayUpdationTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.HolidayUpdationTask");
    @Autowired
    private IEODUpdateHolidayCalenderBAO eodUpdateHolidayCalenderBAO;

    void updateHolidayCalender() {
        
        LOGGER.info("Notify admin task started");
        eodUpdateHolidayCalenderBAO.notifyAdmin();
        LOGGER.info("Notify admin task completed");
        
    }

}
