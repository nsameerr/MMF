package com.gtl.mmf.entity;
// Generated Feb 20, 2014 10:41:01 AM by Hibernate Tools 3.2.1.GA

/**
 * CountryTb generated by hbm2java
 */
public class CountryTb implements java.io.Serializable {

    private Integer id;
    private String countryCode;
    private String countryName;

    public CountryTb() {
    }

    public CountryTb(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}