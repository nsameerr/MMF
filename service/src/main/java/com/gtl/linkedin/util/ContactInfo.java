/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.linkedin.util;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author trainee3
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfo {
    
    public String _total;
    List<LinkedinPhoneVO> values;

    public String getTotal() {
        return _total;
    }

    public void setTotal(String _total) {
        this._total = _total;
    }
    public List<LinkedinPhoneVO> getValues() {
        return values;
    }

    public void setValues(List<LinkedinPhoneVO> values) {
        this.values = values;
    }
}
