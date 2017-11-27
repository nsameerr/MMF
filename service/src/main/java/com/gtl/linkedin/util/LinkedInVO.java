/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.linkedin.util;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author 07960
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedInVO {

    private String firstName;
    private String headline;
    private String lastName;
    private String middleName;
    private String expires_in;
    private String access_token;
    private String mainAddress;
    private String id;
    private ContactInfo phoneNumbers;
    private String emailAddress;
    public String _total;
    List<LinkedInConnectionsVO> values;
    private String pictureUrl;
    private String distance;
    private ConnectionsVO connections;
    private LinkedinDobVO  dateOfBirth;
    private PositionsVO positions;
    private String publicProfileUrl;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public ContactInfo getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ContactInfo phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getTotal() {
        return _total;
    }

    public void setTotal(String _total) {
        this._total = _total;
    }

    public List<LinkedInConnectionsVO> getValues() {
        return values;
    }

    public void setValues(List<LinkedInConnectionsVO> values) {
        this.values = values;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ConnectionsVO getConnections() {
        return connections;
    }

    public void setConnections(ConnectionsVO connections) {
        this.connections = connections;
    }

    public LinkedinDobVO getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LinkedinDobVO dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PositionsVO getPositions() {
        return positions;
    }

    public void setPositions(PositionsVO positions) {
        this.positions = positions;
    }

    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    public void setPublicProfileUrl(String publicProfileUrl) {
        this.publicProfileUrl = publicProfileUrl;
    }
}