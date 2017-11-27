package com.gtl.mmf.servlet;

import static com.gtl.mmf.service.util.IConstants.ADMIN_USER;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;

import java.io.IOException;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.bao.IUserLogoutBAO;
import com.gtl.mmf.controller.UserSessionBean;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.OMSOrderVO;



@WebServlet("/faces/logoutUser")
public class LogoutServlet extends HttpServlet {

	private static String REDIRECT_TO_HOME = "homeRedirect";
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.LogoutServlet");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		logout(req, resp);
		//session.invalidate();
		String redirect = req.getContextPath()+ PropertiesLoader.getPropertiesValue(REDIRECT_TO_HOME);
		resp.sendRedirect(redirect);
	}
	
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = "/index.xhtml";
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        // Check current FacesContext.
        if (facesContext == null) {

            // Create new Lifecycle.
            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY); 
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            // Create new FacesContext.
            FacesContextFactory contextFactory  = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            facesContext = contextFactory.getFacesContext(req.getSession().getServletContext(), req, resp, lifecycle);

            HttpSession session = req.getSession();
            UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
            //UserSessionBean userSessionBean = (UserSessionBean)facesContext.getExternalContext().getSessionMap().get(USER_SESSION);
            IUserLogoutBAO userLogoutBAO = (IUserLogoutBAO) BeanLoader.getBean("userLogoutBAO");
            IRebalancePortfolioBAO rebalancePortfolioBAO = (IRebalancePortfolioBAO) BeanLoader.getBean("rebalancePortfolioBAO");
            if (userSessionBean != null) {
                if (userSessionBean.getUserType().equalsIgnoreCase(ADMIN_USER)) {
                    redirectPage = "/pages/admin/index";
                    LOGGER.info("Admin logged out status - success");
                } else {
                    //logging out from OMS if already logged in.
                    if (session.getAttribute("OMSOrderVO") != null) {
                        OMSOrderVO omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
                        rebalancePortfolioBAO.omslogout(omsOrderVO);
                    }
                    int userLogout = userLogoutBAO.userLogout(userSessionBean.getUserType(), userSessionBean.getUserId());
                    redirectPage = "/index";
                    LOGGER.log(Level.INFO, "{0} logged out update rows {1}", new Object[]{userSessionBean.getUserType(), userLogout});
                }
            }
            facesContext.getExternalContext().invalidateSession();
            facesContext.getExternalContext().getSessionMap().remove(STORED_VALUES);
            
        }
        
     
        
        return redirectPage + "?faces-redirect=true";
    }
}

