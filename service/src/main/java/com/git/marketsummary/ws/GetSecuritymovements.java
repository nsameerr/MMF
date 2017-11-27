
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSecuritymovements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSecuritymovements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="venuecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scripCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="allOrPeriod" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSecuritymovements", propOrder = {
    "venuecode",
    "scripCode",
    "interval",
    "allOrPeriod",
    "fromDate",
    "toDate"
})
public class GetSecuritymovements {

    protected String venuecode;
    protected String scripCode;
    protected int interval;
    protected int allOrPeriod;
    protected String fromDate;
    protected String toDate;

    /**
     * Gets the value of the venuecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVenuecode() {
        return venuecode;
    }

    /**
     * Sets the value of the venuecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVenuecode(String value) {
        this.venuecode = value;
    }

    /**
     * Gets the value of the scripCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScripCode() {
        return scripCode;
    }

    /**
     * Sets the value of the scripCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScripCode(String value) {
        this.scripCode = value;
    }

    /**
     * Gets the value of the interval property.
     * 
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     */
    public void setInterval(int value) {
        this.interval = value;
    }

    /**
     * Gets the value of the allOrPeriod property.
     * 
     */
    public int getAllOrPeriod() {
        return allOrPeriod;
    }

    /**
     * Sets the value of the allOrPeriod property.
     * 
     */
    public void setAllOrPeriod(int value) {
        this.allOrPeriod = value;
    }

    /**
     * Gets the value of the fromDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the value of the fromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromDate(String value) {
        this.fromDate = value;
    }

    /**
     * Gets the value of the toDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the value of the toDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToDate(String value) {
        this.toDate = value;
    }

}
