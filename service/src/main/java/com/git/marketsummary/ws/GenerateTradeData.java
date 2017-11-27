
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generateTradeData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generateTradeData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enteringusercode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderingusercode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="venuecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generateTradeData", propOrder = {
    "enteringusercode",
    "orderingusercode",
    "venuecode"
})
public class GenerateTradeData {

    protected String enteringusercode;
    protected String orderingusercode;
    protected String venuecode;

    /**
     * Gets the value of the enteringusercode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteringusercode() {
        return enteringusercode;
    }

    /**
     * Sets the value of the enteringusercode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteringusercode(String value) {
        this.enteringusercode = value;
    }

    /**
     * Gets the value of the orderingusercode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderingusercode() {
        return orderingusercode;
    }

    /**
     * Sets the value of the orderingusercode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderingusercode(String value) {
        this.orderingusercode = value;
    }

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

}
