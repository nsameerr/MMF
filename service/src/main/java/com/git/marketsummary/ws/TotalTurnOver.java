
package com.git.marketsummary.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for totalTurnOver complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="totalTurnOver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noOfSecuritiesTraded" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="noOfTrades" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tradeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tradedQuantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tradedValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "totalTurnOver", propOrder = {
    "noOfSecuritiesTraded",
    "noOfTrades",
    "tradeDate",
    "tradedQuantity",
    "tradedValue"
})
public class TotalTurnOver {

    protected BigDecimal noOfSecuritiesTraded;
    protected BigDecimal noOfTrades;
    protected String tradeDate;
    protected BigDecimal tradedQuantity;
    protected BigDecimal tradedValue;

    /**
     * Gets the value of the noOfSecuritiesTraded property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNoOfSecuritiesTraded() {
        return noOfSecuritiesTraded;
    }

    /**
     * Sets the value of the noOfSecuritiesTraded property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNoOfSecuritiesTraded(BigDecimal value) {
        this.noOfSecuritiesTraded = value;
    }

    /**
     * Gets the value of the noOfTrades property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNoOfTrades() {
        return noOfTrades;
    }

    /**
     * Sets the value of the noOfTrades property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNoOfTrades(BigDecimal value) {
        this.noOfTrades = value;
    }

    /**
     * Gets the value of the tradeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeDate() {
        return tradeDate;
    }

    /**
     * Sets the value of the tradeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeDate(String value) {
        this.tradeDate = value;
    }

    /**
     * Gets the value of the tradedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTradedQuantity() {
        return tradedQuantity;
    }

    /**
     * Sets the value of the tradedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTradedQuantity(BigDecimal value) {
        this.tradedQuantity = value;
    }

    /**
     * Gets the value of the tradedValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTradedValue() {
        return tradedValue;
    }

    /**
     * Sets the value of the tradedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTradedValue(BigDecimal value) {
        this.tradedValue = value;
    }

}
