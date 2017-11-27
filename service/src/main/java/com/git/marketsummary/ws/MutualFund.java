
package com.git.marketsummary.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mutualFund complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mutualFund">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="debit_Net" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="debit_Purchase" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="debit_Sales" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="equity_Net" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="equity_Purchase" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="equity_Sales" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tradeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mutualFund", propOrder = {
    "debitNet",
    "debitPurchase",
    "debitSales",
    "equityNet",
    "equityPurchase",
    "equitySales",
    "tradeDate"
})
public class MutualFund {

    @XmlElement(name = "debit_Net")
    protected BigDecimal debitNet;
    @XmlElement(name = "debit_Purchase")
    protected BigDecimal debitPurchase;
    @XmlElement(name = "debit_Sales")
    protected BigDecimal debitSales;
    @XmlElement(name = "equity_Net")
    protected BigDecimal equityNet;
    @XmlElement(name = "equity_Purchase")
    protected BigDecimal equityPurchase;
    @XmlElement(name = "equity_Sales")
    protected BigDecimal equitySales;
    protected String tradeDate;

    /**
     * Gets the value of the debitNet property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebitNet() {
        return debitNet;
    }

    /**
     * Sets the value of the debitNet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebitNet(BigDecimal value) {
        this.debitNet = value;
    }

    /**
     * Gets the value of the debitPurchase property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebitPurchase() {
        return debitPurchase;
    }

    /**
     * Sets the value of the debitPurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebitPurchase(BigDecimal value) {
        this.debitPurchase = value;
    }

    /**
     * Gets the value of the debitSales property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebitSales() {
        return debitSales;
    }

    /**
     * Sets the value of the debitSales property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebitSales(BigDecimal value) {
        this.debitSales = value;
    }

    /**
     * Gets the value of the equityNet property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEquityNet() {
        return equityNet;
    }

    /**
     * Sets the value of the equityNet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEquityNet(BigDecimal value) {
        this.equityNet = value;
    }

    /**
     * Gets the value of the equityPurchase property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEquityPurchase() {
        return equityPurchase;
    }

    /**
     * Sets the value of the equityPurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEquityPurchase(BigDecimal value) {
        this.equityPurchase = value;
    }

    /**
     * Gets the value of the equitySales property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEquitySales() {
        return equitySales;
    }

    /**
     * Sets the value of the equitySales property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEquitySales(BigDecimal value) {
        this.equitySales = value;
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

}
