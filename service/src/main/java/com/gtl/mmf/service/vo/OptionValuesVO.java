/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

public class OptionValuesVO {

    private int optionId;
    private String optionName;
    private int optionvale;

    public OptionValuesVO(int optionId, String optionName, int optionvale) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionvale = optionvale;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getOptionvale() {
        return optionvale;
    }

    public void setOptionvale(int optionvale) {
        this.optionvale = optionvale;
    }

}
