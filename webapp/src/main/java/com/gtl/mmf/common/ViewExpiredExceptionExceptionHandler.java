/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 *
 * @author 07958
 */
public class ViewExpiredExceptionExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                FacesContext fc = FacesContext.getCurrentInstance();
                NavigationHandler nav = fc.getApplication().getNavigationHandler();
                fc.responseComplete();
                try {
                    // Push some useful stuff to the request scope for use in the page
//                    requestMap.put("currentViewId", vee.getViewId());
                    if (vee.getViewId().contains("/admin")) {
                        nav.handleNavigation(fc, null, "admin_view_expired");
                    } else {
                        nav.handleNavigation(fc, null, "user_view_expired");
                    }
                    fc.renderResponse();

                } catch (Exception e) {
                } finally {
                    i.remove();
                }
            } else if (t instanceof IllegalStateException) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.responseComplete();
                try {
                } finally {
                    i.remove();
                }
            }

        }
        getWrapped().handle();

    }
}
