package com.gtl.mmf.entity;
// Generated Jul 15, 2015 11:10:48 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * TotaladvisorpayoutId generated by hbm2java
 */
public class TotaladvisorpayoutId  implements java.io.Serializable {


     private int advisorId;
     private Date payDate;

    public TotaladvisorpayoutId() {
    }

    public TotaladvisorpayoutId(int advisorId, Date payDate) {
       this.advisorId = advisorId;
       this.payDate = payDate;
    }
   
    public int getAdvisorId() {
        return this.advisorId;
    }
    
    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }
    public Date getPayDate() {
        return this.payDate;
    }
    
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TotaladvisorpayoutId) ) return false;
		 TotaladvisorpayoutId castOther = ( TotaladvisorpayoutId ) other; 
         
		 return (this.getAdvisorId()==castOther.getAdvisorId())
 && ( (this.getPayDate()==castOther.getPayDate()) || ( this.getPayDate()!=null && castOther.getPayDate()!=null && this.getPayDate().equals(castOther.getPayDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getAdvisorId();
         result = 37 * result + ( getPayDate() == null ? 0 : this.getPayDate().hashCode() );
         return result;
   }   


}


