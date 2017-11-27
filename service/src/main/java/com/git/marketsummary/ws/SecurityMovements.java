
package com.git.marketsummary.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for securityMovements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="securityMovements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="avgRate" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="HPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="LPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="LTQ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="OPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="TTQ" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="TTV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "securityMovements", propOrder = {
    "avgRate",
    "cPrice",
    "hPrice",
    "lPrice",
    "ltq",
    "oPrice",
    "ttq",
    "ttv"
})
public class SecurityMovements {

    protected double avgRate;
    @XmlElement(name = "CPrice")
    protected double cPrice;
    @XmlElement(name = "HPrice")
    protected double hPrice;
    @XmlElement(name = "LPrice")
    protected double lPrice;
    @XmlElement(name = "LTQ")
    protected double ltq;
    @XmlElement(name = "OPrice")
    protected double oPrice;
    @XmlElement(name = "TTQ")
    protected long ttq;
    @XmlElement(name = "TTV")
    protected double ttv;

    /**
     * Gets the value of the avgRate property.
     * 
     */
    public double getAvgRate() {
        return avgRate;
    }

    /**
     * Sets the value of the avgRate property.
     * 
     */
    public void setAvgRate(double value) {
        this.avgRate = value;
    }

    /**
     * Gets the value of the cPrice property.
     * 
     */
    public double getCPrice() {
        return cPrice;
    }

    /**
     * Sets the value of the cPrice property.
     * 
     */
    public void setCPrice(double value) {
        this.cPrice = value;
    }

    /**
     * Gets the value of the hPrice property.
     * 
     */
    public double getHPrice() {
        return hPrice;
    }

    /**
     * Sets the value of the hPrice property.
     * 
     */
    public void setHPrice(double value) {
        this.hPrice = value;
    }

    /**
     * Gets the value of the lPrice property.
     * 
     */
    public double getLPrice() {
        return lPrice;
    }

    /**
     * Sets the value of the lPrice property.
     * 
     */
    public void setLPrice(double value) {
        this.lPrice = value;
    }

    /**
     * Gets the value of the ltq property.
     * 
     */
    public double getLTQ() {
        return ltq;
    }

    /**
     * Sets the value of the ltq property.
     * 
     */
    public void setLTQ(double value) {
        this.ltq = value;
    }

    /**
     * Gets the value of the oPrice property.
     * 
     */
    public double getOPrice() {
        return oPrice;
    }

    /**
     * Sets the value of the oPrice property.
     * 
     */
    public void setOPrice(double value) {
        this.oPrice = value;
    }

    /**
     * Gets the value of the ttq property.
     * 
     */
    public long getTTQ() {
        return ttq;
    }

    /**
     * Sets the value of the ttq property.
     * 
     */
    public void setTTQ(long value) {
        this.ttq = value;
    }

    /**
     * Gets the value of the ttv property.
     * 
     */
    public double getTTV() {
        return ttv;
    }

    /**
     * Sets the value of the ttv property.
     * 
     */
    public void setTTV(double value) {
        this.ttv = value;
    }

}
