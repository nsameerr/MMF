/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.git.marketsummary.ws.HolidayCalender;
import com.git.marketsummary.ws.WsSummary;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.dd_MM_yyyy;
import static com.gtl.mmf.service.util.IConstants.NSE;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trainee8
 */
public class HoliDayCalenderUtil {

    private static boolean holyday_NSE;
    private static boolean holyday_BSE;
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.HoliDayCalenderUtil");

    /**
     * This method is used to check whether the current day is market holiday or
     * not
     *
     * @param venue
     * @return
     */
    public static boolean checkHoliday(String venue) {
        boolean venue_holiday = false;
        try {
            WsSummary ws = new WsSummary();
            List<HolidayCalender> holiDayCalender
                    = ws.getWsdlPort().getHolidayCalender(venue, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
            SimpleDateFormat parser = new SimpleDateFormat(dd_MM_yyyy);
            SimpleDateFormat localDateFormat = new SimpleDateFormat(dd_MM_yyyy);
            String time = localDateFormat.format(new Date());
            Date current_date = parser.parse(time);
            if (!holiDayCalender.isEmpty()) {
                for (HolidayCalender holyday : holiDayCalender) {
                    Date holiday_date = parser.parse(holyday.getHolidayDate());
                    if (holiday_date.equals(current_date)) {
                        venue_holiday = true;
                        break;
                    }
                }
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(current_date);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                venue_holiday = true;
            }

        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return venue_holiday;
    }

    /**
     * This method is used to check whether the specified date is market holiday
     * or not
     *
     * @param venue
     * @param date
     * @return
     */
    public static boolean checkHoliday(String venue, Date date) {
        boolean venue_holiday = false;
        try {
//            boolean testStatus = testURLStatus();
            boolean testStatus = false;
            LOGGER.log(Level.INFO, "WsSummary Test Connection status{0}", testStatus);
            SimpleDateFormat parser = new SimpleDateFormat(dd_MM_yyyy);
            SimpleDateFormat localDateFormat = new SimpleDateFormat(dd_MM_yyyy);
            String time = localDateFormat.format(date);
            Date current_date = parser.parse(time);
            if (testStatus) {
                WsSummary ws = new WsSummary();
                List<HolidayCalender> holiDayCalender
                        = ws.getWsdlPort().getHolidayCalender(venue, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
                if (!holiDayCalender.isEmpty()) {
                    for (HolidayCalender holyday : holiDayCalender) {
                        Date holiday_date = parser.parse(holyday.getHolidayDate());
                        if (holiday_date.equals(current_date)) {
                            venue_holiday = true;
                            break;
                        }
                    }
                }
            } else {
                LOGGER.log(Level.INFO, "Loading Holiday calender from database.");
                List<ExchangeHolidayTb> holidayTbs = LookupDataLoader.getExchangeHoliday();
                LOGGER.log(Level.INFO, "Loading Holiday calender completed.");
                if (!holidayTbs.isEmpty()) {
                    LOGGER.log(Level.INFO, "Checking holiday or not ");
                    for (ExchangeHolidayTb exchangeHolidayTb : holidayTbs) {
                        Date holdy_date = DateUtil.stringToDate(DateUtil.dateToString(exchangeHolidayTb.getHdate(), dd_MM_yyyy), dd_MM_yyyy);
                        if (holdy_date.equals(current_date)) {
                            venue_holiday = true;
                            break;
                        }
                    }
                }
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                venue_holiday = true;
            }
            LOGGER.log(Level.INFO, " Date - {0} , Day of week - {1}", new Object[]{date, cal.get(Calendar.DAY_OF_WEEK)});
            LOGGER.log(Level.INFO, "Checking for Exchange-{0} ,holiday completed . - status {1}", new Object[]{venue, venue_holiday});
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            return false;
        }
        return venue_holiday;
    }

    /**
     * Method to check WsSummary connection status.
     *
     * @return
     */
    public static boolean testURLStatus() {
        Socket socket = null;
        boolean status = true;
        try {
            LOGGER.log(Level.INFO, "Checking connection to WsSummary");
            socket = new Socket("192.168.9.92", 80);
            status = true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            return false;
        } finally {
            if (socket != null && socket.isConnected()) {
                try {
                    socket.close();
                    status = true;
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                    return false;
                }

            }
        }
        return status;
    }
}
