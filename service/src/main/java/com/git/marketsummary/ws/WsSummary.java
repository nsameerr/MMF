
package com.git.marketsummary.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WsSummary", targetNamespace = "http://ws.marketsummary.git.com/", wsdlLocation = "http://192.168.9.92/WsSummary/wsdl?wsdl")
public class WsSummary
    extends Service
{

    private final static URL WSSUMMARY_WSDL_LOCATION;
    private final static WebServiceException WSSUMMARY_EXCEPTION;
    private final static QName WSSUMMARY_QNAME = new QName("http://ws.marketsummary.git.com/", "WsSummary");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.9.92/WsSummary/wsdl?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSSUMMARY_WSDL_LOCATION = url;
        WSSUMMARY_EXCEPTION = e;
    }

    public WsSummary() {
        super(__getWsdlLocation(), WSSUMMARY_QNAME);
    }

    public WsSummary(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSSUMMARY_QNAME, features);
    }

    public WsSummary(URL wsdlLocation) {
        super(wsdlLocation, WSSUMMARY_QNAME);
    }

    public WsSummary(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSSUMMARY_QNAME, features);
    }

    public WsSummary(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WsSummary(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Wsdl
     */
    @WebEndpoint(name = "wsdlPort")
    public Wsdl getWsdlPort() {
        return super.getPort(new QName("http://ws.marketsummary.git.com/", "wsdlPort"), Wsdl.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Wsdl
     */
    @WebEndpoint(name = "wsdlPort")
    public Wsdl getWsdlPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.marketsummary.git.com/", "wsdlPort"), Wsdl.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSSUMMARY_EXCEPTION!= null) {
            throw WSSUMMARY_EXCEPTION;
        }
        return WSSUMMARY_WSDL_LOCATION;
    }

}
