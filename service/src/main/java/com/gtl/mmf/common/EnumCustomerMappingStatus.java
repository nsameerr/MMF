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
public enum EnumCustomerMappingStatus {

    NO_RELATION(0),
    INVITE_RECIEVED(1),
    INVITE_SENT(2),
    INVITE_ACCEPTED(3),
    CONTRACT_RECIEVED(4),
    CONTRACT_REVIEW(10),
    CONTRACT_SIGNED(5),
    QUESTIONNAIRE_RECIEVED(6),
    RESPONSE_SENT(7),
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
    WITHDRAW(310),
    ADVISOR_INVITE_DECLINED(200),
    ADVISOR_WITHDRAW(210),
    ORDER_PLACED_IN_OMS(401),
     //For the new feature contract termination performed by admin
    CONTRACT_TERMINATED(403);
   
    
    
    private final int value;

    private EnumCustomerMappingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    private static final Map<Integer, EnumCustomerMappingStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumCustomerMappingStatus>();

    static {
        for (EnumCustomerMappingStatus type : EnumCustomerMappingStatus.values()) {
            INT_TO_TYPE_MAP.put(type.value, type);
        }
    }

    public static EnumCustomerMappingStatus fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
}
