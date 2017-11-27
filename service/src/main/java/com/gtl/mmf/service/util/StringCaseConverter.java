/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;

/**
 *
 * @author 07958
 */
public final class StringCaseConverter {

    private StringCaseConverter() {
    }

    public static String toProperCase(String inputString) {
        String[] partsString = inputString.split("_");
        StringBuilder outputString = new StringBuilder();
        for (String stringItem : partsString) {
            stringItem = stringItem.toLowerCase();
            if (stringItem.equalsIgnoreCase(IConstants.IPS)) {
                stringItem = stringItem.toUpperCase();
            }
            String stringItemFL = stringItem.substring(ZERO, ONE).toUpperCase();
            outputString.append(stringItemFL).append(stringItem.substring(ONE, stringItem.length())).append(" ");
        }
        return outputString.toString();
    }

    public static String checkStartingZero(String Str) {
        String s = Str;
        if (Str != null && Str.startsWith("0")) {
            s = Str.substring(1, Str.length());
        }
        if (Str == null) {
            s = EMPTY_STRING;
        }
        return s;
    }
}
