package com.gtl.mmf.entity;
// Generated Mar 24, 2014 2:23:31 PM by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;

/**
 * ContractPercLookupTb generated by hbm2java
 */
public class ContractPercLookupTb implements java.io.Serializable {

    private Integer id;
    private String fieldType;
    private String fieldLabel;
    private BigDecimal fieldValue;

    public ContractPercLookupTb() {
    }

    public ContractPercLookupTb(String fieldType, String fieldLabel, BigDecimal fieldValue) {
        this.fieldType = fieldType;
        this.fieldLabel = fieldLabel;
        this.fieldValue = fieldValue;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLabel() {
        return this.fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public BigDecimal getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(BigDecimal fieldValue) {
        this.fieldValue = fieldValue;
    }

}
