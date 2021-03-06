package com.gtl.mmf.entity;
// Generated Jul 15, 2015 11:10:48 AM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;

/**
 * Totaladvisorpayout generated by hbm2java
 */
public class Totaladvisorpayout  implements java.io.Serializable {


     private TotaladvisorpayoutId id;
     private MasterAdvisorTb masterAdvisorTb;
     private BigDecimal payout;

    public Totaladvisorpayout() {
    }

	
    public Totaladvisorpayout(TotaladvisorpayoutId id, MasterAdvisorTb masterAdvisorTb) {
        this.id = id;
        this.masterAdvisorTb = masterAdvisorTb;
    }
    public Totaladvisorpayout(TotaladvisorpayoutId id, MasterAdvisorTb masterAdvisorTb, BigDecimal payout) {
       this.id = id;
       this.masterAdvisorTb = masterAdvisorTb;
       this.payout = payout;
    }
   
    public TotaladvisorpayoutId getId() {
        return this.id;
    }
    
    public void setId(TotaladvisorpayoutId id) {
        this.id = id;
    }
    public MasterAdvisorTb getMasterAdvisorTb() {
        return this.masterAdvisorTb;
    }
    
    public void setMasterAdvisorTb(MasterAdvisorTb masterAdvisorTb) {
        this.masterAdvisorTb = masterAdvisorTb;
    }
    public BigDecimal getPayout() {
        return this.payout;
    }
    
    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }




}


