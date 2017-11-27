package com.gtl.mmf.entity;
// Generated Apr 3, 2014 10:18:46 AM by Hibernate Tools 3.2.1.GA

/**
 * ClientButtonsTb generated by hbm2java
 */
public class ClientButtonsTb implements java.io.Serializable {

    private Integer id;
    private String userType;
    private int statusCode;
    private String buttonText;
    private String styleClass;
    private Boolean immediate;
    private Boolean confirm;

    public ClientButtonsTb() {
    }

    public ClientButtonsTb(String userType, int statusCode, String buttonText) {
        this.userType = userType;
        this.statusCode = statusCode;
        this.buttonText = buttonText;
    }

    public ClientButtonsTb(String userType, int statusCode, String buttonText, String styleClass, Boolean immediate, Boolean confirm) {
        this.userType = userType;
        this.statusCode = statusCode;
        this.buttonText = buttonText;
        this.styleClass = styleClass;
        this.immediate = immediate;
        this.confirm = confirm;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getButtonText() {
        return this.buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getStyleClass() {
        return this.styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public Boolean getImmediate() {
        return this.immediate;
    }

    public void setImmediate(Boolean immediate) {
        this.immediate = immediate;
    }

    public Boolean getConfirm() {
        return this.confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

}