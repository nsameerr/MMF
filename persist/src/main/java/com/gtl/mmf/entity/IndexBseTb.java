package com.gtl.mmf.entity;
// Generated Oct 22, 2014 3:04:46 PM by Hibernate Tools 3.6.0

/**
 * IndexBseTb generated by hbm2java
 */
public class IndexBseTb implements java.io.Serializable {

    private Integer id;
    private String scripCode;
    private String scripName;
    private String isin;

    public IndexBseTb() {
    }

    public IndexBseTb(String scripCode, String scripName, String isin) {
        this.scripCode = scripCode;
        this.scripName = scripName;
        this.isin = isin;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScripCode() {
        return this.scripCode;
    }

    public void setScripCode(String scripCode) {
        this.scripCode = scripCode;
    }

    public String getScripName() {
        return this.scripName;
    }

    public void setScripName(String scripName) {
        this.scripName = scripName;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

}
