/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Administrator notification status 
 * @author 09860
 */
public enum EnumAdminNotificationStatus {

    REG_KIT_INADEQUATE(50),
    REG_KIT_OUT_OF_STOCK(100),
    UPDATE_HOLIDAY_CALENDER(150);

    private final int value;

    private EnumAdminNotificationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumAdminNotificationStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumAdminNotificationStatus>();

    static {
        for (EnumAdminNotificationStatus type : EnumAdminNotificationStatus.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumAdminNotificationStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }

}
