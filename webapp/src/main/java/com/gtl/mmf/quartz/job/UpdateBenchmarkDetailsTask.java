/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.git.mds.common.MDSMsg;
import com.gtl.mmf.bao.IBenchmarkDetailsBAO;
import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import com.gtl.mmf.service.util.HoliDayCalenderUtil;
import static com.gtl.mmf.service.util.IConstants.BSE;
import static com.gtl.mmf.service.util.IConstants.MINUS_ONE;
import static com.gtl.mmf.service.util.IConstants.NSE;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.MarketDataUtil;
import com.gtl.mmf.service.vo.BenchmarkDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * this class is used to Updating NSE & BSE benchmark details
 *
 * @author 07958
 */
public class UpdateBenchmarkDetailsTask {

    @Autowired
    private IBenchmarkDetailsBAO benchmarkDetailsBAO;
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.UpdateBenchmarkDetailsTask");
    private static final String OPEN_INDEX = "OPEN_INDEX";
    private static final String HIGH_INDEX_VALUE = "HIGH_INDEX_VALUE";
    private static final String LOW_INDEX_VALUE = "LOW_INDEX_VALUE";
    private static final String CLOSE_INDEX = "CLOSE_INDEX";
    private static final int NSE_BENCHMARKID = ONE;
    private static final int BSE_BENCHMARKID = 2;
    @Autowired
    private IExceptionLogDAO exceptionLogDAO;

    /**
     * Inserting NSE & BSE benchmark details
     */
    public void updateBenchmarkDetails() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(cal.DATE, MINUS_ONE);
            LOGGER.info("Checking for NSE-Exchange holiday");
            if (!HoliDayCalenderUtil.checkHoliday(NSE, cal.getTime())) {
                // Updating NSE benchmark details
                LOGGER.info("Benchmark details updation - NSE - started.");
                BenchmarkDetailsVO nseBenchmarkDetailsVO = this.getBenchmarkDetailsVO(NSE, NSE_BENCHMARKID);
                if (nseBenchmarkDetailsVO != null) {
                    benchmarkDetailsBAO.insertNewBenchmarkDetails(nseBenchmarkDetailsVO);
                    LOGGER.info("Benchmark details updation - NSE - completed.");
                } else {
                    LOGGER.info("Benchmark details updation for NSE- Failed");
                    LOGGER.info("No data recieved from MDS");
                }
            }
            LOGGER.info("Checking for BSE-Exchange holiday");
            if (!HoliDayCalenderUtil.checkHoliday(BSE, cal.getTime())) {
                // Updating BSE benchmark details
                LOGGER.info("Benchmark details updation - BSE - started.");
                BenchmarkDetailsVO bseBenchmarkDetailsVO = this.getBenchmarkDetailsVO(BSE, BSE_BENCHMARKID);
                if (bseBenchmarkDetailsVO != null) {
                    benchmarkDetailsBAO.insertNewBenchmarkDetails(bseBenchmarkDetailsVO);
                    LOGGER.info("Benchmark details updation - BSE - completed.");
                } else {
                    LOGGER.info("Benchmark details updation for BSE- Failed");
                    LOGGER.info("No data recieved from MDS");
                }
            }

        } catch (Exception ex) {
            ExceptionLogUtil.logError("Benchmark details updation failed.", ex);
            ExceptionLogUtil.mailError("Benchmark details updation failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Benchmark details updation failed.", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method is used to get Current Benchmark details from MDS
     *
     * @param benchmarkName
     * @param benchmarkId
     * @return
     */
    private BenchmarkDetailsVO getBenchmarkDetailsVO(String benchmarkName, int benchmarkId) {
        MDSMsg mdsResponse = MarketDataUtil.getBenchmarkDetails(benchmarkName);
        BenchmarkDetailsVO benchmarkDetailsVO = new BenchmarkDetailsVO();
        if (mdsResponse != null) {
            benchmarkDetailsVO.setBenchmarkId(benchmarkId);
            benchmarkDetailsVO.setClosedIndex(mdsResponse.getField(CLOSE_INDEX) == null ? ZERO
                    : mdsResponse.getField(CLOSE_INDEX).doubleValue());
            benchmarkDetailsVO.setOpenIndex(mdsResponse.getField(OPEN_INDEX) == null ? ZERO
                    : mdsResponse.getField(OPEN_INDEX).doubleValue());
            benchmarkDetailsVO.setHighIndex(mdsResponse.getField(HIGH_INDEX_VALUE) == null ? ZERO
                    : mdsResponse.getField(HIGH_INDEX_VALUE).doubleValue());
            benchmarkDetailsVO.setLowIndex(mdsResponse.getField(LOW_INDEX_VALUE) == null ? ZERO
                    : mdsResponse.getField(LOW_INDEX_VALUE).doubleValue());
        }
        return mdsResponse != null ? benchmarkDetailsVO : null;
    }

    private boolean checksaturdaySundayOff() {
        boolean venue_holiday = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.DATE, MINUS_ONE);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            venue_holiday = true;
        }
        LOGGER.log(Level.INFO, " Date - {0} , Day of week - {1}", new Object[]{cal.getTime(), cal.get(Calendar.DAY_OF_WEEK)});
        LOGGER.log(Level.INFO, "Checking for Saturday- Sunday off completed . - status {0}", venue_holiday);
        return venue_holiday;
    }
}
