package com.gtl.mmf.entity;
// Generated Jul 13, 2015 10:58:18 AM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tiers generated by hbm2java
 */
public class Tiers  implements java.io.Serializable {


     private Integer tierId;
     private String tierName;
     private String rangeStart;
     private String rangeEnd;
     private Set tieredpromocommissionmatrixes = new HashSet(0);

    public Tiers() {
    }

    public Tiers(String tierName, String rangeStart, String rangeEnd, Set tieredpromocommissionmatrixes) {
       this.tierName = tierName;
       this.rangeStart = rangeStart;
       this.rangeEnd = rangeEnd;
       this.tieredpromocommissionmatrixes = tieredpromocommissionmatrixes;
    }
   
    public Integer getTierId() {
        return this.tierId;
    }
    
    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }
    public String getTierName() {
        return this.tierName;
    }
    
    public void setTierName(String tierName) {
        this.tierName = tierName;
    }
    public String getRangeStart() {
        return this.rangeStart;
    }
    
    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
    }
    public String getRangeEnd() {
        return this.rangeEnd;
    }
    
    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
    }
    public Set getTieredpromocommissionmatrixes() {
        return this.tieredpromocommissionmatrixes;
    }
    
    public void setTieredpromocommissionmatrixes(Set tieredpromocommissionmatrixes) {
        this.tieredpromocommissionmatrixes = tieredpromocommissionmatrixes;
    }




}

