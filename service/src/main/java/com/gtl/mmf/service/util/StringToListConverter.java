/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class StringToListConverter {

    private StringToListConverter() {
    }

    public static List<String> toStringList(String inputString, String seperator, String serquenceSeperator) {

        List<String> lst = new ArrayList<String>();
        Map<Integer, String> listMap = new LinkedHashMap<Integer, String>();
        Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
        if (!inputString.equals(EMPTY_STRING)) {
            for (String str : inputString.split(seperator)) {
                String arr[] = str.split(serquenceSeperator);
                listMap.put(Integer.parseInt(arr[ONE]), arr[ZERO]);
            }
            List<Integer> keySet = new ArrayList<Integer>(listMap.keySet());
            Collections.sort(keySet);
            for (Integer key : keySet) {
                returnMap.put(key, listMap.get(key));
            }
            for (Map.Entry<Integer, String> entry : returnMap.entrySet()) {
                Integer integer = entry.getKey();
                String string = entry.getValue();
                lst.add(string);
            }
        }
        return lst;
    }

}
