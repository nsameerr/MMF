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
public enum EnumContractStatus {

    NOT_SENT(0),
    NOT_RECIEVED(1),
    SENT(2),
    RECIEVED(3),
    REVIEW(4),
    SIGNED(5),
    EXPIRED(6),
    RENEW(7);
    private final int value;

    private EnumContractStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumContractStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumContractStatus>();

    static {
        for (EnumContractStatus type : EnumContractStatus.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumContractStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);

    }
}
