/**
 *
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.StringUtils;

/**
 * @author 08237
 *
 */
public final class DateUtil {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.DateUtil");
    private static final String EMPTY_STRING = "";
    private static final String SHORT_DATE_FORMAT = "ddMMyyyy";

    private DateUtil() {
    }

    public static String dateToString(Date obj, String format) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        SimpleDateFormat dtFormatter = new SimpleDateFormat(format);
        return dtFormatter.format(obj);
    }

    public static Date stringToDate(String strDate, String format) throws ParseException {
        SimpleDateFormat dtFormatter = new SimpleDateFormat(format);
        if (StringUtils.isEmpty(strDate)) {
            throw new ParseException("Cannot convert empty string to Date", ZERO);
        }
        return dtFormatter.parse(strDate.trim());
    }

    public static String convertShortDate(Date obj) {
        return dateToString(obj, SHORT_DATE_FORMAT);
    }

    public static Date convertShortDate(String str) throws ParseException {
        return stringToDate(str, SHORT_DATE_FORMAT);
    }

    public static String getElapsedTimeMsg(Date statusDate) {
        Date currentDate = new Date();
        long elapsedTime = currentDate.getTime() - statusDate.getTime();
        if (TimeUnit.MILLISECONDS.toDays(elapsedTime) != ZERO) {
            return String.format("%d day(s)", TimeUnit.MILLISECONDS.toDays(elapsedTime));
        }
        if (TimeUnit.MILLISECONDS.toHours(elapsedTime) != ZERO) {
            return String.format("%d hour(s)", TimeUnit.MILLISECONDS.toHours(elapsedTime));
        }
        if (TimeUnit.MILLISECONDS.toMinutes(elapsedTime) != ZERO) {
            return String.format("%d min(s)", TimeUnit.MILLISECONDS.toMinutes(elapsedTime));
        }
        return String.format("%d sec(s)", TimeUnit.MILLISECONDS.toSeconds(elapsedTime));
    }

    public static Date getECSPayDate() {
        Calendar calendar = Calendar.getInstance();
        LOGGER.info("ECS calculated date :" + calendar.getTime());
        System.out.println("Before - " + calendar.getTime());
        calendar.add(Calendar.DATE, 4);
        if (calendar.DATE == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 2);
        } else if (calendar.DATE == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        Date dueDate = calendar.getTime();
        LOGGER.log(Level.INFO, "ECS Pay date :{0}", dueDate);
        return dueDate;
    }

    public static boolean isQuarter() {
        Map quarterMap = new HashMap<String, String>();
        quarterMap.put("Q4", "1_1");
        quarterMap.put("Q1", "1_4");
        quarterMap.put("Q2", "1_7");
        quarterMap.put("Q3", "1_10");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        LOGGER.log(Level.INFO, "Checking Current date is quarter Date  {0}", cal.getTime());
        StringBuffer quarterString = new StringBuffer();
        quarterString = quarterString.append(cal.get(cal.DAY_OF_MONTH)).append("_").append(cal.get(cal.MONTH) + 1);
        LOGGER.log(Level.INFO, "Quarter String  {0}", quarterString.toString());
        return quarterMap.containsValue(quarterString.toString());
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String format = sdf.format(cal.getTime());
        return format;
    }


}
