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
public enum EnumStatus {

    ACTIVE(100),
    INACTIVE(200),
    NEW_APPLICANT(13),
    MAIL_NOT_VERIFIED(300),
    MAIL_VERIFIED(301),
    SUCCESS(1),
    FAILED(0),
    RESET_PWD(101),
	FP_NOT_SUBMITTED(302);
    final int VALUE;

    private EnumStatus(int value) {
        this.VALUE = value;
    }

    public int getValue() {
        return VALUE;
    }
    private static final Map<Integer, EnumStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumStatus>();

    static {
        for (EnumStatus type : EnumStatus.values()) {
            INT_TO_TYPE_MAP.put(type.VALUE, type);
        }
    }

    public static EnumStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
}
