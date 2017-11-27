/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.entity.CitiesTb;
import static com.gtl.mmf.service.util.IConstants.COLON;
import static com.gtl.mmf.service.util.IConstants.CROSS;
import static com.gtl.mmf.service.util.IConstants.ELEVEN;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INDEX_POS_ONE;
import static com.gtl.mmf.service.util.IConstants.INDEX_POS_SIX;
import static com.gtl.mmf.service.util.IConstants.INDEX_POS_TEN;
import static com.gtl.mmf.service.util.IConstants.SEVEN;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.THIRTEEN;
import static com.gtl.mmf.service.util.IConstants.TWO;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 *
 * @author trainee12
 */
public final class ConversionMethods {

    private static final String MULTI_SPACE = "\\s+";

    /**
     *
     * @param value
     * @param mappingData
     * @return
     */
    public static String[] convertToCheckBoxArray(String value, HashMap<String, Integer> mappingData) {
        String[] data = new String[mappingData.size()];
        if (mappingData.containsKey(value)) {
            data[mappingData.get(value)] = CROSS;
        }
        return data;
    }

    /**
     *
     * @param value
     * @param lengthInfo
     * @return
     */
    public static String[] convertArray(String value, String lengthInfo) {

        String[] info = lengthInfo.split(COLON.trim());
        String[] data = null;
        if (value != null) {
            data = new String[Integer.parseInt(info[1]) > value.length() ? Integer.parseInt(info[1]) : value.length()];
            value = value.replaceAll(MULTI_SPACE, SPACE);
            for (int i = 0; i < value.length(); i++) {
                data[i] = String.valueOf(value.charAt(i)).toUpperCase();
            }
        }
        return data;
    }

    /**
     *
     * @param number
     * @return array containing number,std code and isd code at respective
     * positions.
     */
    public static String[] splitTelephone(String number) {
        String[] data = new String[3];
        int len = number.length();
        if (len >= SEVEN) {
            data[0] = number.substring((len - INDEX_POS_ONE) - INDEX_POS_SIX, len);
        }
        if (len >= ELEVEN) {
            data[1] = number.substring((len - INDEX_POS_ONE) - INDEX_POS_TEN, (len - INDEX_POS_ONE) - INDEX_POS_SIX);
        }
        if (len == THIRTEEN) {
            data[2] = number.substring(ZERO, TWO);
        }
        return data;
    }

    /**
     *
     * @param state
     * @return collection of cities coming under the selected state.
     */
    public static Map<String, String> getStateWiseCity(String state) {
        HashMap<String, String> cityMap = new LinkedHashMap<String, String>();
        List<CitiesTb> citymaster = LookupDataLoader.getMasterCities();
        for (CitiesTb city : citymaster) {
            if (city.getStateCode().equals(state)) {
                cityMap.put(city.getCityName(), city.getCityName());
            }
        }
        return cityMap;
    }

    public static String[] splitIntoThreePosition(String name) {
        String[] nameArray = new String[3];
        if (StringUtils.hasText(name)) {
            String[] spaceSplit = name.split(SPACE);
            if (spaceSplit.length == 1) {
                nameArray[0] = spaceSplit[0];
                nameArray[1] = EMPTY_STRING;
                nameArray[2] = EMPTY_STRING;
            } else if (spaceSplit.length == 2) {
                nameArray[0] = spaceSplit[0];
                nameArray[1] = EMPTY_STRING;
                nameArray[2] = spaceSplit[1];
            } else {
                StringBuilder lastName = new StringBuilder();
                for (int i = 2; i < spaceSplit.length; i++) {
                    lastName.append(spaceSplit[i]).append(SPACE);
                }
                nameArray[0] = spaceSplit[0];
                nameArray[1] = spaceSplit[1];
                nameArray[2] = lastName.toString();
            }

        }
        return nameArray;
    }
}
