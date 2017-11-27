/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.service.util.HoliDayCalenderUtil;
import static com.gtl.mmf.service.util.IConstants.BSE;
import static com.gtl.mmf.service.util.IConstants.NSE;
import com.gtl.mmf.service.util.LookupDataLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trainee8
 */
public class CheckingVenueHolidayTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.CheckingVenueHolidayTask");

    public void checkingVenueHoliday() {
       
        boolean holiday_NSE = HoliDayCalenderUtil.checkHoliday(NSE);
        LOGGER.log(Level.INFO, "Checking holiday for venue NSE: Holiday", holiday_NSE);

        LookupDataLoader.setHolyday_NSE(holiday_NSE);
        boolean holiday_BSE = HoliDayCalenderUtil.checkHoliday(BSE);
        LOGGER.log(Level.INFO, "Checking holiday for venue BSE: Holiday", holiday_BSE);

        LookupDataLoader.setHolyday_BSE(holiday_BSE);
    }
}
