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
public enum EnumAdvisorMappingStatus {

    NO_RELATION(0),
    INVITE_SENT(1),
    INVITE_RECIEVED(2),
    INVITE_ACCEPTED(3),
    CONTRACT_SENT(4),
    CONTRACT_REVIEW(10),
    CONTRACT_SIGNED(5),
    QUESTIONNAIRE_SENT(6),
    RESPONSE_RECIEVED(7),
    RESPONSE_REVIEW(11),
    IPS_SHARED(8),
    IPS_CREATED(125),
    IPS_REVIEWED(150),
    IPS_ACCEPTED(175),
    REBALANCE_INITIATED(400),
    ACTIVE(100),
    SUSPENDED(9),
    EXPIRED(15),
    INVITE_DECLINED(300),
    WITHDRAW(210),
    INVESTOR_INVITE_DECLINED(200),
    INVESTOR_WITHDRAW(310),
    ORDER_PLACED_IN_OMS(401),
    CONTRACT_TERMINATED(403);
    
    private final int value;

    private EnumAdvisorMappingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumAdvisorMappingStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumAdvisorMappingStatus>();

    static {
        for (EnumAdvisorMappingStatus type : EnumAdvisorMappingStatus.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumAdvisorMappingStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
}
