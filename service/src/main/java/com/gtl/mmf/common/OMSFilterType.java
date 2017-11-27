/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

/**
 *
 * @author 07662
 */
public enum OMSFilterType {

    ALL("A"),
    INTRADAY("I"),
    EXPIRY("E");

    private final String value;

    public String getValue() {
        return value;
    }

    private OMSFilterType(String value) {
        this.value = value;
    }

}
