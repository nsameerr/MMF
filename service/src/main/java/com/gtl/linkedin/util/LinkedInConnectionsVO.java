/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.linkedin.util;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author trainee3
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedInConnectionsVO {
    private String distance;
    private String id;
    private String numConnections;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumConnections() {
        return numConnections;
    }

    public void setNumConnections(String numConnections) {
        this.numConnections = numConnections;
    }
    
}
