package com.gtl.mmf.entity;
// Generated 23 Sep, 2014 11:11:13 AM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * MmfDailyClientBalanceTb generated by hbm2java
 */
public class MmfDailyClientBalanceTb  implements java.io.Serializable {


     private Integer id;
     private Date trndate;
     private String tradecode;
     private BigDecimal ledgerbalanec;
     private String euser;
     private Date lastupdatedon;

    public MmfDailyClientBalanceTb() {
    }

    public MmfDailyClientBalanceTb(Date trndate, String tradecode, BigDecimal ledgerbalanec, String euser, Date lastupdatedon) {
       this.trndate = trndate;
       this.tradecode = tradecode;
       this.ledgerbalanec = ledgerbalanec;
       this.euser = euser;
       this.lastupdatedon = lastupdatedon;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getTrndate() {
        return this.trndate;
    }
    
    public void setTrndate(Date trndate) {
        this.trndate = trndate;
    }
    public String getTradecode() {
        return this.tradecode;
    }
    
    public void setTradecode(String tradecode) {
        this.tradecode = tradecode;
    }
    public BigDecimal getLedgerbalanec() {
        return this.ledgerbalanec;
    }
    
    public void setLedgerbalanec(BigDecimal ledgerbalanec) {
        this.ledgerbalanec = ledgerbalanec;
    }
    public String getEuser() {
        return this.euser;
    }
    
    public void setEuser(String euser) {
        this.euser = euser;
    }
    public Date getLastupdatedon() {
        return this.lastupdatedon;
    }
    
    public void setLastupdatedon(Date lastupdatedon) {
        this.lastupdatedon = lastupdatedon;
    }




}

