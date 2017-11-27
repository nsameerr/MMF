/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

/**
 *
 * @author 07958
 */
public class BenchmarkUtil {

    private static final String NSE = "NSE";
    private static final String BSE = "BSE";
    private static final String NSEFO = "NSEFO";
    private static final String NSECD = "NSECD";
    private static final String NSEMF = "NSEMF";
    private static final String BSEFO = "BSEFO";
    private static final String NSEDEBT = "NSEDEBT";

    private BenchmarkUtil() {
    }

    
    /**
     * This method is used to get the code of venue when venue name is given
     * @param venue - name of venue 
     * @return string code that representing venue
     */
    public static String getVenueCode(String venue) {
        String venueCode = "0";
        if (venue.equalsIgnoreCase(NSE)) {
            venueCode = "1";
        } else if (venue.equalsIgnoreCase(BSE)) {
            venueCode = "2";
        } else if (venue.equalsIgnoreCase(NSEFO)) {
            venueCode = "3";
        } else if (venue.equalsIgnoreCase(NSECD)) {
            venueCode = "7";
        } else if (venue.equalsIgnoreCase(NSEMF)) {
            venueCode = "9";
        } else if (venue.equalsIgnoreCase(BSEFO)) {
            venueCode = "11";
        } else if (venue.equalsIgnoreCase(NSEDEBT)) {
            venueCode = "14";
        }
        return venueCode;
    }
}
