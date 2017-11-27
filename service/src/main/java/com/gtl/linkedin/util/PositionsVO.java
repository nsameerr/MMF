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
public class PositionsVO {
    public String _total;
    List<LinkedInJobVO> values;
   

    public String getTotal() {
        return _total;
    }

    public void setTotal(String _total) {
        this._total = _total;
    }

    public List<LinkedInJobVO> getValues() {
        return values;
    }

    public void setValues(List<LinkedInJobVO> values) {
        this.values = values;
    }

    
    
}
