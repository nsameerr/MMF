package com.gtl.mmf.entity;
// Generated 8 Jul, 2014 12:20:29 PM by Hibernate Tools 3.6.0

/**
 * CitiesTb generated by hbm2java
 */
public class CitiesTb implements java.io.Serializable {

    private Integer cityId;
    private String cityName;
    private String stateCode;

    public CitiesTb() {
    }

    public CitiesTb(String cityName, String stateCode) {
        this.cityName = cityName;
        this.stateCode = stateCode;
    }

    public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateCode() {
        return this.stateCode;
    }
    
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

}