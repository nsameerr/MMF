/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * create by 07662
 */
package com.gtl.mmf.service.util;

public final class NumberRoundUtil {

    public static Double roundTwoDecimalValue(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
