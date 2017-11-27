/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import com.gtl.mmf.controller.UserSessionBean;
import com.gtl.mmf.service.util.IConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 07958
 */
public class SessionFilter implements Filter, IConstants {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private List<String> urlList;
    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";
    private static final String USER_LOGIN_PATH = "/faces/pages/login.xhtml";
    private static final String ADMIN_LOGIN_PATH = "/admin";
    private static final String AJAX_HEADER_KEY = "partial/ajax";
    private static final String AJAX_HEADER_VALUE = "Faces-Request";
//    private static final String HOME_PAGE = "/alpha/faces/index.xhtml";
    private static final String USERSESSION = "userSession";
    private static final String CONTEXT_PARAM_AVOID_URL = "avoid-urls";
    private static final String CHECK_ADMIN = "/admin";
    private static final String MMF_HOME_PATH = "/faces/index.xhtml";

    public SessionFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // Skip JSF resources (CSS/JS/Images/etc)
        if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            // HTTP 1.1.
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            // HTTP 1.0.
            response.setHeader("Pragma", "no-cache");
            // Proxies.
            response.setDateHeader("Expires", 0);
        }
        String url = request.getRequestURI();
        HttpSession session = request.getSession(true);
       
        boolean ajaxRequest = AJAX_HEADER_KEY.equals(request.getHeader(AJAX_HEADER_VALUE));
//        if (url.equalsIgnoreCase("/alpha/")) {
//            response.sendRedirect(HOME_PAGE);
//        } else 
        	if (url.contains(".jsp")) {
        	Map<String, Object> storedValues =null;
    		String userFirstName = "";
    		
			if(session != null){
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userFirstName = (String)storedValues.get("userFirstName");
				}else{
					response.sendRedirect(request.getContextPath() + MMF_HOME_PATH);
				}
			}
        } else if (!urlList.contains(url)) {
            String loginURL = request.getContextPath() + (url.contains(CHECK_ADMIN) ? ADMIN_LOGIN_PATH : MMF_HOME_PATH);
            UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute(USERSESSION);
            Boolean linkedin_expired = (Boolean) (session.getAttribute(USER_EXPIRED) != null
                    ? session.getAttribute(USER_EXPIRED) : false);
            if (linkedin_expired) {
                session.removeAttribute(USER_EXPIRED);
            }
            if (userSessionBean == null && !linkedin_expired) {
                if (ajaxRequest) {
                    response.setContentType("text/xml");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().printf(AJAX_REDIRECT_XML, loginURL);
                } else {
                    response.sendRedirect(loginURL);
                    return;
                }
            } else if (session.getAttribute("OmsUserInactive") != null && (Boolean) session.getAttribute("OmsUserInactive")) {
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">alert('Your OMS session has expired or have been logged in from another machine. Please re-login.');");
                out.println("window.location.replace('"+ request.getContextPath() +"/faces/pages/investordashboard.xhtml?faces-redirect=true');</script>");
                session.removeAttribute("OmsUserInactive");
                return;
            }
        } else {
            if (!UserSessionBean.getLogins().isEmpty()) {
                if (session != null && UserSessionBean.getLogins().containsKey(session)) {
                    UserSessionBean usereSession = UserSessionBean.getLogins().get(session);
                    String redirectionPath = EMPTY_STRING;
                    if (usereSession.getUserType().equalsIgnoreCase(INVESTOR)) {
                        redirectionPath = EnumCustLoginStatus.fromInt(usereSession.getInitLogin()).toString();
                    } else if (usereSession.getUserType().equalsIgnoreCase(ADVISOR)) {
                        redirectionPath = EnumAdvLoginStatus.fromInt(usereSession.getInitLogin()).toString();
                    } else {
                        redirectionPath = "admin/new_user_list";
                    }
                    redirectionPath = request.getContextPath() + "/pages/" + redirectionPath + ".xhtml?faces-redirect=true";
                    response.sendRedirect(redirectionPath.replaceAll("/pages/pages/", "/pages/"));
                }
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        String contextPath  = filterConfig.getServletContext().getContextPath();
        String urls = filterConfig.getInitParameter(CONTEXT_PARAM_AVOID_URL);
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList<String>();
        while (token.hasMoreTokens()) {
            urlList.add(contextPath + token.nextToken());
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return "SessionFilter()";
        }
        StringBuffer sb = new StringBuffer("SessionFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return sb.toString();
    }
}
