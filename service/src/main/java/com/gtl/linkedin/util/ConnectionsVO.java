/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.linkedin.util;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author 07958
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionsVO {

    public String _total;

    public String getTotal() {
        return _total;
    }

    public void setTotal(String _total) {
        this._total = _total;
    }
}
