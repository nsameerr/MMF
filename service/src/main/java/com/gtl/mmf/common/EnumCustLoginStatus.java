/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 07960
 */
public enum EnumCustLoginStatus {

    changePassword(0),
    investor_network_view(1),
    investordashboard(2);
    private final int value;

    private EnumCustLoginStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumCustLoginStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumCustLoginStatus>();

    static {
        for (EnumCustLoginStatus type : EnumCustLoginStatus.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumCustLoginStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);

    }
}
