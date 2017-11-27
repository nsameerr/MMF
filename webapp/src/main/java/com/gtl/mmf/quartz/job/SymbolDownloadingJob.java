/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import static com.gtl.mmf.service.util.IConstants.MMF_VENUE_HOLIDAY_ENABLED;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author trainee3
 */
public class SymbolDownloadingJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.SymbolDownloadingJob");
    private SymbolDownloadingTask symbolDownloadingTask;
    private CheckingVenueHolidayTask checkingVenueHolidayTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            LOGGER.info("Downloading symbolfile started");
            symbolDownloadingTask.symbolfileDownload();
            LOGGER.info("Downloading symbolfile terminated.");

            LOGGER.info("Checking venue holiday started");
            boolean chekHolidayEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_VENUE_HOLIDAY_ENABLED));
            if (chekHolidayEnabled) {
                checkingVenueHolidayTask.checkingVenueHoliday();
            }
            LOGGER.info("Checking venue holiday completed");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "SymbolDownloadingJob execution failed. Exception : {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    public SymbolDownloadingTask getSymbolDownloadingTask() {
        return symbolDownloadingTask;
    }

    public void setSymbolDownloadingTask(SymbolDownloadingTask symbolDownloadingTask) {
        this.symbolDownloadingTask = symbolDownloadingTask;
    }

    public CheckingVenueHolidayTask getCheckingVenueHolidayTask() {
        return checkingVenueHolidayTask;
    }

    public void setCheckingVenueHolidayTask(CheckingVenueHolidayTask checkingVenueHolidayTask) {
        this.checkingVenueHolidayTask = checkingVenueHolidayTask;
    }

}
