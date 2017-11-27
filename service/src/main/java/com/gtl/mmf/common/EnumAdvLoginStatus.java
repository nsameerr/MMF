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
public enum EnumAdvLoginStatus {

    changePassword(0),
    advisor_network_view(1),
    advisordashboard(2);
    private final int value;

    private EnumAdvLoginStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumAdvLoginStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumAdvLoginStatus>();

    static {
        for (EnumAdvLoginStatus type : EnumAdvLoginStatus.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumAdvLoginStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
}
