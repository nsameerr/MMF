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
public class LinkedInJobVO {
    CompanyVO company;   
    private String id;

    public CompanyVO getCompany() {
        return company;
    }

    public void setCompany(CompanyVO company) {
        this.company = company;
    }
    private String isCurrent;
    private String title;

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
