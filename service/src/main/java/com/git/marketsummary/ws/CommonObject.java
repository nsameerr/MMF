
package com.git.marketsummary.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for commonObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commonObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="advancesDeclines" type="{http://ws.marketsummary.git.com/}advancesDeclines" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FIITrading" type="{http://ws.marketsummary.git.com/}fiiTrading" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mutualFund" type="{http://ws.marketsummary.git.com/}mutualFund" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalTurnOver" type="{http://ws.marketsummary.git.com/}totalTurnOver" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonObject", propOrder = {
    "advancesDeclines",
    "fiiTrading",
    "mutualFund",
    "totalTurnOver"
})
public class CommonObject {

    @XmlElement(nillable = true)
    protected List<AdvancesDeclines> advancesDeclines;
    @XmlElement(name = "FIITrading", nillable = true)
    protected List<FiiTrading> fiiTrading;
    @XmlElement(nillable = true)
    protected List<MutualFund> mutualFund;
    @XmlElement(nillable = true)
    protected List<TotalTurnOver> totalTurnOver;

    /**
     * Gets the value of the advancesDeclines property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the advancesDeclines property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdvancesDeclines().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdvancesDeclines }
     * 
     * 
     */
    public List<AdvancesDeclines> getAdvancesDeclines() {
        if (advancesDeclines == null) {
            advancesDeclines = new ArrayList<AdvancesDeclines>();
        }
        return this.advancesDeclines;
    }

    /**
     * Gets the value of the fiiTrading property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fiiTrading property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFIITrading().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FiiTrading }
     * 
     * 
     */
    public List<FiiTrading> getFIITrading() {
        if (fiiTrading == null) {
            fiiTrading = new ArrayList<FiiTrading>();
        }
        return this.fiiTrading;
    }

    /**
     * Gets the value of the mutualFund property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mutualFund property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMutualFund().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MutualFund }
     * 
     * 
     */
    public List<MutualFund> getMutualFund() {
        if (mutualFund == null) {
            mutualFund = new ArrayList<MutualFund>();
        }
        return this.mutualFund;
    }

    /**
     * Gets the value of the totalTurnOver property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the totalTurnOver property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTotalTurnOver().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TotalTurnOver }
     * 
     * 
     */
    public List<TotalTurnOver> getTotalTurnOver() {
        if (totalTurnOver == null) {
            totalTurnOver = new ArrayList<TotalTurnOver>();
        }
        return this.totalTurnOver;
    }

}
