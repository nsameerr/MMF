
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for boardMeetings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="boardMeetings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BMDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BMPurpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="security" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "boardMeetings", propOrder = {
    "bmDate",
    "bmPurpose",
    "security"
})
public class BoardMeetings {

    @XmlElement(name = "BMDate")
    protected String bmDate;
    @XmlElement(name = "BMPurpose")
    protected String bmPurpose;
    protected String security;

    /**
     * Gets the value of the bmDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBMDate() {
        return bmDate;
    }

    /**
     * Sets the value of the bmDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBMDate(String value) {
        this.bmDate = value;
    }

    /**
     * Gets the value of the bmPurpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBMPurpose() {
        return bmPurpose;
    }

    /**
     * Sets the value of the bmPurpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBMPurpose(String value) {
        this.bmPurpose = value;
    }

    /**
     * Gets the value of the security property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurity(String value) {
        this.security = value;
    }

}
