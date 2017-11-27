/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import java.util.HashMap;
import java.util.Map;

/**
 * For ECS Mandate
 *
 * @author trainee12
 */
public enum EnumMandate {

    NOT_VERIFIED(0),
    VERIFIED(1),
    REJECTED(2);

    private final int VALUE;

    private EnumMandate(int VALUE) {
        this.VALUE = VALUE;
    }

    public int getValue() {
        return VALUE;
    }
    
    private static final Map<Integer, EnumMandate> INT_TO_TYPE_MAP = new HashMap<Integer, EnumMandate>();

    static {
        for (EnumMandate type : EnumMandate.values()) {
            INT_TO_TYPE_MAP.put(type.VALUE, type);
        }
    }

    public static EnumMandate fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
}
