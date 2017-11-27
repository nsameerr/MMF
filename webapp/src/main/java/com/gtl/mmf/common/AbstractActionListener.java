/**
 *
 */
package com.gtl.mmf.common;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 08237
 *
 */
public abstract class AbstractActionListener implements ActionListener {

    private ExternalContext eContext = null;
    private HttpServletRequest httpRequest = null;
    private Map<String, Object> sessionMap = null;
    private Map<String, Object> requestMap = null;
    private Map<String, Object> viewMap = null;

    /* (non-Javadoc)
     * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
     */
    public void processAction(ActionEvent event) throws AbortProcessingException {
        eContext = FacesContext.getCurrentInstance().getExternalContext();
        httpRequest = (HttpServletRequest) eContext.getRequest();
        sessionMap = eContext.getSessionMap();
        requestMap = eContext.getRequestMap();
        viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        doProcessAction(event, eContext, httpRequest, sessionMap, requestMap, viewMap);
    }

    /**
     *
     * @param event
     * @param request
     * @param _sessionMap
     * @param _requestMap
     * @param _viewMap
     */
    public abstract void doProcessAction(ActionEvent event, ExternalContext _eContext,
            HttpServletRequest request, Map<String, Object> _sessionMap,
            Map<String, Object> _requestMap, Map<String, Object> _viewMap);
}
