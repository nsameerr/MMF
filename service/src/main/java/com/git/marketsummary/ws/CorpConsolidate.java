
package com.git.marketsummary.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for corpConsolidate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="corpConsolidate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="boardMeetings" type="{http://ws.marketsummary.git.com/}boardMeetings" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="corpAction" type="{http://ws.marketsummary.git.com/}corpAction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="corperateAnnouncements" type="{http://ws.marketsummary.git.com/}corperateAnnouncements" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "corpConsolidate", propOrder = {
    "boardMeetings",
    "corpAction",
    "corperateAnnouncements"
})
public class CorpConsolidate {

    @XmlElement(nillable = true)
    protected List<BoardMeetings> boardMeetings;
    @XmlElement(nillable = true)
    protected List<CorpAction> corpAction;
    @XmlElement(nillable = true)
    protected List<CorperateAnnouncements> corperateAnnouncements;

    /**
     * Gets the value of the boardMeetings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boardMeetings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoardMeetings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BoardMeetings }
     * 
     * 
     */
    public List<BoardMeetings> getBoardMeetings() {
        if (boardMeetings == null) {
            boardMeetings = new ArrayList<BoardMeetings>();
        }
        return this.boardMeetings;
    }

    /**
     * Gets the value of the corpAction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the corpAction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorpAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CorpAction }
     * 
     * 
     */
    public List<CorpAction> getCorpAction() {
        if (corpAction == null) {
            corpAction = new ArrayList<CorpAction>();
        }
        return this.corpAction;
    }

    /**
     * Gets the value of the corperateAnnouncements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the corperateAnnouncements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorperateAnnouncements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CorperateAnnouncements }
     * 
     * 
     */
    public List<CorperateAnnouncements> getCorperateAnnouncements() {
        if (corperateAnnouncements == null) {
            corperateAnnouncements = new ArrayList<CorperateAnnouncements>();
        }
        return this.corperateAnnouncements;
    }

}
