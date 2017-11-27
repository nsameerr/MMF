/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 07958
 */
public enum EnumCustomerNotification {

    REBALANCE_REQUIRED(10),
    PORTFOLIO_CHANGED(20),
    RATE_ADVISOR(30);
    private final int value;

    private EnumCustomerNotification(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumCustomerNotification> INT_TO_TYPE_MAP = new HashMap<Integer, EnumCustomerNotification>();

    static {
        for (EnumCustomerNotification type : EnumCustomerNotification.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumCustomerNotification fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
}
