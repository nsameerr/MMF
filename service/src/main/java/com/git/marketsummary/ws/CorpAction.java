
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for corpAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="corpAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BCEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BCStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exBCDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NDEendDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NDStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RECDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="series" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "corpAction", propOrder = {
    "bcEndDate",
    "bcStartDate",
    "exBCDate",
    "ndEendDate",
    "ndStartDate",
    "recDate",
    "remarks",
    "securityCode",
    "series"
})
public class CorpAction {

    @XmlElement(name = "BCEndDate")
    protected String bcEndDate;
    @XmlElement(name = "BCStartDate")
    protected String bcStartDate;
    protected String exBCDate;
    @XmlElement(name = "NDEendDate")
    protected String ndEendDate;
    @XmlElement(name = "NDStartDate")
    protected String ndStartDate;
    @XmlElement(name = "RECDate")
    protected String recDate;
    protected String remarks;
    protected String securityCode;
    protected String series;

    /**
     * Gets the value of the bcEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBCEndDate() {
        return bcEndDate;
    }

    /**
     * Sets the value of the bcEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBCEndDate(String value) {
        this.bcEndDate = value;
    }

    /**
     * Gets the value of the bcStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBCStartDate() {
        return bcStartDate;
    }

    /**
     * Sets the value of the bcStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBCStartDate(String value) {
        this.bcStartDate = value;
    }

    /**
     * Gets the value of the exBCDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExBCDate() {
        return exBCDate;
    }

    /**
     * Sets the value of the exBCDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExBCDate(String value) {
        this.exBCDate = value;
    }

    /**
     * Gets the value of the ndEendDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNDEendDate() {
        return ndEendDate;
    }

    /**
     * Sets the value of the ndEendDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNDEendDate(String value) {
        this.ndEendDate = value;
    }

    /**
     * Gets the value of the ndStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNDStartDate() {
        return ndStartDate;
    }

    /**
     * Sets the value of the ndStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNDStartDate(String value) {
        this.ndStartDate = value;
    }

    /**
     * Gets the value of the recDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECDate() {
        return recDate;
    }

    /**
     * Sets the value of the recDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECDate(String value) {
        this.recDate = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the securityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the value of the securityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityCode(String value) {
        this.securityCode = value;
    }

    /**
     * Gets the value of the series property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeries() {
        return series;
    }

    /**
     * Sets the value of the series property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeries(String value) {
        this.series = value;
    }

}
