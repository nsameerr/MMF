
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for corperateAnnouncements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="corperateAnnouncements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="annDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="announcements" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "corperateAnnouncements", propOrder = {
    "annDate",
    "announcements",
    "security"
})
public class CorperateAnnouncements {

    protected String annDate;
    protected String announcements;
    protected String security;

    /**
     * Gets the value of the annDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnDate() {
        return annDate;
    }

    /**
     * Sets the value of the annDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnDate(String value) {
        this.annDate = value;
    }

    /**
     * Gets the value of the announcements property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnouncements() {
        return announcements;
    }

    /**
     * Sets the value of the announcements property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnouncements(String value) {
        this.announcements = value;
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
