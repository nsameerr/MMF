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
public class CompanyVO {
    private String id;
    private String industry;
    private String name;
    private String size;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }   
        
}
