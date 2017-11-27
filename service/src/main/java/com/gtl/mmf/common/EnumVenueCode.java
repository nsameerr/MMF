/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author trainee12
 */
public enum EnumVenueCode {
    BSEMF(16),
    BSECD(15),
    NSEDEBT(14),
    MCXSXFO(13),
    MCXSXCM(12),
    BSEFO(11),
    NSEMF(9),
    MCXSX(8),
    NSECD(7), 
    NSEFO(3),
    BSE(2),
    NSE(1);
    private final int VALUE;

    private EnumVenueCode(int VALUE) {
        this.VALUE = VALUE;
    }
    
    public int getValue() {
        return VALUE;
    }
    
    private static final Map<Integer, EnumVenueCode> INT_TO_TYPE_MAP = new HashMap<Integer, EnumVenueCode>();
    
    static {
        for (EnumVenueCode type : EnumVenueCode.values()) {
            INT_TO_TYPE_MAP.put(type.VALUE, type);
        }
    }
    
    public static EnumVenueCode fromInt(Integer integer) {
        return INT_TO_TYPE_MAP.get(integer);
    }
    
}
