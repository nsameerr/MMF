
package com.git.marketsummary.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketCapitalization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketCapitalization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mktCapitalisationcrore" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="month" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noofCompAvailforTrading" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="noofCompPermittedtoTrade" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="noofCompaniesListed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marketCapitalization", propOrder = {
    "mktCapitalisationcrore",
    "month",
    "noofCompAvailforTrading",
    "noofCompPermittedtoTrade",
    "noofCompaniesListed"
})
public class MarketCapitalization {

    protected BigDecimal mktCapitalisationcrore;
    protected String month;
    protected int noofCompAvailforTrading;
    protected int noofCompPermittedtoTrade;
    protected int noofCompaniesListed;

    /**
     * Gets the value of the mktCapitalisationcrore property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMktCapitalisationcrore() {
        return mktCapitalisationcrore;
    }

    /**
     * Sets the value of the mktCapitalisationcrore property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMktCapitalisationcrore(BigDecimal value) {
        this.mktCapitalisationcrore = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonth(String value) {
        this.month = value;
    }

    /**
     * Gets the value of the noofCompAvailforTrading property.
     * 
     */
    public int getNoofCompAvailforTrading() {
        return noofCompAvailforTrading;
    }

    /**
     * Sets the value of the noofCompAvailforTrading property.
     * 
     */
    public void setNoofCompAvailforTrading(int value) {
        this.noofCompAvailforTrading = value;
    }

    /**
     * Gets the value of the noofCompPermittedtoTrade property.
     * 
     */
    public int getNoofCompPermittedtoTrade() {
        return noofCompPermittedtoTrade;
    }

    /**
     * Sets the value of the noofCompPermittedtoTrade property.
     * 
     */
    public void setNoofCompPermittedtoTrade(int value) {
        this.noofCompPermittedtoTrade = value;
    }

    /**
     * Gets the value of the noofCompaniesListed property.
     * 
     */
    public int getNoofCompaniesListed() {
        return noofCompaniesListed;
    }

    /**
     * Sets the value of the noofCompaniesListed property.
     * 
     */
    public void setNoofCompaniesListed(int value) {
        this.noofCompaniesListed = value;
    }

}
