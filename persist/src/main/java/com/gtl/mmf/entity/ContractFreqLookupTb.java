package com.gtl.mmf.entity;
// Generated 24 Apr, 2014 5:56:04 PM by Hibernate Tools 3.6.0

/**
 * ContractFreqLookupTb generated by hbm2java
 */
public class ContractFreqLookupTb implements java.io.Serializable {

    private Integer id;
    private String fieldType;
    private String fieldLabel;
    private int fieldValue;

    public ContractFreqLookupTb() {
    }

    public ContractFreqLookupTb(String fieldType, String fieldLabel, int fieldValue) {
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

    public int getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue = fieldValue;
    }

}