/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.quartz.job;

import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author 09860
 */
public class HolidayCalenderUpdationJob extends QuartzJobBean{
    
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.HolidayCalenderUpdationJob");
    private HolidayUpdationTask holidayUpdationTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        
        LOGGER.info("Check for update Holiday Calender and notify admin.");
        holidayUpdationTask.updateHolidayCalender();
        LOGGER.info("Check for update Holiday Calender and notify admin completed.");
        
    }

    public HolidayUpdationTask getHolidayUpdationTask() {
        return holidayUpdationTask;
    }

    public void setHolidayUpdationTask(HolidayUpdationTask holidayUpdationTask) {
        this.holidayUpdationTask = holidayUpdationTask;
    }
    
    
    
}
