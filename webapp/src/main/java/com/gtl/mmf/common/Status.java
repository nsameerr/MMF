/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

/**
 *
 * @author 07958
 */
public enum Status {

    ACTIVE(100),
    DEACTIVE(200);
    private final int value;

    private Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
