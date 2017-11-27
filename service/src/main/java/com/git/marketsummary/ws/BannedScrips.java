
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bannedScrips complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bannedScrips">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scripCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="venueCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bannedScrips", propOrder = {
    "scripCode",
    "securityName",
    "venueCode"
})
public class BannedScrips {

    protected String scripCode;
    protected String securityName;
    protected String venueCode;

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
     * Gets the value of the securityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityName() {
        return securityName;
    }

    /**
     * Sets the value of the securityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityName(String value) {
        this.securityName = value;
    }

    /**
     * Gets the value of the venueCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVenueCode() {
        return venueCode;
    }

    /**
     * Sets the value of the venueCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVenueCode(String value) {
        this.venueCode = value;
    }

}
