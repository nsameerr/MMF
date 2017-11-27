/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author 07958
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketDataVO implements Serializable {

    private String l;
    private Double l_fix;
    private String c;
    private String c_fix;

    public Double getL_fix() {
        return l_fix;
    }

    public void setL_fix(Double l_fix) {
        this.l_fix = l_fix;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getC_fix() {
        return c_fix;
    }

    public void setC_fix(String c_fix) {
        this.c_fix = c_fix;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }
}
