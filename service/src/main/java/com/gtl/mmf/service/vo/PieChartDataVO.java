/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author 07958
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PieChartDataVO {

    private String label;
    private Double data;
    private String color;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
