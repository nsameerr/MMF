/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author 09860
 */
public final class AgeUtil {

    public static int findAge(String dob) {

//TAKE SUBSTRINGS OF THE DOB SO SPLIT OUT YEAR, MONTH AND DAY
//INTO SEPERATE VARIABLES
        int yearDOB = Integer.parseInt(dob.substring(6, 10));
        int monthDOB = Integer.parseInt(dob.substring(3, 5));
        int dayDOB = Integer.parseInt(dob.substring(0, 2));

//CALCULATE THE CURRENT YEAR, MONTH AND DAY
//INTO SEPERATE VARIABLES
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        java.util.Date date = new java.util.Date();
        int thisYear = Integer.parseInt(dateFormat.format(date));

        dateFormat = new SimpleDateFormat("mm");
        date = new java.util.Date();
        int thisMonth = Integer.parseInt(dateFormat.format(date));

        dateFormat = new SimpleDateFormat("dd");
        date = new java.util.Date();
        int thisDay = Integer.parseInt(dateFormat.format(date));

//CREATE AN AGE VARIABLE TO HOLD THE CALCULATED AGE
//TO START WILL – SET THE AGE EQUEL TO THE CURRENT YEAR MINUS THE YEAR
//OF THE DOB
        int age = thisYear - yearDOB;

//IF THE CURRENT MONTH IS LESS THAN THE DOB MONTH
//THEN REDUCE THE DOB BY 1 AS THEY HAVE NOT HAD THEIR
//BIRTHDAY YET THIS YEAR
        if (thisMonth < monthDOB) {
            age = age - 1;
        }

//IF THE MONTH IN THE DOB IS EQUEL TO THE CURRENT MONTH
//THEN CHECK THE DAY TO FIND OUT IF THEY HAVE HAD THEIR
//BIRTHDAY YET. IF THE CURRENT DAY IS LESS THAN THE DAY OF THE DOB
//THEN REDUCE THE DOB BY 1 AS THEY HAVE NOT HAD THEIR
//BIRTHDAY YET THIS YEAR
        if (thisMonth == monthDOB && thisDay < dayDOB) {
            age = age
                    - 1;
        }

        return age;
    }

}
