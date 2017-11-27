/**
 *
 */
package com.gtl.mmf.common;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 08237
 *
 */
public abstract class AbstractValueChangeListener implements ValueChangeListener {

    private ExternalContext eContext = null;
    private HttpServletRequest httpRequest = null;
    private Map<String, Object> sessionMap = null;
    private Map<String, Object> requestMap = null;
    private Map<String, Object> viewMap = null;

    /* (non-Javadoc)
     * @see javax.faces.event.ValueChangeListener#processValueChange(javax.faces.event.ValueChangeEvent)
     */
    public void processValueChange(ValueChangeEvent event)
            throws AbortProcessingException {
        eContext = FacesContext.getCurrentInstance().getExternalContext();
        httpRequest = (HttpServletRequest) eContext.getRequest();
        sessionMap = eContext.getSessionMap();
        requestMap = eContext.getRequestMap();
        viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        doProcessValueChange(event, eContext, httpRequest, sessionMap, requestMap, viewMap);
    }

    public abstract void doProcessValueChange(ValueChangeEvent event, ExternalContext _eContext,
            HttpServletRequest request, Map<String, Object> _sessionMap,
            Map<String, Object> _requestMap, Map<String, Object> _viewMap);
}
